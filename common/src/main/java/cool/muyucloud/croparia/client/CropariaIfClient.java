package cool.muyucloud.croparia.client;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crops;
import cool.muyucloud.croparia.client.command.ClientCommandRoot;
import cool.muyucloud.croparia.registry.CropariaBlocks;
import cool.muyucloud.croparia.registry.CropariaItems;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;

public class CropariaIfClient {
    public static void init() {
        CropariaIf.LOGGER.info("Initializing client setup");
        ClientCommandRoot.register();
        CropariaIf.LOGGER.debug("Registering crop color");
        Crops.forEachCrop(crop -> {
            ColorHandlerRegistry.registerBlockColors((blockState, blockAndTintGetter, blockPos, i) -> crop.getColor(), crop.getCropBlock());
            ColorHandlerRegistry.registerItemColors((itemStack, i) -> i == 1 ? crop.getColor() | 0xFF000000 : -1, crop.getFruitItem());
            ColorHandlerRegistry.registerItemColors((itemStack, i) -> crop.getColor() | 0xFF000000, crop.getSeedItem());
            RenderTypeRegistry.register(RenderType.cutoutMipped(), crop.getCropBlock());
        });
        CropariaIf.LOGGER.debug("Registering fluid bucket color");
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> i == 1 ? 0xFF5F2F71 : -1, CropariaItems.ELEMATILIUS_BUCKET.get());
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> i == 1 ? 0xFFB55719 : -1, CropariaItems.EARTH_BUCKET.get());
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> i == 1 ? 0xFF0b729E : -1, CropariaItems.WATER_BUCKET.get());
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> i == 1 ? 0xFFA80007 : -1, CropariaItems.FIRE_BUCKET.get());
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> i == 1 ? 0xFF627882 : -1, CropariaItems.AIR_BUCKET.get());
        CropariaIf.LOGGER.debug("Registering cutout rendering");
        RenderTypeRegistry.register(RenderType.cutout(), CropariaBlocks.GREENHOUSE.get());
        RenderTypeRegistry.register(RenderType.cutout(), CropariaBlocks.ACTIVATED_SHRIEKER.get());
    }
}
