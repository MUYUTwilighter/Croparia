package cool.muyucloud.croparia.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

public class Util {
    public static boolean allNull(Object... objects) {
        return Arrays.stream(objects).allMatch(Objects::isNull);
    }

    public static void deleteDir(File dir) throws IOException {
        if (dir.isDirectory()) {
            for (File child : Objects.requireNonNull(dir.listFiles())) {
                deleteDir(child);
            }
        }
        Files.delete(dir.toPath());
    }

    public static boolean isEdible(ItemStack item) {
        return item.has(DataComponents.FOOD);
    }

    @Nullable
    public static FoodProperties getFoodProperties(ItemStack item) {
        if (isEdible(item)) {
            return item.get(DataComponents.FOOD);
        }
        return null;
    }

    public static ServerLevel getLevel(ResourceLocation id, MinecraftServer server) {
        return server.getLevel(ResourceKey.create(Registries.DIMENSION, id));
    }

    /**
     * Returns the block position that the player is currently looking at.
     *
     * @param player The player whose line of sight is being checked.
     * @return The block position that the player is looking at.
     */
    public static BlockPos lookingAt(@NotNull Player player) {
        Level world = player.level();
        ClipContext context = new ClipContext(player.getEyePosition(), player.getEyePosition().add(player.getLookAngle().multiply(5, 5, 5)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
        BlockHitResult result = world.clip(context);
        return result.getBlockPos();
    }

    /**
     * Places an item at the specified position in the world.
     *
     * @param world The world where the item will be placed.
     * @param pos   The position where the item will be placed.
     * @param stack The item stack to be placed.
     */
    public static void placeItem(Level world, BlockPos pos, ItemStack stack) {
        ItemStack newStack = stack.copyAndClear();
        world.addFreshEntity(new ItemEntity(world, (double) pos.getX() + 0.5, (double) pos.getY() + 0.6, (double) pos.getZ() + 0.5, newStack, 0, 0, 0));
    }

    /**
     * Attempts to store an item stack in a container below the specified position.
     *
     * @param world The world where the item will be stored.
     * @param pos   The position below which the container is located.
     * @param stack The item stack to be stored.
     * @return The remaining item stack if it couldn't be fully stored, or an empty stack if it was fully stored.
     */
    public static ItemStack tryStoreItemBelow(Level world, BlockPos pos, ItemStack stack) {
        BlockEntity below = world.getBlockEntity(pos.below());
        if (below instanceof Container container) {
            for (int i = 0; i < container.getMaxStackSize(); i++) {
                ItemStack containerItem = container.getItem(i);
                if (containerItem.isEmpty()) {
                    container.setItem(i, stack);
                    return ItemStack.EMPTY;
                } else if (ItemStack.isSameItemSameComponents(containerItem, stack)) {
                    int space = containerItem.getMaxStackSize() - containerItem.getCount();
                    int count = Math.min(stack.getCount(), space);
                    containerItem.setCount(containerItem.getCount() + count);
                    stack.shrink(count);
                }
            }
        }
        return stack;
    }

    /**
     * Exports an item to the world, attempting to store it in a container below the specified position.
     * If the item cannot be stored, it will be added to the player's inventory or dropped as an item entity.
     *
     * @param world  the level to export the item to
     * @param pos    the position to export the item at
     * @param stack  the item stack to export
     * @param player the player to add the item to, or null to drop the item
     */
    public static void exportItem(Level world, BlockPos pos, ItemStack stack, @Nullable Player player) {
        ItemStack remain = tryStoreItemBelow(world, pos, stack);
        if (remain.isEmpty()) {
            return;
        }
        if (player != null) {
            player.addItem(remain);
        }
        world.addFreshEntity(new ItemEntity(world, (double) pos.getX() + 0.5, (double) pos.getY() + 0.6, (double) pos.getZ() + 0.5, remain, 0, 0, 0));
    }

    public static DataComponentPredicate extractPredicate(DataComponentPatch patch) {
        DataComponentPredicate.Builder builder = DataComponentPredicate.builder();
        patch.entrySet().forEach(entry -> builder.expect(TypedDataComponent.createUnchecked(entry.getKey(), entry.getValue())));
        return builder.build();
    }
}
