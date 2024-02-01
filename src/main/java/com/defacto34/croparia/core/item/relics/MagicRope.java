//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.item.relics;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class MagicRope extends Item {
    public MagicRope() {
        super(new FabricItemSettings());
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World level = context.getWorld();
        if (!level.isClient) {
            PlayerEntity player = context.getPlayer();
            ItemStack itemStack = context.getStack();
            NbtCompound tag = itemStack.getOrCreateNbt();
            int[] position;
            if (player.isSneaking()) {
                position = new int[]{player.getBlockPos().getX(), player.getBlockPos().getY(), player.getBlockPos().getZ()};
                tag.putIntArray("targetPos", position);
                player.sendMessage(Text.of("x = " + position[0] + " y = " + position[1] + " z = " + position[2]), true);
                return ActionResult.SUCCESS;
            }

            if (tag.contains("targetPos")) {
                position = tag.getIntArray("targetPos");
                player.teleport((double)position[0] + 0.5, (double)position[1], (double)position[2] + 0.5);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.FAIL;
    }
}
