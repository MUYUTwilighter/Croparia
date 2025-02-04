package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.annotation.PostReg;
import cool.muyucloud.croparia.api.core.block.*;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.block.CropariaCropBlock;
import cool.muyucloud.croparia.api.element.ElementsEnum;
import cool.muyucloud.croparia.api.element.block.ElementalLiquidBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CropariaBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<ActivatedShrieker> ACTIVATED_SHRIEKER = registerBlock(
        "activated_shrieker",
        () -> new ActivatedShrieker(
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("activated_shrieker")))
                .mapColor(MapColor.COLOR_BLACK).strength(3.0F, 3.0F).sound(SoundType.SCULK_SHRIEKER)
        )
    );
    public static final RegistrySupplier<Placeholder> PLACEHOLDER = registerBlock("placeholder_block", () -> new Placeholder(
        BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("placeholder_block")))
    ));
    public static final RegistrySupplier<Greenhouse> GREENHOUSE = registerBlock(
        "greenhouse",
        () -> new Greenhouse(
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("greenhouse")))
                .strength(1.0F, 1.0F).randomTicks().lightLevel(state -> 8)
                .isSuffocating((state, world, pos) -> false)
                .isViewBlocking((state, world, pos) -> false)
        )
    );
    public static final RegistrySupplier<Infusor> INFUSOR = registerBlock("infusor", () -> new Infusor(
        BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("infusor")))
            .strength(1.0F, 1.0F).requiresCorrectToolForDrops()
    ));
    public static final RegistrySupplier<RitualStand> RITUAL_STAND = registerBlock(
        "ritual_stand", () -> new RitualStand(
            1,
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("ritual_stand")))
                .strength(1.0F, 1.0F).sound(SoundType.ANVIL).requiresCorrectToolForDrops()
        )
    );
    public static final RegistrySupplier<RitualStand> RITUAL_STAND_2 = registerBlock(
        "ritual_stand_2", () -> new RitualStand(
            2,
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("ritual_stand_2")))
                .strength(1.0F, 1.0F).sound(SoundType.ANVIL).requiresCorrectToolForDrops()
        )
    );
    public static final RegistrySupplier<RitualStand> RITUAL_STAND_3 = registerBlock(
        "ritual_stand_3", () -> new RitualStand(
            3,
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("ritual_stand_3")))
                .strength(1.0F, 1.0F).sound(SoundType.ANVIL).requiresCorrectToolForDrops()
        )
    );
    public static final RegistrySupplier<Block> ELEMENTAL_STONE = registerBlock(
        "elemental_stone",
        () -> new DropExperienceBlock(
            UniformInt.of(0, 2),
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("elemental_stone")))
                .strength(1.0F, 1.0F).requiresCorrectToolForDrops()
        )
    );
    public static final RegistrySupplier<DropExperienceBlock> ELEMATILIUS_ORE = registerBlock(
        "elematilius_ore",
        () -> new DropExperienceBlock(
            UniformInt.of(0, 2),
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("elematilius_ore")))
                .strength(1.0F, 1.0F).requiresCorrectToolForDrops()
        )
    );
    public static final RegistrySupplier<DropExperienceBlock> DEEPSLATE_ELEMATILIUS_ORE = registerBlock(
        "deepslate_elematilius_ore",
        () -> new DropExperienceBlock(
            UniformInt.of(0, 2),
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("deepslate_elematilius_ore")))
                .strength(1.0F, 1.0F).requiresCorrectToolForDrops().mapColor(MapColor.DEEPSLATE)
                .sound(SoundType.DEEPSLATE)
        )
    );
    @PostReg
    public static final RegistrySupplier<ElementalLiquidBlock> FLUID_ELEMATILIUS = registerBlock(
        "fluid_elematilius",
        () -> new ElementalLiquidBlock(ElementsEnum.ELEMENTAL, Fluids.ELEMATILIUS, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
            .setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("fluid_elematilius"))))
    );
    @PostReg
    public static final RegistrySupplier<ElementalLiquidBlock> FLUID_EARTH = registerBlock(
        "fluid_earth",
        () -> new ElementalLiquidBlock(ElementsEnum.EARTH, Fluids.EARTH, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
            .setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("fluid_earth"))))
    );
    @PostReg
    public static final RegistrySupplier<ElementalLiquidBlock> FLUID_WATER = registerBlock(
        "fluid_water",
        () -> new ElementalLiquidBlock(ElementsEnum.WATER, Fluids.WATER, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
            .setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("fluid_water"))))
    );
    @PostReg
    public static final RegistrySupplier<ElementalLiquidBlock> FLUID_FIRE = registerBlock(
        "fluid_fire",
        () -> new ElementalLiquidBlock(ElementsEnum.FIRE, Fluids.FIRE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
            .setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("fluid_fire"))))
    );
    @PostReg
    public static final RegistrySupplier<ElementalLiquidBlock> FLUID_AIR = registerBlock(
        "fluid_air",
        () -> new ElementalLiquidBlock(ElementsEnum.AIR, Fluids.AIR, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
            .setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of("fluid_air"))))
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
