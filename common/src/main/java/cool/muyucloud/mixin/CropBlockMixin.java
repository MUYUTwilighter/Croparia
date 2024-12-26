package cool.muyucloud.mixin;

import cool.muyucloud.access.CropBlockAccess;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends BushBlock implements BonemealableBlock, CropBlockAccess {
    public CropBlockMixin(Properties settings) {
        super(settings);
    }

    @Shadow protected abstract IntegerProperty getAgeProperty();

    @Unique
    @Override
    public IntegerProperty croparia_if$invokeGetAgeProperty() {
        return this.getAgeProperty();
    }
}
