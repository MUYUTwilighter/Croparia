package cool.muyucloud.croparia.api.repo.fabric;

import cool.muyucloud.croparia.api.repo.Repo;
import cool.muyucloud.croparia.api.repo.RepoProxy;
import cool.muyucloud.croparia.api.resource.FabricItemSpec;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class ItemRepoProxy extends RepoProxy<ItemSpec> implements Storage<ItemVariant> {
    public ItemRepoProxy(Repo<ItemSpec> repo) {
        super(repo);
    }

    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext context) {
        ItemSpec itemSpec = FabricItemSpec.from(resource);
        if (context == null) {
            return this.accept(itemSpec, maxAmount);
        } else {
            long amount = this.simAccept(itemSpec, maxAmount);
            context.addCloseCallback((ignored, result) -> {
                if (result == TransactionContext.Result.COMMITTED) {
                    this.accept(itemSpec, amount);
                }
            });
            return amount;
        }
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext context) {
        ItemSpec itemSpec = FabricItemSpec.from(resource);
        if (context == null) {
            return this.consume(itemSpec, maxAmount);
        } else {
            long amount = this.simConsume(itemSpec, maxAmount);
            context.addCloseCallback((ignored, result) -> {
                if (result == TransactionContext.Result.COMMITTED) {
                    this.consume(itemSpec, amount);
                }
            });
            return amount;
        }
    }

    @Override
    public @NotNull Iterator<StorageView<ItemVariant>> iterator() {
        return new ItemIterator();
    }

    class ItemIterator implements Iterator<StorageView<ItemVariant>> {
        private int i = 0;

        @Override
        public boolean hasNext() {
            return this.i < ItemRepoProxy.this.size();
        }

        @Override
        public StorageView<ItemVariant> next() {
            return new ItemView(this.i++);
        }
    }

    class ItemView implements StorageView<ItemVariant> {
        private final int i;

        public ItemView(int i) {
            if (ItemRepoProxy.this.size() <= i) {
                throw new IllegalArgumentException("Index %s is out of bounds: %s".formatted(i, ItemRepoProxy.this.size()));
            }
            this.i = i;
        }

        @Override
        public long extract(ItemVariant resource, long maxAmount, TransactionContext context) {
            ItemSpec itemSpec = FabricItemSpec.from(resource);
            if (context == null) {
                return ItemRepoProxy.this.consume(i, itemSpec, maxAmount);
            } else {
                long amount = ItemRepoProxy.this.simConsume(i, itemSpec, maxAmount);
                context.addCloseCallback((ignored, result) -> {
                    if (result == TransactionContext.Result.COMMITTED) {
                        ItemRepoProxy.this.consume(i, itemSpec, amount);
                    }
                });
                return amount;
            }
        }

        @Override
        public boolean isResourceBlank() {
            return ItemRepoProxy.this.isEmpty(i);
        }

        @Override
        public ItemVariant getResource() {
            return FabricItemSpec.of(ItemRepoProxy.this.resourceFor(i));
        }

        @Override
        public long getAmount() {
            return ItemRepoProxy.this.amountFor(i, ItemRepoProxy.this.resourceFor(i));
        }

        @Override
        public long getCapacity() {
            return ItemRepoProxy.this.capacityFor(i, ItemRepoProxy.this.resourceFor(i));
        }
    }
}
