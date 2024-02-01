//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.datagen;

import com.defacto34.croparia.Croparia;
import com.defacto34.croparia.api.crop.CropariaCrops;
import com.defacto34.croparia.init.CropInit;
import com.defacto34.croparia.init.ItemInit;
import java.util.Optional;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.VariantSettings;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModelGenerator extends FabricModelProvider {
    public ModelGenerator(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        CropInit.cropList.forEach((crop) -> {
            switch (crop.type) {
                case BASIC:
                    blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(crop.cropBlock).coordinate(BlockStateVariantMap.create(CropariaCrops.AGE).register(0, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage0"))).register(1, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage1"))).register(2, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage2"))).register(3, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage3"))).register(4, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage4"))).register(5, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage5"))).register(6, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage6"))).register(7, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage7")))));
                    break;
                case MONSTER:
                    blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(crop.cropBlock).coordinate(BlockStateVariantMap.create(CropariaCrops.AGE).register(0, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage0"))).register(1, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage1"))).register(2, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage2"))).register(3, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage3"))).register(4, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage4"))).register(5, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/monster_stage5"))).register(6, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/monster_stage6"))).register(7, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/monster_stage7")))));
                    break;
                case ANIMAL:
                    blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(crop.cropBlock).coordinate(BlockStateVariantMap.create(CropariaCrops.AGE).register(0, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage0"))).register(1, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage1"))).register(2, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage2"))).register(3, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage3"))).register(4, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage4"))).register(5, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/animal_stage5"))).register(6, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/animal_stage6"))).register(7, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/animal_stage7")))));
                    break;
                case NATURE:
                    blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(crop.cropBlock).coordinate(BlockStateVariantMap.create(CropariaCrops.AGE).register(0, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage0"))).register(1, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage1"))).register(2, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage2"))).register(3, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage3"))).register(4, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage4"))).register(5, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/nature_stage5"))).register(6, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/nature_stage6"))).register(7, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/nature_stage7")))));
                    break;
                case FOOD:
                    blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(crop.cropBlock).coordinate(BlockStateVariantMap.create(CropariaCrops.AGE).register(0, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage0"))).register(1, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage1"))).register(2, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage2"))).register(3, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage3"))).register(4, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage4"))).register(5, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/food_stage5"))).register(6, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/food_stage6"))).register(7, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/food_stage7")))));
                    break;
                case CUSTOM:
                    blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(crop.cropBlock).coordinate(BlockStateVariantMap.create(CropariaCrops.AGE).register(0, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage0"))).register(1, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage1"))).register(2, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage2"))).register(3, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage3"))).register(4, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/crop_stage4"))).register(5, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/" + crop.cropName + "_stage5"))).register(6, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/" + crop.cropName + "_stage6"))).register(7, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Croparia.MOD_ID, "block/" + crop.cropName + "_stage7")))));
            }

        });
    }

    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        TextureKey layer1 = TextureKey.of("layer1");
        CropInit.cropList.forEach((crop) -> {
            Model fruit;
            switch (crop.type) {
                case BASIC:
                    Models.GENERATED.upload(ModelIds.getItemModelId(crop.seed), TextureMap.layer0(new Identifier(Croparia.MOD_ID, "item/seed_crop")), itemModelGenerator.writer);
                    fruit = new Model(Optional.of(new Identifier("minecraft", "item/generated")), Optional.empty(), new TextureKey[]{TextureKey.LAYER0, layer1});
                    fruit.upload(ModelIds.getItemModelId(crop.fruit), (new TextureMap()).put(TextureKey.LAYER0, new Identifier(Croparia.MOD_ID, "item/fruit_crop")).put(layer1, new Identifier(Croparia.MOD_ID, "item/fruit_crop_overlay")), itemModelGenerator.writer);
                    break;
                case MONSTER:
                    Models.GENERATED.upload(ModelIds.getItemModelId(crop.seed), TextureMap.layer0(new Identifier(Croparia.MOD_ID, "item/seed_crop")), itemModelGenerator.writer);
                    fruit = new Model(Optional.of(new Identifier("minecraft", "item/generated")), Optional.empty(), new TextureKey[]{TextureKey.LAYER0, layer1});
                    fruit.upload(ModelIds.getItemModelId(crop.fruit), (new TextureMap()).put(TextureKey.LAYER0, new Identifier(Croparia.MOD_ID, "item/fruit_monster")).put(layer1, new Identifier(Croparia.MOD_ID, "item/fruit_monster_overlay")), itemModelGenerator.writer);
                    break;
                case ANIMAL:
                    Models.GENERATED.upload(ModelIds.getItemModelId(crop.seed), TextureMap.layer0(new Identifier(Croparia.MOD_ID, "item/seed_crop")), itemModelGenerator.writer);
                    fruit = new Model(Optional.of(new Identifier("minecraft", "item/generated")), Optional.empty(), new TextureKey[]{TextureKey.LAYER0, layer1});
                    fruit.upload(ModelIds.getItemModelId(crop.fruit), (new TextureMap()).put(TextureKey.LAYER0, new Identifier(Croparia.MOD_ID, "item/fruit_animal")).put(layer1, new Identifier(Croparia.MOD_ID, "item/fruit_animal_overlay")), itemModelGenerator.writer);
                    break;
                case NATURE:
                    Models.GENERATED.upload(ModelIds.getItemModelId(crop.seed), TextureMap.layer0(new Identifier(Croparia.MOD_ID, "item/seed_crop")), itemModelGenerator.writer);
                    fruit = new Model(Optional.of(new Identifier("minecraft", "item/generated")), Optional.empty(), new TextureKey[]{TextureKey.LAYER0, layer1});
                    fruit.upload(ModelIds.getItemModelId(crop.fruit), (new TextureMap()).put(TextureKey.LAYER0, new Identifier(Croparia.MOD_ID, "item/fruit_nature")).put(layer1, new Identifier(Croparia.MOD_ID, "item/fruit_nature_overlay")), itemModelGenerator.writer);
                    break;
                case FOOD:
                    Models.GENERATED.upload(ModelIds.getItemModelId(crop.seed), TextureMap.layer0(new Identifier(Croparia.MOD_ID, "item/seed_crop")), itemModelGenerator.writer);
                    fruit = new Model(Optional.of(new Identifier("minecraft", "item/generated")), Optional.empty(), new TextureKey[]{TextureKey.LAYER0, layer1});
                    fruit.upload(ModelIds.getItemModelId(crop.fruit), (new TextureMap()).put(TextureKey.LAYER0, new Identifier(Croparia.MOD_ID, "item/fruit_food")).put(layer1, new Identifier(Croparia.MOD_ID, "item/fruit_food_overlay")), itemModelGenerator.writer);
                    break;
                case CUSTOM:
                    Models.GENERATED.upload(ModelIds.getItemModelId(crop.seed), TextureMap.layer0(new Identifier(Croparia.MOD_ID, "item/seed_crop_" + crop.cropName)), itemModelGenerator.writer);
                    Models.GENERATED.upload(ModelIds.getItemModelId(crop.fruit), TextureMap.layer0(new Identifier(Croparia.MOD_ID, "item/fruit_" + crop.cropName)), itemModelGenerator.writer);
            }

        });

        for(int i = 0; i < ItemInit.croparias.size(); ++i) {
            Models.GENERATED.upload(ModelIds.getItemModelId((Item)ItemInit.croparias.get(i)), TextureMap.layer0(new Identifier(Croparia.MOD_ID, "item/croparia" + (i + 1))), itemModelGenerator.writer);
        }

    }
}
