package cool.muyucloud.croparia.api.recipe.entry;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.TagUtil;
import cool.muyucloud.croparia.util.Util;
import cool.muyucloud.croparia.util.codec.AnyCodec;
import cool.muyucloud.croparia.util.codec.CodecUtil;
import cool.muyucloud.croparia.util.supplier.OnLoadSupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.DisplayContentsFactory;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class ItemInput implements SlotDisplay {
    public static final Codec<ItemInput> CODEC_SINGLE = Codec.STRING.xmap(
        s -> new ItemInput(s, 1),
        input -> input.getId().map(String::valueOf).orElse("#" + input.getTag().orElseThrow().location())
    );
    public static final MapCodec<ItemInput> CODEC_COMP = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.optionalFieldOf("id").forGetter(ItemInput::getId),
        TagKey.codec(Registries.ITEM).optionalFieldOf("tag").forGetter(ItemInput::getTag),
        DataComponentPredicate.CODEC.optionalFieldOf("components").forGetter(itemInput -> Optional.of(itemInput.getComponentsPredicate())),
        Codec.LONG.optionalFieldOf("amount").forGetter(entry -> Optional.of(entry.getAmount()))).apply(
        instance, (id, tag, components, amount) -> new ItemInput(id.orElse(null), tag.orElse(null),
            components.orElse(DataComponentPredicate.EMPTY), amount.orElse(1L))
    ));
    public static final AnyCodec<ItemInput> CODEC = new AnyCodec<>(CODEC_COMP.codec(), CODEC_SINGLE);
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemInput> STREAM_CODEC = CodecUtil.toStream(CODEC);
    public static final Type<ItemInput> TYPE = new Type<>(CODEC_COMP, STREAM_CODEC);

    public static ItemInput of(final ItemStack stack) {
        DataComponentPredicate.Builder builder = DataComponentPredicate.builder();
        stack.getComponentsPatch().entrySet().forEach(entry -> builder.expect(TypedDataComponent.createUnchecked(entry.getKey(), entry.getValue())));
        return new ItemInput(stack.getItem().arch$registryName(), null, builder.build(), stack.getCount());
    }

    @Nullable
    private final ResourceLocation id;
    @Nullable
    private final TagKey<Item> tag;
    @NotNull
    private final DataComponentPredicate componentPredicate;
    private final long amount;
    private final transient OnLoadSupplier<ImmutableList<ItemStack>> displayStacks;

    public ItemInput(String s, int amount) {
        this(s.startsWith("#") ? null : ResourceLocation.parse(s),
            s.startsWith("#") ? TagKey.create(Registries.ITEM, ResourceLocation.parse(s.substring(1))) : null,
            DataComponentPredicate.EMPTY, amount);
    }

    public ItemInput(@NotNull ResourceLocation id, int amount) {
        this(id, null, DataComponentPredicate.EMPTY, amount);
    }

    public ItemInput(@NotNull ItemStack stack) {
        this(stack.getItem().arch$registryName(), null, Util.extractPredicate(stack.getComponentsPatch()), stack.getCount());
    }

    public ItemInput(@Nullable ResourceLocation id, @Nullable TagKey<Item> tag, @NotNull DataComponentPredicate componentPredicate, long amount) {
        this.id = id;
        this.tag = tag;
        if (this.id != null && this.tag != null)
            throw new IllegalArgumentException("id and tag cannot be set at the same time");
        this.componentPredicate = componentPredicate;
        this.amount = amount;
        if (this.amount <= 0) throw new IllegalArgumentException("amount must be greater than 0");
        this.displayStacks = OnLoadSupplier.of(() -> {
            if (this.getId().isPresent()) {
                ItemStack stack = new ItemStack(Holder.direct(BuiltInRegistries.ITEM.getValue(this.getId().get())),
                    (int) Math.min(this.getAmount(), Integer.MAX_VALUE), this.getComponentsPredicate().asPatch());
                return ImmutableList.of(stack);
            } else if (this.getTag().isPresent()) {
                LinkedList<ItemStack> stacks = new LinkedList<>();
                TagUtil.forEntries(this.getTag().get()).forEach(entry -> stacks.addLast(new ItemStack(entry, (int) Math.min(this.getAmount(), Integer.MAX_VALUE), this.getComponentsPredicate().asPatch())));
                return ImmutableList.copyOf(stacks);
            } else {
                return ImmutableList.of(new ItemStack(Holder.direct(CropariaItems.PLACEHOLDER.get()), (int) Math.min(this.getAmount(), Integer.MAX_VALUE), this.getComponentsPredicate().asPatch()));
            }
        });
    }

    public Optional<ResourceLocation> getId() {
        return Optional.ofNullable(id);
    }

    public ResourceLocation getDisplayId() {
        return this.getTag().map(TagKey::location).orElse(this.getDisplayStacks().getFirst().getItem().arch$registryName());
    }

    public Optional<TagKey<Item>> getTag() {
        return Optional.ofNullable(tag);
    }

    @NotNull
    public DataComponentPredicate getComponentsPredicate() {
        return this.componentPredicate;
    }

    public long getAmount() {
        return amount;
    }

    public ImmutableList<ItemStack> getDisplayStacks() {
        return this.displayStacks.get();
    }

    public boolean matches(@NotNull Item item) {
        if (this.getId().isPresent()) {
            return this.getId().get().equals(item.arch$registryName());
        } else if (this.getTag().isPresent()) {
            return TagUtil.isIn(this.getTag().get(), item);
        }
        return true;
    }

    public boolean matches(@NotNull DataComponentMap components) {
        return this.getComponentsPredicate().test(components);
    }

    /**
     * Whether the specified item matches the input, considering only the type of the item & components
     *
     * @param item The item to match
     * @return {@code true} if the specified and components item matches
     */
    public boolean matches(@NotNull ItemSpec item) {
        return this.matches(item.getResource()) && this.matches(item.getComponents());
    }

    /**
     * Whether the specified item stack matches the input, considering the type of the item, components and amount
     */
    public boolean matches(@NotNull ItemStack stack) {
        return this.matches(stack.getItem()) && this.matches(stack.getComponents()) && this.getAmount() <= stack.getCount();
    }

    public boolean matches(@NotNull ItemSpec item, long amount) {
        return this.matches(item) && this.getAmount() <= amount;
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
