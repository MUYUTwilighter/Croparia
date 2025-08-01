package cool.muyucloud.croparia.api.element.item;

import cool.muyucloud.croparia.api.element.Element;
import cool.muyucloud.croparia.api.element.ElementAccess;
import dev.architectury.core.item.ArchitecturyBucketItem;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ElementalBucket extends ArchitecturyBucketItem implements ElementAccess {
    private final Element element;

    public ElementalBucket(Element element, Supplier<? extends Fluid> fluid, Properties properties) {
        super(fluid, properties);
        this.element = assertEmpty(element);
    }

    @Override
    public @NotNull Element getElement() {
        return element;
    }
}
