package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.annotation.PostReg;
import cool.muyucloud.croparia.block.*;
import cool.muyucloud.croparia.data.ElementsEnum;
import cool.muyucloud.croparia.data.crop.Crop;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CropariaBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<ActivatedShrieker> ACTIVATED_SHRIEKER = registerBlock(
        "activated_shrieker",
        () -> new ActivatedShrieker(
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(3.0F, 3.0F).sound(SoundType.SCULK_SHRIEKER)
        )
    );
    public static final RegistrySupplier<Placeholder> PLACEHOLDER = registerBlock("placeholder_block", Placeholder::new);
    public static final RegistrySupplier<Greenhouse> GREENHOUSE = registerBlock(
        "greenhouse",
        () -> new Greenhouse(
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).randomTicks().lightLevel(state -> 8)
                .isSuffocating((state, world, pos) -> false)
                .isViewBlocking((state, world, pos) -> false)
        )
    );
    public static final RegistrySupplier<Infusor> INFUSOR = registerBlock("infusor", Infusor::new);
    public static final RegistrySupplier<RitualStand> RITUAL_STAND = registerBlock(
        "ritual_stand", () -> new RitualStand(1)
    );
    public static final RegistrySupplier<RitualStand> RITUAL_STAND_2 = registerBlock(
        "ritual_stand_2", () -> new RitualStand(2)
    );
    public static final RegistrySupplier<RitualStand> RITUAL_STAND_3 = registerBlock(
        "ritual_stand_3", () -> new RitualStand(3)
    );
    public static final RegistrySupplier<Block> ELEMENTAL_STONE = registerBlock(
        "elemental_stone",
        () -> new DropExperienceBlock(
            UniformInt.of(0, 2),
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).requiresCorrectToolForDrops()
        )
    );
    public static final RegistrySupplier<DropExperienceBlock> ELEMATILIUS_ORE = registerBlock(
        "elematilius_ore",
        () -> new DropExperienceBlock(
            UniformInt.of(0, 2),
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).requiresCorrectToolForDrops()
        )
    );
    public static final RegistrySupplier<DropExperienceBlock> DEEPSLATE_ELEMATILIUS_ORE = registerBlock(
        "deepslate_elematilius_ore",
        () -> new DropExperienceBlock(
            UniformInt.of(0, 2),
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).requiresCorrectToolForDrops().
                mapColor(MapColor.DEEPSLATE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()
        )
    );
    @PostReg
    public static final RegistrySupplier<LiquidBlock> FLUID_ELEMATILIUS = registerBlock(
        "fluid_elematilius",
        () -> new ElementalLiquidBlock(ElementsEnum.ELEMENTAL, Fluids.ELEMATILIUS, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER))
    );
    @PostReg
    public static final RegistrySupplier<LiquidBlock> FLUID_EARTH = registerBlock(
        "fluid_earth",
        () -> new ElementalLiquidBlock(ElementsEnum.EARTH, Fluids.EARTH, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER))
    );
    @PostReg
    public static final RegistrySupplier<LiquidBlock> FLUID_WATER = registerBlock(
        "fluid_water",
        () -> new ElementalLiquidBlock(ElementsEnum.WATER, Fluids.WATER, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER))
    );
    @PostReg
    public static final RegistrySupplier<LiquidBlock> FLUID_FIRE = registerBlock(
        "fluid_fire",
        () -> new ElementalLiquidBlock(ElementsEnum.FIRE, Fluids.FIRE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER))
    );
    @PostReg
    public static final RegistrySupplier<LiquidBlock> FLUID_AIR = registerBlock(
        "fluid_air",
        () -> new ElementalLiquidBlock(ElementsEnum.AIR, Fluids.AIR, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER))
    );


    @NotNull
    public static <T extends Block> RegistrySupplier<T> registerBlock(@NotNull String name, @NotNull Supplier<T> supplier) {
        return BLOCKS.register(name, supplier);
    }

    public static void registerCrop(@NotNull Crop crop) {
        BLOCKS.register(crop.getBlockId(), () -> new CropariaCropBlock(crop));
    }

    public static void register() {
        CropariaIf.LOGGER.debug("Registering blocks");
        BLOCKS.register();
    }
}
