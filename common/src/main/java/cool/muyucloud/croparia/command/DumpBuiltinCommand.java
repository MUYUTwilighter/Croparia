package cool.muyucloud.croparia.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.data.crop.CropFileHandler;
import cool.muyucloud.croparia.registry.Crops;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class DumpBuiltinCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> DUMP_BUILTIN = Commands.literal("dumpBuiltin")
        .executes(context -> dumpAll(context.getSource()::sendSuccess, false));

    public static int dumpAll(SuccessMessage success, boolean openFile) {
        int size = Crops.size();
        MutableComponent component = Component.translatable("commands.croparia.dump.perform", size);
        if (openFile) {
            component.withStyle(ServerCommandRoot.openFile(CropariaIf.CONFIG.getDumpPath().toString()));
            component.withStyle(ServerCommandRoot.blockMouseBehavior());
        }
        success.send(() -> component, false);
        CropFileHandler.dumpBuiltinCrops();
        return size;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return DUMP_BUILTIN;
    }
}
