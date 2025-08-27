package cool.muyucloud.croparia.api.core.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.CropAccess;
import cool.muyucloud.croparia.api.crop.block.CropariaCropBlock;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.Crops;
import cool.muyucloud.croparia.registry.DgRegistries;
import cool.muyucloud.croparia.util.Util;
import cool.muyucloud.croparia.util.text.FailureMessenger;
import cool.muyucloud.croparia.util.text.SuccessMessenger;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
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
            return reportSingular(id, Texts.success(context.getSource()), Texts.failure(context.getSource()));
        })
    );

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return CROP;
    }

    public static int reportSingular(ResourceLocation id, SuccessMessenger success, FailureMessenger failure) {
        Optional<Crop> crop = DgRegistries.CROPS.forName(id);
        if (crop.isEmpty()) {
            failure.send(Texts.translatable("commands.croparia.crop.absent", id));
            return 0;
        }
        MutableComponent report = buildReport(crop.get());
        success.send(report, false);
        return crop.get().getTier();
    }

    public static int reportForPlayer(Player player, Level world, SuccessMessenger success, FailureMessenger failure) {
        Crop crop = null;
        if (player.getWeaponItem().getItem() instanceof CropAccess<?> access) {
            crop = CropAccess.tryGet(access);
        } else if (world.getBlockState(Util.lookingAt(player)).getBlock() instanceof CropAccess<?> access) {
            crop = CropAccess.tryGet(access);
        }
        if (crop == null) {
            failure.send(Texts.translatable("commands.croparia.crop.no_crop"));
            return 0;
        }
        MutableComponent report = buildReport(crop);
        success.send(report, false);
        return crop.getTier();
    }

    public static MutableComponent buildReport(@NotNull Crop crop) {
        MutableComponent name = Texts.translatable("commands.croparia.crop.id", crop.getKey().toString());
        MutableComponent translation = Texts.translatable(
            "commands.croparia.crop.translationKey",
            Texts.forStyles(Texts.translatable(crop.getTranslationKey()),
                Texts.hoverText(crop.getTranslationKey()),
                Texts.copyText(crop.getTranslationKey()))
        );
        MutableComponent material = Texts.translatable("commands.croparia.crop.material", Texts.literal(
            crop.getMaterialName(),
            Texts.suggestCommand("give @s", Objects.requireNonNull(crop.getResult().arch$registryName()).toString()),
            Texts.hoverItem(crop.getMaterialStack()),
            Texts.inlineMouseBehavior()
        ));
        MutableComponent tier = Texts.forStyles(Texts.translatable(
            "commands.croparia.crop.tier", Texts.literal(crop.getTier() + "",
                Texts.suggestCommand("give @s", CropariaItems.getCroparia(crop.getTier()).getId().toString()),
                Texts.hoverItem(CropariaItems.getCroparia(crop.getTier()).get()),
                Texts.inlineMouseBehavior())
        ));
        MutableComponent color = Texts.translatable(
            "commands.croparia.crop.color",
            Texts.literal(crop.getColorForm(), Texts.copyText(crop.getColorForm())).withColor(crop.getColor().getValue())
        );
        MutableComponent type = Texts.translatable(
            "commands.croparia.crop.type", Texts.literal(crop.getType(), Texts.copyText(crop.getType()))
        );
        MutableComponent seed = Texts.translatable(
            "commands.croparia.crop.seed",
            Texts.literal(crop.getSeedId().toString(),
                Texts.suggestCommand("give @s", crop.getSeedId().toString()),
                Texts.hoverItem(crop.getSeedId()),
                Texts.inlineMouseBehavior())
        );
        MutableComponent fruit = Texts.translatable(
            "commands.croparia.crop.fruit",
            Texts.literal(crop.getFruitId().toString(),
                Texts.suggestCommand("give @s", crop.getFruitId().toString()),
                Texts.hoverItem(crop.getFruitId()),
                Texts.inlineMouseBehavior())
        );
        MutableComponent cropBlock = Texts.translatable(
            "commands.croparia.crop.cropBlock",
            Texts.literal(crop.getBlockId().toString(),
                Texts.suggestCommand("setblock ~ ~ ~", crop.getBlockId() + "[age=7]"),
                Texts.hoverText(crop.getCropBlock().map(CropariaCropBlock::getName).orElse(Texts.literal("error"))),
                Texts.inlineMouseBehavior())
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
            return Texts.translatable("commands.croparia.crop.status.material").withStyle(ChatFormatting.RED);
        }
        if (!crop.shouldLoad()) {
            return Texts.translatable("commands.croparia.crop.status.unavailable").withStyle(ChatFormatting.YELLOW);
        }
        return Texts.translatable("commands.croparia.crop.status.good").withStyle(ChatFormatting.GREEN);
    }
}
