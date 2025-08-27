package cool.muyucloud.croparia.compat.rei.widget;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.WidgetWithBounds;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class Item2DWidget extends WidgetWithBounds {
    private static final int SLOT_SIZE = 18;

    private int x = 0;
    private int y = 0;
    private int cols = 0;
    private int rows = 0;
    private transient BiFunction<Integer, Integer, Collection<? extends EntryStack<?>>> itemProvider =
        (x, y) -> EntryIngredients.of(ItemStack.EMPTY);
    private transient Slot[][] slots = new Slot[0][0];

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
    public Item2DWidget items(@NotNull BiFunction<Integer, Integer, Collection<? extends EntryStack<?>>> itemProvider) {
        this.itemProvider = itemProvider;
        this.slots = new Slot[this.rows][this.cols];
        return this;
    }

    @NotNull
    public Item2DWidget cols(int cols) {
        this.cols = cols;
        this.slots = new Slot[this.rows][this.cols];
        return this;
    }

    @NotNull
    public Item2DWidget rows(int rows) {
        this.rows = rows;
        this.slots = new Slot[this.rows][this.cols];
        return this;
    }

    public Slot get(int x, int y) throws ArrayIndexOutOfBoundsException {
        Slot slot = this.slots[y][x];
        if (slot == null) {
            slot = Widgets.createSlot(
                new Point(this.x + (x + 1) * SLOT_SIZE, this.y + (y + 1) * SLOT_SIZE)
            ).entries(itemProvider.apply(x, y)).interactable(true).markInput();
            this.slots[y][x] = slot;
        }
        return slot;
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

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        for (int x = 0; x < this.cols; x++) {
            for (int y = 0; y < this.rows; y++) {
                this.get(x, y).render(graphics, mouseX, mouseY, delta);
            }
        }
    }

    @Override
    public @NotNull List<? extends GuiEventListener> children() {
        return List.of();
    }

    public static Item2DWidget create() {
        return new Item2DWidget();
    }
}
