package cool.muyucloud.fabric;

import cool.muyucloud.CropariaIf;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class CropariaIfFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CropariaIf.init();
        ServerLifecycleEvents.SERVER_STARTING.register(server -> CropariaIf.onServerStarting());
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> CropariaIf.onServerStopping());
    }
}
