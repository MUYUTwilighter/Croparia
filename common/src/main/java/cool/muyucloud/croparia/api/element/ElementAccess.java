package cool.muyucloud.croparia.api.element;

import org.jetbrains.annotations.NotNull;

public interface ElementAccess {
    @NotNull
    Element getElement();

    default Element assertEmpty(@NotNull Element element) {
        if (element != Element.EMPTY) return element;
        throw new IllegalArgumentException("Element cannot be empty");
    }
}
