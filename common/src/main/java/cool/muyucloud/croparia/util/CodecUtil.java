package cool.muyucloud.croparia.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class CodecUtil {
    public static <B extends FriendlyByteBuf, T> StreamCodec<B, T> toStream(Codec<T> codec) {
        return StreamCodec.of(
            (buf, inst) -> buf.writeJsonWithCodec(codec, inst),
            buf -> buf.readJsonWithCodec(codec)
        );
    }

    public static <B extends FriendlyByteBuf, T> StreamCodec<B, T> toStream(MapCodec<T> codec) {
        return toStream(codec.codec());
    }
}
