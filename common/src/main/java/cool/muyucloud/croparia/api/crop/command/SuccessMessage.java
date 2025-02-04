package cool.muyucloud.croparia.api.crop.command;

import net.minecraft.network.chat.Component;

public interface SuccessMessage {
    void send(Component msg, boolean broadcast);
}
