package cool.muyucloud.forge;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import cool.muyucloud.CropariaIfClient;
import dev.architectury.platform.forge.EventBuses;
import cool.muyucloud.CropariaIf;
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
    public static void onClientSetup(FMLClientSetupEvent event) {
        CropariaIfClient.init();
    }
}
