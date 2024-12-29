package cool.muyucloud.data;

import net.minecraft.core.Vec3i;

import java.util.List;

public class Char3DWithMark extends Char3D {
    private final Vec3i mark;

    public Char3DWithMark(Char3D pattern, Vec3i mark) {
        super(pattern.structure());
        this.mark = mark;
    }

    public Char3DWithMark(List<Char2D> structure, Vec3i mark) {
        super(structure);
        this.mark = mark;
    }

    public Vec3i mark() {
        return mark;
    }

    public Char3DWithMark rotate() {
        int newX = mark.getY();
        int newY = rows() - 1 - mark.getY();
        Vec3i rotatedMark = new Vec3i(newX, newY, mark.getZ());
        return new Char3DWithMark(super.rotate(), rotatedMark);
    }

    public Char3DWithMark mirror() {
        int newX = cols() - 1 - mark.getX();
        int newY = mark.getY();
        Vec3i mirroredMark = new Vec3i(newX, newY, mark.getZ());
        return new Char3DWithMark(super.mirror(), mirroredMark);
    }
}
