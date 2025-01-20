package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class Fluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(CropariaIf.MOD_ID, Registry.FLUID_REGISTRY);

    public static final RegistrySupplier<ArchitecturyFlowingFluid> ELEMATILIUS = register(
        "elematilius", () -> new ArchitecturyFlowingFluid.Source(FluidAttributes.ELEMATILIUS)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> ELEMATILIUS_FLOWING = register(
        "elematilius_flowing", () -> new ArchitecturyFlowingFluid.Flowing(FluidAttributes.ELEMATILIUS)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> EARTH = register(
        "earth", () -> new ArchitecturyFlowingFluid.Source(FluidAttributes.EARTH)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> EARTH_FLOWING = register(
        "earth_flowing", () -> new ArchitecturyFlowingFluid.Flowing(FluidAttributes.EARTH)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> WATER = register(
        "water", () -> new ArchitecturyFlowingFluid.Source(FluidAttributes.WATER)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> WATER_FLOWING = register(
        "water_flowing", () -> new ArchitecturyFlowingFluid.Flowing(FluidAttributes.WATER)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> FIRE = register(
        "fire", () -> new ArchitecturyFlowingFluid.Source(FluidAttributes.FIRE)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> FIRE_FLOWING = register(
        "fire_flowing", () -> new ArchitecturyFlowingFluid.Flowing(FluidAttributes.FIRE)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> AIR = register(
        "air", () -> new ArchitecturyFlowingFluid.Source(FluidAttributes.AIR)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> AIR_FLOWING = register(
        "air_flowing", () -> new ArchitecturyFlowingFluid.Flowing(FluidAttributes.AIR)
    );

    public static <T extends Fluid> RegistrySupplier<T> register(String id, Supplier<T> supplier) {
        return FLUIDS.register(id, supplier);
    }

    public static void register() {
        CropariaIf.LOGGER.debug("Registering fluids");
        FLUIDS.register();
    }
}
