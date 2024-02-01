//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.init;

import com.defacto34.croparia.Croparia;
import com.defacto34.croparia.core.block.Greenhouse;
import com.defacto34.croparia.core.blockEntity.GreenhouseBE;
import com.mojang.datafixers.types.Type;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BlockEntityInit {
    public static BlockEntityType<GreenhouseBE> GREENHOUSE_BE;

    public BlockEntityInit() {
    }

    public static void onInitialize() {
        GREENHOUSE_BE = (BlockEntityType)Registry.register(Registries.BLOCK_ENTITY_TYPE, Croparia.MOD_ID + "greenhouse_be", FabricBlockEntityTypeBuilder.create(GreenhouseBE::new, (Block[])Greenhouse.blockGreenhouse.toArray((x$0) -> {
            return new Block[x$0];
        })).build((Type)null));
    }
}
