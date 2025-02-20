package cool.muyucloud.croparia.api.repo.fluid.neoforge;

import cool.muyucloud.croparia.api.repo.fluid.PlatformFluidProxy;
import cool.muyucloud.croparia.api.repo.neoforge.PlatformFluidProxyImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.Optional;

@SuppressWarnings("unused")
public class FluidProxyProviderImpl {
    static Optional<PlatformFluidProxy> find(Level world, BlockPos pos, Direction direction) {
        IFluidHandler handler = world.getCapability(Capabilities.FluidHandler.BLOCK, pos, direction);
        if (handler == null ) {
            return Optional.empty();
        } else {
            return Optional.of(PlatformFluidProxyImpl.of(handler));
        }
    }
}
