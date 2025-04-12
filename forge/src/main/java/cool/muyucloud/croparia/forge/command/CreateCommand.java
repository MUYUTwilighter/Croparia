package cool.muyucloud.croparia.forge.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.api.crop.CropType;
import cool.muyucloud.croparia.api.crop.command.CommonCommandRoot;
import net.minecraft.commands.CommandSourceStack;

import static cool.muyucloud.croparia.api.crop.command.CreateCommand.create;

public class CreateCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> CREATE = LiteralArgumentBuilder.literal("create");
    private static final RequiredArgumentBuilder<CommandSourceStack, String> TYPE = RequiredArgumentBuilder.argument(
        "type", StringArgumentType.word()
    );
    private static final RequiredArgumentBuilder<CommandSourceStack, String> COLOR = RequiredArgumentBuilder.argument(
        "color", StringArgumentType.word()
    );
    private static final RequiredArgumentBuilder<CommandSourceStack, String> NAME = RequiredArgumentBuilder.argument(
        "name", StringArgumentType.word()
    );
    private static final LiteralArgumentBuilder<CommandSourceStack> REPLACE = LiteralArgumentBuilder.literal("replace");

    static {
        CREATE.requires(s -> s.hasPermission(2));
        COLOR.executes(context -> create(
            CommonCommandRoot.playerOrThrow(context.getSource()),
            null,
            CropType.CROP.getModelName(),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
            true, false
        ));
        TYPE.suggests((context, builder) -> {
            for (CropType type : CropType.values()) {
                builder.suggest(type.getModelName());
            }
            return builder.buildFuture();
        }).executes(context -> create(
            CommonCommandRoot.playerOrThrow(context.getSource()),
            null,
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
            true, false
        ));
        NAME.executes(context -> create(
            CommonCommandRoot.playerOrThrow(context.getSource()),
            StringArgumentType.getString(context, "name"),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
            true, false
        ));
        REPLACE.executes(context -> create(
            CommonCommandRoot.playerOrThrow(context.getSource()),
            StringArgumentType.getString(context, "name"),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
            true, true
        ));
        NAME.then(REPLACE);
        TYPE.then(NAME);
        COLOR.then(TYPE);
        CREATE.then(COLOR);
    }

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return CREATE;
    }
}