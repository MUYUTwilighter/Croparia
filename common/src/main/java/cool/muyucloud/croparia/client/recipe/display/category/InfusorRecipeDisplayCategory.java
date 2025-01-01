package cool.muyucloud.croparia.client.recipe.display.category;

import cool.muyucloud.croparia.client.recipe.display.InfusorRecipeDisplay;
import cool.muyucloud.croparia.registry.CropariaItems;
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
import org.apache.commons.compress.utils.Lists;

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
        return Component.translatable("block.croparia.infusor");
    }

    @Override
    public Renderer getIcon() {
        return EntryStack.of(VanillaEntryTypes.ITEM, CropariaItems.INFUSOR.get().getDefaultInstance());
    }

    @Override
    public List<Widget> setupDisplay(InfusorRecipeDisplay display, Rectangle bounds) {
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));

        Point infusor = new Point(bounds.getCenterX() - 6, bounds.getCenterY() + 5);
        widgets.add(Widgets.createSlot(infusor).entry(INFUSOR).disableBackground().markInput());

        Point ingredient = new Point(infusor.getX(), infusor.getY() - 30);
        widgets.add(Widgets.createSlot(ingredient).entry(display.getIngredient()).markInput().disableBackground());

        Point element = new Point(infusor.getX() - 45, infusor.getY());
        widgets.add(Widgets.createSlot(element).entry(display.getElement()).markInput().disableBackground());

        Point result = new Point(infusor.getX() + 45, infusor.getY());
        widgets.add(Widgets.createSlot(result).entry(display.getResult()).markOutput().disableBackground());

        widgets.add(Widgets.createFilledRectangle(new Rectangle(infusor.getX() + 5, infusor.getY() - 13, 1, 11), 0xFF8b8b8b));
        widgets.add(Widgets.createFilledRectangle(new Rectangle(infusor.getX() + 10, infusor.getY() - 13, 1, 11), 0xFF8b8b8b));

        Point elementArrow = new Point(infusor.getX() - 27, infusor.getY());
        widgets.add(Widgets.createArrow(elementArrow));

        Point resultArrow = new Point(infusor.getX() + 19, infusor.getY());
        widgets.add(Widgets.createArrow(resultArrow));

        return widgets;
    }
}
