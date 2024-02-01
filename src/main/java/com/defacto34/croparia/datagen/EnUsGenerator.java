//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.datagen;

import com.defacto34.croparia.init.BlockInit;
import com.defacto34.croparia.init.CropInit;
import com.defacto34.croparia.init.ItemInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.item.Item;

public class EnUsGenerator extends FabricLanguageProvider {
    public EnUsGenerator(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    public void generateTranslations(FabricLanguageProvider.TranslationBuilder translationBuilder) {
        translationBuilder.add("itemGroup.croparia.main", "Croparia");
        translationBuilder.add("itemGroup.croparia.crop", "Croparia : Crops");
        translationBuilder.add("container.greenhouse", "Greenhouse");
        CropInit.cropList.forEach((crop) -> {
            String name = capitalizeWords(crop.cropName);
            translationBuilder.add(crop.seed, name + " Seeds");
            translationBuilder.add(crop.fruit, name + " Fruit");
        });
        translationBuilder.add(ItemInit.CROPARIA, "Croparia");

        for(int i = 1; i < ItemInit.croparias.size(); ++i) {
            translationBuilder.add((Item)ItemInit.croparias.get(i), "Croparia T" + (i + 1));
        }

        translationBuilder.add(ItemInit.ELEMATILIUS, "Elematilius");
        translationBuilder.add(ItemInit.POTION_ELEMATILIUS, "Elematilius Potion");
        translationBuilder.add(ItemInit.MIDAS_HAND, "Midas Hand");
        translationBuilder.add(ItemInit.HORN_PLENTY, "Horn of Plenty");
        translationBuilder.add(ItemInit.INFINITE_APPLE, "Infinite Apple");
        translationBuilder.add(ItemInit.MAGIC_ROPE, "Magic Rope");
        translationBuilder.add(BlockInit.INFUSOR, "Infusor");
        translationBuilder.add(BlockInit.ELEMENTAL_STONE, "Elemental Stone");
        translationBuilder.add(BlockInit.RITUAL_STAND, "Ritual Stand");
        translationBuilder.add(BlockInit.ELEMATILIUS_ORE, "Elematilius Ore");
        translationBuilder.add(BlockInit.DEEPSLATE_ELEMATILIUS_ORE, "Deepslate Elematilius Ore");
        translationBuilder.add(BlockInit.GREENHOUSE, "Greenhouse");
    }

    public static String capitalizeWords(String input) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;
        char[] var3 = input.replace('_', ' ').toCharArray();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            char ch = var3[var5];
            if (Character.isWhitespace(ch)) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                ch = Character.toTitleCase(ch);
                capitalizeNext = false;
            }

            result.append(ch);
        }

        return result.toString();
    }
}
