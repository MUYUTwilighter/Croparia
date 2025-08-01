package cool.muyucloud.croparia.api.recipe.structure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import cool.muyucloud.croparia.util.Vec2i;
import net.minecraft.core.Vec3i;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class Char3D extends AbstractChar3D<Char3D> implements Iterable<Char2D> {
    public static final Codec<Char3D> CODEC = Char2D.CODEC.listOf().xmap(Char3D::new, Char3D::layers);

    private final List<Char2D> pattern;
    private final Map<Character, Integer> counts;
    protected transient int hash = 0;

    public Char3D(Char3D old) {
        this.pattern = old.pattern;
        this.counts = old.counts;
    }

    public Char3D(List<Char2D> structure) {
        int height = structure.size();
        int maxZ = structure.getFirst().maxZ();
        int maxX = structure.getFirst().maxX();
        List<Char2D> pattern = new ArrayList<>(height);
        Map<Character, Integer> counts = new HashMap<>();
        for (Char2D layer : structure) {
            if (layer.maxZ() != maxZ || layer.maxX() != maxX) {
                throw new IllegalArgumentException("Varying size: " + structure);
            }
            pattern.add(layer);
            layer.forEachChar((c, count) -> counts.compute(c, (character, integer) -> integer == null ? count : integer + count));
        }
        this.pattern = ImmutableList.copyOf(pattern);
        this.counts = ImmutableMap.copyOf(counts);
        this.hash = this.pattern.hashCode();
    }

    @Override
    public List<Char2D> layers() {
        return ImmutableList.copyOf(pattern);
    }

    @Override
    public Char3D rotate() {
        List<Char2D> rotated = new ArrayList<>(pattern.size());
        for (Char2D surface : pattern) {
            rotated.add(surface.rotate());
        }
        return new Char3D(rotated);
    }

    @Override
    public Char3D mirror() {
        List<Char2D> mirrored = new ArrayList<>(pattern.size());
        for (Char2D surface : pattern) {
            mirrored.add(surface.mirror());
        }
        return new Char3D(mirrored);
    }

    @Override
    public int maxY() {
        return pattern.size();
    }

    @Override
    public int maxZ() {
        return pattern.isEmpty() ? 0 : pattern.getFirst().maxZ();
    }

    @Override
    public int maxX() {
        return pattern.isEmpty() ? 0 : pattern.getFirst().maxX();
    }

    @Override
    public Vec3i size() {
        return new Vec3i(maxX(), maxY(), maxZ());
    }

    @Override
    public char get(int x, int y, int z) {
        return pattern.get(y).get(x, z);
    }

    @Override
    public int count(char c) {
        return counts.getOrDefault(c, 0);
    }

    @Override
    public Collection<Character> chars() {
        return counts.keySet();
    }

    @Override
    public void forEachChar(BiConsumer<Character, Integer> consumer) {
        counts.forEach(consumer);
    }

    @Override
    public @NotNull Optional<Vec3i> find(char c) {
        if (this.contains(c)) {
            for (int y = 0; y < maxY(); y++) {
                Optional<Vec2i> result = pattern.get(y).find(c);
                if (result.isPresent()) {
                    Vec2i pos = result.get();
                    return Optional.of(pos.toVec3i(y));
                }
            }
        } else return Optional.empty();
        throw new IllegalStateException("Char %s not present but found %d times".formatted(c, this.count(c)));
    }

    @Override
    public boolean contains(char c) {
        return this.counts.containsKey(c) && this.counts.get(c) > 0;
    }

    @Override
    public @NotNull Iterator<Char2D> iterator() {
        return this.pattern.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Char3D that)) return false;
        return Objects.equals(pattern, that.pattern);
    }

    @Override
    public int hashCode() {
        return hash == 0 ? (hash = this.pattern.hashCode()) : hash;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (Char2D layer : pattern) {
            builder.append(layer).append(",\n");
        }
        return builder.substring(0, builder.length() - 2) + "]";
    }
}
