package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.block.entity.ActivatedShriekerBlockEntity;
import cool.muyucloud.croparia.block.entity.GreenhouseBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(CropariaIf.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<GreenhouseBlockEntity>> GREENHOUSE_BE = register(
        "greenhouse",
        () -> BlockEntityType.Builder.of(GreenhouseBlockEntity::new, CropariaBlocks.GREENHOUSE.get()).build(null)
    );
    public static final RegistrySupplier<BlockEntityType<ActivatedShriekerBlockEntity>> ACTIVATED_SHRIEKER = register(
        "activated_shrieker",
        () -> BlockEntityType.Builder.of(ActivatedShriekerBlockEntity::new, CropariaBlocks.ACTIVATED_SHRIEKER.get()).build(null)
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
