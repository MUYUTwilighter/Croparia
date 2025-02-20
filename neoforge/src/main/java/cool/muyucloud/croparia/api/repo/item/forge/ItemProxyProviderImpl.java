package cool.muyucloud.croparia.api.repo.item.forge;

import cool.muyucloud.croparia.api.repo.forge.PlatformItemProxyImpl;
import cool.muyucloud.croparia.api.repo.item.PlatformItemProxy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.Optional;

public class ItemProxyProviderImpl {
    static Optional<PlatformItemProxy> find(Level world, BlockPos pos, Direction direction) {
        IItemHandler handler = world.getCapability(Capabilities.ItemHandler.BLOCK, pos, direction);
        if (handler == null ) {
            return Optional.empty();
        } else {
            return Optional.of(PlatformItemProxyImpl.of(handler));
        }
    }
}
