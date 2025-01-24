package cool.muyucloud.croparia.rei.display;

import me.shedaniel.rei.api.common.display.Display;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public interface SimpleDisplay<R extends Recipe<?>> extends Display {
    R getRecipe();

    ResourceLocation getRecipeId();
}
