package cool.muyucloud.croparia.api.repo.forge;

import cool.muyucloud.croparia.api.repo.ProxyProvider;
import cool.muyucloud.croparia.api.repo.RepoProxy;
import cool.muyucloud.croparia.api.repo.platform.PlatformFluidProxy;
import cool.muyucloud.croparia.api.repo.platform.PlatformItemProxy;
import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.*;

public class ProxyProviderImpl {
    private static Map<Block, List<ProxyProvider<ItemSpec>>> ITEMS = new HashMap<>();
    private static Map<Block, List<ProxyProvider<FluidSpec>>> FLUIDS = new HashMap<>();

    public static Optional<PlatformItemProxy> findItem(Level world, BlockPos pos, Direction direction) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be == null) return Optional.empty();
        return be.getCapability(ForgeCapabilities.ITEM_HANDLER, direction).map(PlatformItemProxyImpl::of);
    }

    public static Optional<PlatformFluidProxy> findFluid(Level world, BlockPos pos, Direction direction) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be == null) return Optional.empty();
        return be.getCapability(ForgeCapabilities.FLUID_HANDLER, direction).map(PlatformFluidProxyImpl::of);
    }

    public static void registerItem(ProxyProvider<ItemSpec> provider, Block... blocks) {
        for (Block block : blocks) {
            ITEMS.computeIfAbsent(block, k -> new ArrayList<>()).add(provider);
        }
    }

    public static void registerFluid(ProxyProvider<FluidSpec> provider, Block... blocks) {
        for (Block block : blocks) {
            FLUIDS.computeIfAbsent(block, k -> new ArrayList<>()).add(provider);
        }
    }

    public static void freeze() {
        Map<Block, List<ProxyProvider<ItemSpec>>> items = new HashMap<>();
        Map<Block, List<ProxyProvider<FluidSpec>>> fluids = new HashMap<>();
        ITEMS.forEach((block, providers) -> items.put(block, Collections.unmodifiableList(providers)));
        FLUIDS.forEach((block, providers) -> fluids.put(block, Collections.unmodifiableList(providers)));
        ITEMS = Collections.unmodifiableMap(items);
        FLUIDS = Collections.unmodifiableMap(fluids);
    }

    public static Optional<IItemHandler> findItem(Level world, BlockPos pos, BlockState state, BlockEntity be, Direction direction) {
        List<ProxyProvider<ItemSpec>> providers = ITEMS.get(state.getBlock());
        if (providers == null) return Optional.empty();
        for (ProxyProvider<ItemSpec> provider : providers) {
            RepoProxy<ItemSpec> proxy = provider.visit(world, pos, state, be, direction);
            if (proxy != null) return Optional.of((IItemHandler) proxy);
        }
        return Optional.empty();
    }

    public static Optional<IFluidHandler> findFluid(Level world, BlockPos pos, BlockState state, BlockEntity be, Direction direction) {
        List<ProxyProvider<FluidSpec>> providers = FLUIDS.get(state.getBlock());
        if (providers == null) return Optional.empty();
        for (ProxyProvider<FluidSpec> provider : providers) {
            RepoProxy<FluidSpec> proxy = provider.visit(world, pos, state, be, direction);
            if (proxy != null) return Optional.of((IFluidHandler) proxy);
        }
        return Optional.empty();
    }
}