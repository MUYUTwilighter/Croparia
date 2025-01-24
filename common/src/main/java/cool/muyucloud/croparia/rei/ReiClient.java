package cool.muyucloud.croparia.rei;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.rei.category.InfusorRecipeDisplayCategory;
import cool.muyucloud.croparia.rei.category.RitualRecipeDisplayCategory;
import cool.muyucloud.croparia.rei.category.RitualStructureDisplayCategory;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;

public class ReiClient implements REIClientPlugin {
    public void registerCategories(CategoryRegistry registry) {
        CropariaIf.LOGGER.info("Registering rei recipe categories...");
        registry.add(new InfusorRecipeDisplayCategory());
        registry.add(new RitualRecipeDisplayCategory());
        registry.add(new RitualStructureDisplayCategory());
    }
}
