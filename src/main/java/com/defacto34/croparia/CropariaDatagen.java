//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia;

import com.defacto34.croparia.datagen.EnUsGenerator;
import com.defacto34.croparia.datagen.LootTableGenerator;
import com.defacto34.croparia.datagen.ModelGenerator;
import com.defacto34.croparia.datagen.RecipeGenerator;
import com.defacto34.croparia.datagen.WorldGenerator;
import com.defacto34.croparia.worldgen.features.ConfiguredFeatures;
import com.defacto34.croparia.worldgen.features.PlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class CropariaDatagen implements DataGeneratorEntrypoint {
    public CropariaDatagen() {
    }

    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModelGenerator::new);
        pack.addProvider(RecipeGenerator::new);
        pack.addProvider(LootTableGenerator::new);
        pack.addProvider(EnUsGenerator::new);
        pack.addProvider(WorldGenerator::new);
    }

    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, PlacedFeatures::bootstrap);
    }
}
