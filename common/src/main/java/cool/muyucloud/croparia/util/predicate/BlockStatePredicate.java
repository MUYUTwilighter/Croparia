package cool.muyucloud.croparia.util.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.access.StateHolderAccess;
import cool.muyucloud.croparia.registry.CropariaItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class BlockStatePredicate implements Predicate<BlockState> {
    public static final ItemStack STACK_UNKNOWN = Items.BEDROCK.getDefaultInstance();
    public static final ItemStack STACK_AIR = Items.BARRIER.getDefaultInstance();
    public static final ItemStack STACK_ANY = Items.LIGHT_GRAY_STAINED_GLASS_PANE.getDefaultInstance();

    static {
        STACK_UNKNOWN.set(DataComponents.CUSTOM_NAME, Component.translatable("tooltip.croparia.unknown"));
        STACK_AIR.set(DataComponents.CUSTOM_NAME, Component.translatable("tooltip.croparia.air"));
        STACK_ANY.set(DataComponents.CUSTOM_NAME, Component.translatable("tooltip.croparia.any"));
    }

    public static final BlockStatePredicate ANY = new BlockStatePredicate(b -> true, b -> true, 0, Builder.create());
    public static final BlockStatePredicate AIR = new BlockStatePredicate(BlockBehaviour.BlockStateBase::isAir, b -> true, 0, Builder.create());

    @NotNull
    private transient final Predicate<BlockState> block;
    @NotNull
    private transient final Predicate<BlockState> properties;
    private transient final int hashCode;
    private final Builder builder;

    public BlockStatePredicate(
        @NotNull Predicate<BlockState> block, @NotNull Predicate<BlockState> properties, int hashCode, Builder builder
    ) {
        this.block = block;
        this.properties = properties;
        this.hashCode = hashCode;
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

    @Override
    public boolean test(BlockState state) {
        return this.block.test(state) && this.properties.test(state);
    }

    public static Builder builder() {
        return Builder.create();
    }

    public boolean isSpecified() {
        return builder.isTag();
    }

    public Collection<ItemStack> availableBlockItems() {
        if (this.builder.isBlock()) {
            return Collections.singleton(getBlockItem(ResourceLocation.tryParse(Objects.requireNonNull(this.builder.getBlock()))));
        } else if (this.builder.isTag()) {
            Iterator<Holder<Block>> blocks = BuiltInRegistries.BLOCK.getTagOrEmpty(
                TagKey.create(Registries.BLOCK, ResourceLocation.tryParse(Objects.requireNonNull(builder.getBlock()).substring(1)))
            ).iterator();
            if (!blocks.hasNext()) {
                return builder.properties.isEmpty() ? Collections.singleton(STACK_UNKNOWN)
                    : Collections.singleton(CropariaItems.PLACEHOLDER_BLOCK.get().getDefaultInstance());
            } else {
                List<ItemStack> list = new ArrayList<>();
                blocks.forEachRemaining(
                    holder -> {
                        ItemStack item = holder.value().asItem().getDefaultInstance();
                        item.set(DataComponents.CUSTOM_NAME, Component.literal(builder.getBlock()));
                        list.add(item);
                    }
                );
                return list;
            }
        } else {
            return builder.properties.isEmpty() ? Collections.singleton(STACK_UNKNOWN)
                : Collections.singleton(CropariaItems.PLACEHOLDER_BLOCK.get().getDefaultInstance());
        }
    }

    public static ItemStack getBlockItem(ResourceLocation id) {
        Block block = BuiltInRegistries.BLOCK.getOptional(id).orElse(null);
        if (block != null) {
            return block.asItem().getDefaultInstance();
        } else {
            return STACK_UNKNOWN;
        }
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    public List<Component> tooltip() {
        if (this.builder.properties.isEmpty()) {
            return List.of();
        }
        List<Component> components = new LinkedList<>();
        this.builder.properties.forEach((property, value) -> components.add(Component.literal("%s=%s".formatted(property, value))));
        return components;
    }

    public static Builder ofState(BlockState state) {
        Builder builder = Builder.create();
        builder.block(Objects.requireNonNull(state.getBlock().arch$registryName()).toString());
        builder.properties(((StateHolderAccess) state).croparia_if$getProperties());
        return builder;
    }

    public static class Builder {
        public static final Codec<Builder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("block").forGetter(builder -> Optional.ofNullable(builder.getBlock())),
            Codec.unboundedMap(Codec.STRING, Codec.STRING).optionalFieldOf("properties")
                .forGetter(builder -> builder.properties.isEmpty() ? Optional.empty() : Optional.of(builder.properties))
        ).apply(instance, (block, properties) -> {
            Builder builder = create();
            block.ifPresent(builder::block);
            properties.ifPresent(builder::properties);
            return builder;
        }));

        @Nullable
        private String block = null;
        @NotNull
        private final Map<String, String> properties = new HashMap<>();
        private transient boolean tag = false;
        private transient boolean built = false;

        public static Builder create() {
            return new Builder();
        }

        public BlockStatePredicate build() {
            Predicate<BlockState> blockPredicate;
            if (block == null) {
                blockPredicate = b -> true;
            } else if (block.startsWith("#")) {
                TagKey<Block> tag = TagKey.create(Registries.BLOCK, ResourceLocation.tryParse(block.substring(1)));
                blockPredicate = b -> b.is(tag);
            } else {
                Block block = BuiltInRegistries.BLOCK.getValue(ResourceLocation.tryParse(this.block));
                if (block == Blocks.AIR) {
                    throw new IllegalArgumentException("Invalid block: " + this.block);
                }
                blockPredicate = b -> b.is(block);
            }
            Predicate<BlockState> propertiesPredicate = this.properties.isEmpty() ? b -> true : b -> {
                for (Map.Entry<String, String> entry : this.properties.entrySet()) {
                    @NotNull String key = entry.getKey();
                    @Nullable String value = entry.getValue();
                    StateHolderAccess state = (StateHolderAccess) b;
                    @Nullable String blockVal = state.croparia_if$getValue(key);
                    if (value == null && blockVal != null) {
                        continue;
                    }
                    if (!Objects.equals(blockVal, value)) {
                        return false;
                    }
                }
                return true;
            };
            built = true;
            return new BlockStatePredicate(
                blockPredicate, propertiesPredicate, Objects.hash(block, properties), this
            );
        }

        public @Nullable String getBlock() {
            return block;
        }

        protected Builder properties(@NotNull Map<String, String> properties) {
            if (built) {
                throw new IllegalStateException("Builder already built");
            }
            properties.forEach(this::property);
            return this;
        }

        public Builder block(@Nullable String block) {
            if (built) {
                throw new IllegalStateException("Builder already built");
            }
            this.block = block;
            this.tag = block != null && block.startsWith("#");
            return this;
        }

        public Builder property(@NotNull String property, @Nullable Object value) {
            if (built) {
                throw new IllegalStateException("Builder already built");
            }
            this.properties.put(property, value == null ? null : value.toString());
            return this;
        }

        public boolean isBlock() {
            return block != null && !tag;
        }

        public boolean isTag() {
            return tag;
        }
    }
}
