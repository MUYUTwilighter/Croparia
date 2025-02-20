package cool.muyucloud.croparia.api.resource.neoforge;

import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.fluids.FluidStack;

public class ForgeFluidSpec {
    public static FluidStack of(FluidSpec fluidSpec, long amount) {
        return new FluidStack(Holder.direct(fluidSpec.getFluid()), (int) Math.min(amount / 81L, Integer.MAX_VALUE), fluidSpec.getNbt());
    }

    public static FluidStack of(FluidSpec fluidSpec, int amount) {
        return new FluidStack(Holder.direct(fluidSpec.getFluid()), amount, fluidSpec.getNbt());
    }

    public static FluidSpec from(FluidStack stack) {
        return new FluidSpec(stack.getFluid(), stack.getComponentsPatch());
    }

    public static boolean matches(FluidSpec a, FluidStack b) {
        return a.getFluid() == b.getFluid() && FluidStack.isSameFluidSameComponents(of(a, 1), b);
    }
}
