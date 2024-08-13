//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.blockEntity;

import com.defacto34.croparia.access.CropBlockAccess;
import com.defacto34.croparia.init.BlockEntityInit;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GreenhouseBE extends BlockEntity implements NamedScreenHandlerFactory, Inventory {
    private final DefaultedList<ItemStack> inventory;

    public GreenhouseBE(BlockPos pos, BlockState state) {
        super(BlockEntityInit.GREENHOUSE_BE, pos, state);
        this.inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    }

    public static void tick(World level, BlockPos worldPosition, GreenhouseBE greenHouseBE) {
        if (!level.isClient) {
            BlockState belowState = level.getBlockState(worldPosition.down());
            if (belowState.getBlock() instanceof CropBlock block) {
                Item seed = block.getPickStack(level, worldPosition, belowState).getItem();
                if (block.isMature(belowState)) {
                    List<ItemStack> droppedStacks = Block.getDroppedStacks(
                            belowState, level.getServer().getWorld(level.getRegistryKey()),
                            worldPosition.down(), level.getBlockEntity(worldPosition.down()));
                    boolean decreased = false;
                    for (ItemStack stack : droppedStacks) {
                        if (!decreased && stack.isOf(seed)) {
                            stack.decrement(1);
                            decreased = true;
                        }
                        addItemStackInInventory(stack, greenHouseBE);
                    }

                    IntProperty property = ((CropBlockAccess) block).invokeGetAgeProperty();
                    int maxAge = block.getMaxAge();
                    level.setBlockState(worldPosition.down(), block.getDefaultState().with(property, maxAge / 2));
                }
            }
        }
    }

    public static void addItemStackInInventory(ItemStack itemstack, GreenhouseBE greenHouseBE) {
        int i = greenHouseBE.inventory.size();
        ItemStack stack = itemstack;

        for (int j = 0; j < i && !stack.isEmpty(); ++j) {
            if (greenHouseBE.getStack(j).getCount() < 64 && greenHouseBE.getStack(j).getItem() == stack.getItem() || greenHouseBE.getStack(j).isEmpty()) {
                greenHouseBE.setStack(j, new ItemStack(stack.getItem(), stack.getCount() + greenHouseBE.getStack(j).getCount()));
                stack = new ItemStack(stack.getItem(), stack.getCount() - greenHouseBE.getStack(j).getCount());
            }
        }

    }

    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, this.inventory, registryLookup);
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.writeNbt(nbt, this.inventory, registryLookup);
        super.writeNbt(nbt, registryLookup);
    }

    public int size() {
        return this.inventory.size();
    }

    public boolean isEmpty() {
        AtomicBoolean empty = new AtomicBoolean(true);
        this.inventory.stream().forEach((itemStack) -> {
            if (!itemStack.isEmpty()) {
                empty.set(false);
            }

        });
        return empty.get();
    }

    public ItemStack getStack(int slot) {
        return (ItemStack) this.inventory.get(slot);
    }

    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
    }

    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return player.squaredDistanceTo((double) this.pos.getX() + 0.5, (double) this.pos.getY() + 0.5, (double) this.pos.getZ() + 0.5) <= 64.0;
        }
    }

    public void clear() {
        this.inventory.clear();
    }

    public Text getDisplayName() {
        return Text.of("Greenhouse");
    }

    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new Generic3x3ContainerScreenHandler(syncId, inv, this);
    }
}
