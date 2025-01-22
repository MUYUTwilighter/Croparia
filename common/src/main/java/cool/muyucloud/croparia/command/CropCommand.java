package cool.muyucloud.croparia.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.access.CropAccess;
import cool.muyucloud.croparia.data.crop.Crop;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.Crops;
import cool.muyucloud.croparia.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class CropCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> CROP = Commands.literal("crop").then(
        Commands.argument("name", StringArgumentType.greedyString()
        ).suggests(
            (context, builder) -> Crops.cropSuggestions(builder.getInput(), builder.getStart())
        ).executes(context -> {
            String name = StringArgumentType.getString(context, "name");
            return reportSingular(name, context.getSource()::sendSuccess, context.getSource()::sendFailure);
        })
    );

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return CROP;
    }

    public static int reportSingular(String name, SuccessMessage success, FailureMessage failure) {
        Crop crop = Crops.forName(name);
        if (crop == null) {
            failure.send(Component.translatable("commands.croparia.crop.absent", name));
            return 0;
        }
        Component report = buildReport(crop);
        success.send(() -> report, false);
        return crop.getTier();
    }

    public static int reportForPlayer(Player player, Level world, SuccessMessage success, FailureMessage failure) {
        @NotNull CropAccess cropAccess;
        if (player.getWeaponItem().getItem() instanceof CropAccess tmpCropAccess) {
            cropAccess = tmpCropAccess;
        } else if (world.getBlockState(Util.lookingAt(player)).getBlock() instanceof CropAccess tmpCropAccess) {
            cropAccess = tmpCropAccess;
        } else {
            failure.send(Component.translatable("commands.croparia.crop.no_crop"));
            return 0;
        }
        Component report = buildReport(cropAccess.getCrop());
        success.send(() -> report, false);
        return cropAccess.getCrop().getTier();
    }

    public static MutableComponent buildReport(@NotNull Crop crop) {
        MutableComponent name = Component.translatable("commands.croparia.crop.name", crop.getName());
        MutableComponent translation = Component.translatable("commands.croparia.crop.translation", Component.translatable(crop.getTranslationKey()).withStyle(ServerCommandRoot.hoverText(crop.getTranslationKey())).withStyle(ServerCommandRoot.copyText(crop.getTranslationKey())));
        MutableComponent material = Component.translatable("commands.croparia.crop.material", Component.literal(crop.taggableMaterial()).withStyle(ServerCommandRoot.suggestCommand("/give @s %s", crop.getMaterialItem().arch$registryName())).withStyle(ServerCommandRoot.hoverItem(crop.getMaterialItem().arch$registryName())).withStyle(ServerCommandRoot.inlineMouseBehavior()));
        MutableComponent tier = Component.translatable("commands.croparia.crop.tier", Component.literal(crop.getTier() + "").withStyle(ServerCommandRoot.suggestCommand("/give @s %s", CropariaItems.getCroparia(crop.getTier()).getId())).withStyle(ServerCommandRoot.hoverItem(CropariaItems.getCroparia(crop.getTier()).get())).withStyle(ServerCommandRoot.inlineMouseBehavior()));
        MutableComponent color = Component.translatable("commands.croparia.crop.color", Component.literal(crop.serializeColor()).withColor(crop.getColor()));
        MutableComponent type = Component.translatable("commands.croparia.crop.type", crop.getType().getModelName());
        MutableComponent seed = Component.translatable("commands.croparia.crop.seed", Component.literal(crop.getSeedId().toString()).withStyle(ServerCommandRoot.suggestCommand("/give @s %s", crop.getSeedId().toString())).withStyle(ServerCommandRoot.hoverItem(crop.getSeedItem())).withStyle(ServerCommandRoot.inlineMouseBehavior()));
        MutableComponent fruit = Component.translatable("commands.croparia.crop.fruit", Component.literal(crop.getFruitId().toString()).withStyle(ServerCommandRoot.suggestCommand("/give @s %s", crop.getFruitId().toString())).withStyle(ServerCommandRoot.hoverItem(crop.getFruitItem())).withStyle(ServerCommandRoot.inlineMouseBehavior()));
        MutableComponent cropBlock = Component.translatable("commands.croparia.crop.cropBlock", Component.literal(crop.getBlockId().toString()).withStyle(ServerCommandRoot.suggestCommand("/setblock ~ ~ ~ %s[age=7]", crop.getBlockId().toString())).withStyle(ServerCommandRoot.hoverText(crop.getCropBlock().getName())).withStyle(ServerCommandRoot.inlineMouseBehavior()));
        MutableComponent status = diagnose(crop);
        return name.append("\n").append(translation).append("\n").append(material).append("\n").append(tier).append("\n").append(color).append("\n").append(type).append("\n").append(seed).append("\n").append(fruit).append("\n").append(cropBlock).append("\n").append(status);
    }

    public static MutableComponent diagnose(@NotNull Crop crop) {
        if (crop.getMaterialItem() == Items.AIR) {
            return Component.translatable("commands.croparia.crop.status.material").withStyle(ChatFormatting.RED);
        }
        if (crop.getSeedItem() == Items.AIR) {
            return Component.translatable("commands.croparia.crop.status.seed").withStyle(ChatFormatting.RED);
        }
        if (crop.getFruitItem() == Items.AIR) {
            return Component.translatable("commands.croparia.crop.status.fruit").withStyle(ChatFormatting.RED);
        }
        if (crop.getCropBlock() == Blocks.AIR) {
            return Component.translatable("commands.croparia.crop.status.crop_block").withStyle(ChatFormatting.RED);
        }
        return Component.translatable("commands.croparia.crop.status.good").withStyle(ChatFormatting.GREEN);
    }
}
