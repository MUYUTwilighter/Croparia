package cool.muyucloud.croparia.kubejs;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.block.CropariaCropBlock;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.CropType;
import cool.muyucloud.croparia.api.crop.item.CropFruit;
import cool.muyucloud.croparia.api.crop.item.CropSeed;
import cool.muyucloud.croparia.api.crop.Crops;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@SuppressWarnings({"unused"})
public class CropRegistry {
    @NotNull
    private final DeferredRegister<Item> itemRegistry = DeferredRegister.create(CropariaIf.MOD_ID, Registries.ITEM);
    @NotNull
    private final DeferredRegister<Block> blockRegistry = DeferredRegister.create(CropariaIf.MOD_ID, Registries.BLOCK);
    @NotNull
    private final String name;

    public CropRegistry() {
        this.name = "Unnamed";
    }

    public CropRegistry(@NotNull String name) {
        this.name = name;
    }

    /**
     * Add a simple custom crop.
     *
     * @param name           crop name
     * @param material       material id which the crop grows, could be item ID or item tag
     * @param color          int value of color
     * @param tier           tier
     * @param type           crop type that specifies the textures. See also {@link CropType}
     * @param translationKey translation translationKey for the crop, used for formatting item & block names.
     * @param translations   custom translations
     */
    public void simpleCrop(
        @NotNull String name, @NotNull String material, int color, int tier, @Nullable String type,
        @Nullable String translationKey, @Nullable Map<String, String> translations
    ) {
        CropType parsedType;
        if (type == null) {
            parsedType = CropType.CROP;
        } else {
            try {
                parsedType = CropType.valueOf(type.toUpperCase());
            } catch (Exception e) {
                parsedType = CropType.CROP;
            }
        }
        Crop crop = Crop.create(name, material, color, tier, parsedType, translationKey, translations).orElseThrow(
            () -> new IllegalArgumentException("Failed to create crop \"%s\"".formatted(name))
        );
        if (Crops.recordCustom(crop)) {
            RegistrySupplier<CropariaCropBlock> cropBlock = blockRegistry.register(crop.getBlockId(), () -> new CropariaCropBlock(crop));
            RegistrySupplier<CropSeed> seed = itemRegistry.register(crop.getSeedId(), () -> new CropSeed(crop));
            RegistrySupplier<CropFruit> fruit = itemRegistry.register(crop.getFruitId(), () -> new CropFruit(crop));
            CropariaIf.LOGGER.debug("Added crop \"{}\" for CropRegistry \"{}\"", name, this.name);
        } else {
            CropariaIf.LOGGER.error("Duplicated custom crop \"{}\" from KubeJS CropRegistry \"{}\"", name, this.name);
        }
    }

    public void register() {
        blockRegistry.register();
        itemRegistry.register();
        CropariaIf.LOGGER.info("Done registering crops for CropRegistry \"{}\"", name);
    }
}