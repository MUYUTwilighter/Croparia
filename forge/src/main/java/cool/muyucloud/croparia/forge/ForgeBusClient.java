package cool.muyucloud.croparia.forge;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.forge.command.ClientCommandRoot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CropariaIf.MOD_ID, value = Dist.CLIENT)
public class ForgeBusClient {
    @SubscribeEvent
    public static void onClientCommand(RegisterClientCommandsEvent event) {
        ClientCommandRoot.register(event.getDispatcher());
    }
}
