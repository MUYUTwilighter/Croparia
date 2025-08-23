package cool.muyucloud.croparia.compat.rei;

import cool.muyucloud.croparia.CropariaIf;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;

public class ReiClient implements REIClientPlugin {
    public void registerCategories(CategoryRegistry registry) {
        CropariaIf.LOGGER.info("Registering rei recipe categories...");
        ReiCommon.forEach(category -> {
            registry.add(category);
            registry.addWorkstations(category.getCategoryIdentifier(), category.stations());
        });
    }
}
