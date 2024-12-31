package cool.muyucloud.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;

import java.util.List;

public class Char3DWithMark extends Char3D {
    private final Vec3i mark;

    public Char3DWithMark(Char3D pattern, Vec3i mark) {
        this(pattern.structure(), mark);
    }

    public Char3DWithMark(List<Char2D> structure, Vec3i mark) {
        super(structure);
        if (mark.getX() >= this.maxX() || mark.getY() >= this.maxY() || mark.getZ() >= this.maxZ() || mark.getX() < 0 || mark.getY() < 0 || mark.getZ() < 0) {
            throw new IllegalArgumentException("Mark position out of bounds");
        }
        this.mark = mark;
    }

    public Vec3i mark() {
        return mark;
    }

    public Char3DWithMark rotate() {
        int newX = mark.getZ();
        int newZ = maxZ() - 1 - mark.getX();
        Vec3i rotatedMark = new Vec3i(newX, mark.getY(), newZ);
        return new Char3DWithMark(super.rotate(), rotatedMark);
    }

    public Char3DWithMark mirror() {
        int newX = maxX() - 1 - mark.getX();
        int newZ = mark.getZ();
        Vec3i mirroredMark = new Vec3i(newX, mark.getY(), newZ);
        return new Char3DWithMark(super.mirror(), mirroredMark);
    }

    public BlockPos getOriginInWorld(BlockPos markInWorld) {
        return markInWorld.subtract(mark);
    }
}
