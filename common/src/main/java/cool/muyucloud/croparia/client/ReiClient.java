package cool.muyucloud.croparia.client;

import cool.muyucloud.croparia.client.recipe.display.InfusorRecipeDisplay;
import cool.muyucloud.croparia.client.recipe.display.category.InfusorRecipeDisplayCategory;
import cool.muyucloud.croparia.data.ElementsEnum;
import cool.muyucloud.croparia.recipe.InfusorRecipe;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.RecipeTypes;
import cool.muyucloud.croparia.util.GenericIngredient;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

public class ReiClient {
    public static void registerCategories(CategoryRegistry registry) {
        registry.add(new InfusorRecipeDisplayCategory());
    }

    public static void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(InfusorRecipe.class, RecipeTypes.INFUSOR.get(), InfusorRecipeDisplay::new);
    }

    public static EntryIngredient ofIngredient(GenericIngredient ingredient) {
        return EntryIngredients.ofItemStacks(ingredient.availableStacks());
    }

    public static EntryIngredient ofElement(ElementsEnum element) {
        return EntryIngredients.of(CropariaItems.getPotion(element));
    }
}
