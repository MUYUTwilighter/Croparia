package cool.muyucloud.croparia.api.resource.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.resource.ResourceType;
import cool.muyucloud.croparia.api.resource.TypeToken;
import cool.muyucloud.croparia.util.TagUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class ItemSpec implements ResourceType {
    public static final MapCodec<ItemSpec> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("id").forGetter(item -> item.getItem().arch$registryName()),
        DataComponentPatch.CODEC.fieldOf("nbt").forGetter(ItemSpec::getNbt)
    ).apply(instance, (id, nbt) -> new ItemSpec(BuiltInRegistries.ITEM.get(id), nbt)));
    public static final ItemSpec EMPTY = ItemSpec.from(Items.AIR);
    public static final TypeToken<ItemSpec> TYPE = TypeToken.register(CropariaIf.of("item_spec"), EMPTY, CODEC).orElseThrow();

    public static ItemSpec from(ItemStack stack) {
        return new ItemSpec(stack.getItem(), stack.getComponentsPatch());
    }

    public static ItemSpec from(Item item) {
        return new ItemSpec(item, DataComponentPatch.EMPTY);
    }

    @NotNull
    private final Item item;
    @NotNull
    private final DataComponentPatch nbt;

    public ItemSpec(@NotNull Item item, @NotNull DataComponentPatch nbt) {
        this.item = item;
        this.nbt = nbt;
    }

    public @NotNull Item getItem() {
        return item;
    }

    public @NotNull DataComponentPatch getNbt() {
        return nbt;
    }

    public ItemSpec withItem(Item item) {
        return new ItemSpec(item, nbt);
    }

    public ItemSpec replaceNbt(@NotNull DataComponentPatch nbt) {
        return new ItemSpec(item, nbt);
    }

    public boolean isEmpty() {
        return this.getItem() == Items.AIR;
    }

    public ItemStack toStack(long amount) {
        return new ItemStack(Holder.direct(this.getItem()), (int) amount, this.getNbt());
    }

    public ItemStack toStack() {
        ItemStack stack = this.getItem().getDefaultInstance();
        stack.applyComponents(this.getNbt());
        return stack;
    }

    public boolean is(Item item) {
        return this.getItem() == item;
    }

    public boolean is(ItemStack stack) {
        return ItemStack.isSameItemSameComponents(stack, this.toStack());
    }

    public boolean isOf(ResourceLocation tag) {
        return TagUtil.isIn(TagKey.create(BuiltInRegistries.ITEM.key(), tag), this.getItem());
    }

    public boolean matches(ItemStack stack) {
        return ItemStack.isSameItemSameComponents(stack, this.toStack());
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
