//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.api.crop;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class CropSeed extends BlockItem {
    public Crop crop;

    public CropSeed(Crop crop) {
        super(crop.cropBlock, new Item.Settings());
        this.crop = crop;
    }
}
