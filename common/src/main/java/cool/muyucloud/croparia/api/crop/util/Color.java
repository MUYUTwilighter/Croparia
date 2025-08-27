package cool.muyucloud.croparia.api.crop.util;

import com.mojang.serialization.Codec;
import cool.muyucloud.croparia.util.codec.AnyCodec;
import net.minecraft.network.chat.Style;

import java.util.Locale;

public class Color {
    public static final Codec<Color> CODEC_INT = Codec.INT.xmap(Color::new, Color::getValue);
    public static final Codec<Color> CODEC_STR = Codec.STRING.xmap(Color::new, Color::toString);
    public static final Codec<Color> CODEC = new AnyCodec<>(CODEC_STR, CODEC_INT);

    public static Color of(int value) {
        return new Color(value);
    }

    public static Color of(String format) {
        return new Color(format);
    }

    private final int value;

    public Color(int value) {
        this.value = value;
    }

    public Color(String format) {
        if (format.startsWith("#")) this.value = Integer.parseInt(format.substring(1), 16);
        else if (format.startsWith("0x")) this.value = Integer.parseInt(format.substring(2), 16);
        else this.value = Integer.parseInt(format) | 0xFF000000;
    }

    public int getValue() {
        return this.value;
    }

    public Style apply(Style style) {
        return style.withColor(this.getValue());
    }

    public String toString() {
        return String.format("#%08x", this.getValue()).toLowerCase(Locale.ROOT);
    }

    public String toHexString() {
        return String.format("%08x", this.getValue()).toUpperCase(Locale.ROOT);
    }

    public String toDecString() {
        return String.format("%d", this.getValue());
    }
}
