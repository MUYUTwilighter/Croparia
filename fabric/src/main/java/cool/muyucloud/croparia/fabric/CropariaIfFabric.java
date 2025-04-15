package cool.muyucloud.croparia.fabric;

import cool.muyucloud.croparia.CropariaIf;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class CropariaIfFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CompatCrops.init();
        CropariaIf.init();
        ServerLifecycleEvents.SERVER_STARTING.register(server -> CropariaIf.onServerStarting());
        ServerLifecycleEvents.SERVER_STARTED.register(CropariaIf::onServerStarted);
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> CropariaIf.onServerStopping());
    }
}
