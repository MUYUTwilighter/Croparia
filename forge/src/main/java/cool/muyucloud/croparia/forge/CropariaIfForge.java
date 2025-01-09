package cool.muyucloud.croparia.forge;

import cool.muyucloud.croparia.CropariaIf;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CropariaIf.MOD_ID)
@Mod.EventBusSubscriber(modid = CropariaIf.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
public class CropariaIfForge {
    public CropariaIfForge() {
        EventBuses.registerModEventBus(CropariaIf.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        CompatCrops.init();
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
