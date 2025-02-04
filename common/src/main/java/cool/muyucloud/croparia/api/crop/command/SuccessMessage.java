package cool.muyucloud.croparia.api.crop.command;

import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public interface SuccessMessage {
    void send(Supplier<Component> msg, boolean broadcast);
}
