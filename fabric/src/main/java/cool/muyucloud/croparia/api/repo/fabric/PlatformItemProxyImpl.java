package cool.muyucloud.croparia.api.repo.fabric;

import cool.muyucloud.croparia.api.repo.Repo;
import cool.muyucloud.croparia.api.repo.platform.PlatformItemProxy;
import cool.muyucloud.croparia.api.resource.FabricItemSpec;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Optional;

public class PlatformItemProxyImpl implements PlatformItemProxy {
    @NotNull
    public static PlatformItemProxyImpl of(@NotNull Storage<ItemVariant> storage) {
        return new PlatformItemProxyImpl(storage);
    }

    private final Storage<ItemVariant> storage;

    public PlatformItemProxyImpl(Storage<ItemVariant> storage) {
        this.storage = storage;
    }

    public Storage<ItemVariant> get() {
        return this.storage;
    }

    @Nullable
    public StorageView<ItemVariant> get(int i) {
        int v = 0;
        Iterator<StorageView<ItemVariant>> iterator = this.storage.iterator();
        StorageView<ItemVariant> view = null;
        while (iterator.hasNext() && i > v) {
            v++;
            view = iterator.next();
        }
        if (i != v || view == null) {
            return null;
        } else {
            return view;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Repo<ItemSpec>> peel() {
        return this.get() instanceof Repo<?> fluidRepo ? Optional.of((Repo<ItemSpec>) fluidRepo) : Optional.empty();
    }

    @Override
    public int size() {
        int i = 0;
        for (StorageView<ItemVariant> ignored : this.get()) {
            i++;
        }
        return i;
    }

    @Override
    public boolean isEmpty(int i) {
        StorageView<ItemVariant> view = this.get(i);
        return view == null || view.isResourceBlank();
    }

    @Nullable
    @Override
    public ItemSpec resourceFor(int i) {
        StorageView<ItemVariant> view = this.get(i);
        if (view == null) {
            return null;
        } else {
            return FabricItemSpec.from(view.getResource());
        }
    }

    @Override
    public long simConsume(int i, ItemSpec resource, long amount) {
        if (!this.get().supportsExtraction()) {
            return 0L;
        }
        StorageView<ItemVariant> view = this.get(i);
        if (view == null) {
            return 0L;
        } else {
            return StorageUtil.simulateExtract(view, FabricItemSpec.of(resource), amount, null);
        }
    }

    @Override
    public long consume(ItemSpec resource, long amount) {
        if (!this.get().supportsExtraction()) {
            return 0L;
        }
        try (Transaction transaction = Transaction.openOuter()) {
            long result = this.get().extract(FabricItemSpec.of(resource), amount, transaction);
            transaction.commit();
            return result;
        }
    }

    @Override
    public long consume(int i, ItemSpec resource, long amount) {
        if (!this.get().supportsExtraction()) {
            return 0L;
        }
        StorageView<ItemVariant> view = this.get(i);
        if (view == null) {
            return 0L;
        } else {
            try (Transaction transaction = Transaction.openOuter()) {
                long result = view.extract(FabricItemSpec.of(resource), amount, transaction);
                transaction.commit();
                return result;
            }
        }
    }

    @Override
    public long simAccept(int i, ItemSpec resource, long amount) {
        if (!this.get().supportsInsertion()) {
            return 0L;
        }
        StorageView<ItemVariant> view = this.get(i);
        if (!(view instanceof Storage<?> s)) {
            return 0L;
        } else {
            try {
                @SuppressWarnings("unchecked")
                Storage<ItemVariant> storage = (Storage<ItemVariant>) s;
                return StorageUtil.simulateInsert(storage, FabricItemSpec.of(resource), amount, null);
            } catch (ClassCastException e) {
                return 0L;
            }
        }
    }

    @Override
    public long accept(int i, ItemSpec fluid, long amount) {
        if (!this.get().supportsInsertion()) {
            return 0L;
        }
        StorageView<ItemVariant> view = this.get(i);
        if (!(view instanceof Storage<?> s)) {
            return 0L;
        } else {
            try {
                @SuppressWarnings("unchecked")
                Storage<ItemVariant> storage = (Storage<ItemVariant>) s;
                try (Transaction transaction = Transaction.openOuter()) {
                    long result = storage.insert(FabricItemSpec.of(fluid), amount, transaction);
                    transaction.commit();
                    return result;
                }
            } catch (ClassCastException e) {
                return 0L;
            }
        }
    }

    @Override
    public long accept(ItemSpec fluid, long amount) {
        if (!this.get().supportsInsertion()) {
            return 0L;
        }
        try (Transaction transaction = Transaction.openOuter()) {
            long result = this.get().insert(FabricItemSpec.of(fluid), amount, transaction);
            transaction.commit();
            return result;
        }
    }

    @Override
    public long capacityFor(int i, ItemSpec fluid) {
        StorageView<ItemVariant> view = this.get(i);
        if (view == null) {
            return 0L;
        } else if (view.isResourceBlank() || FabricItemSpec.matches(view.getResource(), fluid)) {
            return view.getCapacity();
        } else {
            return 0L;
        }
    }

    @Override
    public long capacityFor(ItemSpec fluid) {
        if (!this.get().supportsInsertion()) {
            return 0L;
        }
        long result = 0L;
        for (StorageView<ItemVariant> view : this.get()) {
            if (FabricItemSpec.matches(view.getResource(), fluid) || view.isResourceBlank()) {
                result += view.getCapacity();
            }
        }
        return result;
    }

    @Override
    public long amountFor(int i, ItemSpec fluid) {
        StorageView<ItemVariant> view = this.get(i);
        if (view == null || !FabricItemSpec.matches(view.getResource(), fluid)) {
            return 0L;
        } else {
            return view.getAmount();
        }
    }

    @Override
    public long amountFor(ItemSpec fluid) {
        long result = 0L;
        for (StorageView<ItemVariant> view : this.get()) {
            if (FabricItemSpec.matches(view.getResource(), fluid)) {
                result += view.getAmount();
            }
        }
        return result;
    }
}
