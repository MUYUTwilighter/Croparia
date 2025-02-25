package cool.muyucloud.croparia.forge.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.api.crop.CropType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

import static cool.muyucloud.croparia.api.crop.command.CreateCommand.create;

public class CreateCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> CREATE = LiteralArgumentBuilder.literal("create");
    private static final RequiredArgumentBuilder<CommandSourceStack, String> TYPE = RequiredArgumentBuilder.argument(
        "type", StringArgumentType.word()
    );
    private static final RequiredArgumentBuilder<CommandSourceStack, String> COLOR = RequiredArgumentBuilder.argument(
        "color", StringArgumentType.word()
    );

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        TYPE.suggests((context, builder) -> {
            for (CropType type : CropType.values()) {
                builder.suggest(type.getModelName());
            }
            return builder.buildFuture();
        });
        CREATE.requires(s -> s.hasPermission(2)).then(COLOR.then(TYPE.executes(context -> create(
            (Player) context.getSource().getEntity(),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            (msg, broadcast) -> context.getSource().sendSuccess(msg, broadcast),
            context.getSource()::sendFailure,
            true
        ))).executes(context -> create(
            (Player) context.getSource().getEntity(),
            CropType.CROP.getModelName(),
            StringArgumentType.getString(context, "color"),
            (msg, broadcast) -> context.getSource().sendSuccess(msg, broadcast),
            context.getSource()::sendFailure,
            true
        )));
        return CREATE;
    }
}