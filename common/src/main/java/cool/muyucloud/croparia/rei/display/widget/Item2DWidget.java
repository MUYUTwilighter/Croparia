package cool.muyucloud.croparia.rei.display.widget;

import cool.muyucloud.croparia.util.BiFunction;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.WidgetWithBounds;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Item2DWidget extends WidgetWithBounds {
    private static final int SLOT_SIZE = 18;

    private BiFunction<Integer, Integer, Collection<EntryStack<ItemStack>>> itemProvider =
        (posZ, posX) -> Collections.singleton(EntryStacks.of(ItemStack.EMPTY));
    private int x = 0;
    private int y = 0;
    private int cols = 0;
    private int rows = 0;

    @NotNull
    public Item2DWidget x(int x) {
        this.x = x;
        return this;
    }

    @NotNull
    public Item2DWidget y(int y) {
        this.y = y;
        return this;
    }

    @NotNull
    public Item2DWidget itemProvider(@NotNull BiFunction<Integer, Integer, Collection<EntryStack<ItemStack>>> itemProvider) {
        this.itemProvider = itemProvider;
        return this;
    }

    @NotNull
    public Item2DWidget cols(int cols) {
        this.cols = cols;
        return this;
    }

    @NotNull
    public Item2DWidget rows(int rows) {
        this.rows = rows;
        return this;
    }

    public int width() {
        return (this.cols + 2) * SLOT_SIZE;
    }

    public int height() {
        return (this.rows + 2) * SLOT_SIZE;
    }

    @NotNull
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width(), height());
    }

    @NotNull
    private Collection<EntryStack<ItemStack>> get(int posX, int posZ) {
        return itemProvider.apply(posX, posZ);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        for (int posX = 0; posX < cols; posX++) {
            for (int posZ = 0; posZ < rows; posZ++) {
                Widgets.createSlot(
                    new Point(x + (posX + 1) * SLOT_SIZE, y + (posZ + 1) * SLOT_SIZE)
                ).entries(itemProvider.apply(posX, posZ)).render(graphics, mouseX, mouseY, delta);
            }
        }
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }

    public static Item2DWidget create() {
        return new Item2DWidget();
    }
}
