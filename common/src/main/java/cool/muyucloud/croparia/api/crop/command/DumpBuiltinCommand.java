package cool.muyucloud.croparia.api.crop.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.CropFileHandler;
import cool.muyucloud.croparia.registry.Crops;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class DumpBuiltinCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> DUMP_BUILTIN = Commands.literal("dumpBuiltin")
        .executes(context -> dumpAll(context.getSource()::sendSuccess, false));

    public static int dumpAll(SuccessMessage success, boolean openFile) {
        int size = Crops.size();
        MutableComponent component = new TranslatableComponent("commands.croparia.dump.perform", size);
        if (openFile) {
            component.withStyle(CommonCommandRoot.openFile(CropariaIf.CONFIG.getDumpPath().toString())).withStyle(CommonCommandRoot.blockMouseBehavior());
        }
        success.send(component, false);
        CropFileHandler.dumpBuiltinCrops();
        return size;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return DUMP_BUILTIN;
    }
}
