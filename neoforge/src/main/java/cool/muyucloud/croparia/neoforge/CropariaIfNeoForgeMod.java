package cool.muyucloud.croparia.neoforge;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.repo.fluid.FluidProxyProvider;
import cool.muyucloud.croparia.api.repo.item.ItemProxyProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;

@EventBusSubscriber(modid = CropariaIf.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CropariaIfNeoForgeMod {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (!event.isBlockRegistered(Capabilities.ItemHandler.BLOCK, block)) {
                event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, be, context) -> {
                    if (be instanceof ItemProxyProvider provider && context instanceof Direction direction) {
                        return (IItemHandler) provider.visitItem(direction);
                    }
                    return null;
                }, block);
            }
            if (!event.isBlockRegistered(Capabilities.FluidHandler.BLOCK, block)) {
                event.registerBlock(Capabilities.FluidHandler.BLOCK, (level, pos, state, be, context) -> {
                    if (be instanceof FluidProxyProvider provider && context instanceof Direction direction) {
                        return (IFluidHandler) provider.visitFluid(direction);
                    }
                    return null;
                }, block);
            }
        });
    }
}
