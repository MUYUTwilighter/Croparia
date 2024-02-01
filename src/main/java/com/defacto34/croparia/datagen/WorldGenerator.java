//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.datagen;

import com.defacto34.croparia.Croparia;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

public class WorldGenerator extends FabricDynamicRegistryProvider {
    public WorldGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFeature) {
        super(output, registriesFeature);
    }

    protected void configure(RegistryWrapper.WrapperLookup registries, FabricDynamicRegistryProvider.Entries entries) {
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.CONFIGURED_FEATURE));
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE));
    }

    public String getName() {
        return Croparia.MOD_ID;
    }
}
