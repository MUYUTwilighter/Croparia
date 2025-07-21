package cool.muyucloud.croparia;

import com.mojang.logging.LogUtils;
import cool.muyucloud.croparia.api.crop.command.CommonCommandRoot;
import cool.muyucloud.croparia.api.generator.BlockTagGenerator;
import cool.muyucloud.croparia.api.generator.ItemTagGenerator;
import cool.muyucloud.croparia.api.generator.pack.DataPackHandler;
import cool.muyucloud.croparia.api.generator.pack.ResourcePackHandler;
import cool.muyucloud.croparia.client.generator.BlockStateModelGenerator;
import cool.muyucloud.croparia.client.generator.ItemModelGenerator;
import cool.muyucloud.croparia.client.generator.LangGenerator;
import cool.muyucloud.croparia.config.Config;
import cool.muyucloud.croparia.config.ConfigFileHandler;
import cool.muyucloud.croparia.registry.*;
import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class CropariaIf {
    public static final String MOD_ID = "croparia";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Config CONFIG = ConfigFileHandler.load();

    public static void init() {
        CropariaIf.LOGGER.info("=== Croparia common setup ===");
        CropariaComponents.register();
        RecipeTypes.register();
        RecipeSerializers.register();
        Crops.register();
        Fluids.register();
        CropariaBlocks.register();
        BlockEntities.register();
        CropariaItems.register();
        Tabs.register();
        CommonCommandRoot.register();
        CropariaIf.LOGGER.debug("Adding data generators");
        DataPackHandler.INSTANCE.registerGenerator(ItemTagGenerator::init);
        DataPackHandler.INSTANCE.registerGenerator(BlockTagGenerator::init);
        ResourcePackHandler.INSTANCE.registerGenerator(ItemModelGenerator::init);
        ResourcePackHandler.INSTANCE.registerGenerator(BlockStateModelGenerator::init);
        ResourcePackHandler.INSTANCE.registerGenerator(LangGenerator::init);
        PlacedFeatures.register();
        CropariaIf.LOGGER.info("=== Croparia common setup done ===");
        LifecycleEvent.SERVER_STARTING.register(server -> {
            ConfigFileHandler.reload(CONFIG);
            if (CONFIG.getOverride()) {
                DataPackHandler.INSTANCE.clear();
            }
        });
        LifecycleEvent.SERVER_STARTED.register(server -> {
            if (CONFIG.getAutoReload()) {
                LOGGER.info("Croparia IF is performing a datapack reload to apply data generators");
                server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "reload");
            }
        });
        LifecycleEvent.SERVER_STOPPING.register(server -> ConfigFileHandler.save(CONFIG));
    }

    public static ResourceLocation of(String path) {
        ResourceLocation id = ResourceLocation.tryBuild(MOD_ID, path);
        if (id == null) throw new IllegalArgumentException("Invalid resource location: " + path);
        return id;
    }
}
