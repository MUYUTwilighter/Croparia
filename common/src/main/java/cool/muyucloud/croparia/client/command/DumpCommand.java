package cool.muyucloud.croparia.client.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import me.shedaniel.architectury.event.events.client.ClientCommandRegistrationEvent;

import static cool.muyucloud.croparia.api.crop.command.DumpCommand.dump;
import static cool.muyucloud.croparia.api.crop.command.DumpCommand.dumpAll;

public class DumpCommand {
    private static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> DUMP =
        LiteralArgumentBuilder.literal("dump");
    private static final RequiredArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack, String> CROP =
        RequiredArgumentBuilder.argument("crop", StringArgumentType.greedyString());

    public static LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> build() {
        CROP.executes(context -> {
            String name = StringArgumentType.getString(context, "crop");
            return dump(name, context.getSource()::arch$sendSuccess, context.getSource()::arch$sendFailure, true);
        });
        DUMP.requires(s -> s.hasPermission(2));
        DUMP.executes(context -> dumpAll(context.getSource()::arch$sendSuccess, true));
        DUMP.then(CROP);
        return DUMP;
    }
}
