package cool.muyucloud.croparia.neoforge.mixin;

import cool.muyucloud.croparia.api.repo.ProxyProvider;
import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import cool.muyucloud.croparia.neoforge.access.BlockCapabilityAccess;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.IBlockCapabilityProvider;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mixin(BlockCapability.class)
public abstract class BlockCapabilityMixin<T, C extends @Nullable Object> extends BaseCapability<T, C> implements BlockCapabilityAccess {
    @Shadow
    @Final
    Map<Block, List<IBlockCapabilityProvider<T, C>>> providers;

    protected BlockCapabilityMixin(ResourceLocation name, Class<T> typeClass, Class<C> contextClass) {
        super(name, typeClass, contextClass);
        System.out.println("test");
    }

    @Override
    @Unique
    public void croparia_if$registerItem(ProxyProvider<ItemSpec> provider, Block... blocks) {
        //noinspection EqualsBetweenInconvertibleTypes
        if (!Objects.equals(this, Capabilities.ItemHandler.BLOCK)) {
            throw new UnsupportedOperationException("Calling registerItem on the wrong capability!");
        }
        for (Block block : blocks) {
            //noinspection unchecked
            this.providers.computeIfAbsent(block, k -> new ArrayList<>()).add(
                (world, pos, state, be, direction) -> (T) provider.visit(world, pos, state, be, (Direction) direction)
            );
        }
    }

    @Override
    @Unique
    public void croparia_if$registerFluid(ProxyProvider<FluidSpec> provider, Block... blocks) {
        //noinspection EqualsBetweenInconvertibleTypes
        if (!Objects.equals(this, Capabilities.FluidHandler.BLOCK)) {
            throw new UnsupportedOperationException("Calling registerFluid on the wrong capability!");
        }
        for (Block block : blocks) {
            //noinspection unchecked
            this.providers.computeIfAbsent(block, k -> new ArrayList<>()).add(
                (world, pos, state, be, direction) -> (T) provider.visit(world, pos, state, be, (Direction) direction)
            );
        }
    }
}