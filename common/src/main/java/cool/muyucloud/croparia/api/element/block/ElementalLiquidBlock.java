package cool.muyucloud.croparia.api.element.block;

import cool.muyucloud.croparia.access.ElementAccess;
import cool.muyucloud.croparia.api.element.ElementsEnum;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ElementalLiquidBlock extends LiquidBlock implements ElementAccess {
    private final ElementsEnum element;

    public ElementalLiquidBlock(ElementsEnum element, Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid.get(), properties);
        this.element = assertEmpty(element);
    }

    @Override
    public @NotNull ElementsEnum getElement() {
        return this.element;
    }
}