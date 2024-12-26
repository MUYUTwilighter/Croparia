package cool.muyucloud.fabric;

import cool.muyucloud.CropariaIfClient;
import net.fabricmc.api.ClientModInitializer;

public class CropariaIfClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CropariaIfClient.init();
    }
}
