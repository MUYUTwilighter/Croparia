package cool.muyucloud.croparia.api.generator.util;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Placeholder<E> {
    public static <E> Placeholder<E> of(String regex, Function<E, String> mapper) {
        return new Placeholder<>(Pattern.compile(regex), (matcher, element) -> mapper.apply(element));
    }

    public static <E> Placeholder<E> of(String regex, BiFunction<Matcher, E, String> mapper) {
        return new Placeholder<>(Pattern.compile(regex), mapper);
    }

    public static <E> Placeholder<E> of(Pattern pattern, Function<E, String> mapper) {
        return new Placeholder<>(pattern, (matcher, element) -> mapper.apply(element));
    }

    public static <E> Placeholder<E> of(Pattern pattern, BiFunction<Matcher, E, String> mapper) {
        return new Placeholder<>(pattern, mapper);
    }

    private final Pattern pattern;
    private final BiFunction<Matcher, E, String> mapper;

    public Placeholder(Pattern pattern, BiFunction<Matcher, E, String> mapper) {
        this.pattern = pattern;
        this.mapper = mapper;
    }

    public Pattern getPattern() {
        return pattern;
    }

    @SuppressWarnings("unchecked")
    public String mapAll(String source, Object element) {
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            String matched = matcher.group();
            String mapped = mapper.apply(matcher, (E) element);
            if (mapped == null) continue;
            source = source.replace(matched, mapped);
        }
        return source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Placeholder<?> that)) return false;
        return Objects.equals(pattern, that.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pattern);
    }
}
