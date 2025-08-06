package cool.muyucloud.croparia.api.core.recipe.container;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public record RitualContainer(
    int tier, @NotNull ItemStack item, @NotNull BlockState state
) implements RecipeInput {
    public static RitualContainer of(int tier, @NotNull ItemStack input, BlockState state) {
        return new RitualContainer(tier, input, state);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public @NotNull ItemStack getItem(int i) {
        return i == 0 ? item : ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 1;
    }
}
