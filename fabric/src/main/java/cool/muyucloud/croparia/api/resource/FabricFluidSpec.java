package cool.muyucloud.croparia.api.resource;

import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

public class FabricFluidSpec {
    public static FluidVariant of(FluidSpec fluid) {
        return FluidVariant.of(fluid.getFluid(), fluid.getNbt());
    }

    public static FluidSpec from(FluidVariant fluid) {
        return new FluidSpec(fluid.getFluid(), fluid.getComponents());
    }

    public static boolean matches(FluidVariant a, FluidSpec b) {
        return a.getFluid() == b.getFluid() && b.getNbt().equals(a.getComponents());
    }
}
