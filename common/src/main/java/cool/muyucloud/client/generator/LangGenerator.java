package cool.muyucloud.client.generator;

import com.google.gson.JsonObject;
import cool.muyucloud.CropariaIf;
import cool.muyucloud.data.crop.Crop;
import cool.muyucloud.registry.Crops;
import cool.muyucloud.util.pack.ResourcePackHandler;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class LangGenerator {
    public static final Map<ResourceLocation, Map<String, String>> CACHE = new HashMap<>();

    public static void init() {
        for (Crop crop : Crops.CROPS) {
            addCrop(crop);
        }
        for (Map.Entry<ResourceLocation, Map<String, String>> entry : CACHE.entrySet()) {
            ResourceLocation lang = entry.getKey();
            Map<String, String> map = entry.getValue();
            JsonObject json = new JsonObject();
            map.forEach(json::addProperty);
            ResourcePackHandler.INSTANCE.addLang(lang, json);
        }
    }

    public static void addCrop(Crop crop) {
        String key = crop.getTranslationKey();
        for (String lang : crop.availableLangs()) {
            ResourceLocation id = new ResourceLocation(CropariaIf.MOD_ID, lang);
            Map<String, String> map = CACHE.getOrDefault(id, new HashMap<>());
            map.put(key, crop.translate(lang));
            CACHE.put(id, map);
        }
    }
}
