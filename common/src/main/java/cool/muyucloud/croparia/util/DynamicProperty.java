package cool.muyucloud.croparia.util;

import com.google.common.collect.BiMap;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class DynamicProperty<T extends Comparable<T>> extends Property<T> {
    private final Supplier<BiMap<String, T>> map;

    public DynamicProperty(String name, Class<T> tClass, Supplier<BiMap<String, T>> map) {
        super(name, tClass);
        this.map = map;
    }

    @Override
    public @NotNull List<T> getPossibleValues() {
        return this.map.get().values().stream().toList();
    }

    @Override
    public @NotNull String getName(T value) {
        return this.map.get().inverse().get(value);
    }

    @Override
    public @NotNull Optional<T> getValue(String string) {
        return Optional.ofNullable(this.map.get().get(string));
    }

    @Override
    public int getInternalIndex(T value) {
        return this.getPossibleValues().indexOf(value);
    }
}
