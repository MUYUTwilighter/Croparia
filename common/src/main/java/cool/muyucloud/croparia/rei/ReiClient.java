package cool.muyucloud.croparia.rei;

import cool.muyucloud.croparia.recipe.InfusorRecipe;
import cool.muyucloud.croparia.recipe.RitualRecipe;
import cool.muyucloud.croparia.recipe.RitualStructure;
import cool.muyucloud.croparia.registry.RecipeTypes;
import cool.muyucloud.croparia.rei.display.InfusorRecipeDisplay;
import cool.muyucloud.croparia.rei.display.RitualRecipeDisplay;
import cool.muyucloud.croparia.rei.display.RitualStructureDisplay;
import cool.muyucloud.croparia.rei.display.category.InfusorRecipeDisplayCategory;
import cool.muyucloud.croparia.rei.display.category.RitualRecipeDisplayCategory;
import cool.muyucloud.croparia.rei.display.category.RitualStructureDisplayCategory;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;


public class ReiClient implements REIClientPlugin {
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new InfusorRecipeDisplayCategory());
        registry.add(new RitualRecipeDisplayCategory());
        registry.add(new RitualStructureDisplayCategory());
    }

    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(InfusorRecipe.class, RecipeTypes.INFUSOR.get(), InfusorRecipeDisplay::new);
        registry.registerRecipeFiller(RitualRecipe.class, RecipeTypes.RITUAL.get(), RitualRecipeDisplay::new);
        registry.registerRecipeFiller(RitualStructure.class, RecipeTypes.RITUAL_STRUCTURE.get(), RitualStructureDisplay::new);
    }
}
