package cool.muyucloud.croparia.util;

import com.google.gson.JsonElement;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.PrimitiveCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class CodecUtil {
    public static final PrimitiveCodec<Character> CHAR = new PrimitiveCodec<>() {
        @Override
        public <T> DataResult<Character> read(DynamicOps<T> ops, T input) {
            return ops.getStringValue(input).map(s -> s.charAt(0));
        }

        @Override
        public <T> T write(DynamicOps<T> ops, Character value) {
            return ops.createString(value.toString());
        }
    };

    public static <T> MapCodec<T> toMap(Codec<T> codec) {
        if (codec instanceof MapCodec.MapCodecCodec<T> codecCodec) return codecCodec.codec();
        return new SingletonMapCodec<>(codec);
    }

    public static <B extends FriendlyByteBuf, T> StreamCodec<B, T> toStream(Codec<T> codec) {
        return StreamCodec.of(
            (buf, inst) -> buf.writeJsonWithCodec(codec, inst),
            buf -> buf.readJsonWithCodec(codec)
        );
    }

    public static <B extends FriendlyByteBuf, T> StreamCodec<B, T> toStream(MapCodec<T> codec) {
        return toStream(codec.codec());
    }

    public static <T> JsonElement encodeJson(T object, Codec<T> codec) {
        return codec.encodeStart(JsonOps.INSTANCE, object).getOrThrow();
    }

    public static <T> JsonElement encodeJson(T object, MapCodec<T> codec) {
        return encodeJson(object, codec.codec());
    }

    public static <T> T decodeJson(JsonElement element, Codec<T> codec) {
        return codec.decode(JsonOps.INSTANCE, element).getOrThrow().getFirst();
    }

    public static <T> T decodeJson(JsonElement element, MapCodec<T> codec) {
        return decodeJson(element, codec.codec());
    }
}
