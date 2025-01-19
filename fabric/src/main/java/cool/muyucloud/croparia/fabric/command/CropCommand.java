package cool.muyucloud.croparia.fabric.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.registry.Crops;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;

import static cool.muyucloud.croparia.command.CropCommand.reportForPlayer;
import static cool.muyucloud.croparia.command.CropCommand.reportSingular;

public class CropCommand {
    private static final LiteralArgumentBuilder<FabricClientCommandSource> CROP =
        LiteralArgumentBuilder.literal("crop");
    private static final RequiredArgumentBuilder<FabricClientCommandSource, String> NAME =
        RequiredArgumentBuilder.argument("name", StringArgumentType.greedyString());

    public static LiteralArgumentBuilder<FabricClientCommandSource> build() {
        NAME.suggests((context, builder) -> Crops.suggestCrops(builder.getInput(), builder.getStart()));
        NAME.executes(context -> {
            FabricClientCommandSource source = context.getSource();
            return reportSingular(StringArgumentType.getString(context, "name"), (msg, broadcast) -> source.sendFeedback(msg.get()), source::sendError);
        });
        CROP.executes(context -> {
            FabricClientCommandSource source = context.getSource();
            if (source.getPlayer() != null) {
                AbstractClientPlayer player = source.getPlayer();
                return reportForPlayer(player, player.level(), (msg, broadcast) -> source.sendFeedback(msg.get()), source::sendError);
            } else {
                source.sendError(Component.translatable("commands.croparia.crop.not_player"));
            }
            return 0;
        });
        CROP.then(NAME);
        return CROP;
    }
}
