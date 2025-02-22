package cool.muyucloud.croparia.fabric;

import cool.muyucloud.croparia.client.CropariaIfClient;
import cool.muyucloud.croparia.fabric.command.ClientCommandRoot;
import net.fabricmc.api.ClientModInitializer;

public class CropariaIfClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CropariaIfClient.init();
        ClientCommandRoot.register();
    }
}
