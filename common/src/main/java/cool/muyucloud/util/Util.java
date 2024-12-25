package cool.muyucloud.util;

public class Util {
    public static boolean hasNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }
}
