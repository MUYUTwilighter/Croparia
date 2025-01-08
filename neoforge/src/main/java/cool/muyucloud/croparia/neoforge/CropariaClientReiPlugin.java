package cool.muyucloud.croparia.neoforge;

import cool.muyucloud.croparia.rei.ReiClient;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.forge.REIPluginClient;

@REIPluginClient
public class CropariaClientReiPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        ReiClient.registerCategories(registry);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        ReiClient.registerDisplays(registry);
    }
}
