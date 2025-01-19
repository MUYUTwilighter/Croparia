package cool.muyucloud.croparia.forge;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.client.CropariaIfClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CropariaIf.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBusClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        CropariaIfClient.init();
    }
}
