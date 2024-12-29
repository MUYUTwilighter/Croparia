package cool.muyucloud.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import net.minecraft.world.phys.Vec2;
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
            for (int i = 0; i < rows; i++) {
                String row = surface.get(i);
                if (row.length() != cols) {
                    throw new IllegalArgumentException("Varying length: " + surface);
                }
                this.chars[i] = row.toCharArray();
            }
        }
    }

    public Char2D(int rows, int cols) {
        this.chars = new char[rows][cols];
    }

    public List<String> surface() {
        return Arrays.stream(chars).map(String::new).toList();
    }

    public int rows() {
        return chars.length;
    }

    public int cols() {
        return chars[0].length;
    }

    public Char2D rotate() {
        Char2D rotated = new Char2D(cols(), rows());
        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < cols(); j++) {
                rotated.chars[j][rows() - i - 1] = chars[i][j];
            }
        }
        return rotated;
    }

    public Char2D mirror() {
        Char2D mirrored = new Char2D(rows(), cols());
        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < cols(); j++) {
                mirrored.chars[i][cols() - j - 1] = chars[i][j];
            }
        }
        return mirrored;
    }

    public char get(int i, int j) {
        return chars[i][j];
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

    public Optional<Vec2> find(char c) {
        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < cols(); j++) {
                if (get(i, j) == c) {
                    return Optional.of(new Vec2(j, i));
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
        private int i = 0, j = 0;

        public Char2DIterator(Char2D surface) {
            this.char2D = surface;
        }

        @Override
        public boolean hasNext() {
            return i > char2D.rows();
        }

        @Override
        public Character next() {
            if (!hasNext()) {
                return null;
            }
            char result = char2D.get(i, j);
            j++;
            if (j == char2D.cols()) {
                j = 0;
                i += 1;
            }
            return result;
        }
    }
}
