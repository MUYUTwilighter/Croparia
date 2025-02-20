package cool.muyucloud.croparia.api.repo.fluid;

import cool.muyucloud.croparia.api.repo.Repo;
import cool.muyucloud.croparia.api.repo.RepoProxy;
import cool.muyucloud.croparia.api.repo.annotation.Unreliable;
import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * A signal to Fluid Repo API to identify a block entity for forge.<br>
 * In most cases, your {@link BlockEntity} implements this.
 */
@SuppressWarnings("unused")
public interface FluidProxyProvider {
    /**
     * <p>
     * Find a {@link Repo} of {@link FluidSpec} in world.
     * </p>
     * <p>
     * The return value might not be fully reliable. See methods in {@link PlatformFluidProxy} with annotation {@link Unreliable}
     * </p>
     *
     * @param world     the world
     * @param pos       the position of the block entity
     * @param direction the direction of the block entity
     * @return the fluid repo
     */
    @ExpectPlatform
    static Optional<PlatformFluidProxy> find(Level world, BlockPos pos, Direction direction) {
        throw new AssertionError("Not implemented");
    }

    /**
     * Provide your {@link RepoProxy}
     *
     * @param direction the directio of interaction
     */
    @Nullable
    RepoProxy<FluidSpec> visitFluid(@Nullable Direction direction);
}
