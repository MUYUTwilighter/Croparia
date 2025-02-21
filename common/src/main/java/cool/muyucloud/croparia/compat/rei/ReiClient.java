package cool.muyucloud.croparia.compat.rei;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.recipe.InfusorRecipe;
import cool.muyucloud.croparia.api.core.recipe.RitualRecipe;
import cool.muyucloud.croparia.api.core.recipe.RitualStructure;
import cool.muyucloud.croparia.compat.rei.category.InfusorRecipeDisplayCategory;
import cool.muyucloud.croparia.compat.rei.category.RitualRecipeDisplayCategory;
import cool.muyucloud.croparia.compat.rei.category.RitualStructureDisplayCategory;
import cool.muyucloud.croparia.compat.rei.display.InfusorRecipeDisplay;
import cool.muyucloud.croparia.compat.rei.display.RitualRecipeDisplay;
import cool.muyucloud.croparia.compat.rei.display.RitualStructureDisplay;
import cool.muyucloud.croparia.registry.RecipeTypes;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;


public class ReiClient implements REIClientPlugin {
    public void registerCategories(CategoryRegistry registry) {
        CropariaIf.LOGGER.debug("Registering rei recipe categories...");
        registry.add(new InfusorRecipeDisplayCategory());
        registry.add(new RitualRecipeDisplayCategory());
        registry.add(new RitualStructureDisplayCategory());
    }

    public void registerDisplays(DisplayRegistry registry) {
        CropariaIf.LOGGER.debug("Registering rei recipe displays...");
        registry.registerRecipeFiller(InfusorRecipe.class, RecipeTypes.INFUSOR.get(), holder -> new InfusorRecipeDisplay(holder.value(), holder.id()));
        registry.registerRecipeFiller(RitualRecipe.class, RecipeTypes.RITUAL.get(), holder -> new RitualRecipeDisplay(holder.value(), holder.id()));
        registry.registerRecipeFiller(RitualStructure.class, RecipeTypes.RITUAL_STRUCTURE.get(), holder -> new RitualStructureDisplay(holder.value(), holder.id()));
    }
}
