package cool.muyucloud.mixin;

import cool.muyucloud.util.pack.ResourcePackHandler;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.LinkedList;
import java.util.List;

/**
 * Mixin class used to insert our resourcepack provided by {@link ResourcePackHandler},
 * so that the datapack can be loaded.
 * */
@Mixin(ReloadableResourceManager.class)
public abstract class ReloadableResourceManagerImplMixin implements ResourceManager, AutoCloseable {
    @ModifyVariable(method = "createReload", at = @At("HEAD"), argsOnly = true)
    public List<PackResources> onReload(List<PackResources> packs) {
        List<PackResources> newPacks = new LinkedList<>();
        newPacks.add(ResourcePackHandler.INSTANCE.getResourcePack());
        newPacks.addAll(packs);
        return newPacks;
    }
}
