package cool.muyucloud.croparia.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class Constants {
    public static final ResourceLocation ITEM_DROP = ResourceLocation.tryParse("croparia:textures/gui/item_drop.png");
    public static final ResourceLocation ELEM_INFUSE = ResourceLocation.tryParse("croparia:textures/gui/elem_infuse.png");
    public static final ResourceLocation BLOCK_PLACE = ResourceLocation.tryParse("croparia:textures/gui/block_place.png");
    public static final ResourceLocation LEFT_DARK = ResourceLocation.tryParse("croparia:textures/gui/left_dark.png");
    public static final ResourceLocation LEFT_WHITE = ResourceLocation.tryParse("croparia:textures/gui/left_white.png");
    public static final ResourceLocation RIGHT_DARK = ResourceLocation.tryParse("croparia:textures/gui/right_dark.png");
    public static final ResourceLocation RIGHT_WHITE = ResourceLocation.tryParse("croparia:textures/gui/right_white.png");
    public static final ResourceLocation UP_DARK = ResourceLocation.tryParse("croparia:textures/gui/up_dark.png");
    public static final ResourceLocation UP_WHITE = ResourceLocation.tryParse("croparia:textures/gui/up_white.png");
    public static final ResourceLocation DOWN_DARK = ResourceLocation.tryParse("croparia:textures/gui/down_dark.png");
    public static final ResourceLocation DOWN_WHITE = ResourceLocation.tryParse("croparia:textures/gui/down_white.png");
    public static final Style USAGE = Style.EMPTY.withItalic(true).withColor(ChatFormatting.GRAY);
    public static final MutableComponent ITEM_DROP_TOOLTIP = new TranslatableComponent("tooltip.croparia.item_drop").setStyle(USAGE);
    public static final MutableComponent ELEM_INFUSE_TOOLTIP = new TranslatableComponent("tooltip.croparia.elem_infuse").setStyle(USAGE);
    public static final MutableComponent BLOCK_PLACE_TOOLTIP = new TranslatableComponent("tooltip.croparia.block_place").setStyle(USAGE);
    public static final MutableComponent INFUSOR_TITLE = new TranslatableComponent("gui.croparia.infusor.title");
    public static final MutableComponent RITUAL_TITLE = new TranslatableComponent("gui.croparia.ritual.title");
    public static final MutableComponent RITUAL_STRUCTURE_TITLE = new TranslatableComponent("gui.croparia.ritual_structure.title");
    public static final MutableComponent RITUAL_STRUCTURE_LOWER = new TranslatableComponent("gui.croparia.ritual_structure.lower");
    public static final MutableComponent RITUAL_STRUCTURE_UPPER = new TranslatableComponent("gui.croparia.ritual_structure.upper");
    public static final MutableComponent INSUFFICIENT_XP = new TranslatableComponent("overlay.croparia.xp");
    public static final MutableComponent TOOLTIP_INPUT = new TranslatableComponent("tooltip.croparia.input");
    public static final MutableComponent TOOLTIP_UNKNOWN = new TranslatableComponent("tooltip.croparia.unknown");
    public static final MutableComponent TOOLTIP_AIR = new TranslatableComponent("tooltip.croparia.air");
    public static final MutableComponent TOOLTIP_ANY = new TranslatableComponent("tooltip.croparia.any");
}
