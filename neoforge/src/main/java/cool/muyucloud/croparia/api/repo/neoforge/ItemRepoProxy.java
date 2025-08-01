package cool.muyucloud.croparia.api.repo.neoforge;

import cool.muyucloud.croparia.api.repo.Repo;
import cool.muyucloud.croparia.api.repo.RepoProxy;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class ItemRepoProxy extends RepoProxy<ItemSpec> implements IItemHandler {
    public ItemRepoProxy(Repo<ItemSpec> repo) {
        super(repo);
    }

    @Override
    public int getSlots() {
        return this.size();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int i) {
        return this.resourceFor(i).createStack();
    }

    @Override
    public @NotNull ItemStack insertItem(int i, @NotNull ItemStack input, boolean simulate) {
        long accepted;
        if (simulate) {
            accepted = this.simAccept(i, ItemSpec.of(input), input.getCount());
        } else {
            accepted = this.accept(i, ItemSpec.of(input), input.getCount());
        }
        input = input.copy();
        input.shrink((int) accepted);
        return input;
    }

    @Override
    public @NotNull ItemStack extractItem(int i, int amount, boolean simulate) {
        ItemSpec item = this.resourceFor(i);
        ItemStack result = item.createStack();
        long consumed;
        if (simulate) {
            consumed = this.simConsume(i, item, amount);
        } else {
            consumed = this.consume(i, item, amount);
        }
        result.setCount((int) consumed);
        return result;
    }

    @Override
    public int getSlotLimit(int i) {
        ItemSpec item = this.resourceFor(i);
        return (int) this.capacityFor(i, item);
    }

    @Override
    public boolean isItemValid(int i, @NotNull ItemStack input) {
        return this.simAccept(i, ItemSpec.of(input), input.getCount()) >= input.getCount();
    }
}
