package cool.muyucloud.croparia.client;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.client.command.ClientCommandRoot;
import cool.muyucloud.croparia.registry.CropariaBlocks;
import cool.muyucloud.croparia.registry.Crops;
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
            RenderTypeRegistry.register(RenderType.cutoutMipped(), crop.getCropBlock());
        });
        CropariaIf.LOGGER.debug("Registering cutout rendering");
        RenderTypeRegistry.register(RenderType.cutout(), CropariaBlocks.GREENHOUSE.get());
        RenderTypeRegistry.register(RenderType.cutout(), CropariaBlocks.ACTIVATED_SHRIEKER.get());
    }
}
