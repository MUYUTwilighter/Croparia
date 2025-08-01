package cool.muyucloud.croparia.api.generator.util;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Placeholder<E extends DgElement> {
    public static <E extends DgElement> Placeholder<E> of(String pattern, Function<E, String> mapper) {
        return new Placeholder<>(Pattern.compile(pattern), (matcher, element) -> mapper.apply(element));
    }

    public static <E extends DgElement> Placeholder<E> of(String pattern, BiFunction<Matcher, E, String> mapper) {
        return new Placeholder<>(Pattern.compile(pattern), mapper);
    }

    public static <E extends DgElement> Placeholder<E> of(Pattern pattern, Function<E, String> mapper) {
        return new Placeholder<>(pattern, (matcher, element) -> mapper.apply(element));
    }

    public static <E extends DgElement> Placeholder<E> of(Pattern pattern, BiFunction<Matcher, E, String> mapper) {
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
    public String mapAll(String source, DgElement element) {
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            String matched = matcher.group();
            source = source.replace(matched, mapper.apply(matcher, (E) element));
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
