package cool.muyucloud.croparia.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class CropCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> CROP = Commands.literal("crop").requires(
        s -> s.hasPermission(2)
    ).executes(context -> {
        if (context.getSource().isPlayer()) {
            Player player = context.getSource().getPlayerOrException();
            Item item = player.getWeaponItem().getItem();
            if (item instanceof CropAccess cropAccess) {
                Component report = buildReport(cropAccess.getCrop());
                context.getSource().sendSuccess(() -> report, false);
                return cropAccess.getCrop().getTier();
            } else {
                Block block = context.getSource().getLevel().getBlockState(Util.lookingAt(player)).getBlock();
                if (block instanceof CropAccess cropAccess) {
                    Component report = buildReport(cropAccess.getCrop());
                    context.getSource().sendSuccess(() -> report, false);
                    return cropAccess.getCrop().getTier();
                }
            }
            context.getSource().sendFailure(Component.translatable("commands.croparia.crop.notCrop"));
            return 0;
        } else {
            context.getSource().sendFailure(Component.translatable("commands.croparia.crop.notPlayer"));
            return 0;
        }
    }).then(Commands.argument("name", StringArgumentType.greedyString()).suggests((context, builder) -> {
        SuggestionsBuilder suggestionsBuilder = new SuggestionsBuilder(context.getInput(), builder.getStart());
        Crops.cropNames().forEach(suggestionsBuilder::suggest);
        return suggestionsBuilder.buildFuture();
    }).executes(context -> {
        String name = StringArgumentType.getString(context, "name");
        Crop crop = Crops.forName(StringArgumentType.getString(context, "name"));
        if (crop == null) {
            context.getSource().sendFailure(Component.translatable("commands.croparia.crop.absent", name));
            return 0;
        }
        Component report = buildReport(crop);
        context.getSource().sendSuccess(() -> report, false);
        return crop.getTier();
    }));

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return CROP;
    }

    public static MutableComponent buildReport(@NotNull Crop crop) {
        MutableComponent name = Component.translatable("commands.croparia.crop.name", crop.getName());
        MutableComponent translation = Component.translatable("commands.croparia.crop.translation", Component.translatable(crop.getTranslationKey()));
        MutableComponent material = Component.translatable("commands.croparia.crop.material", Component.literal(crop.serializeMaterial()).withStyle(ServerRoot.suggestCommand("/give @s %s", crop.getMaterialItem().arch$registryName())).withStyle(ServerRoot.hoverItem(crop.getMaterialItem().arch$registryName())).withStyle(ServerRoot.inlineMouseBehavior()));
        MutableComponent tier = Component.translatable("commands.croparia.crop.tier", Component.literal(crop.getTier() + "").withStyle(ServerRoot.suggestCommand("/give @s %s", CropariaItems.getCroparia(crop.getTier()).getId())).withStyle(ServerRoot.hoverItem(CropariaItems.getCroparia(crop.getTier()).get())).withStyle(ServerRoot.inlineMouseBehavior()));
        MutableComponent color = Component.translatable("commands.croparia.crop.color", Component.literal(crop.serializeColor()).withColor(crop.getColor()));
        MutableComponent type = Component.translatable("commands.croparia.crop.type", crop.getType().getModelName());
        MutableComponent seed = Component.translatable("commands.croparia.crop.seed", Component.literal(crop.getSeedId().toString()).withStyle(ServerRoot.suggestCommand("/give @s %s", crop.getSeedId().toString())).withStyle(ServerRoot.hoverItem(crop.getSeedItem())).withStyle(ServerRoot.inlineMouseBehavior()));
        MutableComponent fruit = Component.translatable("commands.croparia.crop.fruit", Component.literal(crop.getFruitId().toString()).withStyle(ServerRoot.suggestCommand("/give @s %s", crop.getFruitId().toString())).withStyle(ServerRoot.hoverItem(crop.getFruitItem())).withStyle(ServerRoot.inlineMouseBehavior()));
        MutableComponent cropBlock = Component.translatable("commands.croparia.crop.cropBlock", Component.literal(crop.getBlockId().toString()).withStyle(ServerRoot.suggestCommand("/setblock ~ ~ ~ %s[age=7]", crop.getBlockId().toString())).withStyle(ServerRoot.hoverText(crop.getCropBlock().getName())).withStyle(ServerRoot.inlineMouseBehavior()));
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
            return Component.translatable("commands.croparia.crop.status.cropBlock").withStyle(ChatFormatting.RED);
        }
        return Component.translatable("commands.croparia.crop.status.good").withStyle(ChatFormatting.GREEN);
    }
}
