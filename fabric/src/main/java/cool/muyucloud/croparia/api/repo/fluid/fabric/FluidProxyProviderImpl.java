package cool.muyucloud.croparia.api.repo.fluid.fabric;

import cool.muyucloud.croparia.api.repo.fabric.PlatformFluidProxyImpl;
import cool.muyucloud.croparia.api.repo.fluid.PlatformFluidProxy;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public class FluidProxyProviderImpl {
    static Optional<PlatformFluidProxy> find(Level world, BlockPos pos, Direction direction) {
        Storage<FluidVariant> storage = FluidStorage.SIDED.find(world, pos, direction);
        if (storage == null) return Optional.empty();
        return Optional.of(PlatformFluidProxyImpl.of(storage));
    }
}
