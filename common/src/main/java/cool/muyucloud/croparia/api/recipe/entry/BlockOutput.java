package cool.muyucloud.croparia.api.recipe.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.access.StateHolderAccess;
import cool.muyucloud.croparia.api.core.component.BlockProperties;
import cool.muyucloud.croparia.api.recipe.DisplayableRecipe;
import cool.muyucloud.croparia.util.codec.AnyCodec;
import cool.muyucloud.croparia.util.codec.CodecUtil;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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
    public static final Codec<BlockOutput> CODEC_SINGLE = ResourceLocation.CODEC.xmap(
        BlockOutput::create, BlockOutput::getId
    );
    public static final MapCodec<BlockOutput> CODEC_COMP = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("id").forGetter(BlockOutput::getId),
        BlockProperties.CODEC.optionalFieldOf("properties").forGetter(blockOutput -> Optional.of(blockOutput.getProperties()))
    ).apply(instance, (id, properties) -> create(id, properties.orElse(BlockProperties.EMPTY))));
    public static final AnyCodec<BlockOutput> CODEC = AnyCodec.of(CODEC_COMP.codec(), CODEC_SINGLE);
    public static final StreamCodec<RegistryFriendlyByteBuf, BlockOutput> STREAM_CODEC = CodecUtil.toStream(CODEC);
    public static final SlotDisplay.Type<BlockOutput> TYPE = new SlotDisplay.Type<>(CODEC_COMP, STREAM_CODEC);
    public static final ItemStack STACK_UNKNOWN = Items.BEDROCK.getDefaultInstance();
    public static final ItemStack STACK_AIR = Items.BARRIER.getDefaultInstance();

    static {
        STACK_AIR.set(DataComponents.CUSTOM_NAME, Texts.translatable("tooltip.croparia.air"));
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
        }).orElseThrow(() -> new IllegalArgumentException("Unknown block: " + id));
    }

    public @NotNull ResourceLocation getId() {
        return id;
    }

    public Block getBlock() {
        return BuiltInRegistries.BLOCK.getOptional(this.getId()).orElse(null);
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

    public void setBlock(ServerLevel level, BlockPos pos) {
        try {
            BlockState state = this.getBlock().defaultBlockState();
            StateHolderAccess access = (StateHolderAccess) state;
            this.getProperties().forEach(entry -> access.cif$setValue(entry.getKey(), entry.getValue()));
            level.setBlock(pos, state, 3);
        } catch (Throwable t) {
            DisplayableRecipe.LOGGER.error("Failed to set block", t);
        }
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
