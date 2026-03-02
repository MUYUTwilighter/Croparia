package cool.muyucloud.croparia.neoforge;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.client.CropariaIfClient;
import cool.muyucloud.croparia.registry.FluidAttributes;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = CropariaIf.MOD_ID, value = Dist.CLIENT)
public class CropariaIfClientNeoForge {
    @SubscribeEvent()
    public static void onClientSetup(FMLClientSetupEvent event) {
        CropariaIfClient.init();
    }

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        registerFluidClientExtensions(event, FluidAttributes.ELEMATILIUS);
        registerFluidClientExtensions(event, FluidAttributes.EARTH);
        registerFluidClientExtensions(event, FluidAttributes.WATER);
        registerFluidClientExtensions(event, FluidAttributes.FIRE);
        registerFluidClientExtensions(event, FluidAttributes.AIR);
    }

    private static void registerFluidClientExtensions(
        RegisterClientExtensionsEvent event,
        ArchitecturyFluidAttributes attributes
    ) {
        FluidType fluidType = attributes.getSourceFluid().getFluidType();
        ResourceLocation stillTexture = attributes.getSourceTexture();
        ResourceLocation flowingTexture = attributes.getFlowingTexture();
        int tintColor = attributes.getColor();
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public @NotNull ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public @NotNull ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }

            @Override
            public int getTintColor() {
                return tintColor;
            }
        }, fluidType);
    }
}
