package cool.muyucloud.registry;

import cool.muyucloud.CropariaIf;
import cool.muyucloud.blockentity.GreenhouseBE;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(CropariaIf.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<? extends BlockEntity>> GREENHOUSE_BE = BLOCK_ENTITIES.register(
        "greenhouse",
        () -> BlockEntityType.Builder.of(GreenhouseBE::new, Blocks.get("greenhouse")).build(null)
    );

    public static BlockEntityType<?> get(RegistrySupplier<BlockEntityType<? extends BlockEntity>> type) {
        return BuiltInRegistries.BLOCK_ENTITY_TYPE.get(type.getRegistryId());
    }

    public static BlockEntityType<?> get(String name) {
        return BuiltInRegistries.BLOCK_ENTITY_TYPE.get(new ResourceLocation(CropariaIf.MOD_ID, name));
    }

    public static void register() {
        BLOCK_ENTITIES.register();
    }
}
