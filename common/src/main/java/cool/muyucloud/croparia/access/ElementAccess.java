package cool.muyucloud.croparia.access;

import cool.muyucloud.croparia.data.ElementsEnum;
import org.jetbrains.annotations.NotNull;

public interface ElementAccess {
    @NotNull
    ElementsEnum getElement();

    default ElementsEnum assertEmpty(@NotNull ElementsEnum element) {
        if (element != ElementsEnum.EMPTY) return element;
        throw new IllegalArgumentException("Element cannot be empty");
    }
}