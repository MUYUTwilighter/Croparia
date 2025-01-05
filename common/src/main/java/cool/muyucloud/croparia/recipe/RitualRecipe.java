package cool.muyucloud.croparia.recipe;

import cool.muyucloud.croparia.recipe.container.RitualContainer;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.RecipeSerializers;
import cool.muyucloud.croparia.registry.RecipeTypes;
import cool.muyucloud.croparia.util.predicate.BlockStatePredicate;
import cool.muyucloud.croparia.util.predicate.GenericIngredient;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class RitualRecipe implements Recipe<RitualContainer> {
    private final int tier;
    @NotNull
    private final BlockStatePredicate block;
    @NotNull
    private final GenericIngredient ingredient;
    @NotNull
    private final ItemStack result;

    public RitualRecipe(
        int tier, @NotNull BlockStatePredicate state, @NotNull GenericIngredient ingredient, @NotNull ItemStack result
    ) {
        if (tier < 1) {
            throw new IllegalArgumentException("Tier must be at least 1");
        }
        this.tier = tier;
        this.block = state;
        this.ingredient = ingredient;
        this.result = result;
    }

    public ItemStack assemble(RitualContainer recipeInput) {
        if (matches(recipeInput)) {
            recipeInput.item().shrink(this.ingredient.getCount());
            return this.getResult();
        }
        return ItemStack.EMPTY;
    }

    public @NotNull ItemStack getRitualItem() {
        return CropariaItems.getRitualStand(this.tier).get().getDefaultInstance();
    }

    public @NotNull Collection<ItemStack> extractBlockItems() {
        return this.block.availableBlockItems();
    }

    public @NotNull GenericIngredient getIngredient() {
        return ingredient;
    }

    public @NotNull BlockStatePredicate getBlock() {
        return block;
    }

    public @NotNull ItemStack getResult() {
        return result;
    }

    public BlockStatePredicate.Builder getStateBuilder() {
        return block.getBuilder();
    }

    public int getTier() {
        return tier;
    }

    public boolean matches(RitualContainer container) {
        int tier = container.tier();
        ItemStack input = container.item();
        BlockState state = container.state();
        return this.ingredient.test(input)
            && this.block.test(state)
            && tier >= this.tier;
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
    public boolean canCraftInDimensions(int i, int j) {
        return false;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.getResult();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.RITUAL.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypes.RITUAL.get();
    }
}
