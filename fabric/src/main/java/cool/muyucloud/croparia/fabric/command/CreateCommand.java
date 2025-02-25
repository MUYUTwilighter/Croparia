package cool.muyucloud.croparia.fabric.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.api.crop.CropType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import static cool.muyucloud.croparia.api.crop.command.CreateCommand.create;

public class CreateCommand {
    private static final LiteralArgumentBuilder<FabricClientCommandSource> CREATE = LiteralArgumentBuilder.literal("create");
    private static final RequiredArgumentBuilder<FabricClientCommandSource, String> TYPE = RequiredArgumentBuilder.argument(
        "type", StringArgumentType.word()
    );
    private static final RequiredArgumentBuilder<FabricClientCommandSource, String> COLOR = RequiredArgumentBuilder.argument(
        "color", StringArgumentType.word()
    );

    public static LiteralArgumentBuilder<FabricClientCommandSource> build() {
        TYPE.suggests((context, builder) -> {
            for (CropType type : CropType.values()) {
                builder.suggest(type.getModelName());
            }
            return builder.buildFuture();
        });
        CREATE.requires(s -> s.hasPermission(2)).then(COLOR.then(TYPE.executes(context -> create(
            context.getSource().getPlayer(),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            (msg, broadcast) -> context.getSource().sendFeedback(msg.get()),
            context.getSource()::sendError,
            true
        ))).executes(context -> create(
            context.getSource().getPlayer(),
            CropType.CROP.getModelName(),
            StringArgumentType.getString(context, "color"),
            (msg, broadcast) -> context.getSource().sendFeedback(msg.get()),
            context.getSource()::sendError,
            true
        )));
        return CREATE;
    }
}