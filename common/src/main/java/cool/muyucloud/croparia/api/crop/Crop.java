package cool.muyucloud.croparia.api.crop;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.annotation.PostGen;
import cool.muyucloud.croparia.annotation.PostReg;
import cool.muyucloud.croparia.api.crop.block.CropariaCropBlock;
import cool.muyucloud.croparia.api.generator.PlaceHolder;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.BiOptional;
import cool.muyucloud.croparia.util.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crop {
    @NotNull
    private final String name;
    @NotNull
    private final ResourceLocation material;
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
        if (Util.anyNull(raw.name(), raw.material())) {
            throw new IllegalArgumentException("Crop name and material ID cannot be null");
        }
        this.name = parseName(raw.name());
        this.material = parseMaterialId(raw.material(), raw.tag());
        this.type = parseType(raw.type());
        this.translationKey = raw.translationKey() == null ? "crop.croparia." + this.name : raw.translationKey();
        this.translations = parseTranslation(raw.translations(), parseDefaultTranslation(this.name));
        this.color = raw.color().startsWith("0x") ? Integer.parseInt(raw.color().substring(2), 16) : Integer.parseInt(raw.color());
        this.tier = parseTier(raw.tier());
        this.tag = raw.material().trim().startsWith("#");
        this.blockId = CropariaIf.of("block_crop_" + this.name);
        this.seedId = CropariaIf.of("seed_crop_" + this.name);
        this.fruitId = CropariaIf.of("fruit_" + this.name);
    }

    private Crop(@NotNull String name, @NotNull String material, int color, int tier, @Nullable CropType type, @Nullable Map<String, String> translations, @Nullable String translationKey) throws RuntimeException {
        this.name = parseName(name);
        this.material = parseMaterialId(material, null);
        this.color = color;
        this.tier = parseTier(tier);
        this.type = type == null ? CropType.CROP : type;
        this.translationKey = translationKey == null ? "crop.croparia." + this.name : translationKey;
        this.translations = parseTranslation(translations, parseDefaultTranslation(this.name));
        this.tag = material.trim().startsWith("#");
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

    public static Optional<Crop> create(@NotNull String name, @NotNull String material, int color, int tier, @Nullable CropType type, @Nullable String translationKey, @Nullable Map<String, String> translations) {
        try {
            return Optional.of(new Crop(name, material, color, tier, type, translations, translationKey));
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Failed to create crop %s".formatted(name), e);
            return Optional.empty();
        }
    }

    public static Optional<Crop> create(@NotNull String name, @NotNull String material, int color, int tier, @Nullable CropType type) {
        try {
            return Optional.of(new Crop(name, material, color, tier, type, null, null));
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Failed to create crop %s".formatted(name), e);
            return Optional.empty();
        }
    }

    public Optional<Crop> forModified(@Nullable String material, @Nullable Integer color, @Nullable Integer tier, @Nullable CropType type, @Nullable Map<String, String> translations, @Nullable String translationKey) {
        material = material == null ? this.taggableMaterial() : material;
        color = color == null ? this.getColor() : color;
        tier = tier == null ? this.getTier() : tier;
        type = type == null ? this.getType() : type;
        HashMap<String, String> mergedTranslations = new HashMap<>(this.getTranslations());
        if (translations != null) {
            mergedTranslations.putAll(translations);
        }
        translationKey = translationKey == null ? this.getTranslationKey() : translationKey;
        return Crop.create(this.getName(), material, color, tier, type, translationKey, mergedTranslations);
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
                String key = entry.getKey().trim().toLowerCase();
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
    public JsonObject toJson() {
        JsonObject root = new JsonObject();
        root.addProperty("name", this.name);
        root.addProperty("material", (this.tag ? "#" : "") + this.material);
        root.addProperty("color", this.serializeColor());
        root.addProperty("tier", this.tier);
        root.addProperty("type", this.type.getModelName());
        root.addProperty("translationKey", this.translationKey);
        JsonObject translations = new JsonObject();
        this.translations.forEach(translations::addProperty);
        root.add("translations", translations);
        return root;
    }

    protected static int parseTier(int tier) {
        if (tier < CropariaItems.leastTier() || tier > CropariaItems.mostTier()) {
            CropariaIf.LOGGER.warn("Crop tier {} is out of range, defaulting to 1", tier);
            return 1;
        }
        return tier;
    }

    @NotNull
    public CropType getType() {
        return type;
    }

    public String taggableMaterial() {
        return (this.tag ? "#" : "") + this.material;
    }

    @NotNull
    public Item getMaterialItem() {
        if (this.tag) {
            TagKey<Item> tag = TagKey.create(Registries.ITEM, this.material);
            Iterable<Holder<Item>> set = BuiltInRegistries.ITEM.getTagOrEmpty(tag);
            if (set.iterator().hasNext()) {
                return set.iterator().next().value();
            }
        } else {
            return BuiltInRegistries.ITEM.get(this.material);
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

    public String serializeColor() {
        String hex = Integer.toHexString(this.color);
        hex = "0".repeat(6 - hex.length()) + hex;
        return "0x" + hex;
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
    @PostReg
    public CropariaCropBlock getCropBlock() {
        return (CropariaCropBlock) BuiltInRegistries.BLOCK.get(blockId);
    }

    @NotNull
    public ResourceLocation getSeedId() {
        return seedId;
    }

    @NotNull
    @PostReg
    public Item getSeedItem() {
        return BuiltInRegistries.ITEM.get(seedId);
    }

    @NotNull
    public ResourceLocation getFruitId() {
        return fruitId;
    }

    @NotNull
    @PostReg
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
    public ResourceLocation getMaterial() {
        return material;
    }

    public boolean isTag() {
        return tag;
    }

    @SuppressWarnings("unused")
    protected Map<String, String> getTranslations() {
        return Map.copyOf(translations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public String toString() {
        return "Crop{" +
            "name='" + name + '\'' +
            ", material=" + material +
            ", type=" + type +
            ", translationKey='" + translationKey + '\'' +
            ", translations=" + translations +
            ", color=" + color +
            ", tier=" + tier +
            ", tag=" + tag +
            '}';
    }

    private static final Pattern NAME = Pattern.compile("\\{name}");
    private static final Pattern MATERIAL = Pattern.compile("\\{material}");
    private static final Pattern MATERIAL_PATH = Pattern.compile("\\{material_path}");
    private static final Pattern MATERIAL_TYPE = Pattern.compile("\\{material_type}");
    private static final Pattern MATERIAL_TAGGABLE = Pattern.compile("\\{material_taggable}");
    private static final Pattern COLOR = Pattern.compile("\\{color}");
    private static final Pattern COLOR_HEX = Pattern.compile("\\{color_hex}");
    private static final Pattern TYPE = Pattern.compile("\\{type}");
    private static final Pattern TIER = Pattern.compile("\\{tier}");
    private static final Pattern SEED = Pattern.compile("\\{seed}");
    private static final Pattern SEED_PATH = Pattern.compile("\\{seed_path}");
    private static final Pattern FRUIT = Pattern.compile("\\{fruit}");
    private static final Pattern FRUIT_PATH = Pattern.compile("\\{fruit_path}");
    private static final Pattern CROP_BLOCK = Pattern.compile("\\{crop_block}");
    private static final Pattern CROP_BLOCK_PATH = Pattern.compile("\\{crop_block_path}");
    private static final Pattern RESULT = Pattern.compile("\\{result}");
    private static final Pattern RESULT_PATH = Pattern.compile("\\{result_path}");
    private static final Pattern TRANSLATION_KEY = Pattern.compile("\\{translation_key}");
    private static final Pattern RESULT_COUNT = Pattern.compile("\\{result_count\\.(\\d+)}");
    private static final Pattern CROPARIA = Pattern.compile("\\{croparia}");
    private static final Pattern CROPARIA_PATH = Pattern.compile("\\{croparia_path}");
    private static final Pattern TRANSLATIONS = Pattern.compile("\\{translations\\.([^}]+)}");

    @PostGen
    public Map<Pattern, PlaceHolder> placeholders() {
        Map<Pattern, PlaceHolder> map = new HashMap<>();
        map.put(COLOR, placeholder -> Integer.toString(this.color));
        map.put(COLOR_HEX, placeholder -> Integer.toHexString(this.color));
        map.put(CROPARIA, placeholder -> CropariaItems.getCroparia(this.getTier()).getId().toString());
        map.put(CROPARIA_PATH, placeholder -> CropariaItems.getCroparia(this.getTier()).getId().getPath());
        map.put(CROP_BLOCK, placeholder -> this.getBlockId().toString());
        map.put(CROP_BLOCK_PATH, placeholder -> this.getBlockId().getPath());
        map.put(FRUIT, placeholder -> this.getFruitId().toString());
        map.put(FRUIT_PATH, placeholder -> this.getFruitId().getPath());
        map.put(MATERIAL, placeholder -> this.getMaterial().toString());
        map.put(MATERIAL_PATH, placeholder -> this.getMaterial().getPath());
        map.put(MATERIAL_TYPE, placeholder -> this.isTag() ? "tag" : "item");
        map.put(MATERIAL_TAGGABLE, placeholder -> this.taggableMaterial());
        map.put(NAME, placeholder -> this.name);
        map.put(SEED, placeholder -> this.getSeedId().toString());
        map.put(SEED_PATH, placeholder -> this.getSeedId().getPath());
        map.put(RESULT, placeholder -> Objects.requireNonNull(this.getMaterialItem().arch$registryName()).toString());
        map.put(RESULT_COUNT, placeholder -> {
            Matcher matcher = RESULT_COUNT.matcher(placeholder);
            if (matcher.find()) {
                int count = Integer.parseInt(matcher.group(1));
                return String.valueOf(Math.min(this.getMaterialItem().getMaxStackSize(), count));
            } else {
                throw new RuntimeException("Invalid result count placeholder: " + placeholder);
            }
        });
        map.put(RESULT_PATH, placeholder -> Objects.requireNonNull(this.getMaterialItem().arch$registryName()).getPath());
        map.put(TIER, placeholder -> Integer.toString(this.tier));
        map.put(TYPE, placeholder -> this.type.getModelName());
        map.put(TRANSLATION_KEY, placeholder -> this.translationKey);
        map.put(TRANSLATIONS, placeholder -> {
            Matcher matcher = TRANSLATIONS.matcher(placeholder);
            if (matcher.find()) {
                return this.translate(matcher.group(1));
            } else {
                throw new RuntimeException("Invalid translation placeholder: " + placeholder);
            }
        });
        return map;
    }
}
