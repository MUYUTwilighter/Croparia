package cool.muyucloud.croparia.api.element.item;

import cool.muyucloud.croparia.api.element.Element;
import cool.muyucloud.croparia.api.element.ElementAccess;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ElementalGem extends Item implements ElementAccess {
    private static final Map<Element, ElementalGem> ELEMATILIUS_MAP = new HashMap<>();
    @NotNull
    private final Element element;

    public ElementalGem(@NotNull Element element, @NotNull Properties properties) {
        super(properties);
        this.element = this.assertEmpty(element);
        ELEMATILIUS_MAP.put(element, this);
    }

    @Override
    public @NotNull Element getElement() {
        return this.element;
    }

    public static Optional<ElementalGem> getElement(@NotNull Element element) {
        return Optional.ofNullable(ELEMATILIUS_MAP.get(element));
    }
}
