package cool.muyucloud.croparia.util.text;

import net.minecraft.network.chat.Component;

public interface SuccessMessenger {
    void send(Component msg, boolean broadcast);
}
