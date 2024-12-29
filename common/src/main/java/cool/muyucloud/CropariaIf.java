package cool.muyucloud;

import com.mojang.logging.LogUtils;
import cool.muyucloud.data.config.Config;
import cool.muyucloud.data.config.ConfigFileHandler;
import cool.muyucloud.registry.*;
import cool.muyucloud.util.CropariaCauldronInteraction;
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
    }

    public static ResourceLocation of(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
