package cool.muyucloud.registry;

import cool.muyucloud.CropariaIf;
import cool.muyucloud.block.CropariaCropBlock;
import cool.muyucloud.block.Greenhouse;
import cool.muyucloud.block.Infusor;
import cool.muyucloud.block.RitualStand;
import cool.muyucloud.data.ElementsEnum;
import cool.muyucloud.data.crop.Crop;
import cool.muyucloud.util.CropariaCauldronInteraction;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class CropariaBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.BLOCK);

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
        "ritual_stand", () -> new RitualStand(0)
    );
    public static final RegistrySupplier<RitualStand> RITUAL_STAND_2 = registerBlock(
        "ritual_stand_2", () -> new RitualStand(1)
    );
    public static final RegistrySupplier<RitualStand> RITUAL_STAND_3 = registerBlock(
        "ritual_stand_3", () -> new RitualStand(2)
    );
    public static final RegistrySupplier<Block> ELEMENTAL_STONE = registerBlock(
        "elemental_stone",
        () -> new DropExperienceBlock(
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).requiresCorrectToolForDrops(),
            UniformInt.of(0, 2)
        )
    );
    public static final RegistrySupplier<DropExperienceBlock> ELEMATILIUS_ORE = registerBlock(
        "elematilius_ore",
        () -> new DropExperienceBlock(
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).requiresCorrectToolForDrops(),
            UniformInt.of(0, 2)
        )
    );
    public static final RegistrySupplier<DropExperienceBlock> DEEPSLATE_ELEMATILIUS_ORE = registerBlock(
        "deepslate_elematilius_ore",
        () -> new DropExperienceBlock(
            BlockBehaviour.Properties.of().strength(1.0F, 1.0F).requiresCorrectToolForDrops().
                mapColor(MapColor.DEEPSLATE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops(),
            UniformInt.of(0, 2)
        )
    );
    public static final RegistrySupplier<LayeredCauldronBlock> CAULDRON = registerBlock(
        "elematilius_cauldron",
        () -> new LayeredCauldronBlock(
            BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.CAULDRON),
            LayeredCauldronBlock.RAIN, CropariaCauldronInteraction.ELEMATILIUS
        )
    );
    public static final RegistrySupplier<LayeredCauldronBlock> WATER_CAULDRON = registerBlock(
        "water_cauldron",
        () -> new LayeredCauldronBlock(
            BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.CAULDRON),
            LayeredCauldronBlock.RAIN,
            CropariaCauldronInteraction.WATER_CAULDRON
        )
    );
    public static final RegistrySupplier<LayeredCauldronBlock> FIRE_CAULDRON = registerBlock(
        "fire_cauldron",
        () -> new LayeredCauldronBlock(
            BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.CAULDRON),
            LayeredCauldronBlock.RAIN, CropariaCauldronInteraction.FIRE_CAULDRON
        )
    );
    public static final RegistrySupplier<LayeredCauldronBlock> EARTH_CAULDRON = registerBlock(
        "earth_cauldron",
        () -> new LayeredCauldronBlock(
            BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.CAULDRON),
            LayeredCauldronBlock.RAIN, CropariaCauldronInteraction.EARTH_CAULDRON
        )
    );
    public static final RegistrySupplier<LayeredCauldronBlock> AIR_CAULDRON = registerBlock(
        "air_cauldron",
        () -> new LayeredCauldronBlock(
            BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.CAULDRON),
            LayeredCauldronBlock.RAIN, CropariaCauldronInteraction.AIR_CAULDRON
        )
    );

    @NotNull
    public static <T extends Block> RegistrySupplier<T> registerBlock(@NotNull String name, @NotNull Supplier<T> supplier) {
        return BLOCKS.register(name, supplier);
    }

    public static void registerCrop(@NotNull Crop crop) {
        BLOCKS.register(crop.getBlockId(), () -> new CropariaCropBlock(crop));
    }

    public static void register() {
        BLOCKS.register();
    }

    public static @Nullable RegistrySupplier<LayeredCauldronBlock> getCauldron(@NotNull ElementsEnum element) {
        return switch (element) {
            case WATER -> WATER_CAULDRON;
            case FIRE -> FIRE_CAULDRON;
            case EARTH -> EARTH_CAULDRON;
            case AIR -> AIR_CAULDRON;
            case ELEMENTAL -> CAULDRON;
            case EMPTY -> null;
        };
    }

    protected static final List<RegistrySupplier<RitualStand>> RITUAL_STANDS = List.of(RITUAL_STAND, RITUAL_STAND_2, RITUAL_STAND_3);

    /**
     *
     * */
    public static @NotNull RegistrySupplier<RitualStand> getRitualStand(int tier) throws IndexOutOfBoundsException {
        return RITUAL_STANDS.get(tier - 1);
    }
}
