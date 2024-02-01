//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.worldgen.features;

import com.defacto34.croparia.Croparia;
import com.defacto34.croparia.init.BlockInit;
import java.util.List;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ConfiguredFeatures {
    public static RegistryKey<ConfiguredFeature<?, ?>> ELEMATILIUS_ORE_KEY = registerKey("elematilius_ore");

    public ConfiguredFeatures() {
    }

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RegistryEntryLookup<PlacedFeature> placedFeatureRegisrtyEntryLookup = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<OreFeatureConfig.Target> overworldElematiliusOres = List.of(OreFeatureConfig.createTarget(stoneReplaceables, BlockInit.ELEMATILIUS_ORE.getDefaultState()), OreFeatureConfig.createTarget(deepslateReplaceables, BlockInit.DEEPSLATE_ELEMATILIUS_ORE.getDefaultState()));
        register(context, ELEMATILIUS_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldElematiliusOres, 12));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(Croparia.MOD_ID, name));
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature(feature, configuration));
    }
}
