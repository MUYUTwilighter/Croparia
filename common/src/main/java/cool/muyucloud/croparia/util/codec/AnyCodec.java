package cool.muyucloud.croparia.util.codec;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * Codec that tries to decode using any of the provided codecs, and encode using the first one.
 */
public class AnyCodec<T> implements Codec<T>, Iterable<Codec<T>> {
    @SafeVarargs
    public static <T> AnyCodec<T> of(Codec<T>... codecs) {
        return new AnyCodec<>(codecs);
    }

    private final Codec<T>[] codecs;
    private final Function<List<String>, String> onError;

    @SafeVarargs
    public AnyCodec(Codec<T>... codecs) {
        this(logs -> {
            StringBuilder builder = new StringBuilder("Failed to decode with any of the provided codecs:");
            for (String log : logs) {
                builder.append("\n- ").append(log);
            }
            return builder.toString();
        }, codecs);
    }

    @SafeVarargs
    public AnyCodec(@NotNull Function<List<String>, String> onError, Codec<T>... codecs) {
        if (codecs.length == 0) {
            throw new IllegalArgumentException("At least one codec is required");
        }
        this.codecs = Arrays.copyOf(codecs, codecs.length);
        this.onError = onError;
    }

    @Override
    public <O> DataResult<Pair<T, O>> decode(DynamicOps<O> ops, O input) {
        ArrayList<String> logs = new ArrayList<>(this.length());
        for (Codec<T> codec : this) {
            DataResult<Pair<T, O>> result = codec.decode(ops, input);
            if (result.result().isPresent()) {
                return result;
            }
            logs.add(result.error().orElseThrow().message());
        }
        return DataResult.error(() -> this.error(logs));
    }

    @Override
    public <I> DataResult<I> encode(T input, DynamicOps<I> ops, I prefix) {
        return codecs[0].encode(input, ops, prefix);
    }

    public Codec<T> getCodec(int i) throws IndexOutOfBoundsException {
        return codecs[i];
    }

    @NotNull
    @Override
    public Iterator<Codec<T>> iterator() {
        return Arrays.stream(codecs).iterator();
    }

    public int length() {
        return codecs.length;
    }

    public String error(List<String> logs) {
        return this.onError.apply(logs);
    }

    @Override
    public <S> Codec<S> xmap(Function<? super T, ? extends S> to, Function<? super S, ? extends T> from) {
        return Codec.super.xmap(to, from);
    }
}
