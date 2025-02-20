package cool.muyucloud.croparia.api.repo.item.forge;

import cool.muyucloud.croparia.api.repo.forge.PlatformItemProxyImpl;
import cool.muyucloud.croparia.api.repo.item.PlatformItemProxy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Optional;

public class ItemProxyProviderImpl {
    static Optional<PlatformItemProxy> find(Level world, BlockPos pos, Direction direction) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be == null) return Optional.empty();
        return be.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction).map(PlatformItemProxyImpl::of);
    }
}
