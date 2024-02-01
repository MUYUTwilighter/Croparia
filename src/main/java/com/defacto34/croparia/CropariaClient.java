//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia;

import com.defacto34.croparia.core.block.Greenhouse;
import com.defacto34.croparia.init.CropInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.loader.impl.game.minecraft.launchwrapper.FabricClientTweaker;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemConvertible;

public class CropariaClient implements ClientModInitializer {
    public CropariaClient() {
    }

    public void onInitializeClient() {
        CropInit.cropList.forEach((crop) -> {
            ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> crop.color, crop.cropBlock);
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> crop.color, crop.seed);
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 1 ? crop.color : -1, crop.fruit);
            BlockRenderLayerMap.INSTANCE.putBlock(crop.cropBlock, RenderLayer.getCutoutMipped());
        });
        Greenhouse.blockGreenhouse.forEach((block) -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent()));
    }
}
