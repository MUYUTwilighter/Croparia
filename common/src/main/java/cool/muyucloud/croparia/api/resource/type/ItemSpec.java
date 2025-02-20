package cool.muyucloud.croparia.api.resource.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.resource.ResourceType;
import cool.muyucloud.croparia.api.resource.TypeToken;
import cool.muyucloud.croparia.util.TagUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("unused")
public class ItemSpec implements ResourceType {
    public static final MapCodec<ItemSpec> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("id").forGetter(item -> item.getItem().arch$registryName()),
        CompoundTag.CODEC.optionalFieldOf("nbt").forGetter(ItemSpec::getNbt)
    ).apply(instance, (id, nbt) -> new ItemSpec(BuiltInRegistries.ITEM.get(id), nbt.orElse(null))));
    public static final ItemSpec EMPTY = new ItemSpec(Items.AIR, null);
    public static final TypeToken<ItemSpec> TYPE = TypeToken.register(CropariaIf.of("item_spec"), EMPTY, CODEC).orElseThrow();

    public static ItemSpec from(ItemStack stack) {
        return new ItemSpec(stack.getItem(), stack.getTag());
    }

    public static ItemSpec from(Item item) {
        return new ItemSpec(item, null);
    }

    private final Item item;
    private final CompoundTag nbt;

    public ItemSpec(@NotNull Item item, @Nullable CompoundTag nbt) {
        this.item = item;
        this.nbt = nbt;
    }

    public Item getItem() {
        return item;
    }

    public Optional<CompoundTag> getNbt() {
        return Optional.ofNullable(nbt);
    }

    public ItemSpec withItem(Item item) {
        return new ItemSpec(item, nbt);
    }

    public ItemSpec replaceNbt(@NotNull CompoundTag nbt) {
        return new ItemSpec(item, nbt);
    }

    public boolean isEmpty() {
        return this.getItem() == Items.AIR;
    }

    public ItemStack toStack(long amount) {
        ItemStack stack = new ItemStack(this.getItem());
        stack.setTag(this.getNbt().orElse(null));
        stack.setCount((int) Math.min(Integer.MAX_VALUE, amount));
        return stack;
    }

    public ItemStack toStack() {
        ItemStack stack = this.getItem().getDefaultInstance();
        stack.setTag(this.getNbt().orElse(null));
        return stack;
    }

    public boolean is(Item item) {
        return this.getItem() == item;
    }

    public boolean isOf(ResourceLocation tag) {
        return TagUtil.isIn(TagKey.create(BuiltInRegistries.ITEM.key(), tag), this.getItem());
    }

    public boolean matches(ItemStack stack) {
        return ItemStack.isSameItemSameTags(stack, this.toStack());
    }

    @Override
    public TypeToken<ItemSpec> getType() {
        return TYPE;
    }

    @Override
    public MapCodec<?> getCodec() {
        return CODEC;
    }
}
