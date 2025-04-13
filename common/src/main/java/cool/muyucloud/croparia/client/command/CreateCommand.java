package cool.muyucloud.croparia.client.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.api.crop.CropType;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import net.minecraft.network.chat.TranslatableComponent;

import static cool.muyucloud.croparia.api.crop.command.CreateCommand.create;

public class CreateCommand {
    private static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> CREATE = LiteralArgumentBuilder.literal("create");
    private static final RequiredArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack, String> TYPE = RequiredArgumentBuilder.argument(
        "type", StringArgumentType.word()
    );
    private static final RequiredArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack, String> COLOR = RequiredArgumentBuilder.argument(
        "color", StringArgumentType.word()
    );
    private static final RequiredArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack, String> NAME = RequiredArgumentBuilder.argument(
        "name", StringArgumentType.word()
    );
    private static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> REPLACE = LiteralArgumentBuilder.literal("replace");

    static {
        CREATE.requires(s -> s.hasPermission(2));
        COLOR.executes(context -> {
            if (context.getSource().arch$getPlayer() != null) {
                return create(
                    context.getSource().arch$getPlayer(),
                    null,
                    CropType.CROP.getModelName(),
                    StringArgumentType.getString(context, "color"),
                    (msg, broadcast) -> context.getSource().arch$sendSuccess(msg, broadcast),
                    context.getSource()::arch$sendFailure,
                    true, false
                );
            } else {
                context.getSource().arch$sendFailure(new TranslatableComponent("commands.croparia.crop.not_player"));
                return -1;
            }
        });
        TYPE.suggests((context, builder) -> {
            for (CropType type : CropType.values()) {
                builder.suggest(type.getModelName());
            }
            return builder.buildFuture();
        }).executes(context -> create(
            context.getSource().arch$getPlayer(),
            null,
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            (msg, broadcast) -> context.getSource().arch$sendSuccess(msg, broadcast),
            context.getSource()::arch$sendFailure,
            true, false
        ));
        NAME.executes(context -> create(
            context.getSource().arch$getPlayer(),
            StringArgumentType.getString(context, "name"),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            (msg, broadcast) -> context.getSource().arch$sendSuccess(msg, broadcast),
            context.getSource()::arch$sendFailure,
            true, false
        ));
        REPLACE.executes(context -> create(
            context.getSource().arch$getPlayer(),
            StringArgumentType.getString(context, "name"),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            (msg, broadcast) -> context.getSource().arch$sendSuccess(msg, broadcast),
            context.getSource()::arch$sendFailure,
            true, true
        ));
        NAME.then(REPLACE);
        TYPE.then(NAME);
        COLOR.then(TYPE);
        CREATE.then(COLOR);
    }

    public static LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> build() {
        return CREATE;
    }
}