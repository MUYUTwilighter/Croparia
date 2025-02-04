package cool.muyucloud.croparia.fabric.emi;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.recipe.InfusorRecipe;
import cool.muyucloud.croparia.api.core.recipe.RitualRecipe;
import cool.muyucloud.croparia.api.core.recipe.RitualStructure;
import cool.muyucloud.croparia.fabric.emi.recipe.EmiInfusorRecipe;
import cool.muyucloud.croparia.fabric.emi.recipe.EmiRitualRecipe;
import cool.muyucloud.croparia.fabric.emi.recipe.EmiRitualStructure;
import cool.muyucloud.croparia.registry.RecipeTypes;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;

public class EmiCommon implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        CropariaIf.LOGGER.debug("Registering emi recipes...");
        registry.addCategory(EmiInfusorRecipe.CATEGORY);
        registry.addWorkstation(EmiInfusorRecipe.CATEGORY, EmiInfusorRecipe.WORKSTATION);
        for (InfusorRecipe recipe : registry.getRecipeManager().getAllRecipesFor(RecipeTypes.INFUSOR.get())) {
            registry.addRecipe(new EmiInfusorRecipe(recipe));
        }
        registry.addCategory(EmiRitualRecipe.CATEGORY);
        for (RitualRecipe recipe : registry.getRecipeManager().getAllRecipesFor(RecipeTypes.RITUAL.get())) {
            registry.addRecipe(new EmiRitualRecipe(recipe));
        }
        registry.addCategory(EmiRitualStructure.CATEGORY);
        for (RitualStructure recipe : registry.getRecipeManager().getAllRecipesFor(RecipeTypes.RITUAL_STRUCTURE.get())) {
            registry.addRecipe(new EmiRitualStructure(recipe));
        }
    }
}
