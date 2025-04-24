package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.element.ElementsEnum;
import cool.muyucloud.croparia.api.element.fluid.ElementalFlowing;
import cool.muyucloud.croparia.api.element.fluid.ElementalSource;
import me.shedaniel.architectury.core.fluid.ArchitecturyFlowingFluid;
import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class Fluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(CropariaIf.MOD_ID, Registry.FLUID_REGISTRY);

    public static final RegistrySupplier<FlowingFluid> ELEMATILIUS = register(
        "elematilius", () -> new ElementalSource(ElementsEnum.ELEMENTAL, FluidAttributes.ELEMATILIUS)
    );
    public static final RegistrySupplier<FlowingFluid> ELEMATILIUS_FLOWING = register(
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
