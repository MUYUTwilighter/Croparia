//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cool.muyucloud.croparia.api.core.block.entity;

import cool.muyucloud.croparia.access.CropBlockAccess;
import cool.muyucloud.croparia.api.repo.ContainerRepo;
import cool.muyucloud.croparia.api.repo.RepoProxy;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import cool.muyucloud.croparia.registry.BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class GreenhouseBlockEntity extends BlockEntity implements MenuProvider, Container {
    private final NonNullList<ItemStack> inventory;
    private final RepoProxy<ItemSpec> proxy = RepoProxy.item(new ContainerRepo(this));

    public GreenhouseBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.GREENHOUSE_BE.get(), pos, state);
        this.inventory = NonNullList.withSize(9, ItemStack.EMPTY);
    }

    public static void tick(Level level, BlockPos worldPosition, GreenhouseBlockEntity greenHouseBlockEntity) {
        if (!level.isClientSide) {
            BlockState belowState = level.getBlockState(worldPosition.below());
            if (belowState.getBlock() instanceof CropBlock block) {
                Item seed = block.asItem();
                if (block.isMaxAge(belowState)) {
                    List<ItemStack> droppedStacks = Block.getDrops(belowState, Objects.requireNonNull(level.getServer()).getLevel(level.dimension()), worldPosition.below(), level.getBlockEntity(worldPosition.below()));
                    boolean decreased = false;
                    for (ItemStack stack : droppedStacks) {
                        if (!decreased && stack.is(seed)) {
                            stack.shrink(1);
                            decreased = true;
                        }
                        addItemStackInInventory(stack, greenHouseBlockEntity);
                    }

                    IntegerProperty property = ((CropBlockAccess) block).cif$invokeGetAgeProperty();
                    int maxAge = block.getMaxAge();
                    level.setBlockAndUpdate(worldPosition.below(), block.defaultBlockState().setValue(property, maxAge / 2));
                }
            }
        }
    }

    public static void addItemStackInInventory(ItemStack itemstack, GreenhouseBlockEntity greenHouseBlockEntity) {
        int i = greenHouseBlockEntity.inventory.size();
        ItemStack stack = itemstack;

        for (int j = 0; j < i && !stack.isEmpty(); ++j) {
            if (greenHouseBlockEntity.getItem(j).getCount() < 64 && greenHouseBlockEntity.getItem(j).getItem() == stack.getItem() || greenHouseBlockEntity.getItem(j).isEmpty()) {
                greenHouseBlockEntity.setItem(j, new ItemStack(stack.getItem(), stack.getCount() + greenHouseBlockEntity.getItem(j).getCount()));
                stack = new ItemStack(stack.getItem(), stack.getCount() - greenHouseBlockEntity.getItem(j).getCount());
            }
        }

    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        ContainerHelper.loadAllItems(nbt, this.inventory, provider);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        ContainerHelper.saveAllItems(nbt, this.inventory, provider);
        super.saveAdditional(nbt, provider);
    }

    public int getContainerSize() {
        return this.inventory.size();
    }

    public boolean isEmpty() {
        AtomicBoolean empty = new AtomicBoolean(true);
        this.inventory.forEach((itemStack) -> {
            if (!itemStack.isEmpty()) {
                empty.set(false);
            }

        });
        return empty.get();
    }

    public @NotNull ItemStack getItem(int slot) {
        return this.inventory.get(slot);
    }

    public @NotNull ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(this.inventory, slot, amount);
    }

    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.inventory, slot);
    }

    public void setItem(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
    }

    public boolean stillValid(Player player) {
        if (this.level == null || this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double) this.worldPosition.getX() + 0.5, (double) this.worldPosition.getY() + 0.5, (double) this.worldPosition.getZ() + 0.5) <= 64.0;
        }
    }

    public void clearContent() {
        this.inventory.clear();
    }

    public @NotNull Component getDisplayName() {
        return Component.nullToEmpty("Greenhouse");
    }

    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new DispenserMenu(syncId, inv, this);
    }

    public @Nullable RepoProxy<ItemSpec> visitItem() {
        return proxy;
    }
}
