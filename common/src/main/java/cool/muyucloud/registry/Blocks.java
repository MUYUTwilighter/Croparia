package cool.muyucloud.registry;

import cool.muyucloud.Crop;
import cool.muyucloud.CropariaIf;
import cool.muyucloud.block.CropariaCropBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class Blocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.BLOCK);

    public static Block get(RegistrySupplier<Block> supplier) {
        return BuiltInRegistries.BLOCK.get(supplier.getRegistryId());
    }

    public static Block get(String name) {
        return BuiltInRegistries.BLOCK.get(new ResourceLocation(CropariaIf.MOD_ID, name));
    }

    public static RegistrySupplier<Block> registerBlock(String name, Supplier<Block> supplier) {
        return BLOCKS.register(name, supplier);
    }

    public static RegistrySupplier<CropariaCropBlock> registerCrop(Crop crop) {
        return BLOCKS.register(crop.getBlockId(), () -> new CropariaCropBlock(crop));
    }

    public static void register() {
        BLOCKS.register();
    }
}
