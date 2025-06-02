package cool.muyucloud.croparia.compat.rei;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.recipe.DisplayableRecipe;
import cool.muyucloud.croparia.compat.rei.category.InfusorRecipeDisplayCategory;
import cool.muyucloud.croparia.compat.rei.category.RitualRecipeDisplayCategory;
import cool.muyucloud.croparia.compat.rei.display.SimpleDisplay;
import cool.muyucloud.croparia.compat.rei.display.SimpleSerializer;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.PluginManager;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.ReloadStage;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;

import java.util.HashSet;
import java.util.Set;

public class ReiCommon implements REICommonPlugin {
    private final Set<SimpleSerializer<? extends DisplayableRecipe<?>>> serializers = new HashSet<>();

    @Override
    public void preStage(PluginManager<REICommonPlugin> manager, ReloadStage stage) {
        serializers.add(InfusorRecipeDisplayCategory.SERIALIZER);
        serializers.add(RitualRecipeDisplayCategory.SERIALIZER);
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        CropariaIf.LOGGER.info("Registering rei recipe fillers...");
        serializers.forEach(serializer -> registerDisplay(registry, serializer));
    }

    private <R extends DisplayableRecipe<?>> void registerDisplay(ServerDisplayRegistry registry, SimpleSerializer<R> serializer) {
        registry.beginRecipeFiller(serializer.getRecipeClass())
            .filterType(serializer.getRecipeSerializer())
            .fill(holder -> new SimpleDisplay<>(holder, serializer));
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        CropariaIf.LOGGER.info("Registering rei recipe display serializers...");
        serializers.forEach(serializer -> registry.register(serializer.getId().getIdentifier(), serializer.getSerializer()));
    }
}
