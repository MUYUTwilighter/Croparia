package cool.muyucloud.croparia.compat.rei.category;

import cool.muyucloud.croparia.api.core.recipe.InfusorRecipe;
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
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class InfusorRecipeDisplayCategory implements DisplayCategory<SimpleDisplay<InfusorRecipe>> {
    public static final EntryStack<ItemStack> INFUSOR = EntryStack.of(VanillaEntryTypes.ITEM, CropariaItems.INFUSOR.get().getDefaultInstance());
    public static final SimpleSerializer<InfusorRecipe> SERIALIZER = new SimpleSerializer<>(
        InfusorRecipe.class,
        InfusorRecipe.TYPED_SERIALIZER,
        (recipe, map) -> {
            map.put("element", EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM, recipe.getPotion().getDefaultInstance()).tooltip(Constants.ELEM_INFUSE_TOOLTIP)));
            map.put("ingredient", EntryIngredient.of(recipe.getIngredient().getDisplayStacks().stream().map(stack -> EntryStack.of(VanillaEntryTypes.ITEM, stack).tooltip(Constants.ITEM_DROP_TOOLTIP)).toList()));
        },
        (recipe, map) -> {
            map.put("result", EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM, recipe.getResult().getDisplayStack())));
        }
    );

    @Override
    public CategoryIdentifier<SimpleDisplay<InfusorRecipe>> getCategoryIdentifier() {
        return SERIALIZER.getId();
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
    public List<Widget> setupDisplay(SimpleDisplay<InfusorRecipe> display, Rectangle bounds) {
        Widget background = Widgets.createRecipeBase(bounds);
        Widget infusor = Widgets.createSlot(
            new Point(bounds.getCenterX() - 8, bounds.getCenterY() + 8)
        ).entry(INFUSOR).disableBackground().markInput().disableHighlight();
        Widget ingredient = Widgets.createSlot(
            new Point(bounds.getCenterX() - 8, bounds.getCenterY() - 24)
        ).entries(display.getInput("ingredient")).markInput().disableBackground();
        Widget element = Widgets.createSlot(
            new Point(bounds.getCenterX() - 40, bounds.getCenterY() + 8)
        ).entries(display.getInput("element")).markInput().disableBackground();
        Widget result = Widgets.createSlot(
            new Point(bounds.getCenterX() + 34, bounds.getCenterY() + 8)
        ).entries(display.getOutput("result")).markOutput().disableBackground();
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
