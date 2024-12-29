package cool.muyucloud.data;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec2;
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
        int rows = structure.get(0).rows();
        int cols = structure.get(0).cols();
        this.pattern = new ArrayList<>(height);
        for (Char2D surface : structure) {
            if (surface.rows() != rows || surface.cols() != cols) {
                throw new IllegalArgumentException("Varying size: " + structure);
            }
            this.pattern.add(surface);
        }
    }

    public List<Char2D> structure() {
        return ImmutableList.copyOf(pattern);
    }

    public Vec3i size() {
        return new Vec3i(cols(), rows(), height());
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

    public int height() {
        return pattern.size();
    }

    public int rows() {
        if (pattern.isEmpty()) {
            return 0;
        }
        return pattern.get(0).rows();
    }

    public int cols() {
        if (pattern.isEmpty()) {
            return 0;
        }
        return pattern.get(0).cols();
    }

    public char get(int x, int y, int z) {
        return pattern.get(z).get(x, y);
    }

    public boolean contains(char c) {
        for (char ch : this) {
            if (ch == c) {
                return true;
            }
        }
        return false;
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
        for (int z = 0; z < height(); z++) {
            Optional<Vec2> result = pattern.get(z).find(c);
            if (result.isPresent()) {
                Vec2 pos = result.get();
                return Optional.of(new Vec3i((int) pos.x, (int) pos.y, z));
            }
        }
        return Optional.empty();
    }

    public Vec3i mirror(Vec3i mark) {
        int newX = mark.getX();
        int newY = cols() - 1 - mark.getY();
        return new Vec3i(newX, newY, mark.getZ());
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
            return z > char3D.height();
        }

        @Override
        public Character next() {
            if (!hasNext()) {
                return null;
            }
            char result = char3D.get(x, y, z);
            x++;
            if (x == char3D.cols()) {
                x = 0;
                y += 1;
                if (y == char3D.rows()) {
                    y = 0;
                    z += 1;
                }
            }
            return result;
        }
    }
}
