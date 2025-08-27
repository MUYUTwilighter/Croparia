package cool.muyucloud.croparia.api.recipe.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import cool.muyucloud.croparia.util.codec.AnyCodec;
import cool.muyucloud.croparia.util.codec.CodecUtil;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.DisplayContentsFactory;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class ItemOutput implements SlotDisplay {
    public static final Codec<ItemOutput> CODEC_SINGLE = ResourceLocation.CODEC.xmap(
        id -> new ItemOutput(id, 1), ItemOutput::getId
    );
    public static final MapCodec<ItemOutput> CODEC_COMP = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("id").forGetter(ItemOutput::getId),
        DataComponentPatch.CODEC.optionalFieldOf("components").forGetter(itemOutput -> Optional.of(itemOutput.getComponentsPatch())),
        Codec.LONG.optionalFieldOf("amount").forGetter(result -> Optional.of(result.getAmount()))
    ).apply(instance, (id, components, amount) -> new ItemOutput(id, components.orElse(DataComponentPatch.EMPTY), amount.orElse(1L))));
    public static final AnyCodec<ItemOutput> CODEC = new AnyCodec<>(CODEC_COMP.codec(), CODEC_SINGLE);
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemOutput> STREAM_CODEC = CodecUtil.toStream(CODEC);
    public static final Type<ItemOutput> TYPE = new Type<>(CODEC_COMP, STREAM_CODEC);

    @NotNull
    private final ResourceLocation id;
    @NotNull
    private final DataComponentPatch components;
    private final long amount;
    @NotNull
    private final transient ItemSpec itemSpec;
    @NotNull
    private final transient ItemStack displayStack;

    public ItemOutput(@NotNull ItemStack stack) {
        this(Objects.requireNonNull(stack.getItem().arch$registryName()), stack.getComponentsPatch(), stack.getCount());
    }

    public ItemOutput(@NotNull ResourceLocation id, int amount) {
        this(id, DataComponentPatch.EMPTY, 1L);
    }

    public ItemOutput(@NotNull ResourceLocation id, @NotNull DataComponentPatch components, long amount) {
        this.id = id;
        this.components = components;
        this.amount = amount;
        if (this.amount <= 0) throw new IllegalArgumentException("amount must be greater than 0");
        this.itemSpec = new ItemSpec(BuiltInRegistries.ITEM.getValue(id), components);
        if (this.itemSpec.isEmpty()) throw new IllegalArgumentException("Unknown or invalid item: " + id);
        this.displayStack = this.toSpec().createStack(this.getAmount());
    }

    public @NotNull ItemStack getDisplayStack() {
        return this.displayStack;
    }

    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @NotNull
    public DataComponentPatch getComponentsPatch() {
        return this.components;
    }

    public long getAmount() {
        return amount;
    }

    public @NotNull ItemSpec toSpec() {
        return itemSpec;
    }

    public ItemStack createStack() {
        return this.toSpec().createStack(getAmount());
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
    public Type<? extends SlotDisplay> type() {
        return TYPE;
    }
}
