package cool.muyucloud.croparia.api.core.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.StemBlock;
import org.jetbrains.annotations.NotNull;

public class GreenhouseItem extends BlockItem {
    public GreenhouseItem(Block block, Item.Properties settings) {
        super(block, settings);
    }

    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        if (!world.isClientSide) {
            if (world.isEmptyBlock(pos.above()) && (world.getBlockState(pos).getBlock() instanceof CropBlock || world.getBlockState(pos).getBlock() instanceof StemBlock || world.getBlockState(pos).getBlock() instanceof AttachedStemBlock)) {
                world.setBlockAndUpdate(pos.above(), this.getBlock().defaultBlockState());
                assert player != null;
                player.getMainHandItem().shrink(1);
                return InteractionResult.SUCCESS;
            }
            if (world.isEmptyBlock(pos.above(2)) && world.isEmptyBlock(pos.above())) {
                world.setBlockAndUpdate(pos.above(2), this.getBlock().defaultBlockState());
                assert player != null;
                player.getMainHandItem().shrink(1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
