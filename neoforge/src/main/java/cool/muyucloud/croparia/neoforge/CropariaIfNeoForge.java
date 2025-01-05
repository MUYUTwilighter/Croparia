package cool.muyucloud.croparia.neoforge;

import cool.muyucloud.croparia.CropariaIf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

@Mod(CropariaIf.MOD_ID)
@EventBusSubscriber(modid = CropariaIf.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
public class CropariaIfNeoForge {
    public CropariaIfNeoForge() {
//        EventBuses.registerModEventBus(CropariaIf.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        CropariaIf.init();
    }

    @SubscribeEvent
    public static void onServerStarting() {
        CropariaIf.onServerStarting();
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        CropariaIf.onServerStopping();
    }
}
