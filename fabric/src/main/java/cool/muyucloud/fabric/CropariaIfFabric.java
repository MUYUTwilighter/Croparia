package cool.muyucloud.fabric;

import cool.muyucloud.CropariaIf;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class CropariaIfFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CropariaIf.init();
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, serverResourceManager, success) -> CropariaIf.afterDataPackLoaded());
        ServerLifecycleEvents.SERVER_STARTING.register(event -> CropariaIf.onServerStarting());
        ServerLifecycleEvents.SERVER_STOPPING.register(event -> CropariaIf.onServerStopping());
    }
}
