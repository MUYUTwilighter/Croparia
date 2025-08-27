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
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class Texts {
    public static MutableComponent literal(String text, Style... styles) {
        return forStyles(Component.literal(text), styles);
    }

    public static MutableComponent translatable(String key, Object... args) {
        return Component.translatable(key, args);
    }

    public static MutableComponent forStyles(@NotNull MutableComponent component, Style... styles) {
        for (Style style : styles) {
            component.withStyle(style);
        }
        return component;
    }

    public static void chat(@NotNull Player player, Component message) {
        player.displayClientMessage(message, false);
    }

    public static void chat(@NotNull CommandSourceStack source, Component msg) {
        source.sendSystemMessage(msg);
    }

    public static void chat(@NotNull ClientCommandRegistrationEvent.ClientCommandSourceStack source, Component msg) {
        success(source, msg);
    }

    public static void overlay(@NotNull Player player, Component message) {
        player.displayClientMessage(message, true);
    }

    public static SuccessMessenger success(@NotNull CommandSourceStack source) {
        return (msg, broadcast) -> success(source, msg, broadcast);
    }

    public static SuccessMessenger success(@NotNull ClientCommandRegistrationEvent.ClientCommandSourceStack source) {
        return (msg, broadcast) -> success(source, msg, broadcast);
    }

    public static void success(@NotNull CommandSourceStack source, Component message, boolean broadcast) {
        source.sendSuccess(() -> message, broadcast);
    }

    public static void success(@NotNull CommandSourceStack source, Component message) {
        success(source, message, false);
    }

    public static void success(@NotNull ClientCommandRegistrationEvent.ClientCommandSourceStack source, Component message, boolean broadcast) {
        source.arch$sendSuccess(() -> message, broadcast);
    }

    public static void success(@NotNull ClientCommandRegistrationEvent.ClientCommandSourceStack source, Component message) {
        success(source, message, false);
    }

    public static void broadcastSuccess(@NotNull CommandSourceStack source, Component message) {
        source.sendSuccess(() -> message, true);
    }

    public static void broadcastSuccess(@NotNull ClientCommandRegistrationEvent.ClientCommandSourceStack source, Component message) {
        source.arch$sendSuccess(() -> message, true);
    }

    public static FailureMessenger failure(@NotNull CommandSourceStack source) {
        return (msg) -> failure(source, msg);
    }

    public static FailureMessenger failure(@NotNull ClientCommandRegistrationEvent.ClientCommandSourceStack source) {
        return (msg) -> failure(source, msg);
    }

    public static void failure(@NotNull CommandSourceStack source, Component message) {
        source.sendFailure(message);
    }

    public static void failure(@NotNull ClientCommandRegistrationEvent.ClientCommandSourceStack source, Component message) {
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
            hoverText(Texts.translatable("commands.croparia.click2copy", text))
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
        return Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Texts.literal(text)));
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
