package cool.muyucloud.croparia.rei.category;

import cool.muyucloud.croparia.rei.display.InfusorRecipeDisplay;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.Constants;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class InfusorRecipeDisplayCategory implements DisplayCategory<InfusorRecipeDisplay> {
    public static final CategoryIdentifier<InfusorRecipeDisplay> ID = CategoryIdentifier.of("croparia:infusor");
    public static final EntryStack<ItemStack> INFUSOR = EntryStack.of(VanillaEntryTypes.ITEM, CropariaItems.INFUSOR.get().getDefaultInstance());

    @Override
    public CategoryIdentifier<? extends InfusorRecipeDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Constants.INFUSOR_TITLE;
    }

    @Override
    public Renderer getIcon() {
        return EntryStack.of(VanillaEntryTypes.ITEM, CropariaItems.INFUSOR.get().getDefaultInstance());
    }

    @Override
    public List<Widget> setupDisplay(InfusorRecipeDisplay display, Rectangle bounds) {
        Widget background = Widgets.createRecipeBase(bounds);
        Widget infusor = Widgets.createSlot(
            new Point(bounds.getCenterX() - 8, bounds.getCenterY() + 8)
        ).entry(INFUSOR).disableBackground().markInput().disableHighlight();
        Widget ingredient = Widgets.createSlot(
            new Point(bounds.getCenterX() - 8, bounds.getCenterY() - 24)
        ).entries(display.getIngredient()).markInput().disableBackground();
        Widget element = Widgets.createSlot(
            new Point(bounds.getCenterX() - 40, bounds.getCenterY() + 8)
        ).entry(display.getElement()).markInput().disableBackground();
        Widget result = Widgets.createSlot(
            new Point(bounds.getCenterX() + 34, bounds.getCenterY() + 8)
        ).entry(display.getResult()).markOutput().disableBackground();
        Widget itemDrop = Widgets.createTexturedWidget(
            Constants.ITEM_DROP, bounds.getCenterX() - 8, bounds.getCenterY() - 8,
            0, 0, 16, 16, 16, 16
        );
        Widget elemInfuse = Widgets.createTexturedWidget(
            Constants.ELEM_INFUSE, bounds.getCenterX() - 24, bounds.getCenterY() + 8,
            0, 0, 16, 16, 16, 16
        );
        Widget arrow = Widgets.createArrow(new Point(bounds.getCenterX() + 8, bounds.getCenterY() + 8));
        return List.of(background, infusor, ingredient, element, result, itemDrop, elemInfuse, arrow);
    }
}
