package cool.muyucloud.croparia.data;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ElementsEnum implements StringRepresentable {
    EMPTY,
    WATER,
    FIRE,
    EARTH,
    AIR,
    ELEMENTAL;

    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase();
    }
}
