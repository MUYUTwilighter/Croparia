package cool.muyucloud.data.crop;

import cool.muyucloud.CropariaIf;
import cool.muyucloud.util.Util;
import dev.architectury.platform.Platform;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    private final ResourceLocation blockId;
    @NotNull
    private final ResourceLocation seedId;
    @NotNull
    private final ResourceLocation fruitId;
    private final boolean tag;
    @NotNull
    private Item material = Items.AIR;

    private Crop(@NotNull RawCrop raw) {
        if (Util.hasNull(raw.name(), raw.materialId())) {
            throw new IllegalArgumentException("Crop name and material ID cannot be null");
        }
        this.name = parseName(raw.name());
        this.materialId = parseMaterialId(raw.materialId());
        this.type = parseType(raw.type());
        this.translations = parseTranslation(raw.translations(), parseDefaultTranslation(this.name));
        this.translationKey = raw.translationKey() == null ? "croparia.crop." + this.name : raw.translationKey();
        this.color = raw.color();
        this.tier = raw.tier();
        this.tag = raw.materialId().trim().startsWith("#");
        this.blockId = CropariaIf.of("block_" + this.name);
        this.seedId = CropariaIf.of("seed_" + this.name);
        this.fruitId = CropariaIf.of("fruit_" + this.name);
    }

    private Crop(
        @NotNull String name,
        @NotNull String materialId,
        int color,
        int tier,
        @Nullable CropType type,
        @Nullable Map<String, String> translations,
        @Nullable String translationKey
    ) {
        this.name = parseName(name);
        this.materialId = parseMaterialId(materialId);
        this.color = color;
        this.tier = tier;
        this.type = type == null ? CropType.CROP : type;
        this.translations = parseTranslation(translations, parseDefaultTranslation(this.name));
        this.translationKey = translationKey == null ? "croparia.crop." + this.name : translationKey;

        this.tag = materialId.trim().startsWith("#");
        this.blockId = CropariaIf.of("block_" + this.name);
        this.seedId = CropariaIf.of("seed_" + this.name);
        this.fruitId = CropariaIf.of("fruit_" + this.name);
    }

    @NotNull
    public static Crop of(@NotNull RawCrop raw) {
        return new Crop(raw);
    }

    public static Crop create(@NotNull String name, @NotNull String materialId, int color, int tier, @NotNull CropType type, @NotNull String translationKey, @NotNull Map<String, String> translations) {
        return new Crop(name, materialId, color, tier, type, translations, translationKey);
    }

    public static Crop create(@NotNull String name, @NotNull String materialId, int color, int tier, @NotNull CropType type) {
        return new Crop(name, materialId, color, tier, type, null, null);
    }

    @NotNull
    protected static String parseName(@NotNull String name) {
        return name.trim().toLowerCase();
    }

    @NotNull
    protected static ResourceLocation parseMaterialId(@NotNull String materialId) {
        materialId = materialId.trim().toLowerCase();
        materialId = materialId.startsWith("#") ? materialId.substring(1).trim() : materialId;
        return new ResourceLocation(materialId);
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
            builder.append(Character.toUpperCase(token.charAt(0))).
                append(token.substring(1)).
                append(" ");
        }
        return builder.toString().trim();
    }

    public boolean shouldLoad(String... dependencies) {
        if (dependencies != null) {
            for (String dep : dependencies) {
                if (!Platform.isModLoaded(dep)) {
                    return false;
                }
            }
        }
        return true;
    }

    @NotNull
    public CropType getType() {
        return type;
    }

    @NotNull
    public Item getMaterialItem() {
        if (material == Items.AIR && !this.materialId.equals(Items.AIR.arch$registryName())) {
            this.initMaterial();
        }
        return material;
    }

    private void initMaterial() {
        if (this.tag) {
            TagKey<Item> tag = TagKey.create(Registries.ITEM, this.materialId);
            Iterable<Holder<Item>> set = BuiltInRegistries.ITEM.getTagOrEmpty(tag);
            if (set.iterator().hasNext()) {
                this.material = set.iterator().next().value();
            }
        } else {
            this.material = BuiltInRegistries.ITEM.get(this.materialId);
        }
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
    public Block getCropBlock() {
        return BuiltInRegistries.BLOCK.get(blockId);
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
}
