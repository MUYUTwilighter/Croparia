package cool.muyucloud.croparia.item;

import cool.muyucloud.croparia.access.ElementAccess;
import cool.muyucloud.croparia.data.ElementsEnum;
import dev.architectury.core.item.ArchitecturyBucketItem;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ElementalBucket extends ArchitecturyBucketItem implements ElementAccess {
    private final ElementsEnum element;

    public ElementalBucket(ElementsEnum element, Supplier<? extends Fluid> fluid, Properties properties) {
        super(fluid, properties);
        this.element = assertEmpty(element);
    }

    @Override
    public @NotNull ElementsEnum getElement() {
        return element;
    }
}
