package cool.muyucloud.croparia.compat.emi.widget;

//import dev.emi.emi.api.widget.Bounds;
//import dev.emi.emi.api.widget.ButtonWidget;
//import dev.emi.emi.api.widget.TextureWidget;
//import dev.emi.emi.api.widget.Widget;
//import net.minecraft.client.gui.GuiGraphics;
//import net.minecraft.resources.ResourceLocation;
//
//import java.util.function.BooleanSupplier;
//
//public class Button extends Widget {
//    protected final int x, y, width, height, u, v;
//    protected final BooleanSupplier active;
//    protected final ButtonWidget.ClickAction action;
//    protected final ResourceLocation normal, hover;
//    protected final Bounds bounds;
//
//    public Button(int x, int y, int width, int height, ResourceLocation normal, ResourceLocation hover, BooleanSupplier isActive, ButtonWidget.ClickAction action) {
//        this(x, y, width, height, 0, 0, normal, hover, isActive, action);
//    }
//
//    public Button(int x, int y, int width, int height, int u, int v, ResourceLocation normal, ResourceLocation hover, BooleanSupplier isActive, ButtonWidget.ClickAction action) {
//        this.x = x;
//        this.y = y;
//        this.width = width;
//        this.height = height;
//        this.u = u;
//        this.v = v;
//        this.normal = normal;
//        this.hover = hover;
//        this.active = isActive;
//        this.action = action;
//        this.bounds = new Bounds(x, y, width, height);
//    }
//
//
//    @Override
//    public Bounds getBounds() {
//        return this.bounds;
//    }
//
//    @Override
//    public void render(GuiGraphics draw, int mouseX, int mouseY, float delta) {
//        if (this.bounds.contains(mouseX, mouseY)) {
//            new TextureWidget(this.hover, this.x, this.y, this.width, this.height, 0, 0, this.width, this.height, this.width, this.height).render(draw, mouseX, mouseY, delta);
//        } else {
//            new TextureWidget(this.normal, this.x, this.y, this.width, this.height, this.u, this.v, this.width, this.height, this.width, this.height).render(draw, mouseX, mouseY, delta);
//        }
//    }
//
//    @Override
//    public boolean mouseClicked(int mouseX, int mouseY, int button) {
//        if (this.active.getAsBoolean()) {
//            this.action.click(mouseX, mouseY, button);
//            return true;
//        } else {
//            return false;
//        }
//    }
//}

@SuppressWarnings("unused")
public class Button {}