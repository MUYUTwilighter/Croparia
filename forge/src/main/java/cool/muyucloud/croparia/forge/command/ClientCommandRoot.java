package cool.muyucloud.croparia.forge.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import net.minecraft.commands.CommandSourceStack;

public class ClientCommandRoot {
    public static final LiteralArgumentBuilder<CommandSourceStack> ROOT =
        LiteralArgumentBuilder.literal("croparia");

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CropariaIf.LOGGER.debug("Registering commands");
        ROOT.then(DumpCommand.build());
        ROOT.then(DumpBuiltinCommand.build());
        ROOT.then(CropCommand.build());
        dispatcher.register(ROOT);
    }
}
