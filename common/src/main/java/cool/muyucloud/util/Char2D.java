package cool.muyucloud.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Char2D implements Iterable<Character> {
    public static final Codec<Char2D> CODEC = new ListCodec<>(Codec.STRING).xmap(Char2D::new, Char2D::surface);

    private final char[][] chars;

    public Char2D(List<String> surface) {
        if (surface.isEmpty()) {
            throw new IllegalArgumentException("Empty surface");
        } else {
            int cols = surface.get(0).length();
            int rows = surface.size();
            this.chars = new char[rows][cols];
            for (int z = 0; z < rows; z++) {
                String row = surface.get(z);
                if (row.length() != cols) {
                    throw new IllegalArgumentException("Varying length: " + surface);
                }
                this.chars[z] = row.toCharArray();
            }
        }
    }

    public Char2D(int maxX, int maxZ) {
        this.chars = new char[maxZ][maxX];
    }

    public List<String> surface() {
        return Arrays.stream(chars).map(String::new).toList();
    }

    public int maxZ() {
        return chars.length;
    }

    public int maxX() {
        return chars.length == 0 ? 0 : chars[0].length;
    }

    public Char2D rotate() {
        Char2D rotated = new Char2D(maxX(), maxZ());
        for (int z = 0; z < maxZ(); z++) {
            for (int x = 0; x < maxX(); x++) {
                rotated.chars[x][maxZ() - z - 1] = chars[z][x];
            }
        }
        return rotated;
    }

    public Char2D mirror() {
        Char2D mirrored = new Char2D(maxZ(), maxX());
        for (int z = 0; z < maxZ(); z++) {
            for (int x = 0; x < maxX(); x++) {
                mirrored.chars[z][maxX() - x - 1] = chars[z][x];
            }
        }
        return mirrored;
    }

    public char get(int x, int z) {
        return chars[z][x];
    }

    public boolean contains(char c) {
        for (char character : this) {
            if (character == c) {
                return true;
            }
        }
        return false;
    }

    public int count(char c) {
        int count = 0;
        for (char character : this) {
            if (character == c) {
                count++;
            }
        }
        return count;
    }

    public Optional<Vec2i> find(char c) {
        for (int z = 0; z < maxZ(); z++) {
            for (int x = 0; x < maxX(); x++) {
                if (get(x, z) == c) {
                    return Optional.of(Vec2i.of(x, z));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public @NotNull Iterator<Character> iterator() {
        return new Char2DIterator(this);
    }

    public static class Char2DIterator implements Iterator<Character> {
        private final Char2D char2D;
        private int z = 0, x = 0;

        public Char2DIterator(Char2D surface) {
            this.char2D = surface;
        }

        @Override
        public boolean hasNext() {
            return z >= char2D.maxZ();
        }

        @Override
        public Character next() {
            if (!hasNext()) {
                return null;
            }
            char result = char2D.get(z, x);
            x++;
            if (x == char2D.maxX()) {
                x = 0;
                z++;
            }
            return result;
        }
    }
}
