package cool.muyucloud.croparia.api.element.fluid;

import cool.muyucloud.croparia.access.ElementAccess;
import cool.muyucloud.croparia.api.element.ElementsEnum;
import me.shedaniel.architectury.core.fluid.ArchitecturyFlowingFluid;
import me.shedaniel.architectury.core.fluid.ArchitecturyFluidAttributes;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.NotNull;

public class ElementalSource extends FlowingFluid implements ElementAccess {
    private final ElementsEnum element;

    public ElementalSource(@NotNull ElementsEnum element, @NotNull ArchitecturyFluidAttributes attributes) {
        super(attributes);
        if (element == ElementsEnum.EMPTY) {
            throw new IllegalArgumentException("Element cannot be empty");
        }
        this.element = element;
    }

    @Override
    public @NotNull ElementsEnum getElement() {
        return this.element;
    }
}