package cool.muyucloud.croparia.api.element;

import org.jetbrains.annotations.NotNull;

public interface ElementAccess {
    @NotNull
    ElementsEnum getElement();

    default ElementsEnum assertEmpty(@NotNull ElementsEnum element) {
        if (element != ElementsEnum.EMPTY) return element;
        throw new IllegalArgumentException("Element cannot be empty");
    }
}
