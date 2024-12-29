package cool.muyucloud.container;

import cool.muyucloud.data.ElementsEnum;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InfusorContainer implements Container {
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
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.item.isEmpty();
    }

    @Override
    public int getMaxStackSize() {
        return this.item.getMaxStackSize();
    }

    @Override
    public @NotNull ItemStack getItem(int i) {
        return i == 0 ? this.item : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItem(int i, int count) {
        return i == 0 ? this.item.split(count) : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int i) {
        return i == 0 ? this.item : ItemStack.EMPTY;
    }

    @Override
    public void setItem(int i, @NotNull ItemStack itemStack) {
        if (i != 0 || itemStack.isEmpty() || this.item.getCount() >= this.item.getMaxStackSize()) {
            return;
        }
        if (this.item.isEmpty()) {
            this.item = itemStack;
            this.setChanged();
        } else if (ItemStack.isSameItemSameTags(this.item, itemStack)) {
            itemStack = itemStack.split(this.item.getMaxStackSize() - this.item.getCount());
            this.item.setCount(this.item.getCount() + itemStack.getCount());
        }
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.setItem(0, ItemStack.EMPTY);
        this.setElement(ElementsEnum.EMPTY);
    }
}
