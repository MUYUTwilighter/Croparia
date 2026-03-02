package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;

public class FluidAttributes {
    public static final ArchitecturyFluidAttributes ELEMATILIUS = SimpleArchitecturyFluidAttributes
        .ofSupplier(() -> Fluids.ELEMATILIUS_FLOWING, () -> Fluids.ELEMATILIUS)
        .blockSupplier(() -> CropariaBlocks.FLUID_ELEMATILIUS)
        .bucketItemSupplier(() -> CropariaItems.ELEMATILIUS_BUCKET)
        .sourceTexture(CropariaIf.of("block/elematilius_still"))
        .flowingTexture(CropariaIf.of("block/elematilius_flow"))
        .color(0xFFFFFFFF);
    public static final ArchitecturyFluidAttributes EARTH = SimpleArchitecturyFluidAttributes
        .ofSupplier(() -> Fluids.EARTH_FLOWING, () -> Fluids.EARTH)
        .blockSupplier(() -> CropariaBlocks.FLUID_EARTH)
        .bucketItemSupplier(() -> CropariaItems.EARTH_BUCKET)
        .sourceTexture(CropariaIf.of("block/earth_still"))
        .flowingTexture(CropariaIf.of("block/earth_flow"))
        .color(0xFFFFFFFF);
    public static final ArchitecturyFluidAttributes WATER = SimpleArchitecturyFluidAttributes
        .ofSupplier(() -> Fluids.WATER_FLOWING, () -> Fluids.WATER)
        .blockSupplier(() -> CropariaBlocks.FLUID_WATER)
        .bucketItemSupplier(() -> CropariaItems.WATER_BUCKET)
        .sourceTexture(CropariaIf.of("block/water_still"))
        .flowingTexture(CropariaIf.of("block/water_flow"))
        .color(0xFFFFFFFF);
    public static final ArchitecturyFluidAttributes FIRE = SimpleArchitecturyFluidAttributes
        .ofSupplier(() -> Fluids.FIRE_FLOWING, () -> Fluids.FIRE)
        .blockSupplier(() -> CropariaBlocks.FLUID_FIRE)
        .bucketItemSupplier(() -> CropariaItems.FIRE_BUCKET)
        .sourceTexture(CropariaIf.of("block/fire_still"))
        .flowingTexture(CropariaIf.of("block/fire_flow"))
        .color(0xFFFFFFFF);
    public static final ArchitecturyFluidAttributes AIR = SimpleArchitecturyFluidAttributes
        .ofSupplier(() -> Fluids.AIR_FLOWING, () -> Fluids.AIR)
        .blockSupplier(() -> CropariaBlocks.FLUID_AIR)
        .bucketItemSupplier(() -> CropariaItems.AIR_BUCKET)
        .sourceTexture(CropariaIf.of("block/air_still"))
        .flowingTexture(CropariaIf.of("block/air_flow"))
        .color(0xFFFFFFFF);
}
