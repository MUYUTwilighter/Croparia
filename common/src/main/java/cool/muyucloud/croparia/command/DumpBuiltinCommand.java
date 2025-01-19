package cool.muyucloud.croparia.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.data.crop.CropFileHandler;
import cool.muyucloud.croparia.registry.Crops;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class DumpBuiltinCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> DUMP_BUILTIN = Commands.literal("dumpBuiltin")
        .executes(context -> {
            int size = Crops.builtinSize();
            MutableComponent component = Component.translatable("commands.croparia.dump.perform", size);
            context.getSource().sendSuccess(() -> component, false);
            CropFileHandler.dumpBuiltinCrops();
            return size;
        });

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return DUMP_BUILTIN;
    }
}
