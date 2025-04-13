package cool.muyucloud.croparia.api.crop.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.access.CropAccess;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.registry.Crops;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CropCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> CROP = Commands.literal("crop").then(
        Commands.argument("name", StringArgumentType.greedyString()
        ).suggests(
            (context, builder) -> Crops.suggestCrops(builder)
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
            failure.send(new TranslatableComponent("commands.croparia.crop.absent", name));
            return 0;
        }
        Component report = buildReport(crop);
        success.send(report, false);
        return crop.getTier();
    }

    public static int reportForPlayer(Player player, Level world, SuccessMessage success, FailureMessage failure) {
        @NotNull CropAccess cropAccess;
        if (player.getMainHandItem().getItem() instanceof CropAccess tmpCropAccess) {
            cropAccess = tmpCropAccess;
        } else if (world.getBlockState(Util.lookingAt(player)).getBlock() instanceof CropAccess tmpCropAccess) {
            cropAccess = tmpCropAccess;
        } else {
            failure.send(new TranslatableComponent("commands.croparia.crop.no_crop"));
            return 0;
        }
        Component report = buildReport(cropAccess.getCrop());
        success.send(report, false);
        return cropAccess.getCrop().getTier();
    }

    public static MutableComponent buildReport(@NotNull Crop crop) {
        MutableComponent name = new TranslatableComponent("commands.croparia.crop.name", crop.getName());
        MutableComponent translation = new TranslatableComponent(
            "commands.croparia.crop.translation",
            new TranslatableComponent(crop.getTranslationKey()).withStyle(CommonCommandRoot.copyText(crop.getTranslationKey()))
        );
        MutableComponent material = new TranslatableComponent(
            "commands.croparia.crop.material",
            new TextComponent(crop.taggableMaterial())
                .withStyle(CommonCommandRoot.suggestCommand("give @s", Objects.requireNonNull(crop.getMaterialItem().arch$registryName()).toString()))
                .withStyle(CommonCommandRoot.hoverItem(crop.getMaterialItem().arch$registryName()))
                .withStyle(CommonCommandRoot.inlineMouseBehavior())
        );
        MutableComponent tier = new TranslatableComponent(
            "commands.croparia.crop.tier",
            new TextComponent(crop.getTier() + "")
                .withStyle(CommonCommandRoot.suggestCommand("give @s", CropariaItems.getCroparia(crop.getTier()).getId().toString()))
                .withStyle(CommonCommandRoot.hoverItem(CropariaItems.getCroparia(crop.getTier()).get()))
                .withStyle(CommonCommandRoot.inlineMouseBehavior())
        );
        MutableComponent color = new TranslatableComponent(
            "commands.croparia.crop.color",
            new TextComponent(crop.serializeColor()).withStyle(CommonCommandRoot.color(crop.getColor()))
                .withStyle(CommonCommandRoot.copyText(crop.serializeColor()))
        );
        MutableComponent type = new TranslatableComponent(
            "commands.croparia.crop.type",
            new TextComponent(crop.getType().getModelName()).withStyle(CommonCommandRoot.copyText(crop.getType().getModelName()))
        );
        MutableComponent seed = new TranslatableComponent(
            "commands.croparia.crop.seed",
            new TextComponent(crop.getSeedId().toString())
                .withStyle(CommonCommandRoot.suggestCommand("give @s", crop.getSeedId().toString()))
                .withStyle(CommonCommandRoot.hoverItem(crop.getSeedItem()))
                .withStyle(CommonCommandRoot.inlineMouseBehavior())
        );
        MutableComponent fruit = new TranslatableComponent(
            "commands.croparia.crop.fruit",
            new TextComponent(crop.getFruitId().toString())
                .withStyle(CommonCommandRoot.suggestCommand("give @s", crop.getFruitId().toString()))
                .withStyle(CommonCommandRoot.hoverItem(crop.getFruitItem()))
                .withStyle(CommonCommandRoot.inlineMouseBehavior())
        );
        MutableComponent cropBlock = new TranslatableComponent(
            "commands.croparia.crop.cropBlock",
            new TextComponent(crop.getBlockId().toString())
                .withStyle(CommonCommandRoot.suggestCommand("setblock ~ ~ ~", crop.getBlockId() + "[age=7]"))
                .withStyle(CommonCommandRoot.hoverText(crop.getCropBlock().getName()))
                .withStyle(CommonCommandRoot.inlineMouseBehavior())
        );
        MutableComponent status = diagnose(crop);
        return name.append("\n")
            .append(translation).append("\n")
            .append(material).append("\n")
            .append(tier).append("\n")
            .append(color).append("\n")
            .append(type).append("\n")
            .append(seed).append("\n")
            .append(fruit).append("\n")
            .append(cropBlock).append("\n")
            .append(status);
    }

    public static MutableComponent diagnose(@NotNull Crop crop) {
        if (crop.getMaterialItem() == Items.AIR) {
            return new TranslatableComponent("commands.croparia.crop.status.material").withStyle(ChatFormatting.RED);
        }
        if (crop.getSeedItem() == Items.AIR) {
            return new TranslatableComponent("commands.croparia.crop.status.seed").withStyle(ChatFormatting.RED);
        }
        if (crop.getFruitItem() == Items.AIR) {
            return new TranslatableComponent("commands.croparia.crop.status.fruit").withStyle(ChatFormatting.RED);
        }
        if (crop.getCropBlock() == Blocks.AIR) {
            return new TranslatableComponent("commands.croparia.crop.status.crop_block").withStyle(ChatFormatting.RED);
        }
        return new TranslatableComponent("commands.croparia.crop.status.good").withStyle(ChatFormatting.GREEN);
    }
}
