package cool.muyucloud.croparia.util.text;

import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("unused")
public class Texts {
    public static MutableComponent literal(String text, Style... styles) {
        return forStyles(Component.literal(text), styles);
    }

    public static MutableComponent translatable(String key, Object... args) {
        return Component.translatable(key, args);
    }

    public static MutableComponent forStyles(MutableComponent component, Style... styles) {
        for (Style style : styles) {
            component.withStyle(style);
        }
        return component;
    }

    public static void chat(Player player, Component message) {
        player.displayClientMessage(message, false);
    }

    public static void chat(CommandSourceStack source, Component msg) {
        source.sendSystemMessage(msg);
    }

    public static void chat(ClientCommandRegistrationEvent.ClientCommandSourceStack source, Component msg) {
        success(source, msg);
    }

    public static void overlay(Player player, Component message) {
        player.displayClientMessage(message, true);
    }

    public static SuccessMessage success(CommandSourceStack source) {
        return (msg, broadcast) -> success(source, msg, broadcast);
    }

    public static SuccessMessage success(ClientCommandRegistrationEvent.ClientCommandSourceStack source) {
        return (msg, broadcast) -> success(source, msg, broadcast);
    }

    public static void success(CommandSourceStack source, Component message, boolean broadcast) {
        source.sendSuccess(() -> message, broadcast);
    }

    public static void success(CommandSourceStack source, Component message) {
        success(source, message, false);
    }

    public static void success(ClientCommandRegistrationEvent.ClientCommandSourceStack source, Component message, boolean broadcast) {
        source.arch$sendSuccess(() -> message, broadcast);
    }

    public static void success(ClientCommandRegistrationEvent.ClientCommandSourceStack source, Component message) {
        success(source, message, false);
    }

    public static void broadcastSuccess(CommandSourceStack source, Component message) {
        source.sendSuccess(() -> message, true);
    }

    public static void broadcastSuccess(ClientCommandRegistrationEvent.ClientCommandSourceStack source, Component message) {
        source.arch$sendSuccess(() -> message, true);
    }

    public static FailureMessage failure(CommandSourceStack source) {
        return (msg) -> failure(source, msg);
    }

    public static FailureMessage failure(ClientCommandRegistrationEvent.ClientCommandSourceStack source) {
        return (msg) -> failure(source, msg);
    }

    public static void failure(CommandSourceStack source, Component message) {
        source.sendFailure(message);
    }

    public static void failure(ClientCommandRegistrationEvent.ClientCommandSourceStack source, Component message) {
        source.arch$sendFailure(message);
    }

    public static Style suggestCommand(String... words) {
        if (words.length == 0) return Style.EMPTY;
        return Style.EMPTY.withUnderlined(true).withClickEvent(
            new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, (words[0].startsWith("/") ? "" : "/") + String.join(" ", words))
        );
    }

    public static Style runCommand(String... words) {
        if (words.length == 0) return Style.EMPTY;
        return Style.EMPTY.withUnderlined(true).withClickEvent(
            new ClickEvent(ClickEvent.Action.RUN_COMMAND, (words[0].startsWith("/") ? "" : "/") + String.join(" ", words))
        );
    }

    public static Style copyText(String text) {
        return Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text)).applyTo(
            hoverText(Component.translatable("commands.croparia.click2copy", text))
        );
    }

    public static Style openFile(String path) {
        return Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, path));
    }

    public static Style hoverItem(ResourceLocation id) {
        return hoverItem(BuiltInRegistries.ITEM.getValue(id));
    }

    public static Style hoverItem(Item item) {
        return hoverItem(item.getDefaultInstance());
    }

    public static Style hoverItem(ItemStack stack) {
        return stack.isEmpty() ? Style.EMPTY : Style.EMPTY.withHoverEvent(new HoverEvent(
            HoverEvent.Action.SHOW_ITEM,
            new HoverEvent.ItemStackInfo(stack)
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
