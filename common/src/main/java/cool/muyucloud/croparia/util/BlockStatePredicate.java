package cool.muyucloud.croparia.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.access.StateHolderAccess;
import cool.muyucloud.croparia.registry.CropariaBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class BlockStatePredicate implements Predicate<BlockState> {
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

    public List<Block> availableBlocks() {
        if (this.builder.isBlock()) {
            @Nullable Block block = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.tryParse(this.builder.getBlock())).orElse(null);
            if (block == null) {
                return List.of(CropariaBlocks.PLACEHOLDER.get());
            }
            return List.of(block);
        } else if (this.builder.isTag()) {
            Iterator<Holder<Block>> blocks = BuiltInRegistries.BLOCK.getTagOrEmpty(
                TagKey.create(Registries.BLOCK, ResourceLocation.tryParse(builder.getBlock()))
            ).iterator();
            if (!blocks.hasNext()) {
                return List.of(CropariaBlocks.PLACEHOLDER.get());
            } else {
                List<Block> list = new ArrayList<>();
                while (blocks.hasNext()) {
                    list.add(blocks.next().value());
                }
                return list;
            }
        } else {
            return List.of(CropariaBlocks.PLACEHOLDER.get());
        }
    }

    @Override
    public int hashCode() {
        return hashCode;
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


        private boolean built = false;
        @Nullable
        private String block = null;
        @NotNull
        private final Map<String, String> properties = new HashMap<>();
        private transient boolean tag = false;

        public static Builder create() {
            return new Builder();
        }

        public BlockStatePredicate build() {
            Predicate<BlockState> blockPredicate;
            if (block == null) {
                blockPredicate = b -> true;
            } else if (tag) {
                block = block.substring(1);
                TagKey<Block> tag = TagKey.create(Registries.BLOCK, ResourceLocation.tryParse(block));
                blockPredicate = b -> b.is(tag);
            } else {
                Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(this.block));
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
