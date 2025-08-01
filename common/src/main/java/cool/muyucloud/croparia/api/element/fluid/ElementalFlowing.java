package cool.muyucloud.croparia.api.element.fluid;

import cool.muyucloud.croparia.api.element.Element;
import cool.muyucloud.croparia.api.element.ElementAccess;
import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ElementalFlowing extends ArchitecturyFlowingFluid.Flowing implements ElementAccess {
    private static final Map<Element, ElementalFlowing> FLOWING_MAP = new HashMap<>();
    private final Element element;

    public ElementalFlowing(@NotNull Element element, @NotNull ArchitecturyFluidAttributes attributes) {
        super(attributes);
        this.element = this.assertEmpty(element);
        FLOWING_MAP.put(element, this);
    }

    @Override
    public @NotNull Element getElement() {
        return this.element;
    }

    @Nullable
    public static ElementalFlowing fromElement(@NotNull Element element) {
        return FLOWING_MAP.get(element);
    }
}
