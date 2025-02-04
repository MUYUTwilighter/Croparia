package cool.muyucloud.croparia.compat.rei;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.recipe.InfusorRecipe;
import cool.muyucloud.croparia.api.core.recipe.RitualRecipe;
import cool.muyucloud.croparia.api.core.recipe.RitualStructure;
import cool.muyucloud.croparia.compat.rei.display.InfusorRecipeDisplay;
import cool.muyucloud.croparia.compat.rei.display.RitualRecipeDisplay;
import cool.muyucloud.croparia.compat.rei.display.RitualStructureDisplay;
import cool.muyucloud.croparia.registry.RecipeTypes;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;

public class ReiCommon implements REICommonPlugin {
    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        CropariaIf.LOGGER.info("Registering rei recipe display serializers...");
        registry.register(CropariaIf.of("infusor"), InfusorRecipeDisplay.SERIALIZER);
        registry.register(CropariaIf.of("ritual"), RitualRecipeDisplay.SERIALIZER);
        registry.register(CropariaIf.of("ritual_structure"), RitualStructureDisplay.SERIALIZER);
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        CropariaIf.LOGGER.info("Registering rei recipe fillers...");
        registry.beginRecipeFiller(InfusorRecipe.class).filterType(RecipeTypes.INFUSOR.get()).fill(
            holder -> new InfusorRecipeDisplay(holder.value(), holder.id().location())
        );
        registry.beginRecipeFiller(RitualRecipe.class).filterType(RecipeTypes.RITUAL.get()).fill(
            holder -> new RitualRecipeDisplay(holder.value(), holder.id().location())
        );
        registry.beginRecipeFiller(RitualStructure.class).filterType(RecipeTypes.RITUAL_STRUCTURE.get()).fill(
            holder -> new RitualStructureDisplay(holder.value(), holder.id().location())
        );
    }
}
