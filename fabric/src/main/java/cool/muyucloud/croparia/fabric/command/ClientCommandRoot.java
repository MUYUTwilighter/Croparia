package cool.muyucloud.croparia.fabric.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class ClientCommandRoot {
    public static final LiteralArgumentBuilder<FabricClientCommandSource> ROOT =
        LiteralArgumentBuilder.literal("croparia");

    public static void register() {
        CropariaIf.LOGGER.debug("Registering client commands");
        ROOT.then(DumpCommand.build());
        ROOT.then(DumpBuiltinCommand.build());
        ROOT.then(CropCommand.build());
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, context) -> dispatcher.register(ROOT));
    }
}
