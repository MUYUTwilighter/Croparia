package cool.muyucloud.croparia.client.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;

public class ClientCommandRoot {
    public static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> ROOT =
        LiteralArgumentBuilder.literal("croparia");

    public static void register() {
        CropariaIf.LOGGER.debug("Registering commands");
        ROOT.then(DumpCommand.build());
        ROOT.then(DumpBuiltinCommand.build());
        ROOT.then(CropCommand.build());
        ClientCommandRegistrationEvent.EVENT.register((dispatcher, context) -> dispatcher.register(ROOT));
    }

    public static Style openFile(String path) {
        return Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, path));
    }
}
