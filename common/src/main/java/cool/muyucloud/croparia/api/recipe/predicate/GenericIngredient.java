package cool.muyucloud.croparia.api.recipe.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.TagUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class GenericIngredient implements Predicate<ItemStack> {
    public static final Codec<GenericIngredient> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.optionalFieldOf("id").forGetter(GenericIngredient::getId),
        ResourceLocation.CODEC.optionalFieldOf("tag").forGetter(GenericIngredient::getTag),
        Codec.INT.optionalFieldOf("count").forGetter(ingredient -> Optional.of(ingredient.getCount())),
        DataComponentMap.CODEC.optionalFieldOf("nbt").forGetter(GenericIngredient::getNbt)
    ).apply(instance, (id, rawTag, optionalCount, optionalNbt) -> {
        int count = optionalCount.orElse(1);
        DataComponentMap nbt = optionalNbt.orElse(null);
        AtomicReference<GenericIngredient> ingredient = new AtomicReference<>();
        id.map(BuiltInRegistries.ITEM::getValue).map(item -> new GenericIngredient(item, count, nbt)).ifPresentOrElse(
            ingredient::set, () -> rawTag.map(raw -> TagKey.create(Registries.ITEM, raw)).ifPresentOrElse(
                tag -> ingredient.set(new GenericIngredient(tag, count, nbt)),
                () -> ingredient.set(new GenericIngredient(count, nbt))
            ));
        return ingredient.get();
    }));

    @Nullable
    private final Item item;
    @Nullable
    private final TagKey<Item> tag;
    private final int count;
    @Nullable
    private final DataComponentMap nbt;

    public GenericIngredient(int count, @Nullable DataComponentMap nbt) {
        if (count <= 0) {
            throw new IllegalArgumentException("Invalid count: " + count);
        }
        this.item = null;
        this.tag = null;
        this.count = count;
        this.nbt = nbt;
    }

    public GenericIngredient(@NotNull TagKey<Item> tag) {
        this.item = null;
        this.tag = tag;
        this.count = 1;
        this.nbt = null;
    }

    public GenericIngredient(@NotNull Item item) {
        if (item == Items.AIR) {
            item = null;
        }
        this.item = item;
        this.tag = null;
        this.count = 1;
        this.nbt = null;
    }

    public GenericIngredient(@NotNull TagKey<Item> tag, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Invalid count: " + count);
        }
        this.item = null;
        this.tag = tag;
        this.count = count;
        this.nbt = null;
    }

    public GenericIngredient(@NotNull Item item, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Invalid count: " + count);
        }
        if (item == Items.AIR) {
            item = null;
        }
        this.item = item;
        this.tag = null;
        this.count = count;
        this.nbt = null;
    }

    public GenericIngredient(@NotNull ItemStack stack) {
        this(stack.getItem(), stack.getCount(), stack.getComponents());
    }

    public GenericIngredient(@NotNull Item item, int count, @Nullable DataComponentMap tag) {
        if (count <= 0) {
            throw new IllegalArgumentException("Invalid count: " + count);
        }
        if (item == Items.AIR) {
            item = null;
        }
        this.item = item;
        this.tag = null;
        this.count = count;
        this.nbt = tag;
    }

    public GenericIngredient(@NotNull TagKey<Item> tag, int count, @Nullable DataComponentMap nbt) {
        if (count <= 0) {
            throw new IllegalArgumentException("Invalid count: " + count);
        }
        this.item = null;
        this.tag = tag;
        this.count = count;
        this.nbt = nbt;
    }

    public Optional<ResourceLocation> getId() {
        return this.item != null ? Optional.ofNullable(this.item.arch$registryName()) : Optional.empty();
    }

    public Optional<ResourceLocation> getTag() {
        return this.tag != null ? Optional.of(this.tag.location()) : Optional.empty();
    }

    public int getCount() {
        return this.count;
    }

    public Optional<DataComponentMap> getNbt() {
        return Optional.ofNullable(this.nbt);
    }

    public Optional<Component> nbtTooltip() {
        if (this.nbt == null) {
            return Optional.empty();
        }
        return Optional.of(Component.literal(DataComponentMap.CODEC.encodeStart(NbtOps.INSTANCE, this.nbt).getOrThrow().getAsString()));
    }

    public @NotNull List<ItemStack> availableStacks() {
        List<ItemStack> stacks = new LinkedList<>();
        if (this.item != null) {
            ItemStack stack = new ItemStack(this.item, this.count);
            stacks.add(stack);
        } else if (this.tag != null) {
            for (Holder<Item> holder : TagUtil.forEntries(this.tag)) {
                ItemStack stack = new ItemStack(holder.value(), this.count);
                stacks.add(stack);
            }
        } else {
            ItemStack stack = new ItemStack(CropariaItems.PLACEHOLDER.get(), this.count);
            stacks.add(stack);
        }
        return stacks;
    }

    @Override
    public boolean test(ItemStack itemStack) {
        if (this.count > itemStack.getCount()) {
            return false;
        }
        if (this.item != null && itemStack.getItem() != this.item) {
            return false;
        }
        if (this.tag != null && !itemStack.is(this.tag)) {
            return false;
        }
        return matchNbt(this.nbt, itemStack.getComponents());
    }

    private static boolean matchNbt(@Nullable DataComponentMap primary, @Nullable DataComponentMap secondary) {
        if (primary == null) {
            return true;
        } else if (secondary == null) {
            return false;
        }
        for (TypedDataComponent<?> component : primary) {
            if (!secondary.has(component.type())) {
                return false;
            }
            if (!Objects.equals(component.value(), secondary.get(component.type()))) {
                return false;
            }
        }
        return true;
    }
}
