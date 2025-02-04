package cool.muyucloud.croparia.compat.rei.category;

import cool.muyucloud.croparia.compat.rei.display.RitualRecipeDisplay;
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
import net.minecraft.network.chat.Component;

import java.util.List;

public class RitualRecipeDisplayCategory implements DisplayCategory<RitualRecipeDisplay> {
    public static final CategoryIdentifier<RitualRecipeDisplay> ID = CategoryIdentifier.of("croparia:ritual");

    @Override
    public CategoryIdentifier<? extends RitualRecipeDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Constants.RITUAL_TITLE;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(CropariaItems.RITUAL_STAND.get());
    }

    @Override
    public List<Widget> setupDisplay(RitualRecipeDisplay display, Rectangle bounds) {
        Widget background = Widgets.createRecipeBase(bounds);
        Widget blockArrow = Widgets.createTexturedWidget(
            Constants.BLOCK_PLACE, bounds.getCenterX() - 24, bounds.getCenterY() + 8,
            0, 0, 16, 16, 16, 16
        );
        Widget ingredientInteract = Widgets.createTexturedWidget(
            Constants.ITEM_DROP, bounds.getCenterX() - 8, bounds.getCenterY() - 8,
            0, 0, 16, 16, 16, 16
        );
        Widget resultInteract = Widgets.createArrow(new Point(bounds.getCenterX() + 8, bounds.getCenterY() + 8));
        Widget ritual = Widgets.createSlot(new Point(bounds.getCenterX() - 8, bounds.getCenterY() + 8))
            .entry(display.getRitual()).disableBackground().markInput().disableHighlight();
        Widget block = Widgets.createSlot(new Point(bounds.getCenterX() - 40, bounds.getCenterY() + 8))
            .entries(display.getBlockItems()).markInput().disableBackground();
        Widget ingredient = Widgets.createSlot(new Point(bounds.getCenterX() - 8, bounds.getCenterY() - 24))
            .entries(display.getIngredient()).markInput().disableBackground();
        Widget result = Widgets.createSlot(new Point(bounds.getCenterX() + 32, bounds.getCenterY() + 8))
            .entry(display.getResult()).markOutput().disableBackground();
        return List.of(background, blockArrow, ingredientInteract, resultInteract, ritual, block, ingredient, result);
    }
}
