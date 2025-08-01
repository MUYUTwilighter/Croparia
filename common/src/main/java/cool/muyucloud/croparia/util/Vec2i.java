package cool.muyucloud.croparia.util;

import com.mojang.serialization.Codec;
import net.minecraft.core.Vec3i;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public record Vec2i(int x, int z)  implements Comparable<Vec2i> {
    public static final Codec<Vec2i> CODEC = Codec.INT.listOf(2, 2).xmap(ints -> new Vec2i(ints.getFirst(), ints.get(1)), Vec2i::toList);

    public Vec3i toVec3i(int y) {
        return new Vec3i(x, y, z);
    }

    public static Vec2i of(int x, int z) {
        return new Vec2i(x, z);
    }

    public static Vec2i of(Vec3i vec) {
        return of(vec.getX(), vec.getZ());
    }

    public List<Integer> toList() {
        return List.of(x, z);
    }

    @Override
    public int compareTo(@NotNull Vec2i o) {
        return x + z - o.x - o.z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec2i vec2i)) return false;
        return x == vec2i.x && z == vec2i.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }
}
