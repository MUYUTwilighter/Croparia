//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.item.relics;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MidasHand extends Item {
    public MidasHand() {
        super((new FabricItemSettings()).maxCount(1));
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World worldIn = context.getWorld();
        BlockPos pos = context.getBlockPos();
        if (worldIn.getBlockState(pos).getBlock() != Blocks.BEDROCK && !worldIn.isClient) {
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            worldIn.spawnEntity(new ItemEntity(worldIn, (double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, new ItemStack(Items.GOLD_INGOT)));
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        if (!entity.getEntityWorld().isClient) {
            entity.getEntityWorld().setBlockState(entity.getBlockPos(), Blocks.GOLD_BLOCK.getDefaultState());
            entity.remove(RemovalReason.KILLED);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }
}
