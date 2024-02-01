//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.worldgen.features;

import com.defacto34.croparia.Croparia;
import java.util.List;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

public class PlacedFeatures {
    public static final RegistryKey<PlacedFeature> ELEMATILIUS_ORE_PLACED = registryKey("elematilius_ore_placed");

    public PlacedFeatures() {
    }

    public static void bootstrap(Registerable<PlacedFeature> context) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        register(context, ELEMATILIUS_ORE_PLACED, configuredFeatureRegistryEntryLookup.getOrThrow(ConfiguredFeatures.ELEMATILIUS_ORE_KEY), modifiersWithCount(16, HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(32))));
    }

    public static RegistryKey<PlacedFeature> registryKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(Croparia.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return List.of(CountPlacementModifier.of(count), heightModifier);
    }
}
