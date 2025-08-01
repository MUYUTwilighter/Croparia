package cool.muyucloud.croparia.api.recipe.structure;

import net.minecraft.core.Vec3i;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public abstract class AbstractChar3D<T extends AbstractChar3D<T>> {
    public abstract List<Char2D> layers();

    public abstract T rotate();

    public abstract T mirror();

    public abstract int maxY();

    public abstract int maxZ();

    public abstract int maxX();

    public abstract Vec3i size();

    public abstract char get(int x, int y, int z);

    public abstract int count(char c);

    public abstract Collection<Character> chars();

    @SuppressWarnings("unused")
    public abstract void forEachChar(BiConsumer<Character, Integer> consumer);

    public abstract @NotNull Optional<Vec3i> find(char c);

    public abstract boolean contains(char c);
}
