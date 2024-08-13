//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.init;

import com.defacto34.croparia.Croparia;
import com.defacto34.croparia.core.block.Greenhouse;
import com.defacto34.croparia.core.blockEntity.GreenhouseBE;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BlockEntityInit {
    public static BlockEntityType<GreenhouseBE> GREENHOUSE_BE;

    public BlockEntityInit() {
    }

    public static void onInitialize() {
        GREENHOUSE_BE = Registry.register(Registries.BLOCK_ENTITY_TYPE, Croparia.MOD_ID + "greenhouse_be",
            BlockEntityType.Builder.create(GreenhouseBE::new, Greenhouse.blockGreenhouse.toArray(Block[]::new)).build(null));
    }
}
