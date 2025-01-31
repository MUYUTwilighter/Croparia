package cool.muyucloud.croparia.api.crop.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.CropFileHandler;
import cool.muyucloud.croparia.api.crop.Crops;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class DumpCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> DUMP = Commands.literal("dump").requires(s -> s.hasPermission(2)).executes(context -> dumpAll(context.getSource()::sendSuccess, false)).then(Commands.argument("crop", StringArgumentType.greedyString()).suggests((context, builder) -> Crops.suggestCrops(builder.getInput(), builder.getStart())).executes(context -> {
        String name = StringArgumentType.getString(context, "crop");
        return dump(name, context.getSource()::sendSuccess, context.getSource()::sendFailure, false);
    }));

    public static int dumpAll(SuccessMessage success, boolean openFile) {
        int size = Crops.size();
        MutableComponent component = Component.translatable("commands.croparia.dump.perform", size);
        if (openFile) {
            component.withStyle(ServerCommandRoot.openFile(CropariaIf.CONFIG.getDumpPath().toString())).withStyle(ServerCommandRoot.blockMouseBehavior());
        }
        success.send(() -> component, true);
        CropFileHandler.dumpCrops();
        return Crops.size();
    }

    public static int dump(String name, SuccessMessage success, FailureMessage failure, boolean openFile) {
        Crop crop = Crops.forName(name);
        if (crop == null) {
            MutableComponent component = Component.translatable("commands.croparia.dump.singular.absent", name);
            failure.send(component);
            return 0;
        }
        if (CropFileHandler.dumpCrop(crop)) {
            MutableComponent nameComponent = Component.literal(name);
            if (openFile) {
                nameComponent.withStyle(
                    ServerCommandRoot.openFile(CropariaIf.CONFIG.getDumpPath().resolve(name + ".json").toString())
                ).withStyle(ServerCommandRoot.blockMouseBehavior());
            }
            MutableComponent component = Component.translatable("commands.croparia.dump.singular", nameComponent);
            success.send(() -> component, true);
            return 1;
        } else {
            failure.send(Component.translatable("commands.croparia.dump.singular.fail", name));
            return 0;
        }
    }

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return DUMP;
    }
}
