package cool.muyucloud.croparia.api.json;

import com.google.gson.JsonArray;
import com.mojang.serialization.Codec;
import cool.muyucloud.croparia.util.codec.CodecUtil;

public class ListBuilder implements JsonBuilder {
    private final JsonArray json = new JsonArray();

    public ListBuilder(Object... list) {
        for (Object e : list) json.add(JsonBuilder.parse(e));
    }

    @Override
    public JsonArray build() {
        return json;
    }

    public ListBuilder add(Object... list) {
        for (Object e : list) json.add(JsonBuilder.parse(e));
        return this;
    }

    @SafeVarargs
    public final <T> ListBuilder add(Codec<T> codec, T... value) {
        for (T e : value) json.add(CodecUtil.encodeJson(e, codec));
        return this;
    }
}
