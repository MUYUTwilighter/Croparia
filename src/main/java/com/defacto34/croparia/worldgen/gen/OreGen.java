//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.worldgen.gen;

import com.defacto34.croparia.worldgen.features.PlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep.Feature;

public class OreGen {
    public OreGen() {
    }

    public static void generateOres() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), Feature.UNDERGROUND_ORES, PlacedFeatures.ELEMATILIUS_ORE_PLACED);
    }
}
