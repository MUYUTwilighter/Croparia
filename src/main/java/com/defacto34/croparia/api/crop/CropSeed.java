//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.api.crop;

import com.defacto34.croparia.init.ItemInit;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class CropSeed extends BlockItem {
    public Crop crop;

    public CropSeed(Crop crop) {
        super(crop.cropBlock, ItemInit.settingsWithId("seed_crop_" + crop.cropName));
        this.crop = crop;
    }
}
