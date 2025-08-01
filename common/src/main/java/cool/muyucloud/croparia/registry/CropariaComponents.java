package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.component.BlockProperties;
import cool.muyucloud.croparia.api.core.component.TargetPos;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class CropariaComponents {
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<TargetPos>> TARGET_POS = register(
        "target_pos",
        () -> {
            DataComponentType.Builder<TargetPos> builder = DataComponentType.builder();
            builder.persistent(TargetPos.CODEC.codec()).networkSynchronized(TargetPos.TYPE.streamCodec());
            return builder.build();
        }
    );
    public static final RegistrySupplier<DataComponentType<BlockProperties>> BLOCK_PROPERTIES = register(
        "block_properties",
        () -> BlockProperties.TYPE
    );
    public static final RegistrySupplier<DataComponentType<BlockPos>> TAG_MATCH = register(
        "tag_match",
        () -> {
            DataComponentType.Builder<BlockPos> builder = DataComponentType.builder();
            builder.persistent(BlockPos.CODEC).networkSynchronized(BlockPos.STREAM_CODEC);
            return builder.build();
        }
    );

    public static <T> RegistrySupplier<DataComponentType<T>> register(String id, Supplier<DataComponentType<T>> supplier) {
        return DATA_COMPONENTS.register(id, supplier);
    }

    public static void register() {
        CropariaIf.LOGGER.debug("Registering data components");
        DATA_COMPONENTS.register();
    }

    public static void forEach(Consumer<DataComponentType<?>> consumer) {
        DATA_COMPONENTS.forEach(supplier -> consumer.accept(supplier.get()));
    }
}
