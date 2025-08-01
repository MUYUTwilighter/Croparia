package cool.muyucloud.croparia.api.recipe;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A generic interface for recipes that can be displayed in the recipe book, can be polymorphic to many types required
 * */
public interface DisplayableRecipe<C extends RecipeInput> extends Recipe<C>, RecipeDisplay {
    TypedSerializer<? extends DisplayableRecipe<C>> getTypedSerializer();

    @Override
    default @NotNull TypedSerializer<? extends DisplayableRecipe<C>> getType() {
        return getTypedSerializer();
    }

    @Override
    default @NotNull TypedSerializer<? extends DisplayableRecipe<C>> getSerializer() {
        return getTypedSerializer();
    }

    @Override
    default @NotNull TypedSerializer<? extends DisplayableRecipe<C>> recipeBookCategory() {
        return getTypedSerializer();
    }

    @Override
    default @NotNull List<RecipeDisplay> display() {
        return List.of(this);
    }

    @Override
    default @NotNull Type<? extends RecipeDisplay> type() {
        return getTypedSerializer().displayType();
    }
}
