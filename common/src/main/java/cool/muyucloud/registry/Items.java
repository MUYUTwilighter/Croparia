package cool.muyucloud.registry;

import cool.muyucloud.Crop;
import cool.muyucloud.CropariaIf;
import cool.muyucloud.item.CropFruit;
import cool.muyucloud.item.CropSeed;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.ITEM);

    public static void registerCrop(Crop crop) {
        ITEMS.register(crop.getSeedId(), () -> new CropSeed(crop));
        ITEMS.register(crop.getFruitId(), () -> new CropFruit(crop));
    }

    public static void register() {
        ITEMS.register();
    }
}
