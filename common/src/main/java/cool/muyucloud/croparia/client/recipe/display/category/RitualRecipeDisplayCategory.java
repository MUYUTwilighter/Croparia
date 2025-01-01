package cool.muyucloud.croparia.client.recipe.display.category;

import cool.muyucloud.croparia.client.recipe.display.RitualRecipeDisplay;
import cool.muyucloud.croparia.registry.CropariaItems;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
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
        return Component.translatable("block.croparia.ritual_stand");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(CropariaItems.RITUAL_STAND.get());
    }

    @Override
    public List<Widget> setupDisplay(RitualRecipeDisplay display, Rectangle bounds) {
        return DisplayCategory.super.setupDisplay(display, bounds);
    }
}
