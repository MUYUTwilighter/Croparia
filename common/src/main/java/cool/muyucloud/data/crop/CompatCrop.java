package cool.muyucloud.data.crop;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CompatCrop {
    public static final Codec<CompatCrop> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("name").forGetter(CompatCrop::getName),
        Codec.STRING.fieldOf("material").forGetter(CompatCrop::getMaterialString),
        Codec.STRING.fieldOf("type").forGetter(CompatCrop::getTypeString),
        Codec.STRING.fieldOf("color").forGetter(CompatCrop::getColorString),
        Codec.INT.fieldOf("tier").forGetter(CompatCrop::getTier),
        Codec.STRING.optionalFieldOf("translation_key").forGetter(CompatCrop::getTranslationKey),
        Codec.STRING.listOf().listOf().optionalFieldOf("dependencies").forGetter(CompatCrop::getDependencies)
    ).apply(instance, (name, material, rawType, rawColor, tier, optionalTranslationKey, optionalDependencies) -> {
        CropType type = CropType.valueOf(rawType.toUpperCase());
        int color = rawColor.startsWith("0x") ? Integer.parseInt(rawColor.substring(2), 16) :
            rawColor.startsWith("#") ? Integer.parseInt(rawColor.substring(1), 16) :
                Integer.parseInt(rawColor);
        Map<String, String> translationKeys = new HashMap<>();
        optionalTranslationKey.ifPresent(
            translationKey -> optionalDependencies.flatMap(
                dependencies -> dependencies.stream().findFirst().flatMap(mods -> mods.stream().findFirst())
            ).ifPresent(mod -> translationKeys.put(mod, translationKey))
        );
        return new CompatCrop(name, material, type, color, tier, translationKeys);
    }));

    private final String name;
    private final String material;
    private final CropType type;
    private final int color;
    private final int tier;
    private final Map<String, String> translationKeys = new HashMap<>();

    public CompatCrop(String name, String material, CropType type, int color, int tier, Map<String, String> translationKeys) {
        this.name = name;
        this.material = material;
        this.type = type;
        this.color = color;
        this.tier = tier;
        this.translationKeys.putAll(translationKeys);
    }

    public CompatCrop merge(String mod, String key) {
        translationKeys.put(mod, key);
        return this;
    }

    public String getName() {
        return name;
    }

    public String getMaterialString() {
        return material;
    }

    public String getTypeString() {
        return type.getModelName();
    }

    public String getColorString() {
        return "0x" + Integer.toHexString(color);
    }

    public int getTier() {
        return tier;
    }

    public Optional<String> getTranslationKey() {
        return translationKeys.values().stream().findFirst();
    }

    public Optional<List<List<String>>> getDependencies() {
        if (translationKeys.isEmpty()) return Optional.empty();
        else return Optional.of(List.of(this.translationKeys.keySet().stream().toList()));
    }
}
