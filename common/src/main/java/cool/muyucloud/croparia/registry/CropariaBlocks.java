package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.annotation.PostReg;
import cool.muyucloud.croparia.annotation.PreReg;
import cool.muyucloud.croparia.block.*;
import cool.muyucloud.croparia.data.crop.Crop;
import dev.architectury.core.block.ArchitecturyLiquidBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class CropariaBlocks {
    @PreReg
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.BLOCK);

    @PostReg
    public static final RegistrySupplier<ActivatedShrieker> ACTIVATED_SHRIEKER = registerBlock(
        "activated_shrieker",
        () -> new ActivatedShrieker(
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(3.0F, 3.0F).sound(SoundType.SCULK_SHRIEKER)
        )
    );
    @PostReg
    public static final RegistrySupplier<Placeholder> PLACEHOLDER = registerBlock("placeholder_block", Placeholder::new);
    @PostReg
    public static final RegistrySupplier<Greenhouse> GREENHOUSE = registerBlock(
        "greenhouse",
        () -> new Greenhouse(
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).randomTicks().lightLevel(state -> 8)
                .isSuffocating((state, world, pos) -> false)
                .isViewBlocking((state, world, pos) -> false)
        )
    );
    @PostReg
    public static final RegistrySupplier<Infusor> INFUSOR = registerBlock("infusor", Infusor::new);
    @PostReg
    public static final RegistrySupplier<RitualStand> RITUAL_STAND = registerBlock(
        "ritual_stand", () -> new RitualStand(1)
    );
    @PostReg
    public static final RegistrySupplier<RitualStand> RITUAL_STAND_2 = registerBlock(
        "ritual_stand_2", () -> new RitualStand(2)
    );
    @PostReg
    public static final RegistrySupplier<RitualStand> RITUAL_STAND_3 = registerBlock(
        "ritual_stand_3", () -> new RitualStand(3)
    );
    @PostReg
    public static final RegistrySupplier<Block> ELEMENTAL_STONE = registerBlock(
        "elemental_stone",
        () -> new DropExperienceBlock(
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).requiresCorrectToolForDrops(),
            UniformInt.of(0, 2)
        )
    );
    @PostReg
    public static final RegistrySupplier<DropExperienceBlock> ELEMATILIUS_ORE = registerBlock(
        "elematilius_ore",
        () -> new DropExperienceBlock(
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).requiresCorrectToolForDrops(),
            UniformInt.of(0, 2)
        )
    );
    @PostReg
    public static final RegistrySupplier<DropExperienceBlock> DEEPSLATE_ELEMATILIUS_ORE = registerBlock(
        "deepslate_elematilius_ore",
        () -> new DropExperienceBlock(
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).requiresCorrectToolForDrops().
                mapColor(MapColor.DEEPSLATE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops(),
            UniformInt.of(0, 2)
        )
    );
    @PostReg
    public static final RegistrySupplier<LiquidBlock> FLUID_ELEMATILIUS = registerBlock(
        "fluid_elematilius",
        () -> new ArchitecturyLiquidBlock(Fluids.ELEMATILIUS, BlockBehaviour.Properties.copy(Blocks.WATER))
    );
    @PostReg
    public static final RegistrySupplier<LiquidBlock> FLUID_EARTH = registerBlock(
        "fluid_earth",
        () -> new ArchitecturyLiquidBlock(Fluids.EARTH, BlockBehaviour.Properties.copy(Blocks.WATER))
    );
    @PostReg
    public static final RegistrySupplier<LiquidBlock> FLUID_WATER = registerBlock(
        "fluid_water",
        () -> new ArchitecturyLiquidBlock(Fluids.WATER, BlockBehaviour.Properties.copy(Blocks.WATER))
    );
    @PostReg
    public static final RegistrySupplier<LiquidBlock> FLUID_FIRE = registerBlock(
        "fluid_fire",
        () -> new ArchitecturyLiquidBlock(Fluids.FIRE, BlockBehaviour.Properties.copy(Blocks.WATER))
    );
    @PostReg
    public static final RegistrySupplier<LiquidBlock> FLUID_AIR = registerBlock(
        "fluid_air",
        () -> new ArchitecturyLiquidBlock(Fluids.AIR, BlockBehaviour.Properties.copy(Blocks.WATER))
    );

    @PreReg
    @NotNull
    public static <T extends Block> RegistrySupplier<T> registerBlock(@NotNull String name, @NotNull Supplier<T> supplier) {
        return BLOCKS.register(name, supplier);
    }

    @PreReg
    public static void registerCrop(@NotNull Crop crop) {
        BLOCKS.register(crop.getBlockId(), () -> new CropariaCropBlock(crop));
    }

    @PreReg
    public static void register() {
        CropariaIf.LOGGER.debug("Registering blocks");
        BLOCKS.register();
    }

    @PostReg
    protected static final List<RegistrySupplier<RitualStand>> RITUAL_STANDS = List.of(RITUAL_STAND, RITUAL_STAND_2, RITUAL_STAND_3);

    @PostReg
    public static @NotNull RegistrySupplier<RitualStand> getRitualStand(int tier) throws IndexOutOfBoundsException {
        return RITUAL_STANDS.get(tier - 1);
    }
}
