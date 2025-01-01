package cool.muyucloud.croparia.fabric;

import cool.muyucloud.croparia.client.CropariaIfClient;
import net.fabricmc.api.ClientModInitializer;

public class CropariaIfClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CropariaIfClient.init();
    }
}
