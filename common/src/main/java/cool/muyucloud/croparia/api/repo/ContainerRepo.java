package cool.muyucloud.croparia.api.repo;

import cool.muyucloud.croparia.api.resource.TypeToken;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record ContainerRepo(@NotNull Container container) implements Repo<ItemSpec> {
    @Override
    public int size() {
        return this.container().getContainerSize();
    }

    @Override
    public TypeToken<ItemSpec> getType() {
        return ItemSpec.TYPE;
    }

    @Override
    public boolean isEmpty(int i) {
        return this.container().isEmpty();
    }

    @Override
    public ItemSpec resourceFor(int i) {
        return ItemSpec.of(this.container().getItem(i));
    }

    @Override
    public long simConsume(int i, ItemSpec resource, long amount) {
        if (!this.container().canTakeItem(this.container(), i, resource.createStack(amount))) {
            return 0;
        }
        ItemStack stored = this.container().getItem(i);
        if (resource.is(stored)) {
            return Math.min(amount, stored.getCount());
        } else {
            return 0;
        }
    }

    @Override
    public long consume(int i, ItemSpec resource, long amount) {
        if (!this.container().canTakeItem(this.container(), i, resource.createStack(amount))) {
            return 0;
        }
        return this.container().removeItem(i, (int) amount).getCount();
    }

    @Override
    public long simAccept(int i, ItemSpec resource, long amount) {
        if (!this.container().canPlaceItem(i, resource.createStack(amount))) {
            return 0;
        }
        ItemStack stored = this.container().getItem(i);
        if (resource.is(stored) || stored.isEmpty()) {
            int accepted = (int) Math.min(this.capacityFor(i, resource) - stored.getCount(), amount);
            return Math.max(accepted, 0);
        } else {
            return 0;
        }
    }

    @Override
    public long accept(int i, ItemSpec resource, long amount) {
        if (!this.container().canPlaceItem(i, resource.createStack(amount))) {
            return 0;
        }
        ItemStack stored = this.container().getItem(i);
        if (resource.is(stored) || stored.isEmpty()) {
            int accepted = (int) Math.min(this.capacityFor(i, resource) - stored.getCount(), amount);
            this.container().setItem(i, resource.createStack(Math.max(accepted, 0) + stored.getCount()));
            return accepted;
        } else {
            return 0;
        }
    }

    @Override
    public long capacityFor(int i, ItemSpec resource) {
        return Math.min(this.container().getItem(i).getMaxStackSize(), this.container().getMaxStackSize());
    }

    @Override
    public long amountFor(int i, ItemSpec resource) {
        ItemStack stored = this.container().getItem(i);
        if (resource.is(stored)) {
            return stored.getCount();
        } else {
            return 0;
        }
    }
}
