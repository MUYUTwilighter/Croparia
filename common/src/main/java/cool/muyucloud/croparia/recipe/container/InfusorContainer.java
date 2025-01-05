package cool.muyucloud.croparia.recipe.container;

import cool.muyucloud.croparia.data.ElementsEnum;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public class InfusorContainer implements RecipeInput {
    @NotNull
    private ElementsEnum element = ElementsEnum.EMPTY;
    private ItemStack item = ItemStack.EMPTY;

    public static InfusorContainer of(ElementsEnum element, ItemStack item) {
        InfusorContainer container = new InfusorContainer();
        container.element = element;
        container.item = item;
        return container;
    }

    public @NotNull ElementsEnum getElement() {
        return element;
    }

    public void setElement(@NotNull ElementsEnum element) {
        this.element = element;
    }

    @Override
    public @NotNull ItemStack getItem(int i) {
        return i == 0 ? item : ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
