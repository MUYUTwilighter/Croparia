package cool.muyucloud;

import com.mojang.logging.LogUtils;
import cool.muyucloud.client.generator.BlockStateModelGenerator;
import cool.muyucloud.client.generator.ItemModelGenerator;
import cool.muyucloud.client.generator.LangGenerator;
import cool.muyucloud.data.config.Config;
import cool.muyucloud.data.config.ConfigFileHandler;
import cool.muyucloud.generator.*;
import cool.muyucloud.registry.*;
import cool.muyucloud.util.CropariaCauldronInteraction;
import cool.muyucloud.util.pack.DataPackHandler;
import cool.muyucloud.util.pack.ResourcePackHandler;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class CropariaIf {
    public static final String MOD_ID = "croparia";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Config CONFIG = ConfigFileHandler.load();

    public static void init() {
        RecipeTypes.register();
        RecipeSerializers.register();
        Crops.register();
        CropariaBlocks.register();
        BlockEntities.register();
        CropariaItems.register();
        Tabs.register();
        CropariaCauldronInteraction.bootStrap();
        DataPackHandler.INSTANCE.registerGenerator(RecipeGenerator::init);
        DataPackHandler.INSTANCE.registerGenerator(LootTableGenerator::init);
        DataPackHandler.INSTANCE.registerGenerator(ItemTagGenerator::init);
        DataPackHandler.INSTANCE.registerGenerator(BlockTagGenerator::init);
        ResourcePackHandler.INSTANCE.registerGenerator(ItemModelGenerator::init);
        ResourcePackHandler.INSTANCE.registerGenerator(BlockStateModelGenerator::init);
        ResourcePackHandler.INSTANCE.registerGenerator(LangGenerator::init);
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
