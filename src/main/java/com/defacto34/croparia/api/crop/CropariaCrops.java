//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.api.crop;

import com.defacto34.croparia.init.BlockInit;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.BlockSoundGroup;

public class CropariaCrops extends CropBlock {
    public Crop crop;

    public CropariaCrops(Crop crop) {
        super(BlockInit.settingsWithId("crop_" + crop.cropName).noCollision().sounds(BlockSoundGroup.CROP));
        this.crop = crop;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return this.crop.seed;
    }
}
