package cool.muyucloud.croparia.fabric;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.client.CropariaIfClient;
import cool.muyucloud.croparia.fabric.command.ClientCommandRoot;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class CropariaIfClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CropariaIfClient.init();
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            CropariaIf.LOGGER.debug("Registering client commands");
            ClientCommandRoot.register();
        });
    }
}
