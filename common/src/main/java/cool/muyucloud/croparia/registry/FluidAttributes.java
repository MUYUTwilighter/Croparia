package cool.muyucloud.croparia.registry;

import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import net.minecraft.resources.ResourceLocation;

public class FluidAttributes {
    public static final ArchitecturyFluidAttributes ELEMATILIUS = SimpleArchitecturyFluidAttributes
        .ofSupplier(() -> Fluids.ELEMATILIUS_FLOWING, () -> Fluids.ELEMATILIUS)
        .blockSupplier(() -> CropariaBlocks.FLUID_ELEMATILIUS)
        .bucketItemSupplier(() -> CropariaItems.ELEMATILIUS_BUCKET)
        .sourceTexture(ResourceLocation.tryParse("croparia:block/elematilius_still"))
        .flowingTexture(ResourceLocation.tryParse("croparia:block/elematilius_flow"));
    public static final ArchitecturyFluidAttributes EARTH = SimpleArchitecturyFluidAttributes
        .ofSupplier(() -> Fluids.EARTH_FLOWING, () -> Fluids.EARTH)
        .blockSupplier(() -> CropariaBlocks.FLUID_EARTH)
        .bucketItemSupplier(() -> CropariaItems.EARTH_BUCKET)
        .sourceTexture(ResourceLocation.tryParse("croparia:block/earth_still"))
        .flowingTexture(ResourceLocation.tryParse("croparia:block/earth_flow"));
    public static final ArchitecturyFluidAttributes WATER = SimpleArchitecturyFluidAttributes
        .ofSupplier(() -> Fluids.WATER_FLOWING, () -> Fluids.WATER)
        .blockSupplier(() -> CropariaBlocks.FLUID_WATER)
        .bucketItemSupplier(() -> CropariaItems.WATER_BUCKET)
        .sourceTexture(ResourceLocation.tryParse("croparia:block/water_still"))
        .flowingTexture(ResourceLocation.tryParse("croparia:block/water_flow"));
    public static final ArchitecturyFluidAttributes FIRE = SimpleArchitecturyFluidAttributes
        .ofSupplier(() -> Fluids.FIRE_FLOWING, () -> Fluids.FIRE)
        .blockSupplier(() -> CropariaBlocks.FLUID_FIRE)
        .bucketItemSupplier(() -> CropariaItems.FIRE_BUCKET)
        .sourceTexture(ResourceLocation.tryParse("croparia:block/fire_still"))
        .flowingTexture(ResourceLocation.tryParse("croparia:block/fire_flow"));
    public static final ArchitecturyFluidAttributes AIR = SimpleArchitecturyFluidAttributes
        .ofSupplier(() -> Fluids.AIR_FLOWING, () -> Fluids.AIR)
        .blockSupplier(() -> CropariaBlocks.FLUID_AIR)
        .bucketItemSupplier(() -> CropariaItems.AIR_BUCKET)
        .sourceTexture(ResourceLocation.tryParse("croparia:block/air_still"))
        .flowingTexture(ResourceLocation.tryParse("croparia:block/air_flow"));
}
