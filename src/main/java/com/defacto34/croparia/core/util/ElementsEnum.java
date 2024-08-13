//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.util;

import net.minecraft.util.StringIdentifiable;

public enum ElementsEnum implements StringIdentifiable {
    EMPTY("empty"),
    WATER("water"),
    FIRE("fire"),
    EARTH("earth"),
    AIR("air"),
    ELEMENTAL("elemental");

    private final String name;

    ElementsEnum(String name) {
        this.name = name;
    }

    public String getTranslationKey() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}
