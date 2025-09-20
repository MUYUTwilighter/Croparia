package cool.muyucloud.croparia.access;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Unique;

public interface CropBlockAccess {
    @Unique
    IntegerProperty cif$getAgeProperty();
}
