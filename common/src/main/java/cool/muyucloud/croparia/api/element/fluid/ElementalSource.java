package cool.muyucloud.croparia.api.element.fluid;

import cool.muyucloud.croparia.api.element.Element;
import cool.muyucloud.croparia.api.element.ElementAccess;
import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import org.jetbrains.annotations.NotNull;

public class ElementalSource extends ArchitecturyFlowingFluid.Source implements ElementAccess {
    private final Element element;

    public ElementalSource(@NotNull Element element, @NotNull ArchitecturyFluidAttributes attributes) {
        super(attributes);
        if (element == Element.EMPTY) {
            throw new IllegalArgumentException("Element cannot be empty");
        }
        this.element = element;
    }

    @Override
    public @NotNull Element getElement() {
        return this.element;
    }


}
