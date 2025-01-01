package cool.muyucloud.croparia.client;

import cool.muyucloud.croparia.registry.CropariaBlocks;
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
        RenderTypeRegistry.register(RenderType.cutout(), CropariaBlocks.GREENHOUSE.get());
    }
}
