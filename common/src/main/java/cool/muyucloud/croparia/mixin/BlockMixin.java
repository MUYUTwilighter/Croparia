package cool.muyucloud.croparia.mixin;

import cool.muyucloud.croparia.access.BlockAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Block.class)
public abstract class BlockMixin implements BlockAccess {
    @Shadow private BlockState defaultBlockState;

    @Override
    public void cif$modifyDefaultState(BlockState state) {
        this.defaultBlockState = state;
    }
}
