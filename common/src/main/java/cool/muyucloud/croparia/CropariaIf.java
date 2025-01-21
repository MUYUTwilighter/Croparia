package cool.muyucloud.croparia;

import com.mojang.logging.LogUtils;
import cool.muyucloud.croparia.client.generator.BlockStateModelGenerator;
import cool.muyucloud.croparia.client.generator.ItemModelGenerator;
import cool.muyucloud.croparia.client.generator.LangGenerator;
import cool.muyucloud.croparia.command.ServerCommandRoot;
import cool.muyucloud.croparia.data.config.Config;
import cool.muyucloud.croparia.data.config.ConfigFileHandler;
import cool.muyucloud.croparia.generator.BlockTagGenerator;
import cool.muyucloud.croparia.generator.ItemTagGenerator;
import cool.muyucloud.croparia.generator.LootTableGenerator;
import cool.muyucloud.croparia.generator.RecipeGenerator;
import cool.muyucloud.croparia.registry.*;
import cool.muyucloud.croparia.util.pack.DataPackHandler;
import cool.muyucloud.croparia.util.pack.ResourcePackHandler;
import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class CropariaIf {
    public static final String MOD_ID = "croparia";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Config CONFIG = ConfigFileHandler.load();

    public static void init() {
        CropariaIf.LOGGER.info("=== Croparia common setup ===");
        DataPackHandler.INSTANCE.onInitial();
        RecipeTypes.register();
        RecipeSerializers.register();
        Crops.register();
        Fluids.register();
        CropariaBlocks.register();
        BlockEntities.register();
        CropariaItems.register();
        Tabs.register();
        CropariaIf.LOGGER.debug("Adding data generators");
        DataPackHandler.INSTANCE.registerGenerator(RecipeGenerator::init);
        DataPackHandler.INSTANCE.registerGenerator(LootTableGenerator::init);
        DataPackHandler.INSTANCE.registerGenerator(ItemTagGenerator::init);
        DataPackHandler.INSTANCE.registerGenerator(BlockTagGenerator::init);
        ResourcePackHandler.INSTANCE.registerGenerator(ItemModelGenerator::init);
        ResourcePackHandler.INSTANCE.registerGenerator(BlockStateModelGenerator::init);
        ResourcePackHandler.INSTANCE.registerGenerator(LangGenerator::init);
        LifecycleEvent.SETUP.register(PlacedFeatures::init);
        ServerCommandRoot.register();
        CropariaIf.LOGGER.info("=== Croparia common setup done ===");
    }

    public static void onServerStarting() {
        ConfigFileHandler.reload(CONFIG);
    }

    public static void onServerStopping() {
        ConfigFileHandler.save(CONFIG);
    }

    public static ResourceLocation of(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
