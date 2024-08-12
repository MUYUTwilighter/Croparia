package com.defacto34.croparia.mixin;

import com.defacto34.croparia.handler.pack.DataPackHandler;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ResourcePackManager.class)
public abstract class ResourcePackManagerMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static ResourcePackProvider[] insertProviders(ResourcePackProvider... providers) {
        ResourcePackProvider[] newProviders = new ResourcePackProvider[providers.length + 1];
        ResourcePackProvider provider = DataPackHandler.INSTANCE.getDatapack();
        newProviders[0] = provider;
        System.arraycopy(providers, 0, newProviders, 1, providers.length);
        return newProviders;
    }
}
