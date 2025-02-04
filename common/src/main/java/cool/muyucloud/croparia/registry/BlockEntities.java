package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.block.entity.ActivatedShriekerBlockEntity;
import cool.muyucloud.croparia.api.core.block.entity.GreenhouseBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.Supplier;

public class BlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(CropariaIf.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<GreenhouseBlockEntity>> GREENHOUSE_BE = register(
        "greenhouse",
        () -> new BlockEntityType<>(GreenhouseBlockEntity::new, Set.of(CropariaBlocks.GREENHOUSE.get()))
    );
    public static final RegistrySupplier<BlockEntityType<ActivatedShriekerBlockEntity>> ACTIVATED_SHRIEKER = register(
        "activated_shrieker",
        () -> new BlockEntityType<>(ActivatedShriekerBlockEntity::new, Set.of(CropariaBlocks.ACTIVATED_SHRIEKER.get()))
    );

    @NotNull
    public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(@NotNull String name, @NotNull Supplier<BlockEntityType<T>> supplier) {
        return BLOCK_ENTITIES.register(name, supplier);
    }

    public static void register() {
        CropariaIf.LOGGER.debug("Registering block entities");
        BLOCK_ENTITIES.register();
    }
}
