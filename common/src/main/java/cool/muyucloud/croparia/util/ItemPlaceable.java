package cool.muyucloud.croparia.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ItemPlaceable {
    void placeItem(Level world, BlockPos pos, ItemStack stack);
}