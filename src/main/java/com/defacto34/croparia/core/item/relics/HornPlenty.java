//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.item.relics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;

public class HornPlenty extends Item {
    private static List<Item> food = new ArrayList();

    public HornPlenty() {
        super((new FabricItemSettings()).maxCount(1));
    }

    public static void initFood() {
        food = (List)Registries.ITEM.stream().filter((item) -> {
            return item.isFood();
        }).collect(Collectors.toList());
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        int index = context.getWorld().random.nextInt(food.size() - 1);
        context.getWorld().spawnEntity(new ItemEntity(context.getWorld(), (double)context.getBlockPos().getX() + 0.5, (double)(context.getBlockPos().getY() + 1), (double)context.getBlockPos().getZ() + 0.5, new ItemStack((ItemConvertible)food.get(index))));
        return ActionResult.SUCCESS;
    }
}
