package cool.muyucloud.croparia.api.core.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.api.core.recipe.container.RitualContainer;
import cool.muyucloud.croparia.api.recipe.DisplayableRecipe;
import cool.muyucloud.croparia.api.recipe.TypedSerializer;
import cool.muyucloud.croparia.api.recipe.entry.BlockInput;
import cool.muyucloud.croparia.api.recipe.entry.ItemInput;
import cool.muyucloud.croparia.api.recipe.entry.ItemOutput;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.supplier.LazySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class RitualRecipe implements DisplayableRecipe<RitualContainer> {
    public static final TypedSerializer<RitualRecipe> TYPED_SERIALIZER = new TypedSerializer<>(RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.INT.fieldOf("tier").orElse(1).forGetter(RitualRecipe::getTier),
        BlockInput.CODEC.fieldOf("block").forGetter(RitualRecipe::getBlock),
        ItemInput.CODEC.fieldOf("ingredient").forGetter(RitualRecipe::getIngredient),
        ItemOutput.CODEC.fieldOf("result").forGetter(RitualRecipe::getResult)
    ).apply(instance, RitualRecipe::new)));
    public static final TypedSerializer<RitualRecipe> OLD_TYPED_SERIALIZER = new TypedSerializer<>(
        RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("tier").forGetter(RitualRecipe::getTier),
            ResourceLocation.CODEC.fieldOf("block").forGetter(recipe -> recipe.getBlock().getDisplayId()),
            ResourceLocation.CODEC.fieldOf("input").forGetter(recipe -> recipe.getIngredient().getDisplayId()),
            ResourceLocation.CODEC.fieldOf("output").forGetter(recipe -> recipe.getResult().getId()),
            Codec.INT.fieldOf("count").forGetter(recipe -> Math.toIntExact(recipe.getResult().getAmount()))
        ).apply(instance, (tier, block, input, output, count) -> new RitualRecipe(tier, BlockInput.create(block), new ItemInput(input, 1), new ItemOutput(output, count)))),
        StreamCodec.of((buf, recipe) -> {
            buf.writeInt(recipe.getTier());
            ItemStack stack = recipe.getIngredient().getDisplayStacks().getFirst();
            buf.writeJsonWithCodec(ItemStack.CODEC, stack);
            buf.writeJsonWithCodec(ItemStack.CODEC, recipe.getBlock().getDisplayStacks().getFirst());
            buf.writeInt(Math.toIntExact(recipe.getResult().getAmount()));
        }, buf -> {
            int tier = buf.readInt();
            ItemStack stack = buf.readJsonWithCodec(ItemStack.CODEC);
            ResourceLocation block = buf.readJsonWithCodec(ItemStack.CODEC).getItem().arch$registryName();
            int count = buf.readInt();
            stack.setCount(count);
            return new RitualRecipe(tier, BlockInput.create(Objects.requireNonNull(block)), new ItemInput(stack), new ItemOutput(stack));
        })
    );
    public static final LazySupplier<List<SlotDisplay.ItemSlotDisplay>> STATIONS = LazySupplier.of(() -> List.of(
        new SlotDisplay.ItemSlotDisplay(CropariaItems.RITUAL_STAND.get()),
        new SlotDisplay.ItemSlotDisplay(CropariaItems.RITUAL_STAND_2.get()),
        new SlotDisplay.ItemSlotDisplay(CropariaItems.RITUAL_STAND_3.get())
    ));

    private final int tier;
    @NotNull
    private final BlockInput block;
    @NotNull
    private final ItemInput ingredient;
    @NotNull
    private final ItemOutput result;

    public RitualRecipe(
        int tier, @NotNull BlockInput state, @NotNull ItemInput ingredient, @NotNull ItemOutput result
    ) {
        if (tier < 1) {
            throw new IllegalArgumentException("Tier must be at least 1");
        }
        this.tier = tier;
        this.block = state;
        this.ingredient = ingredient;
        this.result = result;
    }

    public @NotNull ItemInput getIngredient() {
        return ingredient;
    }

    public @NotNull ItemOutput getResult() {
        return result;
    }

    public @NotNull BlockInput getBlock() {
        return block;
    }

    public int getTier() {
        return tier;
    }

    public ItemStack assemble(RitualContainer recipeInput) {
        if (matches(recipeInput)) {
            recipeInput.item().shrink(Math.toIntExact(this.getIngredient().getAmount()));
            return this.getResult().createStack();
        }
        return ItemStack.EMPTY;
    }

    public boolean matches(RitualContainer container) {
        int tier = container.tier();
        ItemStack input = container.item();
        BlockState state = container.state();
        return this.getIngredient().matches(input)
            && this.getBlock().matches(state)
            && tier >= this.getTier();
    }

    @Override
    public boolean matches(RitualContainer container, Level level) {
        return matches(container);
    }

    @Override
    public @NotNull ItemStack assemble(RitualContainer recipeInput, HolderLookup.Provider provider) {
        return assemble(recipeInput);
    }

    @Override
    @NotNull
    public ItemOutput result() {
        return this.getResult();
    }

    @Override
    @NotNull
    public SlotDisplay.ItemSlotDisplay craftingStation() {
        return STATIONS.get().get(this.getTier() - 1);
    }

    @Override
    public TypedSerializer<? extends DisplayableRecipe<RitualContainer>> getTypedSerializer() {
        return TYPED_SERIALIZER;
    }
}
