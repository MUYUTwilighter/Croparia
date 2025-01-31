package cool.muyucloud.croparia.fabric.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import static cool.muyucloud.croparia.api.crop.command.DumpBuiltinCommand.dumpAll;

public class DumpBuiltinCommand {
    private static final LiteralArgumentBuilder<FabricClientCommandSource> DUMP_BUILTIN =
        LiteralArgumentBuilder.literal("dumpBuiltin");

    public static LiteralArgumentBuilder<FabricClientCommandSource> build() {
        DUMP_BUILTIN.executes(context -> {
            FabricClientCommandSource source = context.getSource();
            return dumpAll((msg, broadcast) -> source.sendFeedback(msg.get()), true);
        });
        return DUMP_BUILTIN;
    }
}
