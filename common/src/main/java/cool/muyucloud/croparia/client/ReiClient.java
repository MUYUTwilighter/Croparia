package cool.muyucloud.croparia.client;

import cool.muyucloud.croparia.client.recipe.display.InfusorRecipeDisplay;
import cool.muyucloud.croparia.client.recipe.display.RitualRecipeDisplay;
import cool.muyucloud.croparia.client.recipe.display.RitualStructureDisplay;
import cool.muyucloud.croparia.client.recipe.display.category.InfusorRecipeDisplayCategory;
import cool.muyucloud.croparia.client.recipe.display.category.RitualRecipeDisplayCategory;
import cool.muyucloud.croparia.client.recipe.display.category.RitualStructureDisplayCategory;
import cool.muyucloud.croparia.recipe.InfusorRecipe;
import cool.muyucloud.croparia.recipe.RitualRecipe;
import cool.muyucloud.croparia.recipe.RitualStructure;
import cool.muyucloud.croparia.registry.RecipeTypes;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;


public class ReiClient {

    public static void registerCategories(CategoryRegistry registry) {
        registry.add(new InfusorRecipeDisplayCategory());
        registry.add(new RitualRecipeDisplayCategory());
        registry.add(new RitualStructureDisplayCategory());
    }

    public static void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(InfusorRecipe.class, RecipeTypes.INFUSOR.get(), InfusorRecipeDisplay::new);
        registry.registerRecipeFiller(RitualRecipe.class, RecipeTypes.RITUAL.get(), RitualRecipeDisplay::new);
        registry.registerRecipeFiller(RitualStructure.class, RecipeTypes.RITUAL_STRUCTURE.get(), RitualStructureDisplay::new);
    }
}
