package cool.muyucloud.croparia.client.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.shedaniel.architectury.event.events.client.ClientCommandRegistrationEvent;

import static cool.muyucloud.croparia.api.crop.command.DumpBuiltinCommand.dumpAll;

public class DumpBuiltinCommand {
    private static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> DUMP_BUILTIN =
        LiteralArgumentBuilder.literal("dumpBuiltin");

    public static LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> build() {
        DUMP_BUILTIN.executes(context -> {
            ClientCommandRegistrationEvent.ClientCommandSourceStack source = context.getSource();
            return dumpAll(source::arch$sendSuccess, true);
        });
        return DUMP_BUILTIN;
    }
}
