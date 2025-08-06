package cool.muyucloud.croparia.api.core.recipe.container;

import cool.muyucloud.croparia.api.element.Element;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public class InfusorContainer implements RecipeInput {
    @NotNull
    private Element element = Element.EMPTY;
    private ItemStack item = ItemStack.EMPTY;

    public static InfusorContainer of(Element element, ItemStack item) {
        InfusorContainer container = new InfusorContainer();
        container.element = element;
        container.item = item;
        return container;
    }

    public @NotNull Element getElement() {
        return element;
    }

    public void setElement(@NotNull Element element) {
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
