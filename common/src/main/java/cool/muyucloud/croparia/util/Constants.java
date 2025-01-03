package cool.muyucloud.croparia.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public class Constants {
    public static final ResourceLocation ITEM_DROP = ResourceLocation.tryParse("croparia:textures/gui/item_drop.png");
    public static final ResourceLocation ELEM_INFUSE = ResourceLocation.tryParse("croparia:textures/gui/elem_infuse.png");
    public static final ResourceLocation BLOCK_PLACE = ResourceLocation.tryParse("croparia:textures/gui/block_place.png");
    public static final Style USAGE = Style.EMPTY.withItalic(true).withColor(ChatFormatting.GRAY);
    public static final Component ITEM_DROP_TOOLTIP = Component.translatable("tooltip.croparia.item_drop").setStyle(USAGE);
    public static final Component ELEM_INFUSE_TOOLTIP = Component.translatable("tooltip.croparia.elem_infuse").setStyle(USAGE);
    public static final Component BLOCK_PLACE_TOOLTIP = Component.translatable("tooltip.croparia.block_place").setStyle(USAGE);
    public static final Component INFUSOR_TITLE = Component.translatable("gui.croparia.infusor.title");
    public static final Component RITUAL_TITLE = Component.translatable("gui.croparia.ritual.title");
    public static final Component RITUAL_STRUCTURE_TITLE = Component.translatable("gui.croparia.ritual_structure.title");
    public static final Component RITUAL_STRUCTURE_LOWER = Component.translatable("gui.croparia.ritual_structure.lower");
    public static final Component RITUAL_STRUCTURE_UPPER = Component.translatable("gui.croparia.ritual_structure.upper");
    public static final Component INSUFFICIENT_XP = Component.translatable("overlay.croparia.xp");
}
