package cool.muyucloud.croparia.client.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.api.crop.Crops;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.TranslatableComponent;

import static cool.muyucloud.croparia.api.crop.command.CropCommand.reportForPlayer;
import static cool.muyucloud.croparia.api.crop.command.CropCommand.reportSingular;

public class CropCommand {
    private static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> CROP =
        LiteralArgumentBuilder.literal("crop");
    private static final RequiredArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack, String> NAME =
        RequiredArgumentBuilder.argument("name", StringArgumentType.greedyString());

    public static LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> build() {
        NAME.suggests((context, builder) -> Crops.suggestCrops(builder));
        NAME.executes(context -> {
            ClientCommandRegistrationEvent.ClientCommandSourceStack source = context.getSource();
            return reportSingular(StringArgumentType.getString(context, "name"), source::arch$sendSuccess, source::arch$sendFailure);
        });
        CROP.executes(context -> {
            ClientCommandRegistrationEvent.ClientCommandSourceStack source = context.getSource();
            if (source.arch$getPlayer() != null) {
                AbstractClientPlayer player = source.arch$getPlayer();
                return reportForPlayer(player, player.getLevel(), source::arch$sendSuccess, source::arch$sendFailure);
            } else {
                source.arch$sendFailure(new TranslatableComponent("commands.croparia.crop.not_player"));
            }
            return 0;
        });
        CROP.then(NAME);
        return CROP;
    }
}
