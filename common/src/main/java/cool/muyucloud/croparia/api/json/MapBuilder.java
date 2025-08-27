package cool.muyucloud.croparia.api.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import cool.muyucloud.croparia.util.codec.CodecUtil;

import java.util.Map;

@SuppressWarnings("unused")
public class MapBuilder implements JsonBuilder {
    private final JsonObject json = new JsonObject();

    public MapBuilder(Object... entries) {
        for (int i = 0; i < entries.length; i += 2) {
            json.add((String) entries[i], JsonBuilder.parse(entries[i + 1]));
        }
    }

    public MapBuilder(Map<String, Object> map) {
        this.with(map);
    }

    public JsonObject build() {
        return json;
    }

    public MapBuilder with(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.add(entry.getKey(), JsonBuilder.parse(entry.getValue()));
        }
        return this;
    }

    public MapBuilder with(String key, JsonElement value) {
        json.add(key, value);
        return this;
    }

    public MapBuilder with(String key, JsonBuilder builder) {
        return with(key, builder.build());
    }

    public MapBuilder with(String key, Number value) {
        json.addProperty(key, value);
        return this;
    }

    public MapBuilder with(String key, String value) {
        json.addProperty(key, value);
        return this;
    }

    public MapBuilder with(String key, boolean value) {
        json.addProperty(key, value);
        return this;
    }

    public MapBuilder with(String key, Character value) {
        json.addProperty(key, value);
        return this;
    }

    public <T> MapBuilder with(String key, T value, Codec<T> codec) {
        json.add(key, CodecUtil.encodeJson(value, codec));
        return this;
    }
}
