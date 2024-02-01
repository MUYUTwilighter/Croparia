//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.api.crop;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class CropariaCrops extends CropBlock {
    public Crop crop;

    public CropariaCrops(Crop crop) {
        super(FabricBlockSettings.create().noCollision().sounds(BlockSoundGroup.CROP));
        this.crop = crop;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return this.crop.seed;
    }
}
