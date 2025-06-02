package cool.muyucloud.croparia.api.resource;

import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

public class FabricFluidSpec {
    public static FluidVariant toVariant(FluidSpec fluid) {
        return FluidVariant.of(fluid.getResource(), fluid.getComponentsPatch());
    }

    public static FluidSpec from(FluidVariant fluid) {
        return new FluidSpec(fluid.getFluid(), fluid.getComponents());
    }

    public static boolean matches(FluidVariant a, FluidSpec b) {
        return a.getFluid() == b.getResource() && b.getComponentsPatch().equals(a.getComponents());
    }

    public static boolean matches(FluidSpec a, FluidVariant b) {
        return b.getFluid() == a.getResource() && a.getComponentsPatch().equals(b.getComponents());
    }
}
