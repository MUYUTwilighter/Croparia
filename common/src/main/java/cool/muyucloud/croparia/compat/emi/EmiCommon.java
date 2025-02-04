package cool.muyucloud.croparia.compat.emi;

//import cool.muyucloud.croparia.CropariaIf;
//import cool.muyucloud.croparia.emi.recipe.EmiInfusorRecipe;
//import cool.muyucloud.croparia.emi.recipe.EmiRitualRecipe;
//import cool.muyucloud.croparia.emi.recipe.EmiRitualStructure;
//import cool.muyucloud.croparia.recipe.InfusorRecipe;
//import cool.muyucloud.croparia.recipe.RitualRecipe;
//import cool.muyucloud.croparia.recipe.RitualStructure;
//import cool.muyucloud.croparia.registry.RecipeTypes;
//import dev.emi.emi.api.EmiPlugin;
//import dev.emi.emi.api.EmiRegistry;
//import net.minecraft.world.item.crafting.RecipeHolder;

//public class EmiCommon implements EmiPlugin {
//    @Override
//    public void register(EmiRegistry registry) {
//        CropariaIf.LOGGER.debug("Registering emi recipes...");
//        registry.addCategory(EmiInfusorRecipe.CATEGORY);
//        registry.addWorkstation(EmiInfusorRecipe.CATEGORY, EmiInfusorRecipe.WORKSTATION);
//        for (RecipeHolder<InfusorRecipe> holder : registry.getRecipeManager().getAllRecipesFor(RecipeTypes.INFUSOR.get())) {
//            registry.addRecipe(new EmiInfusorRecipe(holder));
//        }
//        registry.addCategory(EmiRitualRecipe.CATEGORY);
//        for (RecipeHolder<RitualRecipe> holder : registry.getRecipeManager().getAllRecipesFor(RecipeTypes.RITUAL.get())) {
//            registry.addRecipe(new EmiRitualRecipe(holder));
//        }
//        registry.addCategory(EmiRitualStructure.CATEGORY);
//        for (RecipeHolder<RitualStructure> holder : registry.getRecipeManager().getAllRecipesFor(RecipeTypes.RITUAL_STRUCTURE.get())) {
//            registry.addRecipe(new EmiRitualStructure(holder));
//        }
//    }
//}

public class EmiCommon {

}