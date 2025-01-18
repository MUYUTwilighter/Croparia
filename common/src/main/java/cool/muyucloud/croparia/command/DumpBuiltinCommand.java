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
        .requires(s -> s.hasPermission(2))
        .executes(context -> {
            int size = Crops.builtinSize();
            MutableComponent component = Component.translatable("commands.croparia.dump.perform", size);
            if (context.getSource().isPlayer() && context.getSource().getPlayerOrException().isLocalPlayer()) {
                component.withStyle(ServerRoot.openUrl(CropariaIf.CONFIG.getDumpPath().toString()))
                    .withStyle(ServerRoot.blockMouseBehavior());
            }
            context.getSource().sendSuccess(() -> Component.translatable("commands.croparia.dumpBuiltin.success", size), false);
            CropFileHandler.dumpBuiltinCrops();
            return size;
        });

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return DUMP_BUILTIN;
    }
}
