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
    public static final Map<String, Map<String, String>> CACHE = new HashMap<>();

    public static void init() {
        for (Crop crop : Crops.CROPS) {
            addCrop(crop);
        }
        for (Map.Entry<String, Map<String, String>> entry : CACHE.entrySet()) {
            String lang = entry.getKey();
            ResourceLocation id = new ResourceLocation(CropariaIf.MOD_ID, lang);
            Map<String, String> map = entry.getValue();
            JsonObject json = new JsonObject();
            map.forEach(json::addProperty);
            ResourcePackHandler.INSTANCE.addLang(id, json);
        }
    }

    public static void addCrop(Crop crop) {
        String key = crop.getTranslationKey();
        for (String lang : crop.availableLangs()) {
            Map<String, String> map = CACHE.getOrDefault(lang, new HashMap<>());
            map.put(key, crop.translate(lang));
            CACHE.put(lang, map);
        }
    }
}
