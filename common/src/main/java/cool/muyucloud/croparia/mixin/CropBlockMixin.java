package cool.muyucloud.croparia.mixin;

import cool.muyucloud.croparia.access.CropBlockAccess;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/**
 * Mixin class for {@link CropBlock} to access age property from {@link CropBlockAccess}.<br/>
 * @see CropBlockAccess
 */
@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends BushBlock implements BonemealableBlock, CropBlockAccess {
    public CropBlockMixin(Properties settings) {
        super(settings);
    }

    @Shadow
    protected abstract IntegerProperty getAgeProperty();

    @Unique
    @Override
    public IntegerProperty cif$getAgeProperty() {
        return this.getAgeProperty();
    }
}
