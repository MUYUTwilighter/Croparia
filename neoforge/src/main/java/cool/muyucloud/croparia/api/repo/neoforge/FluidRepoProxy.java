package cool.muyucloud.croparia.api.repo.neoforge;

import cool.muyucloud.croparia.api.repo.Repo;
import cool.muyucloud.croparia.api.repo.RepoProxy;
import cool.muyucloud.croparia.api.resource.neoforge.ForgeFluidSpec;
import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class FluidRepoProxy extends RepoProxy<FluidSpec> implements IFluidHandler {
    public FluidRepoProxy(Repo<FluidSpec> repo) {
        super(repo);
    }

    @Override
    public int getTanks() {
        return this.get().size();
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int i) {
        FluidSpec fluidSpec = this.resourceFor(i);
        return ForgeFluidSpec.of(fluidSpec, this.amountFor(i, fluidSpec));
    }

    @Override
    public int getTankCapacity(int i) {
        return (int) (this.capacityFor(i, this.resourceFor(i)) / 81L);
    }

    @Override
    public boolean isFluidValid(int i, @NotNull FluidStack input) {
        FluidSpec fluid = ForgeFluidSpec.from(input);
        long amount = input.getAmount() * 81L;
        return this.simAccept(i, fluid, amount) >= amount;
    }

    @Override
    public int fill(@NotNull FluidStack input, FluidAction fluidAction) {
        FluidSpec fluid = ForgeFluidSpec.from(input);
        if (fluidAction.simulate()) {
            return (int) (this.simAccept(fluid, input.getAmount() * 81L) / 81);
        } else if (fluidAction.execute()) {
            return (int) (this.accept(fluid, input.getAmount() * 81L) / 81L);
        } else {
            return 0;
        }
    }

    @Override
    public @NotNull FluidStack drain(@NotNull FluidStack input, FluidAction fluidAction) {
        FluidSpec fluid = ForgeFluidSpec.from(input);
        if (fluidAction.simulate()) {
            long consumed = this.simConsume(ForgeFluidSpec.from(input), input.getAmount() * 81L);
            return ForgeFluidSpec.of(fluid, consumed);
        } else if (fluidAction.execute()) {
            long consumed = this.consume(ForgeFluidSpec.from(input), input.getAmount() * 81L);
            return ForgeFluidSpec.of(fluid, consumed);
        } else {
            return FluidStack.EMPTY;
        }
    }

    @Override
    public @NotNull FluidStack drain(int amount, @NotNull FluidAction fluidAction) {
        if (this.size() < 1) return FluidStack.EMPTY;
        FluidSpec fluid = this.resourceFor(0);
        if (fluidAction.simulate()) {
            long consumed = this.simConsume(fluid, amount * 81L);
            return ForgeFluidSpec.of(fluid, consumed);
        } else if (fluidAction.execute()) {
            long consumed = this.consume(fluid, amount * 81L);
            return ForgeFluidSpec.of(fluid, consumed);
        } else {
            return FluidStack.EMPTY;
        }
    }
}
