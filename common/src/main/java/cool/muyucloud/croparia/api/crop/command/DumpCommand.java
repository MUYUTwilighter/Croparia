package cool.muyucloud.croparia.api.crop.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.registry.DgIterables;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.Optional;

public class DumpCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> DUMP = Commands.literal("dump").requires(s -> s.hasPermission(2)).executes(
        context -> dumpAll(context.getSource()::sendSuccess, false)
    ).then(Commands.argument("id", ResourceLocationArgument.id()).suggests(
        (context, builder) -> cool.muyucloud.croparia.registry.Crops.cropSuggestions(builder)
    ).executes(context -> {
        ResourceLocation id = ResourceLocationArgument.getId(context, "id");
        return dump(id, context.getSource()::sendSuccess, context.getSource()::sendFailure, false);
    }));

    public static int dumpAll(SuccessMessage success, boolean openFile) {
        int size = DgIterables.CROPS.size();
        MutableComponent component = Component.translatable("commands.croparia.dump.perform", size);
        if (openFile) {
            component.withStyle(CommonCommandRoot.openFile(CropariaIf.CONFIG.getDumpPath().toString()));
            component.withStyle(CommonCommandRoot.blockMouseBehavior());
        }
        success.send(() -> component, true);
        DgIterables.CROPS.dumpCrops();
        return size;
    }

    public static int dump(ResourceLocation id, SuccessMessage success, FailureMessage failure, boolean openFile) {
        Optional<Crop> optional = DgIterables.CROPS.forName(id);
        if (optional.isEmpty()) {
            MutableComponent component = Component.translatable("commands.croparia.dump.singular.absent", id);
            failure.send(component);
            return 0;
        }
        Path dumped = DgIterables.CROPS.dumpCrop(optional.get());
        if (dumped != null) {
            MutableComponent nameComponent = Component.literal(id.toString());
            if (openFile) {
                nameComponent.withStyle(CommonCommandRoot.openFile(dumped.toString()));
            }
            MutableComponent component = Component.translatable("commands.croparia.dump.singular", id);
            success.send(() -> component, true);
            return 1;
        } else {
            failure.send(Component.translatable("commands.croparia.dump.singular.fail", id));
            return 0;
        }
    }

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return DUMP;
    }
}
