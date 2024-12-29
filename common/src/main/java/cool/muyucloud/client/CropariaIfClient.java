package cool.muyucloud.client;

import cool.muyucloud.client.generator.BlockStateModelGenerator;
import cool.muyucloud.client.generator.ItemModelGenerator;
import cool.muyucloud.client.generator.LangGenerator;
import cool.muyucloud.registry.CropariaBlocks;
import cool.muyucloud.registry.Crops;
import cool.muyucloud.util.pack.ResourcePackHandler;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CropariaIfClient {
    public static void init() {
        ItemModelGenerator.init();
        BlockStateModelGenerator.init();
        LangGenerator.init();
        ResourcePackHandler.INSTANCE.flushCache();
        Crops.CROPS.forEach(crop -> {
            ColorHandlerRegistry.registerBlockColors((blockState, blockAndTintGetter, blockPos, i) -> crop.getColor(), crop.getCropBlock());
            ColorHandlerRegistry.registerItemColors((itemStack, i) -> crop.getColor(), crop.getFruitItem());
            ColorHandlerRegistry.registerItemColors((itemStack, i) -> crop.getColor(), crop.getSeedItem());
            RenderTypeRegistry.register(RenderType.cutoutMipped(), crop.getCropBlock());
        });
        RenderTypeRegistry.register(RenderType.cutout(), CropariaBlocks.GREENHOUSE.get());
    }
}
