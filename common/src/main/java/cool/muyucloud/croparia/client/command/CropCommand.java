package cool.muyucloud.croparia.client.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import cool.muyucloud.croparia.access.CropAccess;
import cool.muyucloud.croparia.data.crop.Crop;
import cool.muyucloud.croparia.registry.Crops;
import cool.muyucloud.croparia.util.Util;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;

import static cool.muyucloud.croparia.command.CropCommand.buildReport;

public class CropCommand {
    private static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> CROP =
        LiteralArgumentBuilder.literal("crop");
    private static final RequiredArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack, String> NAME =
        RequiredArgumentBuilder.argument("name", StringArgumentType.greedyString());

    public static LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> build() {
        NAME.suggests((context, builder) -> {
            SuggestionsBuilder suggestionsBuilder = new SuggestionsBuilder(context.getInput(), builder.getStart());
            Crops.cropNames().forEach(suggestionsBuilder::suggest);
            return suggestionsBuilder.buildFuture();
        });
        NAME.executes(context -> {
            ClientCommandRegistrationEvent.ClientCommandSourceStack source = context.getSource();
            String name = StringArgumentType.getString(context, "name");
            Crop crop = Crops.forName(StringArgumentType.getString(context, "name"));
            if (crop == null) {
                source.arch$sendFailure(Component.translatable("commands.croparia.crop.absent", name));
                return 0;
            }
            Component report = buildReport(crop);
            source.arch$sendSuccess(() -> report, false);
            return crop.getTier();
        });
        CROP.executes(context -> {
            ClientCommandRegistrationEvent.ClientCommandSourceStack source = context.getSource();
            if (source.arch$getPlayer() instanceof AbstractClientPlayer player) {
                CropAccess cropAccess = null;
                if (player.getMainHandItem().getItem() instanceof CropAccess tmpCropAccess) {
                    cropAccess = tmpCropAccess;
                } else if (source.arch$getLevel().getBlockState(Util.lookingAt(player)).getBlock() instanceof CropAccess tmpCropAccess) {
                    cropAccess = tmpCropAccess;
                }
                if (cropAccess != null) {
                    Component report = buildReport(cropAccess.getCrop());
                    source.arch$sendSuccess(() -> report, false);
                    return cropAccess.getCrop().getTier();
                } else {
                    source.arch$sendFailure(Component.translatable("commands.croparia.crop.no_crop"));
                }
            } else {
                source.arch$sendFailure(Component.translatable("commands.croparia.crop.not_player"));
            }
            return 0;
        });
        CROP.then(NAME);
        return CROP;
    }
}
