package cool.muyucloud.croparia.api.recipe.structure;

import com.mojang.serialization.Codec;
import cool.muyucloud.croparia.util.Vec2i;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class Char2D implements Iterable<char[]> {
    public static final Codec<Char2D> CODEC = Codec.STRING.listOf().xmap(Char2D::new, Char2D::layer);

    private final char[][] chars;
    private final transient Map<Character, Integer> counts = new HashMap<>();
    private final transient int hash;

    public Char2D(List<String> layer) {
        if (layer.isEmpty()) {
            throw new IllegalArgumentException("Empty surface");
        } else {
            int cols = layer.getFirst().length();
            int rows = layer.size();
            this.chars = new char[rows][cols];
            for (int z = 0; z < rows; z++) {
                String row = layer.get(z);
                if (row.length() != cols) {
                    throw new IllegalArgumentException("Varying length: " + layer);
                }
                this.chars[z] = row.toCharArray();
                for (char c : this.chars[z]) {
                    this.counts.compute(c, (character, integer) -> integer == null ? 1 : integer + 1);
                }
            }
        }
        this.hash = Arrays.deepHashCode(chars);
    }

    public Char2D(int maxX, int maxZ) {
        this.chars = new char[maxZ][maxX];
        this.hash = Arrays.deepHashCode(chars);
    }

    public List<String> layer() {
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
        for (char[] col : this) {
            for (char character : col) {
                if (character == c) {
                    return true;
                }
            }
        }
        return false;
    }

    public int count(char c) {
        return counts.getOrDefault(c, 0);
    }

    public Collection<Character> chars() {
        return counts.keySet();
    }

    public void forEachChar(BiConsumer<Character, Integer> consumer) {
        counts.forEach(consumer);
    }

    public Optional<Vec2i> find(char c) {
        if (contains(c)) {
            for (int z = 0; z < maxZ(); z++) {
                for (int x = 0; x < maxX(); x++) {
                    if (get(x, z) == c) {
                        return Optional.of(Vec2i.of(x, z));
                    }
                }
            }
        } else {
            return Optional.empty();
        }
        throw new IllegalStateException("Char %s not present but found %d times".formatted(c, count(c)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Char2D that)) return false;
        return Objects.deepEquals(chars, that.chars);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public @NotNull Iterator<char[]> iterator() {
        return Arrays.stream(chars).iterator();
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (char[] row : chars) {
            builder.append(String.valueOf(row)).append(",\n");
        }
        return builder.substring(0, builder.length() - 2) + "]";
    }
}
