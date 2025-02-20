package cool.muyucloud.croparia.api.repo.fluid.forge;

import cool.muyucloud.croparia.api.repo.fluid.PlatformFluidProxy;
import cool.muyucloud.croparia.api.repo.forge.PlatformFluidProxyImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Optional;

public class FluidProxyProviderImpl {
    static Optional<PlatformFluidProxy> find(Level world, BlockPos pos, Direction direction) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be == null) return Optional.empty();
        return be.getCapability(ForgeCapabilities.FLUID_HANDLER, direction).map(PlatformFluidProxyImpl::of);
    }
}
