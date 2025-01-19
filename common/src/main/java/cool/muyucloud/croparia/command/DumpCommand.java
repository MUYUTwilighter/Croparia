package cool.muyucloud.croparia.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import cool.muyucloud.croparia.data.crop.Crop;
import cool.muyucloud.croparia.data.crop.CropFileHandler;
import cool.muyucloud.croparia.registry.Crops;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class DumpCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> DUMP = Commands.literal("dump").requires(s -> s.hasPermission(2)).executes(
        context -> dumpAll(context.getSource()::sendSuccess)
    ).then(Commands.argument("crop", StringArgumentType.greedyString()).suggests(
        (context, builder) -> Crops.cropSuggestions(builder.getInput(), builder.getStart())
    ).executes(context -> {
        String name = StringArgumentType.getString(context, "crop");
        return dump(name, context.getSource()::sendSuccess, context.getSource()::sendFailure);
    }));

    public static int dumpAll(SuccessMessage success) {
        int size = Crops.size();
        MutableComponent component = Component.translatable("commands.croparia.dump.perform", size);
        success.send(() -> component, true);
        CropFileHandler.dumpCrops();
        return Crops.size();
    }

    public static int dump(String name, SuccessMessage success, FailureMessage failure) {
        Crop crop = Crops.forName(name);
        if (crop == null) {
            MutableComponent component = Component.translatable("commands.croparia.dump.singular.absent", name);
            failure.send(component);
            return 0;
        }
        if (CropFileHandler.dumpCrop(crop)) {
            MutableComponent component = Component.translatable("commands.croparia.dump.singular", name);
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
