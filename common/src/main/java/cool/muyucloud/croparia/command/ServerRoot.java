package cool.muyucloud.croparia.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ServerRoot {
    private static final LiteralArgumentBuilder<CommandSourceStack> ROOT = Commands.literal("croparia")
        .then(DumpCommand.build())
        .then(DumpBuiltinCommand.build())
        .then(CropCommand.build());

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection selection) {
        CropariaIf.LOGGER.debug("Registering commands");
        dispatcher.register(ROOT);
    }

    public static Style openUrl(String url) {
        return Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
    }

    public static Style suggestCommand(String command, Object... args) {
        return Style.EMPTY.withUnderlined(true).withClickEvent(
            new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command.formatted(args))
        );
    }

    public static Style hoverItem(ResourceLocation id) {
        return hoverItem(BuiltInRegistries.ITEM.get(id));
    }

    public static Style hoverItem(Item item) {
        return Style.EMPTY.withHoverEvent(new HoverEvent(
            HoverEvent.Action.SHOW_ITEM,
            new HoverEvent.ItemStackInfo(item.getDefaultInstance())
        ));
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
