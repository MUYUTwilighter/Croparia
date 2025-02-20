package cool.muyucloud.croparia.api.repo.fluid;

import cool.muyucloud.croparia.api.repo.Repo;
import cool.muyucloud.croparia.api.repo.RepoInterface;
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
public interface FluidInterfaceProvider {
    /**
     * Register a fluid repo provider to fabric
     *
     * @param provider the registration callback, probably it's your {@link BlockEntity} that implement this interface.
     */
    @ExpectPlatform
    static void register(FluidInterfaceProvider provider) {
        throw new AssertionError("Not implemented");
    }

    /**
     * <p>
     * Find a {@link Repo} of {@link FluidSpec} in world.
     * </p>
     * <p>
     * The return value might not be fully reliable. See methods in {@link PlatformFluidAgent} with annotation {@link Unreliable}
     * </p>
     *
     * @param world     the world
     * @param pos       the position of the block entity
     * @param direction the direction of the block entity
     * @return the fluid repo
     */
    @ExpectPlatform
    static Optional<PlatformFluidAgent> find(Level world, BlockPos pos, Direction direction) {
        throw new AssertionError("Not implemented");
    }

    /**
     * Provide your {@link RepoInterface}
     *
     * @param direction the directio of interaction
     */
    @Nullable
    RepoInterface<FluidSpec> visitFluid(@Nullable Direction direction);
}
