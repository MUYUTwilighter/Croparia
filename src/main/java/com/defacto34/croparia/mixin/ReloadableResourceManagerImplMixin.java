package com.defacto34.croparia.mixin;

import com.defacto34.croparia.ResourcePackHandler;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.LinkedList;
import java.util.List;

@Mixin(ReloadableResourceManagerImpl.class)
public class ReloadableResourceManagerImplMixin {
    @ModifyVariable(method = "reload", at = @At("HEAD"), argsOnly = true)
    public List<ResourcePack> onReload(List<ResourcePack> packs) {
        List<ResourcePack> newPacks = new LinkedList<>();
        newPacks.add(ResourcePackHandler.INSTANCE.getResourcePack());
        newPacks.addAll(packs);
        return newPacks;
    }
}
