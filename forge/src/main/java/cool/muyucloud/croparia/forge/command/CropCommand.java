package cool.muyucloud.croparia.forge.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.api.crop.command.CommonCommandRoot;
import cool.muyucloud.croparia.registry.Crops;
import net.minecraft.commands.CommandSourceStack;

import static cool.muyucloud.croparia.api.crop.command.CropCommand.reportForPlayer;
import static cool.muyucloud.croparia.api.crop.command.CropCommand.reportSingular;

public class CropCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> CROP =
        LiteralArgumentBuilder.literal("crop");
    private static final RequiredArgumentBuilder<CommandSourceStack, String> NAME =
        RequiredArgumentBuilder.argument("name", StringArgumentType.greedyString());

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        NAME.suggests((context, builder) -> Crops.suggestCrops(builder));
        NAME.executes(context -> {
            CommandSourceStack source = context.getSource();
            return reportSingular(StringArgumentType.getString(context, "name"), source::sendSuccess, source::sendFailure);
        });
        CROP.executes(context -> reportForPlayer(
            CommonCommandRoot.playerOrThrow(context.getSource()),
            context.getSource().getLevel(),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure)
        );
        CROP.then(NAME);
        return CROP;
    }
}
