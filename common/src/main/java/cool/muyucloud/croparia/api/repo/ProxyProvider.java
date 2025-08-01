package cool.muyucloud.croparia.api.repo;

import cool.muyucloud.croparia.api.repo.platform.PlatformFluidProxy;
import cool.muyucloud.croparia.api.repo.platform.PlatformItemProxy;
import cool.muyucloud.croparia.api.resource.TypedResource;
import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

@FunctionalInterface
@SuppressWarnings("unused")
public interface ProxyProvider<T extends TypedResource<?>> {
    @ExpectPlatform
    static Optional<PlatformItemProxy> findItem(Level world, BlockPos pos, Direction direction) {
        throw new AssertionError("Not implemented");
    }

    @ExpectPlatform
    static Optional<PlatformFluidProxy> findFluid(Level world, BlockPos pos, Direction direction) {
        throw new AssertionError("Not implemented");
    }

    @ExpectPlatform
    static void registerItem(ProxyProvider<ItemSpec> provider, Block... blocks) {
        throw new AssertionError("Not implemented");
    }

    @ExpectPlatform
    static void registerFluid(ProxyProvider<FluidSpec> provider, Block... blocks) {
        throw new AssertionError("Not implemented");
    }

    RepoProxy<T> visit(Level world, BlockPos pos, BlockState state, BlockEntity be, Direction direction);
}