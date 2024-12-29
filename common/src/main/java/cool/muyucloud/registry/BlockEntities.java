package cool.muyucloud.registry;

import cool.muyucloud.CropariaIf;
import cool.muyucloud.blockentity.GreenhouseBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class BlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(CropariaIf.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<GreenhouseBlockEntity>> GREENHOUSE_BE = register(
        "greenhouse",
        () -> BlockEntityType.Builder.of(GreenhouseBlockEntity::new, CropariaBlocks.GREENHOUSE.get()).build(null)
    );

    public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> supplier) {
        return BLOCK_ENTITIES.register(name, supplier);
    }

    public static void register() {
        BLOCK_ENTITIES.register();
    }
}
