//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia;

import com.defacto34.croparia.core.block.Greenhouse;
import com.defacto34.croparia.init.LangEnUsInit;
import com.defacto34.croparia.init.models.BlockStateModelInit;
import com.defacto34.croparia.init.CropInit;
import com.defacto34.croparia.init.models.ItemModelInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;

public class CropariaClient implements ClientModInitializer {
    public CropariaClient() {
    }

    public void onInitializeClient() {
        CropInit.cropList.forEach((crop) -> {
            ItemModelInit.registerCrop(crop);
            BlockStateModelInit.registerCrop(crop);
            ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> crop.color, crop.cropBlock);
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> crop.color, crop.seed);
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 1 ? crop.color : -1, crop.fruit);
            BlockRenderLayerMap.INSTANCE.putBlock(crop.cropBlock, RenderLayer.getCutoutMipped());
        });
        Greenhouse.blockGreenhouse.forEach((block) -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent()));
        LangEnUsInit.registerCrops();
    }
}
