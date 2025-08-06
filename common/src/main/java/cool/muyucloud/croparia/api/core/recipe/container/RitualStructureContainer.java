package cool.muyucloud.croparia.api.core.recipe.container;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public class RitualStructureContainer implements RecipeInput {
    public static final RitualStructureContainer INSTANCE = new RitualStructureContainer();

    @Override
    public @NotNull ItemStack getItem(int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
