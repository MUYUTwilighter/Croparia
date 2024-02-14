package com.defacto34.croparia.mixin;

import com.defacto34.croparia.access.CropBlockAccess;
import net.minecraft.block.CropBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;
import net.minecraft.state.property.IntProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends PlantBlock implements Fertilizable, CropBlockAccess {
    public CropBlockMixin(Settings settings) {
        super(settings);
    }

    @Shadow protected abstract IntProperty getAgeProperty();

    @Unique
    @Override
    public IntProperty invokeGetAgeProperty() {
        return this.getAgeProperty();
    }
}
