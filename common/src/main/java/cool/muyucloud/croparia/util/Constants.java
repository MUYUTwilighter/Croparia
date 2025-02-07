package cool.muyucloud.croparia.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
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
    public static final MutableComponent ITEM_DROP_TOOLTIP = Component.translatable("tooltip.croparia.item_drop").setStyle(USAGE);
    public static final MutableComponent ELEM_INFUSE_TOOLTIP = Component.translatable("tooltip.croparia.elem_infuse").setStyle(USAGE);
    public static final MutableComponent BLOCK_PLACE_TOOLTIP = Component.translatable("tooltip.croparia.block_place").setStyle(USAGE);
    public static final MutableComponent INFUSOR_TITLE = Component.translatable("gui.croparia.infusor.title");
    public static final MutableComponent RITUAL_TITLE = Component.translatable("gui.croparia.ritual.title");
    public static final MutableComponent RITUAL_STRUCTURE_TITLE = Component.translatable("gui.croparia.ritual_structure.title");
    public static final MutableComponent RITUAL_STRUCTURE_LOWER = Component.translatable("gui.croparia.ritual_structure.lower");
    public static final MutableComponent RITUAL_STRUCTURE_UPPER = Component.translatable("gui.croparia.ritual_structure.upper");
    public static final MutableComponent INSUFFICIENT_XP = Component.translatable("overlay.croparia.xp");
    public static final MutableComponent TOOLTIP_RITUAL = Component.translatable("tooltip.croparia.ritual");
}
