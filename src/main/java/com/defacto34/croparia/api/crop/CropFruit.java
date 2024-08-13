//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.api.crop;

import com.defacto34.croparia.Croparia;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class CropFruit extends Item {
    public Crop crop;

    public CropFruit(Crop crop) {
        super(new Item.Settings());
        this.crop = crop;
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!Croparia.CONFIG.getFruitUse()) {
            return ActionResult.PASS;
        }
        if (!context.getWorld().isClient) {
            if (this.crop.material != Items.AIR) {
                context.getWorld().spawnEntity(new ItemEntity(context.getWorld(), (double)context.getBlockPos().getX() + 0.5, context.getBlockPos().getY() + 1, (double)context.getBlockPos().getZ() + 0.5, new ItemStack(this.crop.material, 2)));
                context.getPlayer().getMainHandStack().decrement(1);
                return ActionResult.SUCCESS;
            }

            if (this.crop.tag != null) {
                context.getWorld().spawnEntity(new ItemEntity(context.getWorld(), (double)context.getBlockPos().getX() + 0.5, context.getBlockPos().getY() + 1, (double)context.getBlockPos().getZ() + 0.5, new ItemStack(Croparia.getItemFromTag(Identifier.of(this.crop.tag)).getItem(), 2)));
                context.getPlayer().getMainHandStack().decrement(1);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.FAIL;
    }
}
