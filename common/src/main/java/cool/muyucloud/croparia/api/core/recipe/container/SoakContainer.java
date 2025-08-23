package cool.muyucloud.croparia.api.core.recipe.container;

import cool.muyucloud.croparia.api.element.Element;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SoakContainer implements RecipeInput {
    private final BlockState state;
    private final Element element;
    private final float random;

    public SoakContainer(BlockState state, Element element, float random) {
        this.state = state;
        this.element = element;
        this.random = random;
    }

    public BlockState getState() {
        return state;
    }

    public Element getElement() {
        return element;
    }

    public float getRandom() {
        return random;
    }

    @Override
    public @NotNull ItemStack getItem(int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }
}
