package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.annotation.PostReg;
import cool.muyucloud.croparia.annotation.PreReg;
import cool.muyucloud.croparia.api.core.block.Greenhouse;
import cool.muyucloud.croparia.api.core.block.Infusor;
import cool.muyucloud.croparia.api.core.block.Placeholder;
import cool.muyucloud.croparia.api.core.block.RitualStand;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.block.CropariaCropBlock;
import cool.muyucloud.croparia.api.element.ElementsEnum;
import cool.muyucloud.croparia.api.element.block.ElementalLiquidBlock;
import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.util.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CropariaBlocks {
    @PreReg
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(CropariaIf.MOD_ID, Registry.BLOCK_REGISTRY);

    @PostReg
    public static final RegistrySupplier<Placeholder> PLACEHOLDER = registerBlock("placeholder_block", Placeholder::new);
    @PostReg
    public static final RegistrySupplier<Greenhouse> GREENHOUSE = registerBlock(
        "greenhouse",
        () -> new Greenhouse(
            BlockBehaviour.Properties.of(Material.GLASS).strength(1.0F, 1.0F).randomTicks().lightLevel(state -> 8)
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
        () -> new Block(
            BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).requiresCorrectToolForDrops()
        )
    );
    @PostReg
    public static final RegistrySupplier<OreBlock> ELEMATILIUS_ORE = registerBlock(
        "elematilius_ore",
        () -> new OreBlock(
            BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F).requiresCorrectToolForDrops()
        )
    );
    @PostReg
    public static final RegistrySupplier<ElementalLiquidBlock> FLUID_ELEMATILIUS = registerBlock(
        "fluid_elematilius",
        () -> new ElementalLiquidBlock(ElementsEnum.ELEMENTAL, Fluids.ELEMATILIUS, BlockBehaviour.Properties.copy(Blocks.WATER))
    );
    @PostReg
    public static final RegistrySupplier<ElementalLiquidBlock> FLUID_EARTH = registerBlock(
        "fluid_earth",
        () -> new ElementalLiquidBlock(ElementsEnum.EARTH, Fluids.EARTH, BlockBehaviour.Properties.copy(Blocks.WATER))
    );
    @PostReg
    public static final RegistrySupplier<ElementalLiquidBlock> FLUID_WATER = registerBlock(
        "fluid_water",
        () -> new ElementalLiquidBlock(ElementsEnum.WATER, Fluids.WATER, BlockBehaviour.Properties.copy(Blocks.WATER))
    );
    @PostReg
    public static final RegistrySupplier<ElementalLiquidBlock> FLUID_FIRE = registerBlock(
        "fluid_fire",
        () -> new ElementalLiquidBlock(ElementsEnum.FIRE, Fluids.FIRE, BlockBehaviour.Properties.copy(Blocks.WATER))
    );
    @PostReg
    public static final RegistrySupplier<ElementalLiquidBlock> FLUID_AIR = registerBlock(
        "fluid_air",
        () -> new ElementalLiquidBlock(ElementsEnum.AIR, Fluids.AIR, BlockBehaviour.Properties.copy(Blocks.WATER))
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
}
