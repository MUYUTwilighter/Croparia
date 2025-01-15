package cool.muyucloud.croparia.emi.widget;

import cool.muyucloud.croparia.emi.recipe.EmiRitualStructure;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.widget.SlotWidget;

public class DynamicSlot extends SlotWidget {
    protected EmiIngredient stack = EmiRitualStructure.AIR;

    public DynamicSlot(int x, int y) {
        super(EmiRitualStructure.AIR, x, y);
    }

    public void setStack(EmiIngredient stack) {
        this.stack = stack;
    }

    @Override
    public EmiIngredient getStack() {
        return stack;
    }
}
