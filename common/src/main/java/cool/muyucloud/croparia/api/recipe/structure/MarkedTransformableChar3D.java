package cool.muyucloud.croparia.api.recipe.structure;

import com.fasterxml.jackson.databind.util.ArrayIterator;
import net.minecraft.core.Vec3i;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class MarkedTransformableChar3D implements Iterable<MarkedChar3D> {
    private final transient MarkedChar3D[] transformed;

    public MarkedTransformableChar3D(MarkedChar3D original) {
        Set<MarkedChar3D> transformed = new LinkedHashSet<>();
        for (int i = 0; i < 4; i++) {
            transformed.add(original);
            original = original.rotate();
        }
        original = original.mirror();
        for (int i = 0; i < 4; i++) {
            transformed.add(original);
            original = original.rotate();
        }
        this.transformed = new MarkedChar3D[transformed.size()];
        transformed.toArray(this.transformed);
    }

    public MarkedTransformableChar3D(Char3D original, Vec3i mark) {
        this(new MarkedChar3D(original, mark));
    }

    public MarkedChar3D getOriginal() {
        return transformed[0];
    }

    @NotNull
    @Override
    public Iterator<MarkedChar3D> iterator() {
        return new ArrayIterator<>(transformed);
    }
}
