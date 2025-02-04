package cool.muyucloud.croparia.api.element.block;

import cool.muyucloud.croparia.api.element.ElementAccess;
import cool.muyucloud.croparia.api.element.ElementsEnum;
import dev.architectury.core.block.ArchitecturyLiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ElementalLiquidBlock extends ArchitecturyLiquidBlock implements ElementAccess {
    private final ElementsEnum element;

    public ElementalLiquidBlock(ElementsEnum element, Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid, properties);
        this.element = assertEmpty(element);
    }

    @Override
    public @NotNull ElementsEnum getElement() {
        return this.element;
    }
}
