package cool.muyucloud.util;

import java.util.Arrays;
import java.util.Objects;

public class Util {
    public static boolean hasNull(Object... objects) {
        return Arrays.stream(objects).anyMatch(Objects::isNull);
    }
}
