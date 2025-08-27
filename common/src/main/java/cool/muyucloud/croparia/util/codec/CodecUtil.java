package cool.muyucloud.croparia.util.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.PrimitiveCodec;
import cool.muyucloud.croparia.util.FileUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

@SuppressWarnings("unused")
public class CodecUtil {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final PrimitiveCodec<Character> CHAR = new PrimitiveCodec<>() {
        @Override
        public <T> DataResult<Character> read(DynamicOps<T> ops, T input) {
            return ops.getStringValue(input).map(s -> {
                if (s.length() != 1) throw new IllegalArgumentException("Invalid char: " + s);
                return s.charAt(0);
            });
        }

        @Override
        public <T> T write(DynamicOps<T> ops, Character value) {
            return ops.createString(value.toString());
        }
    };

    @ApiStatus.Experimental
    public static <T> MapCodec<T> toMap(Codec<T> codec) {
        return codec instanceof MapCodec.MapCodecCodec<T> map ? map.codec() : MapCodec.assumeMapUnsafe(codec);
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

    public static <T> void dumpJson(T object, Codec<T> codec, Path path, boolean override) throws IOException, IllegalStateException {
        JsonElement json = encodeJson(object, codec);
        FileUtil.write(path.toFile(), json.toString(), override);
    }

    public static <T> void dumpJson(T object, MapCodec<T> codec, Path path, boolean override) throws IOException, IllegalStateException {
        dumpJson(object, codec.codec(), path, override);
    }

    public static <T> T decodeJson(JsonElement element, Codec<T> codec) {
        return codec.decode(JsonOps.INSTANCE, element).getOrThrow().getFirst();
    }

    public static <T> T decodeJson(JsonElement element, MapCodec<T> codec) {
        return decodeJson(element, codec.codec());
    }

    public static <T> T readJson(File file, Codec<T> codec) throws IOException {
        try (FileReader reader = new FileReader(file)) {
            return decodeJson(GSON.fromJson(reader, JsonElement.class), codec);
        }
    }

    public static <T> T readJson(File file, MapCodec<T> codec) throws IOException {
        return readJson(file, codec.codec());
    }
}
