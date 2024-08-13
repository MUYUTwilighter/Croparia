//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.item.relics;

import net.minecraft.component.ComponentMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.defacto34.croparia.core.CropariaComponentTypes.TARGET_POS;

public class MagicRope extends Item {

    public MagicRope() {
        super(new Item.Settings());
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World level = context.getWorld();
        if (!level.isClient) {
            PlayerEntity player = context.getPlayer();
            ItemStack itemStack = context.getStack();
            ComponentMap components = itemStack.getComponents();
            int[] position;
            if (player.isSneaking()) {
                position = new int[]{player.getBlockPos().getX(), player.getBlockPos().getY(), player.getBlockPos().getZ()};
                itemStack.set(TARGET_POS, player.getBlockPos());
                player.sendMessage(Text.of("x = " + position[0] + " y = " + position[1] + " z = " + position[2]), true);
                return ActionResult.SUCCESS;
            }

            if (components.contains(TARGET_POS)) {
                BlockPos targetPos = components.get(TARGET_POS);
                player.teleport(targetPos.getX() + 0.5D, targetPos.getY() + 0.5D, targetPos.getZ() + 0.5D, false);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.FAIL;
    }
}
