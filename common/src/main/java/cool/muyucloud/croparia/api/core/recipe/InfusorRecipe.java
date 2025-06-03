package cool.muyucloud.croparia.api.core.recipe;

import cool.muyucloud.croparia.api.core.recipe.container.InfusorContainer;
import cool.muyucloud.croparia.api.core.recipe.predicate.GenericIngredient;
import cool.muyucloud.croparia.api.core.recipe.serializer.InfusorRecipeSerializer;
import cool.muyucloud.croparia.api.element.ElementsEnum;
import cool.muyucloud.croparia.api.element.item.ElementalPotion;
import cool.muyucloud.croparia.registry.RecipeSerializers;
import cool.muyucloud.croparia.registry.RecipeTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
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
    protected ResourceLocation id;
    protected ElementsEnum element = ElementsEnum.ELEMENTAL;
    protected GenericIngredient ingredient;
    protected ItemStack result = ItemStack.EMPTY;

    public Item getPotion() {
        return ElementalPotion.fromElement(element).orElseThrow();
    }

    public ItemStack getResult() {
        return result;
    }

    public void setResult(ItemStack result) {
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Empty result item %s in recipe %s".formatted(result, this.getId()));
        }
        this.result = result;
    }

    public ElementsEnum getElement() {
        return element;
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

    @Override
    public boolean matches(@NotNull InfusorContainer container, @Nullable Level level) {
        return matches(container);
    }

    @Override
    public @NotNull ItemStack assemble(InfusorContainer container, RegistryAccess registryAccess) {
        return assemble(container);
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i >= 1 && j >= 2 || i >= 2 && j >= 1;
    }

    @Override
    public @NotNull ItemStack getResultItem(@Nullable RegistryAccess registryAccess) {
        return result.copy();
    }

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
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
