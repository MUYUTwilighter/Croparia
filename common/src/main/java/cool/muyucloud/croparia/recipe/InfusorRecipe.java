package cool.muyucloud.croparia.recipe;

import cool.muyucloud.croparia.data.ElementsEnum;
import cool.muyucloud.croparia.recipe.container.InfusorContainer;
import cool.muyucloud.croparia.recipe.serializer.InfusorRecipeSerializer;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.RecipeSerializers;
import cool.muyucloud.croparia.registry.RecipeTypes;
import cool.muyucloud.croparia.util.predicate.GenericIngredient;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Recipe data entity for the infusor.<br/>
 * For serialization, see {@link InfusorRecipeSerializer}.<br/>
 * For old version of infusor recipe formed by Dalarion, see {@link OldInfusorRecipe}.
 */
public class InfusorRecipe implements Recipe<InfusorContainer> {
    protected ElementsEnum element = ElementsEnum.ELEMENTAL;
    protected GenericIngredient ingredient;
    protected ItemStack result = ItemStack.EMPTY;

    public ItemStack getResult() {
        return result;
    }

    public void setResult(ItemStack result) {
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Empty result item %s in recipe %s".formatted(result, this));
        }
        this.result = result;
    }

    public ItemStack getElementPotion() {
        return CropariaItems.getPotion(this.element).getDefaultInstance();
    }

    public ElementsEnum getElement() {
        return element;
    }

    public String getElementName() {
        return element.getSerializedName();
    }

    public void setElement(ElementsEnum element) {
        this.element = element;
    }

    public GenericIngredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(@NotNull GenericIngredient ingredient) {
        this.ingredient = ingredient;
    }

    public boolean matches(InfusorContainer container) {
        ItemStack input = container.getItem(0);
        return ingredient.test(input) && container.getElement() == element;
    }

    public @NotNull ItemStack assemble(@NotNull InfusorContainer container) {
        if (matches(container)) {
            ItemStack input = container.getItem(0);
            input.shrink(ingredient.getCount());
            return getResult().copy();
        } else {
            return ItemStack.EMPTY;
        }
    }

    public Item getPotion() {
        return CropariaItems.getPotion(this.getElement());
    }

    @Override
    public boolean matches(@NotNull InfusorContainer container, @Nullable Level level) {
        return matches(container);
    }

    @Override
    public @NotNull ItemStack assemble(InfusorContainer recipeInput, HolderLookup.Provider provider) {
        return assemble(recipeInput);
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i >= 1 && j >= 2 || i >= 2 && j >= 1;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.INFUSOR.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypes.INFUSOR.get();
    }
}
