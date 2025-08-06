package cool.muyucloud.croparia.api.resource.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.resource.TypeToken;
import cool.muyucloud.croparia.api.resource.TypedResource;
import cool.muyucloud.croparia.util.TagUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.component.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@SuppressWarnings("unused")
public class ItemSpec implements DataComponentHolder, TypedResource<Item> {
    public static final MapCodec<ItemSpec> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("id").forGetter(itemSpec -> itemSpec.getResource().arch$registryName()),
        DataComponentPatch.CODEC.optionalFieldOf("nbt").forGetter(itemSpec -> Optional.of(itemSpec.getComponentsPatch())),
        DataComponentPatch.CODEC.optionalFieldOf("components").forGetter(itemSpec -> Optional.of(itemSpec.getComponentsPatch()))
    ).apply(instance, (id, nbt, components) -> new ItemSpec(BuiltInRegistries.ITEM.getValue(id), nbt.orElse(components.orElse(DataComponentPatch.EMPTY)))));
    public static final ItemSpec EMPTY = ItemSpec.of(Items.AIR);
    public static final TypeToken<ItemSpec> TYPE = TypeToken.register(CropariaIf.of("item_spec"), EMPTY, CODEC).orElseThrow();

    @NotNull
    private final Item resource;
    @NotNull
    private final PatchedDataComponentMap components;

    @NotNull
    public static ItemSpec of(@NotNull ItemStack stack) {
        return new ItemSpec(stack.getItem(), stack.getComponentsPatch());
    }

    @NotNull
    public static ItemSpec of(@NotNull Item item) {
        return new ItemSpec(item, DataComponentPatch.EMPTY);
    }

    public ItemSpec(@NotNull Item item) {
        this(item, DataComponentPatch.EMPTY);
    }

    public ItemSpec(@NotNull Item item, @NotNull DataComponentPatch components) {
        this.resource = item;
        this.components = PatchedDataComponentMap.fromPatch(item.components(), components);
    }

    public ItemSpec(@NotNull Item item, @NotNull DataComponentMap components) {
        this.resource = item;
        this.components = new PatchedDataComponentMap(item.components());
        components.forEach(typed -> typed.applyTo(this.components));
    }

    @NotNull
    public ItemSpec copy() {
        return new ItemSpec(this.getResource(), this.getComponentsPatch());
    }

    @NotNull
    public ItemSpec with(@NotNull Item item) {
        return new ItemSpec(item, this.getComponents());
    }

    @NotNull
    public ItemSpec with(@NotNull DataComponentPatch components) {
        PatchedDataComponentMap patched = new PatchedDataComponentMap(this.getComponents());
        patched.applyPatch(components);
        return new ItemSpec(this.getResource(), patched);
    }

    @NotNull
    public ItemSpec with(@NotNull DataComponentMap components) {
        PatchedDataComponentMap patched = new PatchedDataComponentMap(this.getComponents());
        patched.setAll(components);
        return new ItemSpec(this.getResource(), patched);
    }

    @NotNull
    public ItemSpec with(@NotNull TypedDataComponent<?> component) {
        PatchedDataComponentMap patched = new PatchedDataComponentMap(this.getComponents());
        component.applyTo(patched);
        return new ItemSpec(this.getResource(), patched);
    }

    @NotNull
    public <T> ItemSpec with(@NotNull DataComponentType<T> type, @NotNull T value) {
        return this.with(new TypedDataComponent<>(type, value));
    }

    @NotNull
    public ItemSpec replaceComponents(@NotNull DataComponentMap nbt) {
        return new ItemSpec(this.getResource(), nbt);
    }

    @NotNull
    public ItemStack createStack(long amount) {
        return new ItemStack(Holder.direct(this.getResource()), (int) Math.min(amount, Integer.MAX_VALUE), this.getComponentsPatch());
    }

    @NotNull
    public ItemStack createStack() {
        ItemStack stack = this.getResource().getDefaultInstance();
        stack.applyComponents(this.getComponents());
        return stack;
    }

    public boolean is(@NotNull ItemStack stack) {
        return ItemStack.isSameItemSameComponents(stack, this.createStack());
    }

    public boolean is(@NotNull ResourceLocation tag) {
        return TagUtil.isIn(Registries.ITEM, tag, this.getResource());
    }

    @Override
    public TypeToken<ItemSpec> getType() {
        return TYPE;
    }

    @Override
    public MapCodec<ItemSpec> getCodec() {
        return CODEC;
    }

    @Override
    @NotNull
    public Item getResource() {
        return this.resource;
    }

    @Override
    @NotNull
    public DataComponentMap getComponents() {
        return this.components;
    }

    @NotNull
    public DataComponentPatch getComponentsPatch() {
        return this.components.asPatch();
    }
}
