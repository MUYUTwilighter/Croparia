package cool.muyucloud.croparia.api.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import cool.muyucloud.croparia.util.codec.CodecUtil;

import java.util.Map;

public interface JsonBuilder {
    static JsonObject map(Object... entries) {
        return new MapBuilder(entries).build();
    }

    static JsonObject map(Map<String, Object> compound) {
        return new MapBuilder(compound).build();
    }

    static JsonArray list(Object... list) {
        return new ListBuilder(list).build();
    }

    static JsonElement parse(Object o) {
        if (o instanceof JsonElement json) return json;
        else if (o instanceof JsonBuilder builder) return builder.build();
        else if (o instanceof Number number) return new JsonPrimitive(number);
        else if (o instanceof Character character) return new JsonPrimitive(character);
        else if (o instanceof String string) return new JsonPrimitive(string);
        else if (o instanceof Boolean bool) return new JsonPrimitive(bool);
        throw new IllegalArgumentException("Codec is required for type: " + o.getClass());
    }

    @SuppressWarnings("unused")
    static <T> JsonElement parse(T value, Codec<T> codec) {
        return CodecUtil.encodeJson(value, codec);
    }

    JsonElement build();
}
