package cool.muyucloud.croparia.forge.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.registry.Crops;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import static cool.muyucloud.croparia.command.CropCommand.reportForPlayer;
import static cool.muyucloud.croparia.command.CropCommand.reportSingular;

public class CropCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> CROP =
        LiteralArgumentBuilder.literal("crop");
    private static final RequiredArgumentBuilder<CommandSourceStack, String> NAME =
        RequiredArgumentBuilder.argument("name", StringArgumentType.greedyString());

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        NAME.suggests((context, builder) -> Crops.suggestCrops(builder.getInput(), builder.getStart()));
        NAME.executes(context -> {
            CommandSourceStack source = context.getSource();
            return reportSingular(StringArgumentType.getString(context, "name"), source::sendSuccess, source::sendFailure);
        });
        CROP.executes(context -> {
            CommandSourceStack source = context.getSource();
            if (source.getEntity() instanceof Player player) {
                return reportForPlayer(player, player.level(), source::sendSuccess, source::sendFailure);
            } else {
                source.sendFailure(Component.translatable("commands.croparia.crop.not_player"));
            }
            return 0;
        });
        CROP.then(NAME);
        return CROP;
    }
}
