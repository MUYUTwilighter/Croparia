package cool.muyucloud.croparia.forge.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

import static cool.muyucloud.croparia.command.DumpBuiltinCommand.dumpAll;

public class DumpBuiltinCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> DUMP_BUILTIN =
        LiteralArgumentBuilder.literal("dumpBuiltin");

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        DUMP_BUILTIN.executes(context -> {
            CommandSourceStack source = context.getSource();
            return dumpAll(source::sendSuccess, true);
        });
        return DUMP_BUILTIN;
    }
}
