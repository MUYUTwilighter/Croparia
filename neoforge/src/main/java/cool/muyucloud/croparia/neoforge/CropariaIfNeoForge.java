package cool.muyucloud.croparia.neoforge;

import cool.muyucloud.croparia.CropariaIf;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

@Mod(CropariaIf.MOD_ID)
@EventBusSubscriber(modid = CropariaIf.MOD_ID)
public class CropariaIfNeoForge {
    public CropariaIfNeoForge() {
        CompatCrops.init();
        CropariaIf.init();
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        CropariaIf.onServerStarting();
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        CropariaIf.onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        CropariaIf.onServerStopping();
    }
}
