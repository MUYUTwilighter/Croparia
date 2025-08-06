package cool.muyucloud.croparia.api.core.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.CropAccess;
import cool.muyucloud.croparia.api.crop.block.CropariaCropBlock;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.Crops;
import cool.muyucloud.croparia.registry.DgRegistries;
import cool.muyucloud.croparia.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class CropCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> CROP = Commands.literal("crop").then(
        Commands.argument("id", ResourceLocationArgument.id()
        ).suggests(
            (context, builder) -> Crops.cropSuggestions(builder)
        ).executes(context -> {
            ResourceLocation id = ResourceLocationArgument.getId(context, "id");
            return reportSingular(id, context.getSource()::sendSuccess, context.getSource()::sendFailure);
        })
    );

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return CROP;
    }

    public static int reportSingular(ResourceLocation id, SuccessMessage success, FailureMessage failure) {
        Optional<Crop> crop = DgRegistries.CROPS.forName(id);
        if (crop.isEmpty()) {
            failure.send(Component.translatable("commands.croparia.crop.absent", id));
            return 0;
        }
        Component report = buildReport(crop.get());
        success.send(() -> report, false);
        return crop.get().getTier();
    }

    public static int reportForPlayer(Player player, Level world, SuccessMessage success, FailureMessage failure) {
        Crop crop = null;
        if (player.getWeaponItem().getItem() instanceof CropAccess<?> access) {
            crop = CropAccess.tryGet(access);
        } else if (world.getBlockState(Util.lookingAt(player)).getBlock() instanceof CropAccess<?> access) {
            crop = CropAccess.tryGet(access);
        }
        if (crop == null) {
            failure.send(Component.translatable("commands.croparia.crop.no_crop"));
            return 0;
        }
        Component report = buildReport(crop);
        success.send(() -> report, false);
        return crop.getTier();
    }

    public static MutableComponent buildReport(@NotNull Crop crop) {
        MutableComponent name = Component.translatable("commands.croparia.crop.id", crop.getKey());
        MutableComponent translation = Component.translatable(
            "commands.croparia.crop.translationKey",
            Component.translatable(crop.getTranslationKey())
                .withStyle(CommonCommandRoot.hoverText(crop.getTranslationKey()))
                .withStyle(CommonCommandRoot.copyText(crop.getTranslationKey()))
        );
        MutableComponent material = Component.translatable(
            "commands.croparia.crop.material",
            Component.literal(crop.getMaterialName())
                .withStyle(CommonCommandRoot.suggestCommand("give @s", Objects.requireNonNull(crop.getResult().arch$registryName()).toString()))
                .withStyle(CommonCommandRoot.hoverItem(crop.getMaterialStack()))
                .withStyle(CommonCommandRoot.inlineMouseBehavior())
        );
        MutableComponent tier = Component.translatable(
            "commands.croparia.crop.tier",
            Component.literal(crop.getTier() + "")
                .withStyle(CommonCommandRoot.suggestCommand("give @s", CropariaItems.getCroparia(crop.getTier()).getId().toString()))
                .withStyle(CommonCommandRoot.hoverItem(CropariaItems.getCroparia(crop.getTier()).get()))
                .withStyle(CommonCommandRoot.inlineMouseBehavior())
        );
        MutableComponent color = Component.translatable(
            "commands.croparia.crop.color",
            Component.literal(crop.getColorForm()).withColor(crop.getColor().getValue())
                .withStyle(CommonCommandRoot.copyText(crop.getColorForm()))
        );
        MutableComponent type = Component.translatable(
            "commands.croparia.crop.type", Component.literal(crop.getType())
                .withStyle(CommonCommandRoot.copyText(crop.getType()))
        );
        MutableComponent seed = Component.translatable(
            "commands.croparia.crop.seed",
            Component.literal(crop.getSeedId().toString())
                .withStyle(CommonCommandRoot.suggestCommand("give @s", crop.getSeedId().toString()))
                .withStyle(CommonCommandRoot.hoverItem(crop.getSeedId()))
                .withStyle(CommonCommandRoot.inlineMouseBehavior())
        );
        MutableComponent fruit = Component.translatable(
            "commands.croparia.crop.fruit",
            Component.literal(crop.getFruitId().toString())
                .withStyle(CommonCommandRoot.suggestCommand("give @s", crop.getFruitId().toString()))
                .withStyle(CommonCommandRoot.hoverItem(crop.getFruitId()))
                .withStyle(CommonCommandRoot.inlineMouseBehavior())
        );
        MutableComponent cropBlock = Component.translatable(
            "commands.croparia.crop.cropBlock",
            Component.literal(crop.getBlockId().toString())
                .withStyle(CommonCommandRoot.suggestCommand("setblock ~ ~ ~", crop.getBlockId() + "[age=7]"))
                .withStyle(CommonCommandRoot.hoverText(crop.getCropBlock().map(CropariaCropBlock::getName).orElse(Component.literal("error"))))
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
        if (crop.getResult() == Items.AIR) {
            return Component.translatable("commands.croparia.crop.status.material").withStyle(ChatFormatting.RED);
        }
        if (!crop.shouldLoad()) {
            return Component.translatable("commands.croparia.crop.status.unavailable").withStyle(ChatFormatting.YELLOW);
        }
        return Component.translatable("commands.croparia.crop.status.good").withStyle(ChatFormatting.GREEN);
    }
}
