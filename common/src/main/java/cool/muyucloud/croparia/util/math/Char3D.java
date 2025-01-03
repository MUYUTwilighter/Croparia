package cool.muyucloud.croparia.util.math;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import net.minecraft.core.Vec3i;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Char3D implements Iterable<Character> {
    public static final Codec<Char3D> CODEC = new ListCodec<>(Char2D.CODEC).xmap(Char3D::new, Char3D::structure);

    private final List<Char2D> pattern;

    public Char3D(List<Char2D> structure) {
        int height = structure.size();
        int maxZ = structure.get(0).maxZ();
        int maxX = structure.get(0).maxX();
        this.pattern = new ArrayList<>(height);
        for (Char2D surface : structure) {
            if (surface.maxZ() != maxZ || surface.maxX() != maxX) {
                throw new IllegalArgumentException("Varying size: " + structure);
            }
            this.pattern.add(surface);
        }
    }

    public List<Char2D> structure() {
        return ImmutableList.copyOf(pattern);
    }

    public Vec3i size() {
        return new Vec3i(maxX(), maxY(), maxZ());
    }

    protected Char3D rotate() {
        List<Char2D> rotated = new ArrayList<>(pattern.size());
        for (Char2D surface : pattern) {
            rotated.add(surface.rotate());
        }
        return new Char3D(rotated);
    }

    protected Char3D mirror() {
        List<Char2D> mirrored = new ArrayList<>(pattern.size());
        for (Char2D surface : pattern) {
            mirrored.add(surface.mirror());
        }
        return new Char3D(mirrored);
    }

    public int maxY() {
        return pattern.size();
    }

    public int maxZ() {
        return pattern.isEmpty() ? 0 : pattern.get(0).maxZ();
    }

    public int maxX() {
        return pattern.isEmpty() ? 0 : pattern.get(0).maxX();
    }

    public char get(int x, int y, int z) {
        return pattern.get(y).get(x, z);
    }

    public int count(char c) {
        int count = 0;
        for (char ch : this) {
            if (ch == c) {
                count++;
            }
        }
        return count;
    }

    public @NotNull Optional<Vec3i> find(char c) {
        for (int y = 0; y < maxY(); y++) {
            Optional<Vec2i> result = pattern.get(y).find(c);
            if (result.isPresent()) {
                Vec2i pos = result.get();
                return Optional.of(pos.toVec3i(y));
            }
        }
        return Optional.empty();
    }

    @Override
    public @NotNull Iterator<Character> iterator() {
        return new Char3DIterator(this);
    }

    public static class Char3DIterator implements Iterator<Character> {
        private final Char3D char3D;
        private int x = 0, y = 0, z = 0;

        public Char3DIterator(Char3D structure) {
            this.char3D = structure;
        }

        @Override
        public boolean hasNext() {
            return z > char3D.maxY();
        }

        @Override
        public Character next() {
            if (!hasNext()) {
                return null;
            }
            char result = char3D.get(x, y, z);
            x++;
            if (x == char3D.maxX()) {
                x = 0;
                y += 1;
                if (y == char3D.maxZ()) {
                    y = 0;
                    z += 1;
                }
            }
            return result;
        }
    }
}
