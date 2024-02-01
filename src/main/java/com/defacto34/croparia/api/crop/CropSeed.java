//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.api.crop;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;

public class CropSeed extends BlockItem {
    public Crop crop;

    public CropSeed(Crop crop) {
        super(crop.cropBlock, new FabricItemSettings());
        this.crop = crop;
    }
}
