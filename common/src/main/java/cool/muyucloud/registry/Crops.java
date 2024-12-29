package cool.muyucloud.registry;

import cool.muyucloud.CropariaIf;
import cool.muyucloud.data.crop.Crop;
import cool.muyucloud.data.crop.CropFileReader;
import cool.muyucloud.data.crop.CropType;
import cool.muyucloud.data.crop.RawCrop;
import dev.architectury.platform.Platform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Crops {
    public static final Set<Crop> CROPS = new HashSet<>();

    /**
     * Add a simple crop. Mainly used for croparia crops.
     *
     * @param name       crop name
     * @param materialId id of material which the crop grows, could be item ID or item tag
     * @param color      int value of color
     * @param tier       tier
     * @param type       crop type that specifies the textures. See also {@link CropType}
     */
    public static @NotNull Crop registerCrop(
        @NotNull String name, @NotNull String materialId, int color, int tier, @NotNull CropType type
    ) {
        Crop crop = Crop.create(name, materialId, color, tier, type).orElseThrow(
            () -> new IllegalArgumentException(
                "Failed to create croparia crop %s".formatted(name)
            )
        );
        CROPS.add(crop);
        return crop;
    }

    public static final Crop ELEMATILIUS = registerCrop("elematilius", "croparia:elematilius", 0x7F7F00, 3, CropType.CROP);

    /**
     * Add a simple crop with specified translation key. Mainly used for vanilla crops.
     *
     * @param name           crop name
     * @param materialId     material id which the crop grows, could be item ID or item tag
     * @param color          int value of color
     * @param tier           tier
     * @param type           crop type that specifies the textures. See also {@link CropType}
     * @param translationKey translation key for the crop, used for formatting item & block names.
     */
    public static @NotNull Crop registerCrop(
        @NotNull String name, @NotNull String materialId, int color, int tier, @NotNull CropType type,
        @NotNull String translationKey
    ) {
        Crop crop = Crop.create(name, materialId, color, tier, type, translationKey, Map.of()).orElseThrow(
            () -> new IllegalArgumentException("Vanilla crop %s failed to create".formatted(name))
        );
        CROPS.add(crop);
        return crop;
    }

    public static final Crop APPLE = registerCrop("apple", "minecraft:apple", 0x7F7F00, 0, CropType.FOOD, "item.minecraft.apple");
    public static final Crop IRON = registerCrop("iron", "minecraft:iron_ingot", 0x7F7F00, 0, CropType.CROP, "item.minecraft.iron_ingot");

    /**
     * Add a crop from other mods which should be loaded. Mainly used for crops modded only by 1 mod.
     *
     * @param name           crop name
     * @param materialId     material id which the crop grows, could be item ID or item tag
     * @param color          int value of color
     * @param tier           tier
     * @param type           crop type that specifies the textures. See also {@link CropType}
     * @param translationKey translation key for the crop, used for formatting item & block names.
     * @param dependency     mod dependency
     */
    public static @Nullable Optional<Crop> registerCrop(
        @NotNull String name, @NotNull String materialId, int color, int tier, @NotNull CropType type,
        @NotNull String translationKey, @NotNull String dependency
    ) {
        if (shouldLoad(List.of(dependency))) {
            return Crop.create(name, materialId, color, tier, type, translationKey, Map.of());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Add a crop from other mods which should be loaded. Mainly used for crops modded by more than 1 mod.
     *
     * @param name         crop name
     * @param materialId   material id which the crop grows, could be item ID or item tag
     * @param color        int value of color
     * @param tier         tier
     * @param type         crop type that specifies the textures. See also {@link CropType}
     * @param translations custom translations
     * @param dependencies mod dependencies
     */
    public static @Nullable Optional<Crop> registerCrop(
        @NotNull String name, @NotNull String materialId, int color, int tier, @NotNull CropType type,
        @NotNull Map<String, String> translations, @NotNull String... dependencies
    ) {
        if (shouldLoad(List.of(List.of(dependencies)))) {
            return Crop.create(name, materialId, color, tier, type, null, translations);
        } else {
            return Optional.empty();
        }
    }

    public static boolean shouldLoad(List<List<String>> dependencies) {
        return dependencies.stream().allMatch(list -> list.isEmpty() || list.stream().anyMatch(Platform::isModLoaded));
    }

    @SafeVarargs
    public static boolean shouldLoad(List<String>... dependencies) {
        return Arrays.stream(dependencies).allMatch(list -> list.isEmpty() || list.stream().anyMatch(Platform::isModLoaded));
    }

    public static void register() {
        CropFileReader.readCrops().forEach(Crops::registerFileCrop);
        for (Crop crop : CROPS) {
            CropariaItems.registerCrop(crop);
            CropariaBlocks.registerCrop(crop);
        }
    }

    private static void registerFileCrop(RawCrop raw) {
        if (raw.dependencies() == null || shouldLoad(raw.dependencies())) {
            Crop.of(raw).ifPresentOrElse(
                CROPS::add,
                () -> CropariaIf.LOGGER.error("Failed to create custom crop %s".formatted(raw.name()))
            );
        }
    }
}
