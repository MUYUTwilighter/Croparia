package cool.muyucloud.croparia.compat.rei.category;

import cool.muyucloud.croparia.api.core.recipe.RitualRecipe;
import cool.muyucloud.croparia.compat.rei.display.SimpleDisplay;
import cool.muyucloud.croparia.compat.rei.display.SimpleSerializer;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.Constants;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;

import java.util.List;

public class RitualRecipeDisplayCategory implements DisplayCategory<SimpleDisplay<RitualRecipe>> {
    public static final SimpleSerializer<RitualRecipe> SERIALIZER = new SimpleSerializer<>(
        RitualRecipe.class, RitualRecipe.TYPED_SERIALIZER,
        (recipe, map) -> {
            map.put("block", EntryIngredient.of(recipe.getBlock().getDisplayStacks().stream().map(stack -> EntryStack.of(VanillaEntryTypes.ITEM, stack).tooltip(Constants.BLOCK_PLACE_TOOLTIP)).toList()));
            map.put("ingredient", EntryIngredient.of(recipe.getIngredient().getDisplayStacks().stream().map(stack -> EntryStack.of(VanillaEntryTypes.ITEM, stack).tooltip(Constants.ITEM_DROP_TOOLTIP)).toList()));
        },
        (recipe, map) -> {
            map.put("result", EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM, recipe.getResult().getDisplayStack())));
        }
    );

    @Override
    public CategoryIdentifier<SimpleDisplay<RitualRecipe>> getCategoryIdentifier() {
        return SERIALIZER.getId();
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
    public List<Widget> setupDisplay(SimpleDisplay<RitualRecipe> display, Rectangle bounds) {
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
            .entry(EntryStack.of(VanillaEntryTypes.ITEM, display.getRecipe().craftingStation().item().value().getDefaultInstance())).disableBackground().markInput().disableHighlight();
        Widget block = Widgets.createSlot(new Point(bounds.getCenterX() - 40, bounds.getCenterY() + 8))
            .entries(display.getInput("block")).markInput().disableBackground();
        Widget ingredient = Widgets.createSlot(new Point(bounds.getCenterX() - 8, bounds.getCenterY() - 24))
            .entries(display.getInput("ingredient")).markInput().disableBackground();
        Widget result = Widgets.createSlot(new Point(bounds.getCenterX() + 32, bounds.getCenterY() + 8))
            .entries(display.getOutput("result")).markOutput().disableBackground();
        return List.of(background, blockArrow, ingredientInteract, resultInteract, ritual, block, ingredient, result);
    }
}
