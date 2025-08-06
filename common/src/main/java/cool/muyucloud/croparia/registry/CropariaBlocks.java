package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.block.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class CropariaBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<ActivatedShrieker> ACTIVATED_SHRIEKER = registerBlock(
        "activated_shrieker", properties -> new ActivatedShrieker(
            properties.mapColor(MapColor.COLOR_BLACK).strength(3.0F, 3.0F).sound(SoundType.SCULK_SHRIEKER)
        )
    );
    public static final RegistrySupplier<Placeholder> PLACEHOLDER = registerBlock("placeholder_block", Placeholder::new);
    public static final RegistrySupplier<Greenhouse> GREENHOUSE = registerBlock(
        "greenhouse", properties -> new Greenhouse(
            properties.strength(1.0F, 1.0F).randomTicks().lightLevel(state -> 8)
                .isSuffocating((state, world, pos) -> false)
                .isViewBlocking((state, world, pos) -> false)
        )
    );
    public static final RegistrySupplier<Infusor> INFUSOR = registerBlock("infusor", properties -> new Infusor(
        properties.strength(1.0F, 1.0F).requiresCorrectToolForDrops()
    ));
    public static final RegistrySupplier<RitualStand> RITUAL_STAND = registerBlock(
        "ritual_stand", properties -> new RitualStand(
            1, properties.strength(1.0F, 1.0F).sound(SoundType.ANVIL).requiresCorrectToolForDrops()
        )
    );
    public static final RegistrySupplier<RitualStand> RITUAL_STAND_2 = registerBlock(
        "ritual_stand_2", properties -> new RitualStand(
            2, properties.strength(1.0F, 1.0F).sound(SoundType.ANVIL).requiresCorrectToolForDrops()
        )
    );
    public static final RegistrySupplier<RitualStand> RITUAL_STAND_3 = registerBlock(
        "ritual_stand_3", properties -> new RitualStand(
            3, properties.strength(1.0F, 1.0F).sound(SoundType.ANVIL).requiresCorrectToolForDrops()
        )
    );
    public static final RegistrySupplier<Block> ELEMENTAL_STONE = registerBlock(
        "elemental_stone",
        properties -> new ElementalStone(properties.strength(1.0F, 1.0F).requiresCorrectToolForDrops())
    );
    public static final RegistrySupplier<DropExperienceBlock> ELEMATILIUS_ORE = registerBlock(
        "elematilius_ore",
        properties -> new DropExperienceBlock(
            UniformInt.of(0, 2),
            properties.strength(1.0F, 1.0F).requiresCorrectToolForDrops()
        )
    );
    public static final RegistrySupplier<DropExperienceBlock> DEEPSLATE_ELEMATILIUS_ORE = registerBlock(
        "deepslate_elematilius_ore",
        properties -> new DropExperienceBlock(
            UniformInt.of(0, 2),
            properties.strength(1.0F, 1.0F).requiresCorrectToolForDrops().mapColor(MapColor.DEEPSLATE)
                .sound(SoundType.DEEPSLATE)
        )
    );

    public static <T extends Block> RegistrySupplier<T> registerBlock(
        @NotNull String name, @NotNull Function<BlockBehaviour.Properties, T> supplier
    ) {
        return BLOCKS.register(name, () -> supplier.apply(
            BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, CropariaIf.of(name)))
        ));

    }

    public static void register() {
        CropariaIf.LOGGER.debug("Registering blocks");
        BLOCKS.register();
    }
}
