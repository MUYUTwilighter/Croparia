package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.data.ElementsEnum;
import cool.muyucloud.croparia.fluid.ElementalFlowing;
import cool.muyucloud.croparia.fluid.ElementalSource;
import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class Fluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.FLUID);

    public static final RegistrySupplier<ArchitecturyFlowingFluid> ELEMATILIUS = register(
        "elematilius", () -> new ElementalSource(ElementsEnum.ELEMENTAL, FluidAttributes.ELEMATILIUS)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> ELEMATILIUS_FLOWING = register(
        "elematilius_flowing", () -> new ElementalFlowing(ElementsEnum.ELEMENTAL, FluidAttributes.ELEMATILIUS)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> EARTH = register(
        "earth", () -> new ElementalSource(ElementsEnum.EARTH, FluidAttributes.EARTH)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> EARTH_FLOWING = register(
        "earth_flowing", () -> new ElementalFlowing(ElementsEnum.EARTH, FluidAttributes.EARTH)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> WATER = register(
        "water", () -> new ElementalSource(ElementsEnum.WATER, FluidAttributes.WATER)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> WATER_FLOWING = register(
        "water_flowing", () -> new ElementalFlowing(ElementsEnum.WATER, FluidAttributes.WATER)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> FIRE = register(
        "fire", () -> new ElementalSource(ElementsEnum.FIRE, FluidAttributes.FIRE)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> FIRE_FLOWING = register(
        "fire_flowing", () -> new ElementalFlowing(ElementsEnum.FIRE, FluidAttributes.FIRE)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> AIR = register(
        "air", () -> new ElementalSource(ElementsEnum.AIR, FluidAttributes.AIR)
    );
    public static final RegistrySupplier<ArchitecturyFlowingFluid> AIR_FLOWING = register(
        "air_flowing", () -> new ElementalFlowing(ElementsEnum.AIR, FluidAttributes.AIR)
    );


    public static <T extends Fluid> RegistrySupplier<T> register(String id, Supplier<T> supplier) {
        return FLUIDS.register(id, supplier);
    }

    public static void register() {
        CropariaIf.LOGGER.debug("Registering fluids");
        FLUIDS.register();
    }
}
