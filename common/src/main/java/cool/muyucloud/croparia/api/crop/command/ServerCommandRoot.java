package cool.muyucloud.croparia.api.crop.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ServerCommandRoot {
    private static final LiteralArgumentBuilder<CommandSourceStack> ROOT = Commands.literal("cropariaServer")
        .requires(s -> s.hasPermission(2))
        .then(DumpCommand.build())
        .then(DumpBuiltinCommand.build())
        .then(CropCommand.build())
        .then(ConfigCommand.buildInfusor())
        .then(ConfigCommand.buildRitual());

    public static void register() {
        CropariaIf.LOGGER.debug("Registering commands");
        CommandRegistrationEvent.EVENT.register((dispatcher, registry, selection) -> dispatcher.register(ROOT));
    }

    public static Style suggestCommand(String command, Object... args) {
        return Style.EMPTY.withUnderlined(true).withClickEvent(
            new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command.formatted(args))
        );
    }

    public static Style copyText(String text) {
        return Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text));
    }

    public static Style openFile(String path) {
        return Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, path));
    }

    public static Style hoverItem(ResourceLocation id) {
        return hoverItem(BuiltInRegistries.ITEM.getValue(id));
    }

    public static Style hoverItem(Item item) {
        return item == Items.AIR ? Style.EMPTY : Style.EMPTY.withHoverEvent(new HoverEvent(
            HoverEvent.Action.SHOW_ITEM,
            new HoverEvent.ItemStackInfo(item.getDefaultInstance())
        ));
    }

    public static Style hoverText(String text) {
        return Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(text)));
    }

    public static Style hoverText(Component text) {
        return Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, text));
    }

    public static Style blockMouseBehavior() {
        return Style.EMPTY.withUnderlined(true).withColor(ChatFormatting.WHITE);
    }

    public static Style inlineMouseBehavior() {
        return Style.EMPTY.withUnderlined(true).withColor(ChatFormatting.GRAY);
    }
}
