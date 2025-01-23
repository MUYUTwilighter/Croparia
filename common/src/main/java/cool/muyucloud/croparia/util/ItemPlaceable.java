package cool.muyucloud.croparia.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ItemPlaceable {
    /**
     * Places an item at a specific position in the world.
     *
     * @param world the world to place the item in
     * @param pos   the position to place the item at
     * @param stack the item stack to place
     * @see Util#placeItem(Level, BlockPos, ItemStack)
     */
    void placeItem(Level world, BlockPos pos, ItemStack stack);
}
