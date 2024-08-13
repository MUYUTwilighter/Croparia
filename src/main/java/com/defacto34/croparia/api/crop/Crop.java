//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.api.crop;

import com.defacto34.croparia.init.CropInit;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;

public class Crop {
    public String cropName;
    public Block cropBlock;
    public Item fruit;
    public Item seed;
    public int tier;
    public Item material;
    public int color;
    public CropType type;
    public String tag;
    public String resource;

    public Crop(String cropName, CropType type, int tier, Item material, int color) {
        this.cropName = cropName;
        this.type = type;
        this.material = material;
        this.tier = tier;
        this.color = color | 0xFF000000;
        this.tag = null;
        this.resource = Registries.ITEM.getId(material).toString();
        CropInit.cropList.add(this);
    }

    public Crop(String cropName, CropType type, int tier, String item, int color) {
        this.cropName = cropName;
        this.type = type;
        this.material = Items.AIR;
        this.tier = tier;
        this.color = color | 0xFF000000;
        this.tag = item;
        this.resource = item;
        CropInit.cropList.add(this);
    }
}
