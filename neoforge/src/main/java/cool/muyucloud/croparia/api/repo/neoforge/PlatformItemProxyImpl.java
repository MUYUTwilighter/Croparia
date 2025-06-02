package cool.muyucloud.croparia.api.repo.neoforge;

import cool.muyucloud.croparia.api.repo.Repo;
import cool.muyucloud.croparia.api.repo.platform.PlatformItemProxy;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.Optional;

public class PlatformItemProxyImpl implements PlatformItemProxy {
    public static PlatformItemProxyImpl of(IItemHandler handler) {
        return new PlatformItemProxyImpl(handler);
    }

    private final IItemHandler handler;

    public PlatformItemProxyImpl(IItemHandler handler) {
        this.handler = handler;
    }

    public IItemHandler get() {
        return this.handler;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Repo<ItemSpec>> peel() {
        return this.get() instanceof Repo<?> repo ? Optional.of((Repo<ItemSpec>) repo) : Optional.empty();
    }

    @Override
    public int size() {
        return this.get().getSlots();
    }

    @Override
    public boolean isEmpty(int i) {
        return this.get().getStackInSlot(i).isEmpty();
    }

    @Override
    public long simConsume(int i, ItemSpec item, long amount) {
        ItemStack stored = this.get().getStackInSlot(i);
        if (item.is(stored)) {
            return this.get().extractItem(i, (int) Math.min(amount, stored.getCount()), true).getCount();
        } else {
            return 0;
        }
    }

    @Override
    public long consume(int i, ItemSpec item, long amount) {
        ItemStack stored = this.get().getStackInSlot(i);
        if (item.is(stored)) {
            return this.get().extractItem(i, (int) Math.min(amount, stored.getCount()), false).getCount();
        } else {
            return 0;
        }
    }

    @Override
    public long simAccept(int i, ItemSpec item, long amount) {
        return this.get().insertItem(i, item.createStack(amount), true).getCount();
    }

    @Override
    public long accept(int i, ItemSpec item, long amount) {
        return this.get().insertItem(i, item.createStack(amount), false).getCount();
    }

    @Override
    public long capacityFor(int i, ItemSpec item) {
        ItemStack stored = this.get().getStackInSlot(i);
        if ((stored.isEmpty() && this.get().insertItem(i, item.createStack(1), true).getCount() == 1)
            || item.is(stored)) {
            return this.get().getSlotLimit(i);
        } else {
            return 0;
        }
    }

    @Override
    public long amountFor(int i, ItemSpec item) {
        ItemStack stored = this.get().getStackInSlot(i);
        if (item.is(stored)) {
            return stored.getCount();
        } else {
            return 0;
        }
    }

    @Override
    public ItemSpec resourceFor(int i) {
        return ItemSpec.of(this.get().getStackInSlot(i));
    }
}
