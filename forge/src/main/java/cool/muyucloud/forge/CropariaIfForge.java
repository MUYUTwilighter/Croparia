package cool.muyucloud.forge;

import cool.muyucloud.CropariaIf;
import cool.muyucloud.client.CropariaIfClient;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CropariaIf.MOD_ID)
@Mod.EventBusSubscriber
public class CropariaIfForge {
    public CropariaIfForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(CropariaIf.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        CropariaIf.init();
    }

    @SubscribeEvent
    public static void afterDataPackLoaded(OnDatapackSyncEvent event) {
        CropariaIf.afterDataPackLoaded();
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        CropariaIf.onServerStarting();
    }

    @SubscribeEvent
    public static void onServerStopping() {
        CropariaIf.onServerStopping();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        CropariaIfClient.init();
    }
}
