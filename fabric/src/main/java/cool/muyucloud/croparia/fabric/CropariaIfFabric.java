package cool.muyucloud.croparia.fabric;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.registry.PlacedFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.GenerationStep;

public class CropariaIfFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CompatCrops.init();
        CropariaIf.init();
        BiomeModifications.addFeature(context -> context.canGenerateIn(LevelStem.OVERWORLD), GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.ELEMATILIUS_ORE.unwrapKey().orElseThrow());
    }
}
