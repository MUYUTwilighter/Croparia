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
        "name", StringArgumentType.greedyString()
    );

    static {
        CREATE.requires(s -> s.hasPermission(2));
        COLOR.executes(context -> create(
            CommonCommandRoot.playerOrThrow(context.getSource()),
            null,
            CropType.CROP.getModelName(),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
            true
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
            true
        ));
        NAME.executes(context -> create(
            CommonCommandRoot.playerOrThrow(context.getSource()),
            StringArgumentType.getString(context, "name"),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
            true
        ));
        TYPE.then(NAME);
        COLOR.then(TYPE);
        CREATE.then(COLOR);
    }

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return CREATE;
    }
}