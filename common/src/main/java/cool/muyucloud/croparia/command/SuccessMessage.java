package cool.muyucloud.croparia.command;

import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public interface SuccessMessage {
    void send(Supplier<Component> msg, boolean broadcast);
}
