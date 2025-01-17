package cool.muyucloud.croparia.client;

import cool.muyucloud.croparia.registry.CropariaBlocks;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.Crops;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;

public class CropariaIfClient {
    public static void init() {
        Crops.CROPS.forEach(crop -> {
            ColorHandlerRegistry.registerBlockColors((blockState, blockAndTintGetter, blockPos, i) -> crop.getColor(), crop.getCropBlock());
            ColorHandlerRegistry.registerItemColors((itemStack, i) -> crop.getColor(), crop.getFruitItem());
            ColorHandlerRegistry.registerItemColors((itemStack, i) -> crop.getColor(), crop.getSeedItem());
            RenderTypeRegistry.register(RenderType.cutoutMipped(), crop.getCropBlock());
        });
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> i == 1 ? 0x5F2F71 : -1, CropariaItems.ELEMATILIUS_BUCKET.get());
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> i == 1 ? 0xB55719 : -1, CropariaItems.EARTH_BUCKET.get());
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> i == 1 ? 0x0b729E : -1, CropariaItems.WATER_BUCKET.get());
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> i == 1 ? 0xA80007 : -1, CropariaItems.FIRE_BUCKET.get());
        ColorHandlerRegistry.registerItemColors((itemStack, i) -> i == 1 ? 0x627882 : -1, CropariaItems.AIR_BUCKET.get());
        RenderTypeRegistry.register(RenderType.cutout(), CropariaBlocks.GREENHOUSE.get());
        RenderTypeRegistry.register(RenderType.cutout(), CropariaBlocks.ACTIVATED_SHRIEKER.get());
    }
}
