package cool.muyucloud.croparia.api.repo.neoforge;

import cool.muyucloud.croparia.api.repo.ProxyProvider;
import cool.muyucloud.croparia.api.repo.platform.PlatformFluidProxy;
import cool.muyucloud.croparia.api.repo.platform.PlatformItemProxy;
import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import cool.muyucloud.croparia.neoforge.access.BlockCapabilityAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.Optional;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public class ProxyProviderImpl {
    public static Optional<PlatformItemProxy> findItem(Level world, BlockPos pos, Direction direction) {
        IItemHandler handler = Capabilities.ItemHandler.BLOCK.getCapability(world, pos, null, null, direction);
        return handler == null ? Optional.empty() : Optional.of(PlatformItemProxyImpl.of(handler));
    }

    public static Optional<PlatformFluidProxy> findFluid(Level world, BlockPos pos, Direction direction) {
        IFluidHandler handler = Capabilities.FluidHandler.BLOCK.getCapability(world, pos, null, null, direction);
        return handler == null ? Optional.empty() : Optional.of(PlatformFluidProxyImpl.of(handler));
    }

    public static void registerItem(ProxyProvider<ItemSpec> provider, Block... blocks) {
        ((BlockCapabilityAccess) (Object) Capabilities.ItemHandler.BLOCK).croparia_if$registerItem(provider, blocks);
    }

    public static void registerFluid(ProxyProvider<FluidSpec> provider, Block... blocks) {
        ((BlockCapabilityAccess) (Object) Capabilities.FluidHandler.BLOCK).croparia_if$registerFluid(provider, blocks);
    }
}