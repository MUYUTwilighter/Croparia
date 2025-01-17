package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@SuppressWarnings("UnstableApiUsage")
public class PlacedFeatures {
    protected static final Map<GenerationStep.Decoration, Map<ResourceKey<PlacedFeature>, Predicate<BiomeModifications.BiomeContext>>> PLACED_FEATURES = new HashMap<>();

    public static final Map.Entry<GenerationStep.Decoration, ResourceKey<PlacedFeature>> ELEMATILIUS_ORE = register(
        GenerationStep.Decoration.UNDERGROUND_ORES,
        context -> context.hasTag(TagKey.create(Registries.BIOME, ResourceLocation.tryParse("minecraft:is_overworld"))),
        "elematilius_ore"
    );

    public static Map.Entry<GenerationStep.Decoration, ResourceKey<PlacedFeature>> register(
        GenerationStep.Decoration decoration, Predicate<BiomeModifications.BiomeContext> context, String path
    ) {
        ResourceKey<PlacedFeature> feature = ResourceKey.create(Registries.PLACED_FEATURE, CropariaIf.of(path));
        PLACED_FEATURES.computeIfAbsent(decoration, k -> new HashMap<>()).put(feature, context);
        return Map.entry(decoration, feature);
    }

    public static void register() {
        CropariaIf.LOGGER.debug("Adding biome modifications");
        BiomeModifications.addProperties((context, mutable) -> {
            for (Map.Entry<GenerationStep.Decoration, Map<ResourceKey<PlacedFeature>, Predicate<BiomeModifications.BiomeContext>>> entry : PLACED_FEATURES.entrySet()) {
                GenerationStep.Decoration decoration = entry.getKey();
                Map<ResourceKey<PlacedFeature>, Predicate<BiomeModifications.BiomeContext>> features = entry.getValue();
                for (Map.Entry<ResourceKey<PlacedFeature>, Predicate<BiomeModifications.BiomeContext>> featureEntry : features.entrySet()) {
                    ResourceKey<PlacedFeature> feature = featureEntry.getKey();
                    Predicate<BiomeModifications.BiomeContext> predicate = featureEntry.getValue();
                    if (!predicate.test(context)) continue;
                    mutable.getGenerationProperties().addFeature(decoration, feature);
                }
            }
        });
    }
}
