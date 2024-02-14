package com.defacto34.croparia.access;

import net.minecraft.state.property.IntProperty;
import org.spongepowered.asm.mixin.Unique;

public interface CropBlockAccess {
    @Unique
    IntProperty invokeGetAgeProperty();
}
