package cool.muyucloud.croparia;

import com.mojang.logging.LogUtils;
import cool.muyucloud.croparia.api.core.command.CommonCommandRoot;
import cool.muyucloud.croparia.config.Config;
import cool.muyucloud.croparia.config.ConfigFileHandler;
import cool.muyucloud.croparia.registry.*;
import cool.muyucloud.croparia.util.supplier.OnLoadSupplier;
import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class CropariaIf {
    public static final String MOD_ID = "croparia";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Config CONFIG = ConfigFileHandler.load();
    private static Boolean SERVER_STARTED = false;

    public static void init() {
        CropariaIf.LOGGER.info("=== Croparia common setup ===");
        LOGGER.info("Customize registration");
        DgRegistries.register();
        DataGenerators.register();
        PackHandlers.register();
        Crops.register();
        Elements.register();
        LOGGER.info("Vanilla registration");
        CropariaComponents.register();
        CropariaBlocks.register();
        BlockEntities.register();
        CropariaItems.register();
        SlotDisplays.register();
        Recipes.register();
        Tabs.register();
        CommonCommandRoot.register();
        PlacedFeatures.register();
        LOGGER.info("Event registration");
        LifecycleEvent.SERVER_STARTING.register(server -> ConfigFileHandler.reload(CONFIG));
        LifecycleEvent.SERVER_STARTED.register(server -> {
            SERVER_STARTED = true;
            OnLoadSupplier.LAST_DATA_LOAD = System.currentTimeMillis();
            if (CONFIG.getAutoReload() >= 0) {
                LOGGER.info("Croparia IF is performing a datapack reload to apply data generators");
                server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "schedule function croparia:auto_reload %s".formatted(CONFIG.getAutoReload()));
            }
        });
        LifecycleEvent.SERVER_STOPPING.register(server -> {
            SERVER_STARTED = false;
            ConfigFileHandler.save(CONFIG);
        });
        CropariaIf.LOGGER.info("=== Croparia common setup done ===");
    }

    public static ResourceLocation of(String path) {
        ResourceLocation id = ResourceLocation.tryBuild(MOD_ID, path);
        if (id == null) {
            throw new IllegalArgumentException("Invalid path: " + path);
        } else {
            return id;
        }
    }

    public static boolean isServerStarted() {
        return SERVER_STARTED;
    }
}
