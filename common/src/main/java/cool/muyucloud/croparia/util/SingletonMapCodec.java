package cool.muyucloud.croparia.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;

import java.util.stream.Stream;

/**
 * A codec that encodes a value to a map with a single key-value pair ("value" : value) and decodes the value from the map.
 *
 * @implNote This codec is not equivalent to the wrapped {@link #codec}, the result & input is different from each other
 * when decoding and encoding.
 */
public class SingletonMapCodec<T> extends MapCodec<T> {
    private final Codec<T> codec;

    public SingletonMapCodec(Codec<T> codec) {
        this.codec = codec;
    }

    @Override
    public <I> Stream<I> keys(DynamicOps<I> ops) {
        return Stream.of(ops.createString("value"));
    }

    @Override
    public <I> DataResult<T> decode(DynamicOps<I> ops, MapLike<I> input) {
        I value = input.get("value");
        return codec.decode(ops, value).map(Pair::getFirst);
    }

    @Override
    public <I> RecordBuilder<I> encode(T input, DynamicOps<I> ops, RecordBuilder<I> prefix) {
        return prefix.add("value", codec.encodeStart(ops, input));
    }

    @Override
    public Codec<T> codec() {
        return codec;
    }
}
