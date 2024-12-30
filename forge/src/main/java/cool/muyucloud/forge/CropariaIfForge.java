package cool.muyucloud.forge;

import cool.muyucloud.CropariaIf;
import cool.muyucloud.client.CropariaIfClient;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Optional;

@Mod(CropariaIf.MOD_ID)
@Mod.EventBusSubscriber
public class CropariaIfForge {
    private static final IEventBus EVENT_BUS = MinecraftForge.EVENT_BUS;

    public CropariaIfForge() {
        EventBuses.registerModEventBus(CropariaIf.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        CropariaIf.init();
        EVENT_BUS.addListener(CropariaIfForge::onClientSetup);
        EVENT_BUS.addListener(CropariaIfForge::onServerStarting);
        EVENT_BUS.addListener(CropariaIfForge::onServerStopping);
        EVENT_BUS.addListener(CropariaIfForge::onDataPackLoaded);
    }

    @SubscribeEvent
    public static void onDataPackLoaded(OnDatapackSyncEvent event) {
        Optional.ofNullable(event.getPlayer())
            .flatMap(player -> Optional.ofNullable(player.getServer()))
            .ifPresent(CropariaIf::onDataPackLoaded);
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        CropariaIf.onServerStarting();
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        CropariaIf.onServerStopping();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        CropariaIfClient.init();
    }
}
