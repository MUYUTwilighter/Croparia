package cool.muyucloud.croparia.client.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;

public class ClientCommandRoot {
    public static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> ROOT =
        LiteralArgumentBuilder.literal("croparia");

    public static void register() {
        CropariaIf.LOGGER.debug("Registering commands");
        ROOT.then(DumpCommand.build());
        ROOT.then(CropCommand.build());
        ROOT.then(CreateCommand.build());
        ClientCommandRegistrationEvent.EVENT.register((dispatcher, context) -> dispatcher.register(ROOT));
    }
}
