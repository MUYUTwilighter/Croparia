package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.block.entity.GreenhouseBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(CropariaIf.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<BlockEntityType<GreenhouseBlockEntity>> GREENHOUSE_BE = register(
        "greenhouse",
        () -> BlockEntityType.Builder.of(GreenhouseBlockEntity::new, CropariaBlocks.GREENHOUSE.get()).build(null)
    );

    @NotNull
    public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(@NotNull String name, @NotNull Supplier<BlockEntityType<T>> supplier) {
        CropariaIf.LOGGER.debug("Registering block entities");
        return BLOCK_ENTITIES.register(name, supplier);
    }

    public static void register() {
        BLOCK_ENTITIES.register();
    }
}
