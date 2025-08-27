package cool.muyucloud.croparia.api.core.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import cool.muyucloud.croparia.CropariaIf;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;

import static net.minecraft.commands.CommandSourceStack.ERROR_NOT_PLAYER;

@SuppressWarnings("unused")
public class CommonCommandRoot {
    private static final LiteralArgumentBuilder<CommandSourceStack> ROOT = Commands.literal("cropariaServer")
        .requires(s -> s.hasPermission(2))
        .then(DumpCommand.build())
        .then(CropCommand.build())
        .then(ConfigCommand.buildInfusor())
        .then(ConfigCommand.buildRitual())
        .then(ConfigCommand.buildFruitUse())
        .then(ConfigCommand.buildAutoReload())
        .then(ConfigCommand.buildOverride())
        .then(ConfigCommand.buildSoakAttempts())
        .then(CreateCommand.build());

    public static void register() {
        CropariaIf.LOGGER.debug("Registering commands");
        CommandRegistrationEvent.EVENT.register((dispatcher, registry, selection) -> dispatcher.register(ROOT));
    }

    public static Player playerOrThrow(CommandSourceStack source) throws CommandSyntaxException {
        if (source.getEntity() instanceof Player player) {
            return player;
        } else {
            throw ERROR_NOT_PLAYER.create();
        }
    }

    public static String commandRoot(boolean client) {
        return client ? "croparia" : "cropariaServer";
    }
}
