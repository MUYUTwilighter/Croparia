package cool.muyucloud.croparia.fabric.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import static cool.muyucloud.croparia.command.DumpCommand.dump;
import static cool.muyucloud.croparia.command.DumpCommand.dumpAll;

public class DumpCommand {
    private static final LiteralArgumentBuilder<FabricClientCommandSource> DUMP =
        LiteralArgumentBuilder.literal("dump");
    private static final RequiredArgumentBuilder<FabricClientCommandSource, String> CROP =
        RequiredArgumentBuilder.argument("crop", StringArgumentType.greedyString());

    public static LiteralArgumentBuilder<FabricClientCommandSource> build() {
        CROP.executes(context -> {
            String name = StringArgumentType.getString(context, "crop");
            return dump(name, (msg, broadcast) -> context.getSource().sendFeedback(msg.get()), context.getSource()::sendError, true);
        });
        DUMP.requires(s -> s.hasPermission(2));
        DUMP.executes(context -> dumpAll((msg, broadcast) -> context.getSource().sendFeedback(msg.get()), true));
        DUMP.then(CROP);
        return DUMP;
    }
}
