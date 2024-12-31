package cool.muyucloud.util;

import net.minecraft.core.Vec3i;
import org.jetbrains.annotations.NotNull;

public record Vec2i(int x, int z) implements Comparable<Vec2i> {
    public Vec3i toVec3i(int y) {
        return new Vec3i(x, y, z);
    }

    public static Vec2i of(int x, int z) {
        return new Vec2i(x, z);
    }

    public static Vec2i of(Vec3i vec) {
        return of(vec.getX(), vec.getZ());
    }

    @Override
    public int compareTo(@NotNull Vec2i o) {
        return x + z - o.x - o.z;
    }
}
