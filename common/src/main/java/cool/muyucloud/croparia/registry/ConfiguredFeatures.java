package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.List;

public class ConfiguredFeatures {
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ELEMATILIUS_ORE = FeatureUtils.register(
        CropariaIf.MOD_ID + ":elematilius_ore",
        Feature.ORE, new OreConfiguration(List.of(
            OreConfiguration.target(
                OreFeatures.STONE_ORE_REPLACEABLES, CropariaBlocks.ELEMATILIUS_ORE.get().defaultBlockState()
            ),
            OreConfiguration.target(
                OreFeatures.DEEPSLATE_ORE_REPLACEABLES, CropariaBlocks.DEEPSLATE_ELEMATILIUS_ORE.get().defaultBlockState()
            )), 6)
    );
}
