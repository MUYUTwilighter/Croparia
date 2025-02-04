package cool.muyucloud.croparia.compat.rei.display.category;

import cool.muyucloud.croparia.compat.rei.display.RitualStructureDisplay;
import cool.muyucloud.croparia.compat.rei.display.widget.Item2DWidget;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.Constants;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class RitualStructureDisplayCategory implements DisplayCategory<RitualStructureDisplay> {
    public static final CategoryIdentifier<RitualStructureDisplay> ID = CategoryIdentifier.of("croparia:ritual_structure");
    public static final int SLOT_SIZE = 18;
    public static final int LABEL_MARGIN = 6;
    public static final int FRAME_PADDING = 9;
    public static final int BUTTON_SIZE = 10;

    @Override
    public CategoryIdentifier<? extends RitualStructureDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Constants.RITUAL_STRUCTURE_TITLE;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(CropariaItems.RITUAL_STAND.get().getDefaultInstance());
    }

    @Override
    public List<Widget> setupDisplay(RitualStructureDisplay display, Rectangle bounds) {
        Rectangle layerBound = new Rectangle(
            bounds.x + FRAME_PADDING, bounds.y + FRAME_PADDING, bounds.width - 2 * FRAME_PADDING, bounds.height - 2 * FRAME_PADDING - SLOT_SIZE
        );
        Widget background = Widgets.createSlotBase(
            new Rectangle(layerBound.x - 1, layerBound.y - 1, layerBound.width + 2, layerBound.height + 2)
        );
        Widget lower = Widgets.createButton(
            new Rectangle(
                bounds.x + FRAME_PADDING,
                bounds.y + bounds.height - FRAME_PADDING - (SLOT_SIZE + BUTTON_SIZE) / 2,
                BUTTON_SIZE, BUTTON_SIZE
            ),
            new TextComponent("<")
        ).onClick(button -> display.lower()).tooltipLine(Constants.RITUAL_STRUCTURE_LOWER);
        Widget upper = Widgets.createButton(
            new Rectangle(
                bounds.x + bounds.width - FRAME_PADDING - BUTTON_SIZE,
                bounds.y + bounds.height - FRAME_PADDING - (SLOT_SIZE + BUTTON_SIZE) / 2,
                BUTTON_SIZE, BUTTON_SIZE
            ),
            new TextComponent(">")
        ).onClick(button -> display.upper()).tooltipLine(Constants.RITUAL_STRUCTURE_UPPER);
        Vec3i slotSize = display.size();
        Widget label = Widgets.createDrawableWidget(
            (helper, poseStack, mouseX, mouseY, delta) -> Widgets.createLabel(
                new Point(
                    bounds.x + bounds.width / 2,
                    bounds.y + bounds.height - FRAME_PADDING - SLOT_SIZE + LABEL_MARGIN
                ),
                new TranslatableComponent("gui.croparia.ritual_structure.label", display.lastRead() + 1)
            ).render(poseStack, mouseX, mouseY, delta)
        );
        Widget layer = Widgets.overflowed(
            new Rectangle(
                bounds.x + FRAME_PADDING, bounds.y + FRAME_PADDING, bounds.width - 2 * FRAME_PADDING,
                bounds.height - 2 * FRAME_PADDING - SLOT_SIZE
            ),
            Item2DWidget.create().itemProvider(display::get).cols(slotSize.getX()).rows(slotSize.getZ())
        );
        return List.of(background, lower, upper, label, layer);
    }

    @Override
    public int getDisplayHeight() {
        return DisplayCategory.super.getDisplayHeight() * 2 + FRAME_PADDING * 2;
    }
}
