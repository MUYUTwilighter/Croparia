package cool.muyucloud.croparia.api.element.fluid;

import cool.muyucloud.croparia.api.element.ElementAccess;
import cool.muyucloud.croparia.api.element.ElementsEnum;
import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ElementalFlowing extends ArchitecturyFlowingFluid.Flowing implements ElementAccess {
    private static final Map<ElementsEnum, ElementalFlowing> FLOWING_MAP = new HashMap<>();
    private final ElementsEnum element;

    public ElementalFlowing(@NotNull ElementsEnum element, @NotNull ArchitecturyFluidAttributes attributes) {
        super(attributes);
        this.element = this.assertEmpty(element);
        FLOWING_MAP.put(element, this);
    }

    @Override
    public @NotNull ElementsEnum getElement() {
        return this.element;
    }

    @Nullable
    public static ElementalFlowing fromElement(@NotNull ElementsEnum element) {
        return FLOWING_MAP.get(element);
    }
}
