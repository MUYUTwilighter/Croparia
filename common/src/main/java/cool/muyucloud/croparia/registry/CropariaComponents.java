package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class CropariaComponents {
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<ResourceLocation>> TARGET_WORLD = register(
        "dimension",
        () -> {
            DataComponentType.Builder<ResourceLocation> builder = DataComponentType.builder();
            builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC);
            return builder.build();
        }
    );
    public static final RegistrySupplier<DataComponentType<BlockPos>> TARGET_POSITION = register(
        "position",
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
        DATA_COMPONENTS.register();
    }
}
