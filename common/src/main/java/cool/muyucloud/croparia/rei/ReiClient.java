package cool.muyucloud.croparia.rei;

import cool.muyucloud.croparia.rei.display.InfusorRecipeDisplay;
import cool.muyucloud.croparia.rei.display.RitualRecipeDisplay;
import cool.muyucloud.croparia.rei.display.RitualStructureDisplay;
import cool.muyucloud.croparia.rei.category.InfusorRecipeDisplayCategory;
import cool.muyucloud.croparia.rei.category.RitualRecipeDisplayCategory;
import cool.muyucloud.croparia.rei.category.RitualStructureDisplayCategory;
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
        registry.registerRecipeFiller(InfusorRecipe.class, RecipeTypes.INFUSOR.get(), holder -> new InfusorRecipeDisplay(holder.value(), holder.id()));
        registry.registerRecipeFiller(RitualRecipe.class, RecipeTypes.RITUAL.get(), holder -> new RitualRecipeDisplay(holder.value(), holder.id()));
        registry.registerRecipeFiller(RitualStructure.class, RecipeTypes.RITUAL_STRUCTURE.get(), holder -> new RitualStructureDisplay(holder.value(), holder.id()));
    }
}
