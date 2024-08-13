//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.item.relics;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HornPlenty extends Item {
    private static List food = new ArrayList();

    public HornPlenty() {
        super((new Item.Settings()).maxCount(1));
    }

    public static void initFood() {
        food = Registries.ITEM.stream().filter(
            (item) -> item.getComponents().get(DataComponentTypes.FOOD) != null
            ).collect(Collectors.toList());
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        int index = context.getWorld().random.nextInt(food.size() - 1);
        context.getWorld().spawnEntity(
            new ItemEntity(
                context.getWorld(),
                (double) context.getBlockPos().getX() + 0.5,
                context.getBlockPos().getY() + 1,
                (double) context.getBlockPos().getZ() + 0.5,
                new ItemStack((ItemConvertible) food.get(index))
            )
        );
        return ActionResult.SUCCESS;
    }
}
