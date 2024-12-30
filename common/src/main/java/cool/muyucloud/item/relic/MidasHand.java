package cool.muyucloud.item.relic;

import cool.muyucloud.registry.Tabs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class MidasHand extends Item {
    public MidasHand() {
        super(new Properties().stacksTo(1).arch$tab(Tabs.MAIN));
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (worldIn.getBlockState(pos).getBlock() != Blocks.BEDROCK && !worldIn.isClientSide) {
            worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            worldIn.addFreshEntity(new ItemEntity(worldIn, (double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5, new ItemStack(Items.GOLD_INGOT)));
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.FAIL;
        }
    }

    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (!entity.getCommandSenderWorld().isClientSide) {
            entity.getCommandSenderWorld().setBlockAndUpdate(entity.blockPosition(), Blocks.GOLD_BLOCK.defaultBlockState());
            entity.remove(RemovalReason.KILLED);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.FAIL;
        }
    }
}
