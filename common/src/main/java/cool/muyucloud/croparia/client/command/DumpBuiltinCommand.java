package cool.muyucloud.croparia.client.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.command.ServerCommandRoot;
import cool.muyucloud.croparia.data.crop.CropFileHandler;
import cool.muyucloud.croparia.registry.Crops;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class DumpBuiltinCommand {
    private static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> DUMP_BUILTIN =
        LiteralArgumentBuilder.literal("dumpBuiltin");

    public static LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> build() {
        DUMP_BUILTIN.executes(context -> {
            ClientCommandRegistrationEvent.ClientCommandSourceStack source = context.getSource();
            int size = Crops.builtinSize();
            MutableComponent component = Component.translatable("commands.croparia.dump.perform", size).withStyle(
                ClientCommandRoot.openFile(CropariaIf.CONFIG.getDumpPath().toString())
            ).withStyle(ServerCommandRoot.blockMouseBehavior());
            source.arch$sendSuccess(() -> component, false);
            CropFileHandler.dumpBuiltinCrops();
            return size;
        });
        return DUMP_BUILTIN;
    }
}
