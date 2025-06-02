package cool.muyucloud.croparia.api.resource.neoforge;

import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.fluids.FluidStack;

@SuppressWarnings("unused")
public class ForgeFluidSpec {
    public static FluidStack of(FluidSpec fluidSpec, long amount) {
        return new FluidStack(Holder.direct(fluidSpec.getResource()), (int) Math.min(amount / 81L, Integer.MAX_VALUE), fluidSpec.getComponentsPatch());
    }

    public static FluidStack of(FluidSpec fluidSpec, int amount) {
        return new FluidStack(Holder.direct(fluidSpec.getResource()), amount, fluidSpec.getComponentsPatch());
    }

    public static FluidSpec from(FluidStack stack) {
        return new FluidSpec(stack.getFluid(), stack.getComponentsPatch());
    }

    public static boolean matches(FluidSpec a, FluidStack b) {
        return a.getResource() == b.getFluid() && a.getComponentsPatch().equals(b.getComponentsPatch());
    }

    public static boolean matches(FluidStack a, FluidSpec b) {
        return b.getResource() == a.getFluid() && b.getComponentsPatch().equals(a.getComponentsPatch());
    }
}
