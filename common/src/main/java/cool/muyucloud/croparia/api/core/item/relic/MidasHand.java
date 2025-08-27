package cool.muyucloud.croparia.api.core.item.relic;

import cool.muyucloud.croparia.util.Constants;
import cool.muyucloud.croparia.util.PostConstants;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MidasHand extends Item {
    public MidasHand(Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState block = world.getBlockState(pos);
        @Nullable Player player = context.getPlayer();
        if (!world.getBlockState(pos).is(PostConstants.MIDAS_HAND_IMMUNE_BLOCKS) && !world.isClientSide && player != null) {
            if (player.totalExperience < 10) {
                Texts.overlay(player, Constants.INSUFFICIENT_XP);
                return InteractionResult.FAIL;
            }
            player.giveExperiencePoints(-10);
            player.getCooldowns().addCooldown(context.getItemInHand(), (int) block.getBlock().defaultDestroyTime());
            world.destroyBlock(pos, false);
            world.addFreshEntity(new ItemEntity(world, (double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5, new ItemStack(Items.GOLD_INGOT)));
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.FAIL;
        }
    }

    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (!entity.getCommandSenderWorld().isClientSide && !entity.getType().is(PostConstants.MIDAS_HAND_IMMUNE_ENTITIES)) {
            int xpConsume;
            int cooldown;
            if (entity instanceof Enemy) {
                xpConsume = (int) (entity.getHealth() * 2);
                cooldown = 400;
            } else {
                xpConsume = (int) (entity.getHealth());
                cooldown = 200;
            }
            if (player.totalExperience < xpConsume) {
                Texts.overlay(player, Constants.INSUFFICIENT_XP);
                return InteractionResult.FAIL;
            }
            player.giveExperiencePoints(-xpConsume);
            player.getCooldowns().addCooldown(stack, cooldown);
            ServerLevel world = (ServerLevel) entity.getCommandSenderWorld();
            world.destroyBlock(entity.blockPosition(), true);
            world.setBlock(entity.blockPosition(), Blocks.GOLD_BLOCK.defaultBlockState(), 2);
            entity.remove(RemovalReason.KILLED);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.FAIL;
        }
    }
}
