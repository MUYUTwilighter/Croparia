package cool.muyucloud.croparia.fabric.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.api.crop.CropType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import static cool.muyucloud.croparia.api.crop.command.CreateCommand.create;

public class CreateCommand {
    private static final LiteralArgumentBuilder<FabricClientCommandSource> CREATE = LiteralArgumentBuilder.literal("create");
    private static final RequiredArgumentBuilder<FabricClientCommandSource, String> TYPE = RequiredArgumentBuilder.argument(
        "type", StringArgumentType.word()
    );
    private static final RequiredArgumentBuilder<FabricClientCommandSource, String> COLOR = RequiredArgumentBuilder.argument(
        "color", StringArgumentType.word()
    );
    private static final RequiredArgumentBuilder<FabricClientCommandSource, String> NAME = RequiredArgumentBuilder.argument(
        "name", StringArgumentType.greedyString()
    );

    static {
        CREATE.requires(s -> s.hasPermission(2));
        COLOR.executes(context -> {
            if (context.getSource().getEntity() instanceof Player player) {
                return create(
                    player,
                    null,
                    CropType.CROP.getModelName(),
                    StringArgumentType.getString(context, "color"),
                    (msg, broadcast) -> context.getSource().sendFeedback(msg.get()),
                    context.getSource()::sendError,
                    true
                );
            } else {
                context.getSource().sendError(Component.translatable("commands.croparia.crop.not_player"));
                return -1;
            }
        });
        TYPE.suggests((context, builder) -> {
            for (CropType type : CropType.values()) {
                builder.suggest(type.getModelName());
            }
            return builder.buildFuture();
        }).executes(context -> create(
            context.getSource().getPlayer(),
            null,
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            (msg, broadcast) -> context.getSource().sendFeedback(msg.get()),
            context.getSource()::sendError,
            true
        ));
        NAME.executes(context -> create(
            context.getSource().getPlayer(),
            StringArgumentType.getString(context, "name"),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            (msg, broadcast) -> context.getSource().sendFeedback(msg.get()),
            context.getSource()::sendError,
            true
        ));
        TYPE.then(NAME);
        COLOR.then(TYPE);
        CREATE.then(COLOR);
    }

    public static LiteralArgumentBuilder<FabricClientCommandSource> build() {
        return CREATE;
    }
}