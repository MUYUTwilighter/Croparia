package cool.muyucloud.croparia.client.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.registry.Crops;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;

import static cool.muyucloud.croparia.command.CropCommand.reportForPlayer;
import static cool.muyucloud.croparia.command.CropCommand.reportSingular;

public class CropCommand {
    private static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> CROP =
        LiteralArgumentBuilder.literal("crop");
    private static final RequiredArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack, String> NAME =
        RequiredArgumentBuilder.argument("name", StringArgumentType.greedyString());

    public static LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> build() {
        NAME.suggests((context, builder) -> Crops.cropSuggestions(builder.getInput(), builder.getStart()));
        NAME.executes(context -> {
            ClientCommandRegistrationEvent.ClientCommandSourceStack source = context.getSource();
            return reportSingular(StringArgumentType.getString(context, "name"), source::arch$sendSuccess, source::arch$sendFailure);
        });
        CROP.executes(context -> {
            ClientCommandRegistrationEvent.ClientCommandSourceStack source = context.getSource();
            if (source.arch$getPlayer() instanceof AbstractClientPlayer player) {
                return reportForPlayer(player, player.level(), source::arch$sendSuccess, source::arch$sendFailure);
            } else {
                source.arch$sendFailure(Component.translatable("commands.croparia.crop.not_player"));
            }
            return 0;
        });
        CROP.then(NAME);
        return CROP;
    }
}
