package cool.muyucloud.client;

import net.minecraft.client.Minecraft;

public class ClientUtil {
    public static boolean isAvailableLang(String lang) {
        return Minecraft.getInstance().getLanguageManager().getLanguages().containsKey(lang);
    }
}
