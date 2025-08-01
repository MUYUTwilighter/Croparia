package cool.muyucloud.croparia.api.element.block;

import cool.muyucloud.croparia.api.element.Element;
import cool.muyucloud.croparia.api.element.ElementAccess;
import dev.architectury.core.block.ArchitecturyLiquidBlock;
import org.jetbrains.annotations.NotNull;

public class ElementalLiquidBlock extends ArchitecturyLiquidBlock implements ElementAccess {
    @NotNull
    private final Element element;

    public ElementalLiquidBlock(@NotNull Element element, Properties properties) {
        super(element.getFluidFlowing(), properties);
        this.element = element;
    }

    @Override
    public @NotNull Element getElement() {
        return this.element;
    }
}
