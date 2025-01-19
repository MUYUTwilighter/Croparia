package cool.muyucloud.croparia.client.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.command.ServerCommandRoot;
import cool.muyucloud.croparia.data.crop.CropFileHandler;
import cool.muyucloud.croparia.registry.Crops;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import static cool.muyucloud.croparia.command.DumpBuiltinCommand.dumpAll;

public class DumpBuiltinCommand {
    private static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> DUMP_BUILTIN =
        LiteralArgumentBuilder.literal("dumpBuiltin");

    public static LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> build() {
        DUMP_BUILTIN.executes(context -> {
            ClientCommandRegistrationEvent.ClientCommandSourceStack source = context.getSource();
            return dumpAll(source::arch$sendSuccess);
        });
        return DUMP_BUILTIN;
    }
}
