package cool.muyucloud.croparia;

import com.mojang.logging.LogUtils;
import cool.muyucloud.croparia.registry.Crops;
import cool.muyucloud.croparia.client.generator.BlockStateModelGenerator;
import cool.muyucloud.croparia.client.generator.ItemModelGenerator;
import cool.muyucloud.croparia.client.generator.LangGenerator;
import cool.muyucloud.croparia.api.crop.command.CommonCommandRoot;
import cool.muyucloud.croparia.config.Config;
import cool.muyucloud.croparia.config.ConfigFileHandler;
import cool.muyucloud.croparia.api.generator.BlockTagGenerator;
import cool.muyucloud.croparia.api.generator.ItemTagGenerator;
import cool.muyucloud.croparia.registry.*;
import cool.muyucloud.croparia.api.generator.pack.DataPackHandler;
import cool.muyucloud.croparia.api.generator.pack.ResourcePackHandler;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class CropariaIf {
    public static final String MOD_ID = "croparia";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Config CONFIG = ConfigFileHandler.load();

    public static void init() {
        CropariaIf.LOGGER.info("=== Croparia common setup ===");
        RecipeTypes.register();
        RecipeSerializers.register();
        Crops.register();
        Fluids.register();
        CropariaBlocks.register();
        BlockEntities.register();
        CropariaItems.register();
        Tabs.register();
        CropariaIf.LOGGER.debug("Adding data generators");
        DataPackHandler.INSTANCE.registerGenerator(ItemTagGenerator::init);
        DataPackHandler.INSTANCE.registerGenerator(BlockTagGenerator::init);
        ResourcePackHandler.INSTANCE.registerGenerator(ItemModelGenerator::init);
        ResourcePackHandler.INSTANCE.registerGenerator(BlockStateModelGenerator::init);
        ResourcePackHandler.INSTANCE.registerGenerator(LangGenerator::init);
        PlacedFeatures.register();
        CommonCommandRoot.register();
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
