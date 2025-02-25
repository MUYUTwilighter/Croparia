package cool.muyucloud.croparia.client.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.api.crop.CropType;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;

import static cool.muyucloud.croparia.api.crop.command.CreateCommand.create;

public class CreateCommand {
    private static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> CREATE = LiteralArgumentBuilder.literal("create");
    private static final RequiredArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack, String> TYPE = RequiredArgumentBuilder.argument(
        "type", StringArgumentType.word()
    );
    private static final RequiredArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack, String> COLOR = RequiredArgumentBuilder.argument(
        "color", StringArgumentType.word()
    );

    public static LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> build() {
        TYPE.suggests((context, builder) -> {
            for (CropType type : CropType.values()) {
                builder.suggest(type.getModelName());
            }
            return builder.buildFuture();
        });
        CREATE.requires(s -> s.hasPermission(2)).then(COLOR.then(TYPE.executes(context -> create(
            context.getSource().arch$getPlayer(),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            context.getSource()::arch$sendSuccess,
            context.getSource()::arch$sendFailure,
            true
        ))).executes(context -> create(
            context.getSource().arch$getPlayer(),
            CropType.CROP.getModelName(),
            StringArgumentType.getString(context, "color"),
            context.getSource()::arch$sendSuccess,
            context.getSource()::arch$sendFailure,
            true
        )));
        return CREATE;
    }
}
