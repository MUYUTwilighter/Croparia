package cool.muyucloud.croparia.api.recipe.entry;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.access.StateHolderAccess;
import cool.muyucloud.croparia.api.core.component.BlockProperties;
import cool.muyucloud.croparia.util.CodecUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.display.DisplayContentsFactory;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class BlockOutput implements SlotDisplay {
    public static final MapCodec<BlockOutput> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("id").forGetter(BlockOutput::getId),
        BlockProperties.CODEC.optionalFieldOf("properties").forGetter(blockOutput -> Optional.of(blockOutput.getProperties()))
    ).apply(instance, (id, properties) -> create(id, properties.orElse(BlockProperties.EMPTY))));
    public static final StreamCodec<RegistryFriendlyByteBuf, BlockOutput> STREAM_CODEC = CodecUtil.toStream(CODEC);
    public static final SlotDisplay.Type<BlockOutput> TYPE = new SlotDisplay.Type<>(CODEC, STREAM_CODEC);
    public static final ItemStack STACK_UNKNOWN = Items.BEDROCK.getDefaultInstance();
    public static final ItemStack STACK_AIR = Items.BARRIER.getDefaultInstance();

    static {
        STACK_UNKNOWN.set(DataComponents.CUSTOM_NAME, Component.translatable("tooltip.croparia.unknown"));
        STACK_AIR.set(DataComponents.CUSTOM_NAME, Component.translatable("tooltip.croparia.air"));
    }

    @NotNull
    private final ResourceLocation id;
    @NotNull
    private final BlockProperties properties;
    private final transient ItemStack displayStack;

    public static BlockOutput create(@NotNull ResourceLocation id) {
        return create(id, BlockProperties.EMPTY);
    }

    protected static BlockOutput create(@NotNull ResourceLocation id, @NotNull BlockProperties properties) {
        return new BlockOutput(id, properties);
    }

    protected BlockOutput(@NotNull ResourceLocation id, @NotNull BlockProperties properties) {
        this.id = id;
        this.properties = properties;
        this.displayStack = BuiltInRegistries.BLOCK.getOptional(this.getId()).map(block -> {
            ItemStack stack = block.asItem().getDefaultInstance();
            stack.set(BlockProperties.TYPE, this.getProperties());
            return stack;
        }).orElse(STACK_UNKNOWN);
    }

    public @NotNull ResourceLocation getId() {
        return id;
    }

    @NotNull
    protected BlockProperties getProperties() {
        return properties;
    }

    @NotNull
    public ItemStack getDisplayStack() {
        return displayStack;
    }

    public boolean matches(@NotNull Block block) {
        return Objects.equals(block.arch$registryName(), this.getId());
    }

    public boolean matches(@NotNull BlockState state) {
        return this.matches(state.getBlock()) && this.getProperties().isSubsetOf((StateHolderAccess) state);
    }

    @Override
    @NotNull
    public <T> Stream<T> resolve(ContextMap contextMap, DisplayContentsFactory<T> factory) {
        if (factory instanceof DisplayContentsFactory.ForStacks<T> forStacks) {
            return Stream.of(forStacks.forStack(this.getDisplayStack()));
        }
        return Stream.empty();
    }

    @Override
    @NotNull
    public SlotDisplay.Type<? extends SlotDisplay> type() {
        return TYPE;
    }
}
