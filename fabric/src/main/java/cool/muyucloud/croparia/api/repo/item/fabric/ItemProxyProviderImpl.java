package cool.muyucloud.croparia.api.repo.item.fabric;

import cool.muyucloud.croparia.api.repo.fabric.PlatformItemProxyImpl;
import cool.muyucloud.croparia.api.repo.item.PlatformItemProxy;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ItemProxyProviderImpl {
    static Optional<PlatformItemProxy> find(Level world, BlockPos pos, Direction direction) {
        Storage<ItemVariant> storage = ItemStorage.SIDED.find(world, pos, direction);
        if (storage == null) return Optional.empty();
        return Optional.of(PlatformItemProxyImpl.of(storage));
    }
}
