package cool.muyucloud.croparia.forge.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

import static cool.muyucloud.croparia.api.crop.command.DumpCommand.dump;
import static cool.muyucloud.croparia.api.crop.command.DumpCommand.dumpAll;

public class DumpCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> DUMP =
        LiteralArgumentBuilder.literal("dump");
    private static final RequiredArgumentBuilder<CommandSourceStack, String> CROP =
        RequiredArgumentBuilder.argument("crop", StringArgumentType.greedyString());

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        CROP.executes(context -> {
            String name = StringArgumentType.getString(context, "crop");
            return dump(name, context.getSource()::sendSuccess, context.getSource()::sendFailure, true);
        });
        DUMP.requires(s -> s.hasPermission(2));
        DUMP.executes(context -> dumpAll(context.getSource()::sendSuccess, true));
        DUMP.then(CROP);
        return DUMP;
    }
}
