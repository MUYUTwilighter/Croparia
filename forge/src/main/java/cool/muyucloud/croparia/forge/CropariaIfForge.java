package cool.muyucloud.croparia.forge;

import cool.muyucloud.croparia.CropariaIf;
import cpw.mods.niofs.union.UnionFileSystem;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CropariaIf.MOD_ID)
@Mod.EventBusSubscriber(modid = CropariaIf.MOD_ID)
public class CropariaIfForge {
    public CropariaIfForge() {
        EventBuses.registerModEventBus(CropariaIf.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        CompatCrops.init();
        CropariaIf.init();
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        CropariaIf.onServerStarting();
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        CropariaIf.onServerStopping();
    }
}
