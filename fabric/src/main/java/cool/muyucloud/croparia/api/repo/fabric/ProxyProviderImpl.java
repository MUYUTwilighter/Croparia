package cool.muyucloud.croparia.api.repo.fabric;

import cool.muyucloud.croparia.api.repo.ProxyProvider;
import cool.muyucloud.croparia.api.repo.platform.PlatformFluidProxy;
import cool.muyucloud.croparia.api.repo.platform.PlatformItemProxy;
import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class ProxyProviderImpl {
    public static Optional<PlatformItemProxy> findItem(Level world, BlockPos pos, Direction direction) {
        Storage<ItemVariant> storage = ItemStorage.SIDED.find(world, pos, direction);
        return storage == null ? Optional.empty() : Optional.of(PlatformItemProxyImpl.of(storage));
    }

    public static Optional<PlatformFluidProxy> findFluid(Level world, BlockPos pos, Direction direction) {
        Storage<FluidVariant> storage = FluidStorage.SIDED.find(world, pos, direction);
        return storage == null ? Optional.empty() : Optional.of(PlatformFluidProxyImpl.of(storage));
    }

    @SuppressWarnings("unchecked")
    public static void registerItem(ProxyProvider<ItemSpec> provider, Block... blocks) {
        ItemStorage.SIDED.registerForBlocks((world, pos, state, be, context) -> (Storage<ItemVariant>) provider.visit(world, pos, state, be, context), blocks);
    }

    @SuppressWarnings("unchecked")
    public static void registerFluid(ProxyProvider<FluidSpec> provider, Block... blocks) {
        FluidStorage.SIDED.registerForBlocks((world, pos, state, be, context) -> (Storage<FluidVariant>) provider.visit(world, pos, state, be, context), blocks);
    }
}