package cool.muyucloud.croparia.item;

import cool.muyucloud.croparia.access.ElementAccess;
import cool.muyucloud.croparia.data.ElementsEnum;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Elematilius extends Item implements ElementAccess {
    private static final Map<ElementsEnum, Elematilius> ELEMATILIUS_MAP = new HashMap<>();
    @NotNull
    private final ElementsEnum element;

    public Elematilius(@NotNull ElementsEnum element, @NotNull Properties properties) {
        super(properties);
        this.element = this.assertEmpty(element);
        ELEMATILIUS_MAP.put(element, this);
    }

    @Override
    public @NotNull ElementsEnum getElement() {
        return this.element;
    }

    public static Optional<Elematilius> getElement(@NotNull ElementsEnum element) {
        return Optional.ofNullable(ELEMATILIUS_MAP.get(element));
    }
}
