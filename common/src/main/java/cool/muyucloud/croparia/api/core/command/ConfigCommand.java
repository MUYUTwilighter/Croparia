package cool.muyucloud.croparia.api.core.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ConfigCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> INFUSOR = Commands.literal("infusor").executes(context -> {
        Texts.success(context.getSource(), Texts.translatable("commands.croparia.config.infusor", CropariaIf.CONFIG.getInfusor().toString()), true);
        return 1;
    }).then(Commands.argument("value", BoolArgumentType.bool()).executes(context -> {
        CropariaIf.CONFIG.setInfusor(BoolArgumentType.getBool(context, "value"));
        Texts.success(context.getSource(), Texts.translatable("commands.croparia.config.infusor", CropariaIf.CONFIG.getInfusor().toString()), true);
        return 1;
    }));
    private static final LiteralArgumentBuilder<CommandSourceStack> RITUAL = Commands.literal("ritual").executes(context -> {
        Texts.success(context.getSource(), Texts.translatable("commands.croparia.config.ritual", CropariaIf.CONFIG.getRitual().toString()), true);
        return 1;
    }).then(Commands.argument("value", BoolArgumentType.bool()).executes(context -> {
        CropariaIf.CONFIG.setRitual(BoolArgumentType.getBool(context, "value"));
        Texts.success(context.getSource(), Texts.translatable("commands.croparia.config.ritual", CropariaIf.CONFIG.getRitual().toString()), true);
        return 1;
    }));
    private static final LiteralArgumentBuilder<CommandSourceStack> FRUIT_USE = Commands.literal("fruitUse").executes(context -> {
        Texts.success(context.getSource(), Texts.translatable("commands.croparia.config.fruitUse", CropariaIf.CONFIG.getFruitUse().toString()), true);
        return 1;
    }).then(Commands.argument("value", BoolArgumentType.bool()).executes(context -> {
        CropariaIf.CONFIG.setFruitUse(BoolArgumentType.getBool(context, "value"));
        Texts.success(context.getSource(), Texts.translatable("commands.croparia.config.fruitUse", CropariaIf.CONFIG.getFruitUse().toString()), true);
        return 1;
    }));
    private static final LiteralArgumentBuilder<CommandSourceStack> AUTO_RELOAD = Commands.literal("dump").executes(context -> {
        Texts.success(context.getSource(), Texts.translatable("commands.croparia.config.autoReload", CropariaIf.CONFIG.getAutoReload().toString()), true);
        return 1;
    }).then(Commands.argument("value", BoolArgumentType.bool()).executes(context -> {
        CropariaIf.CONFIG.setAutoReload(BoolArgumentType.getBool(context, "value"));
        Texts.success(context.getSource(), Texts.translatable("commands.croparia.config.autoReload", CropariaIf.CONFIG.getAutoReload().toString()), true);
        return 1;
    }));
    private static final LiteralArgumentBuilder<CommandSourceStack> OVERRIDE = Commands.literal("OVERRIDE").executes(context -> {
        Texts.success(context.getSource(), Texts.translatable("commands.croparia.config.override", CropariaIf.CONFIG.getOverride().toString()), true);
        return 1;
    }).then(Commands.argument("value", BoolArgumentType.bool()).executes(context -> {
        CropariaIf.CONFIG.setOverride(BoolArgumentType.getBool(context, "value"));
        Texts.success(context.getSource(), Texts.translatable("commands.croparia.config.override", CropariaIf.CONFIG.getOverride().toString()), true);
        return 1;
    }));

    public static ArgumentBuilder<CommandSourceStack, ?> buildInfusor() {
        return INFUSOR;
    }

    public static ArgumentBuilder<CommandSourceStack, ?> buildRitual() {
        return RITUAL;
    }

    public static ArgumentBuilder<CommandSourceStack, ?> buildFruitUse() {
        return FRUIT_USE;
    }

    public static ArgumentBuilder<CommandSourceStack, ?> buildAutoReload() {
        return AUTO_RELOAD;
    }

    public static ArgumentBuilder<CommandSourceStack, ?> buildOverride() {
        return OVERRIDE;
    }
}
