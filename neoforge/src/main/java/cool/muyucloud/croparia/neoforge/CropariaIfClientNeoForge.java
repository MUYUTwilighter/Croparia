package cool.muyucloud.croparia.neoforge;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.client.CropariaIfClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = CropariaIf.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CropariaIfClientNeoForge {
    @SubscribeEvent()
    public static void onClientSetup(FMLClientSetupEvent event) {
        CropariaIfClient.init();
    }
}
