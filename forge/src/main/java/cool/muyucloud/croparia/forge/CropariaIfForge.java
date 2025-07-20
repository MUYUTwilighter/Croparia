package cool.muyucloud.croparia.forge;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.repo.forge.ProxyProviderImpl;
import cool.muyucloud.croparia.registry.PlacedFeatures;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CropariaIf.MOD_ID)
@Mod.EventBusSubscriber(modid = CropariaIf.MOD_ID)
public class CropariaIfForge {
    public CropariaIfForge() {
        EventBuses.registerModEventBus(CropariaIf.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        CompatCrops.init();
        CropariaIf.init();
    }

    @SubscribeEvent
    public static void biomeModify(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.BiomeCategory.THEEND || event.getCategory() == Biome.BiomeCategory.NETHER) {
            return;
        }
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.ELEMATILIUS_ORE);
    }

    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {
        ProxyProviderImpl.freeze();
    }
}
