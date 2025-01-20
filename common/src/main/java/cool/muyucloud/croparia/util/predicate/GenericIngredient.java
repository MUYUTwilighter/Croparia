package cool.muyucloud.croparia.util.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.TagUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class GenericIngredient implements Predicate<ItemStack> {
    public static final Codec<GenericIngredient> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.optionalFieldOf("id").forGetter(GenericIngredient::getId),
        ResourceLocation.CODEC.optionalFieldOf("tag").forGetter(GenericIngredient::getTag),
        Codec.INT.optionalFieldOf("count").forGetter(ingredient -> Optional.of(ingredient.getCount())),
        CompoundTag.CODEC.optionalFieldOf("nbt").forGetter(GenericIngredient::getNbt)
    ).apply(instance, (id, rawTag, optionalCount, optionalNbt) -> {
        int count = optionalCount.orElse(1);
        CompoundTag nbt = optionalNbt.orElse(null);
        AtomicReference<GenericIngredient> ingredient = new AtomicReference<>();
        id.map(Registry.ITEM::get).map(item -> new GenericIngredient(item, count, nbt)).ifPresentOrElse(
            ingredient::set, () -> rawTag.map(raw -> TagKey.create(Registry.ITEM.key(), raw)).ifPresentOrElse(
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
    private final CompoundTag nbt;

    public GenericIngredient(int count, @Nullable CompoundTag nbt) {
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
        this(stack.getItem(), stack.getCount(), stack.getTag());
    }

    public GenericIngredient(@NotNull Item item, int count, @Nullable CompoundTag tag) {
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

    public GenericIngredient(@NotNull TagKey<Item> tag, int count, @Nullable CompoundTag nbt) {
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

    public Optional<CompoundTag> getNbt() {
        return Optional.ofNullable(this.nbt);
    }

    public Optional<Component> nbtTooltip() {
        if (this.nbt == null) {
            return Optional.empty();
        }
        return Optional.of(new TextComponent(this.nbt.toString()));
    }

    public @NotNull List<ItemStack> availableStacks() {
        List<ItemStack> stacks = new LinkedList<>();
        if (this.item != null) {
            ItemStack stack = new ItemStack(this.item, this.count);
            stack.setTag(this.nbt);
            stacks.add(stack);
        } else if (this.tag != null) {
            for (Holder<Item> holder : TagUtil.forItems(this.tag)) {
                ItemStack stack = new ItemStack(holder.value(), this.count);
                stack.setTag(this.nbt);
                stacks.add(stack);
            }
        } else {
            ItemStack stack = new ItemStack(CropariaItems.PLACEHOLDER.get(), this.count);
            stack.setTag(this.nbt);
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
        return matchNbt(this.nbt, itemStack.getTag());
    }

    private static boolean matchNbt(@Nullable Tag primary, @Nullable Tag secondary) {
        if (primary == null) {
            return true;
        } else if (secondary == null) {
            return false;
        }
        if (primary instanceof CompoundTag primaryCompound) {
            if (secondary instanceof CompoundTag secondaryCompound) {
                for (String key : primaryCompound.getAllKeys()) {
                    Tag primaryTag = primaryCompound.get(key);
                    Tag secondaryTag = secondaryCompound.get(key);
                    if (!matchNbt(primaryTag, secondaryTag)) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else if (primary instanceof CollectionTag<?> primaryCollection) {
            if (secondary instanceof CollectionTag<?> secondaryCollection) {
                for (int i = 0; i < primaryCollection.size(); i++) {
                    Tag primaryTag = primaryCollection.get(i);
                    Tag secondaryTag = secondaryCollection.get(i);
                    if (!matchNbt(primaryTag, secondaryTag)) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return primary.equals(secondary);
        }
    }

}
