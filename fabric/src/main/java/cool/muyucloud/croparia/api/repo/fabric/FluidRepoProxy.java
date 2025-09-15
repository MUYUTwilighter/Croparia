package cool.muyucloud.croparia.api.repo.fabric;

import cool.muyucloud.croparia.api.repo.Repo;
import cool.muyucloud.croparia.api.repo.RepoProxy;
import cool.muyucloud.croparia.api.resource.FabricFluidSpec;
import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class FluidRepoProxy extends RepoProxy<FluidSpec> implements Storage<FluidVariant> {
    public FluidRepoProxy(Repo<FluidSpec> repo) {
        super(repo);
    }

    @Override
    public long insert(FluidVariant resource, long maxAmount, TransactionContext context) {
        FluidSpec fluidSpec = FabricFluidSpec.from(resource);
        if (context == null) {
            return this.accept(fluidSpec, maxAmount);
        } else {
            long amount = this.simAccept(fluidSpec, maxAmount);
            context.addCloseCallback((ignored, result) -> {
                if (result == TransactionContext.Result.COMMITTED) {
                    this.accept(fluidSpec, amount);
                }
            });
            return amount;
        }
    }

    @Override
    public long extract(FluidVariant resource, long maxAmount, TransactionContext context) {
        FluidSpec fluidSpec = FabricFluidSpec.from(resource);
        if (context == null) {
            return this.consume(fluidSpec, maxAmount);
        } else {
            long amount = this.simConsume(fluidSpec, maxAmount);
            context.addCloseCallback((ignored, result) -> {
                if (result == TransactionContext.Result.COMMITTED) {
                    this.consume(fluidSpec, amount);
                }
            });
            return amount;
        }
    }

    @Override
    public @NotNull Iterator<StorageView<FluidVariant>> iterator() {
        return new ItemIterator();
    }

    class ItemIterator implements Iterator<StorageView<FluidVariant>> {
        private int i = 0;

        @Override
        public boolean hasNext() {
            return this.i < FluidRepoProxy.this.size();
        }

        @Override
        public StorageView<FluidVariant> next() {
            return new ItemView(this.i++);
        }
    }

    class ItemView implements StorageView<FluidVariant> {
        private final int i;

        public ItemView(int i) {
            if (FluidRepoProxy.this.size() <= i) {
                throw new IllegalArgumentException("Index %s is out of bounds: %s".formatted(i, FluidRepoProxy.this.size()));
            }
            this.i = i;
        }

        @Override
        public long extract(FluidVariant resource, long maxAmount, TransactionContext context) {
            FluidSpec fluidSpec = FabricFluidSpec.from(resource);
            if (context == null) {
                return FluidRepoProxy.this.consume(i, fluidSpec, maxAmount);
            } else {
                long amount = FluidRepoProxy.this.simConsume(i, fluidSpec, maxAmount);
                context.addCloseCallback((ignored, result) -> {
                    if (result == TransactionContext.Result.COMMITTED) {
                        FluidRepoProxy.this.consume(i, fluidSpec, amount);
                    }
                });
                return amount;
            }
        }

        @Override
        public boolean isResourceBlank() {
            return FluidRepoProxy.this.isEmpty(i);
        }

        @Override
        public FluidVariant getResource() {
            return FabricFluidSpec.of(FluidRepoProxy.this.resourceFor(i));
        }

        @Override
        public long getAmount() {
            return FluidRepoProxy.this.amountFor(i, FluidRepoProxy.this.resourceFor(i));
        }

        @Override
        public long getCapacity() {
            return FluidRepoProxy.this.capacityFor(i, FluidRepoProxy.this.resourceFor(i));
        }
    }
}
