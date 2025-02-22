package cool.muyucloud.croparia.neoforge.access;

import cool.muyucloud.croparia.api.repo.ProxyProvider;
import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Unique;

public interface BlockCapabilityAccess {
    @Unique
    void croparia_if$registerItem(ProxyProvider<ItemSpec> provider, Block... blocks);

    @Unique
    void croparia_if$registerFluid(ProxyProvider<FluidSpec> provider, Block... blocks);
}