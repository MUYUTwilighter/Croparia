package cool.muyucloud.croparia.api.recipe.entry;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.access.StateHolderAccess;
import cool.muyucloud.croparia.api.core.component.BlockProperties;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.TagUtil;
import cool.muyucloud.croparia.util.codec.AnyCodec;
import cool.muyucloud.croparia.util.codec.CodecUtil;
import cool.muyucloud.croparia.util.supplier.OnLoadSupplier;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.display.DisplayContentsFactory;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class BlockInput implements SlotDisplay {
    public static final Codec<BlockInput> CODEC_SINGLE = Codec.STRING.xmap(BlockInput::create, BlockInput::getTaggable);
    public static final MapCodec<BlockInput> CODEC_COMP = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.optionalFieldOf("id").forGetter(BlockInput::getId),
        TagKey.codec(Registries.BLOCK).optionalFieldOf("tag").forGetter(BlockInput::getTag),
        BlockProperties.CODEC.optionalFieldOf("properties").forGetter(blockInput -> Optional.of(blockInput.getProperties()))
    ).apply(instance, (id, tag, properties) -> create(id.orElse(null), tag.orElse(null), properties.orElse(BlockProperties.EMPTY))));
    public static final AnyCodec<BlockInput> CODEC = AnyCodec.of(CODEC_COMP.codec(), CODEC_SINGLE);
    public static final StreamCodec<RegistryFriendlyByteBuf, BlockInput> STREAM_CODEC = CodecUtil.toStream(CODEC);
    public static final Type<BlockInput> TYPE = new Type<>(CODEC_COMP, STREAM_CODEC);
    public static final ItemStack STACK_UNKNOWN = Items.BEDROCK.getDefaultInstance();
    public static final ItemStack STACK_AIR = Items.BARRIER.getDefaultInstance();
    public static final ItemStack STACK_ANY = Items.LIGHT_GRAY_STAINED_GLASS_PANE.getDefaultInstance();
    public static final Supplier<ItemStack> STACK_PLACEHOLDER = () -> CropariaItems.PLACEHOLDER_BLOCK.get().getDefaultInstance();
    public static final BlockInput UNKNOWN = new BlockInput(CropariaIf.of("unknown"), null, BlockProperties.EMPTY);
    public static final BlockInput ANY = new BlockInput(null, null, BlockProperties.EMPTY);

    static {
        STACK_UNKNOWN.set(DataComponents.CUSTOM_NAME, Texts.translatable("tooltip.croparia.unknown"));
        STACK_AIR.set(DataComponents.CUSTOM_NAME, Texts.translatable("tooltip.croparia.air"));
        STACK_ANY.set(DataComponents.CUSTOM_NAME, Texts.translatable("tooltip.croparia.any"));
    }

    @Nullable
    private final ResourceLocation id;
    @Nullable
    private final TagKey<Block> tag;
    @NotNull
    private final BlockProperties properties;
    private final transient OnLoadSupplier<ImmutableList<ItemStack>> displayStacks;

    public static BlockInput of(@NotNull Block block) {
        return create(Objects.requireNonNull(block.arch$registryName()));
    }

    public static BlockInput of(@NotNull BlockState state) {
        return create(Objects.requireNonNull(state.getBlock().arch$registryName()), null, BlockProperties.create(state));
    }

    public static BlockInput create(String s) {
        if (s.startsWith("#")) {
            s = s.substring(1);
            TagKey<Block> tag = TagKey.create(Registries.BLOCK, ResourceLocation.parse(s));
            return create(null, tag, BlockProperties.EMPTY);
        } else {
            return create(ResourceLocation.parse(s), null, BlockProperties.EMPTY);
        }
    }

    public static BlockInput create(@NotNull ResourceLocation id) {
        return create(id, null, BlockProperties.EMPTY);
    }

    protected static BlockInput create(@Nullable ResourceLocation id, @Nullable TagKey<Block> tag, @NotNull BlockProperties properties) {
        BlockInput blockInput = new BlockInput(id, tag, properties);
        if (blockInput.isAny()) return ANY;
        else return blockInput;
    }

    protected BlockInput(@Nullable ResourceLocation id, @Nullable TagKey<Block> tag, @NotNull BlockProperties properties) {
        this.id = id;
        this.tag = tag;
        if (this.id != null && this.tag != null)
            throw new IllegalArgumentException("id and tag cannot be set at the same time");
        this.properties = properties;
        this.displayStacks = OnLoadSupplier.of(() -> {
            if (this.getId().isPresent()) {
                ItemStack displayStack = BuiltInRegistries.BLOCK.getOptional(this.getId().get()).map(block -> {
                    ItemStack stack = block.asItem().getDefaultInstance();
                    stack.set(BlockProperties.TYPE, this.getProperties());
                    return stack;
                }).orElseThrow(() -> new IllegalArgumentException("Unknown block: %s".formatted(this.getId())));
                return ImmutableList.of(displayStack);
            } else if (this.getTag().isPresent()) {
                LinkedList<ItemStack> stacks = new LinkedList<>();
                for (Holder<Block> holder : TagUtil.forEntries(this.getTag().get())) {
                    ItemStack stack = holder.value().asItem().getDefaultInstance();
                    stack.set(BlockProperties.TYPE, this.getProperties());
                    stacks.add(stack);
                }
                if (stacks.isEmpty()) stacks.add(STACK_UNKNOWN);
                return ImmutableList.copyOf(stacks);
            } else if (this.getProperties().isEmpty()) {
                return ImmutableList.of(STACK_ANY);
            } else {
                ItemStack stack = STACK_PLACEHOLDER.get();
                stack.set(BlockProperties.TYPE, this.getProperties());
                return ImmutableList.of(stack);
            }
        });
    }

    public Optional<ResourceLocation> getId() {
        return Optional.ofNullable(id);
    }

    public ResourceLocation getDisplayId() {
        return this.getTag().map(TagKey::location).orElse(this.getDisplayStacks().getFirst().getItem().arch$registryName());
    }

    public Optional<TagKey<Block>> getTag() {
        return Optional.ofNullable(tag);
    }

    @NotNull
    protected BlockProperties getProperties() {
        return properties;
    }

    @NotNull
    public ImmutableList<ItemStack> getDisplayStacks() {
        return displayStacks.get();
    }

    public String getTaggable() {
        Optional<String> id = this.getId().map(ResourceLocation::toString);
        Optional<String> tag = this.getTag().map(TagKey::location).map(ResourceLocation::toString);
        return id.orElse(tag.orElse(null));
    }

    public boolean isAny() {
        return this.getId().isEmpty() && this.getTag().isEmpty() && this.getProperties().isEmpty();
    }

    public boolean isUnknown() {
        return this.getDisplayStacks().getFirst() == STACK_UNKNOWN;
    }

    public boolean matches(@NotNull Block block) {
        if (this.getId().isPresent()) return Objects.equals(block.arch$registryName(), this.getId().get());
        else if (this.getTag().isPresent()) return TagUtil.isIn(this.getTag().get(), block);
        else return true;
    }

    public boolean matches(@NotNull BlockState state) {
        return this.matches(state.getBlock()) && this.getProperties().isSubsetOf((StateHolderAccess) state);
    }

    @Override
    @NotNull
    public <T> Stream<T> resolve(ContextMap contextMap, DisplayContentsFactory<T> factory) {
        if (factory instanceof DisplayContentsFactory.ForStacks<T> forStacks) {
            return this.getDisplayStacks().stream().map(forStacks::forStack);
        }
        return Stream.empty();
    }

    @Override
    @NotNull
    public Type<? extends SlotDisplay> type() {
        return TYPE;
    }
}
