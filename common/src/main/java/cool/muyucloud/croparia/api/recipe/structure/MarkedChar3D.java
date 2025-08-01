package cool.muyucloud.croparia.api.recipe.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;

import java.util.List;

/**
 * A structure with a mark position in it, this mark position is used for locating the center of the structure.
 * @implNote field {@link #mark} is not considered to be part of the structure and is not compared for equality.
 * */
@SuppressWarnings("unused")
public class MarkedChar3D extends Char3D {
    private final Vec3i mark;

    public MarkedChar3D(Char3D pattern, Vec3i mark) {
        super(pattern);
        if (mark.getX() >= this.maxX() || mark.getY() >= this.maxY() || mark.getZ() >= this.maxZ() || mark.getX() < 0 || mark.getY() < 0 || mark.getZ() < 0) {
            throw new IllegalArgumentException("Mark position out of bounds");
        }
        this.mark = mark;
    }

    public MarkedChar3D(List<Char2D> structure, Vec3i mark) {
        super(structure);
        if (mark.getX() >= this.maxX() || mark.getY() >= this.maxY() || mark.getZ() >= this.maxZ() || mark.getX() < 0 || mark.getY() < 0 || mark.getZ() < 0) {
            throw new IllegalArgumentException("Mark position out of bounds");
        }
        this.mark = mark;
    }

    public Vec3i mark() {
        return mark;
    }

    @Override
    public MarkedChar3D rotate() {
        int newX = mark.getZ();
        int newZ = maxZ() - 1 - mark.getX();
        Vec3i rotatedMark = new Vec3i(newX, mark.getY(), newZ);
        return new MarkedChar3D(super.rotate(), rotatedMark);
    }

    @Override
    public MarkedChar3D mirror() {
        int newX = maxX() - 1 - mark.getX();
        int newZ = mark.getZ();
        Vec3i mirroredMark = new Vec3i(newX, mark.getY(), newZ);
        return new MarkedChar3D(super.mirror(), mirroredMark);
    }

    public BlockPos getOriginInWorld(BlockPos markInWorld) {
        return markInWorld.subtract(mark);
    }
}
