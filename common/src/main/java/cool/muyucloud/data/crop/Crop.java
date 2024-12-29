package cool.muyucloud.data.crop;

import cool.muyucloud.CropariaIf;
import cool.muyucloud.block.CropariaCropBlock;
import cool.muyucloud.util.BiOptional;
import cool.muyucloud.util.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class Crop {

    @NotNull
    private final String name;
    @NotNull
    private final ResourceLocation materialId;
    @NotNull
    private final CropType type;
    @NotNull
    private final String translationKey;
    @NotNull
    private final Map<String, String> translations;
    private final int color;
    private final int tier;
    @NotNull
    private transient final ResourceLocation blockId;
    @NotNull
    private transient final ResourceLocation seedId;
    @NotNull
    private transient final ResourceLocation fruitId;
    private transient final boolean tag;

    private Crop(@NotNull RawCrop raw) throws RuntimeException {
        if (Util.hasNull(raw.name(), raw.material())) {
            throw new IllegalArgumentException("Crop name and material ID cannot be null");
        }
        this.name = parseName(raw.name());
        this.materialId = parseMaterialId(raw.material(), raw.tag());
        this.type = parseType(raw.type());
        this.translations = parseTranslation(raw.translations(), parseDefaultTranslation(this.name));
        this.translationKey = raw.translationKey() == null ? "crop.croparia." + this.name : raw.translationKey();
        this.color = Integer.parseInt(raw.color());
        this.tier = raw.tier();
        this.tag = raw.material().trim().startsWith("#");
        this.blockId = CropariaIf.of("block_crop_" + this.name);
        this.seedId = CropariaIf.of("seed_crop_" + this.name);
        this.fruitId = CropariaIf.of("fruit_" + this.name);
    }

    private Crop(@NotNull String name, @NotNull String materialId, int color, int tier, @Nullable CropType type, @Nullable Map<String, String> translations, @Nullable String translationKey) throws RuntimeException {
        this.name = parseName(name);
        this.materialId = parseMaterialId(materialId, null);
        this.color = color;
        this.tier = tier;
        this.type = type == null ? CropType.CROP : type;
        this.translations = parseTranslation(translations, parseDefaultTranslation(this.name));
        this.translationKey = translationKey == null ? "croparia.crop." + this.name : translationKey;
        this.tag = materialId.trim().startsWith("#");
        this.blockId = CropariaIf.of("block_crop_" + this.name);
        this.seedId = CropariaIf.of("seed_crop_" + this.name);
        this.fruitId = CropariaIf.of("fruit_" + this.name);
    }

    public static Optional<Crop> of(@NotNull RawCrop raw) {
        try {
            return Optional.of(new Crop(raw));
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Failed to create crop %s".formatted(raw.name()), e);
            return Optional.empty();
        }
    }

    public static Optional<Crop> create(@NotNull String name, @NotNull String materialId, int color, int tier, @Nullable CropType type, @Nullable String translationKey, @Nullable Map<String, String> translations) {
        try {
            return Optional.of(new Crop(name, materialId, color, tier, type, translations, translationKey));
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Failed to create crop %s".formatted(name), e);
            return Optional.empty();
        }
    }

    public static Optional<Crop> create(@NotNull String name, @NotNull String materialId, int color, int tier, @Nullable CropType type) {
        try {
            return Optional.of(new Crop(name, materialId, color, tier, type, null, null));
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Failed to create crop %s".formatted(name), e);
            return Optional.empty();
        }
    }

    @NotNull
    protected static String parseName(@NotNull String name) {
        return name.trim().toLowerCase();
    }

    @NotNull
    protected static ResourceLocation parseMaterialId(@Nullable String material, @Nullable String tag) {
        AtomicReference<ResourceLocation> id = new AtomicReference<>();
        BiOptional.of(material, tag).ifEither(
            l -> {
                if (l.startsWith("#")) {
                    l = l.substring(1);
                }
                id.set(new ResourceLocation(l));
            },
            r -> id.set(new ResourceLocation(r)),
            () -> {
                throw new IllegalArgumentException("Ambiguous material, should declare either material or tag");
            }
        );
        return id.get();
    }

    @NotNull
    protected static CropType parseType(@Nullable String type) {
        return type == null ? CropType.CROP : CropType.valueOf(type.trim().toUpperCase());
    }

    @NotNull
    protected static Map<String, String> parseTranslation(@Nullable Map<String, String> translation, @NotNull String defaultTranslation) {
        Map<String, String> map = new HashMap<>();
        if (translation != null) {
            for (Map.Entry<String, String> entry : translation.entrySet()) {
                String key = entry.getKey().trim().toUpperCase();
                String value = entry.getValue();
                map.put(key, value);
            }
        }
        map.putIfAbsent("en_us", defaultTranslation);
        return map;
    }

    @NotNull
    protected static String parseDefaultTranslation(@NotNull String name) {
        name = name.replaceAll("_", " ").trim();
        StringBuilder builder = new StringBuilder();
        for (String token : name.split(" ")) {
            builder.append(Character.toUpperCase(token.charAt(0))).append(token.substring(1)).append(" ");
        }
        return builder.toString().trim();
    }

    @NotNull
    public CropType getType() {
        return type;
    }

    @NotNull
    public Item getMaterialItem() {
        if (this.tag) {
            TagKey<Item> tag = TagKey.create(Registries.ITEM, this.materialId);
            Iterable<Holder<Item>> set = BuiltInRegistries.ITEM.getTagOrEmpty(tag);
            if (set.iterator().hasNext()) {
                return set.iterator().next().value();
            }
        } else {
            return BuiltInRegistries.ITEM.get(this.materialId);
        }
        return Items.AIR;
    }

    @NotNull
    public String getTranslationKey() {
        return translationKey;
    }

    public int getColor() {
        return color;
    }

    public int getTier() {
        return tier;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public ResourceLocation getBlockId() {
        return blockId;
    }

    @NotNull
    public CropariaCropBlock getCropBlock() {
        return (CropariaCropBlock) BuiltInRegistries.BLOCK.get(blockId);
    }

    @NotNull
    public ResourceLocation getSeedId() {
        return seedId;
    }

    @NotNull
    public Item getSeedItem() {
        return BuiltInRegistries.ITEM.get(seedId);
    }

    @NotNull
    public ResourceLocation getFruitId() {
        return fruitId;
    }

    @NotNull
    public Item getFruitItem() {
        return BuiltInRegistries.ITEM.get(fruitId);
    }

    @NotNull
    public Set<String> availableLangs() {
        return Set.copyOf(translations.keySet());
    }

    @NotNull
    public String translate(@Nullable String lang) {
        return translations.getOrDefault(lang, translations.get("en_us"));
    }

    @NotNull
    public ResourceLocation getMaterialId() {
        return materialId;
    }

    public boolean isTag() {
        return tag;
    }
}
