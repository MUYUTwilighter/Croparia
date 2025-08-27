package cool.muyucloud.croparia.util.codec;

import com.mojang.serialization.Codec;

import java.util.List;

/**
 * A list codec that can generically handle a single element or a collection.
 */
@SuppressWarnings("unused")
public class GenericListCodec<T> extends AnyCodec<List<T>> {
    public static <T> GenericListCodec<T> of(Codec<T> codec) {
        return new GenericListCodec<>(codec);
    }

    public GenericListCodec(Codec<T> codec) {
        super(codec.listOf(), codec.xmap(List::of, List::getFirst));
    }

    /**
     * Get the codec that only handles a single element
     */
    public Codec<List<T>> singleton() {
        return this.getCodec(1);
    }

    /**
     * Get the codec that handles an array of elements
     */
    public Codec<List<T>> collection() {
        return this.getCodec(0);
    }
}
