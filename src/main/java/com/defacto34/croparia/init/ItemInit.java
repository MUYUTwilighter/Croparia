//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.init;

import com.defacto34.croparia.Croparia;
import com.defacto34.croparia.api.crop.Crop;
import com.defacto34.croparia.api.crop.CropFruit;
import com.defacto34.croparia.api.crop.CropSeed;
import com.defacto34.croparia.core.item.elements.Elematilius;
import com.defacto34.croparia.core.item.elements.ElementalAir;
import com.defacto34.croparia.core.item.elements.ElementalEarth;
import com.defacto34.croparia.core.item.elements.ElementalFire;
import com.defacto34.croparia.core.item.elements.ElementalWater;
import com.defacto34.croparia.core.item.relics.HornPlenty;
import com.defacto34.croparia.core.item.relics.InfiniteApple;
import com.defacto34.croparia.core.item.relics.MagicRope;
import com.defacto34.croparia.core.item.relics.MidasHand;
import com.defacto34.croparia.core.util.ElementsEnum;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemInit {
    public static final Item ELEMATILIUS = register("elematilius", new Elematilius(new FabricItemSettings()));
    public static final Item ELEMENTAL_FIRE = register("elemental_fire", new ElementalFire());
    public static final Item ELEMENTAL_WATER = register("elemental_water", new ElementalWater());
    public static final Item ELEMENTAL_EARTH = register("elemental_earth", new ElementalEarth());
    public static final Item ELEMENTAL_AIR = register("elemental_air", new ElementalAir());
    public static final Item POTION_ELEMATILIUS = register("potion_elematilius", new Item(new FabricItemSettings()));
    public static final Item POTION_WATER = register("potion_water", new Item(new FabricItemSettings()));
    public static final Item POTION_FIRE = register("potion_fire", new Item(new FabricItemSettings()));
    public static final Item POTION_EARTH = register("potion_earth", new Item(new FabricItemSettings()));
    public static final Item POTION_AIR = register("potion_air", new Item(new FabricItemSettings()));
    public static List<Item> croparias = new ArrayList();
    public static final Item CROPARIA = registerCroparia("croparia", new Item(new FabricItemSettings()));
    public static final Item CROPARIA2 = registerCroparia("croparia2", new Item(new FabricItemSettings()));
    public static final Item CROPARIA3 = registerCroparia("croparia3", new Item(new FabricItemSettings()));
    public static final Item CROPARIA4 = registerCroparia("croparia4", new Item(new FabricItemSettings()));
    public static final Item CROPARIA5 = registerCroparia("croparia5", new Item(new FabricItemSettings()));
    public static final Item CROPARIA6 = registerCroparia("croparia6", new Item(new FabricItemSettings()));
    public static final Item CROPARIA7 = registerCroparia("croparia7", new Item(new FabricItemSettings()));
    public static final Item MIDAS_HAND = register("midas_hand", new MidasHand());
    public static final Item INFINITE_APPLE = register("infinite_apple", new InfiniteApple());
    public static final Item HORN_PLENTY = register("horn_plenty", new HornPlenty());
    public static final Item MAGIC_ROPE = register("magic_rope", new MagicRope());

    public ItemInit() {
    }

    public static ElementsEnum getElementFromPotion(Item item) {
        if (item == POTION_ELEMATILIUS) {
            return ElementsEnum.ELEMENTAL;
        } else if (item == POTION_WATER) {
            return ElementsEnum.WATER;
        } else if (item == POTION_FIRE) {
            return ElementsEnum.FIRE;
        } else if (item == POTION_EARTH) {
            return ElementsEnum.EARTH;
        } else {
            return item == POTION_AIR ? ElementsEnum.AIR : ElementsEnum.EMPTY;
        }
    }

    public static Item getPotionFromElement(ElementsEnum element) {
        switch (element) {
            case WATER:
                return POTION_WATER;
            case FIRE:
                return POTION_FIRE;
            case EARTH:
                return POTION_EARTH;
            case AIR:
                return POTION_AIR;
            case ELEMENTAL:
                return POTION_ELEMATILIUS;
            case EMPTY:
                return Items.GLASS_BOTTLE;
            default:
                return Items.GLASS_BOTTLE;
        }
    }

    public static Item register(String name, Item item) {
        ItemGroupEvents.modifyEntriesEvent(Croparia.MAIN).register((entries) -> {
            entries.add(item);
        });
        return Registry.register(Registries.ITEM, new Identifier(Croparia.MOD_ID, name), item);
    }

    public static Item registerCroparia(String name, Item item) {
        croparias.add(item);
        return register(name, item);
    }

    public static void registerCrop(Crop crop) {
        crop.seed = Registry.register(Registries.ITEM, new Identifier(Croparia.MOD_ID, "seed_crop_" + crop.cropName), new CropSeed(crop));
        crop.fruit = Registry.register(Registries.ITEM, new Identifier(Croparia.MOD_ID, "fruit_" + crop.cropName), new CropFruit(crop));
        ItemGroupEvents.modifyEntriesEvent(Croparia.CROP).register((entries) -> {
            entries.add(crop.seed);
        });
        ItemGroupEvents.modifyEntriesEvent(Croparia.CROP).register((entries) -> {
            entries.add(crop.fruit);
        });
    }

    public static void registerItems() {
    }
}
