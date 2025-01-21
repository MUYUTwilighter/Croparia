package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

@SuppressWarnings("unused")
public class PlacedFeatures {
    public static final Holder<PlacedFeature> ELEMATILIUS_ORE = PlacementUtils.register(
        "croparia:elematilius_ore",
        ConfiguredFeatures.ELEMATILIUS_ORE, List.of(
            CountPlacement.of(10),
            InSquarePlacement.spread(),
            HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(-80), VerticalAnchor.belowTop(80)))
        )
    );

    public static void init() {
        CropariaIf.LOGGER.debug("Adding placed features");
    }
}
