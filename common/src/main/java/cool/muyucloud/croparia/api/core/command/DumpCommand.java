package cool.muyucloud.croparia.api.core.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.registry.DgRegistries;
import cool.muyucloud.croparia.util.text.FailureMessenger;
import cool.muyucloud.croparia.util.text.SuccessMessenger;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.Optional;

public class DumpCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> DUMP = Commands.literal("dump").requires(s -> s.hasPermission(2)).executes(
        context -> dumpAll((msg, broadcast) -> Texts.success(context.getSource(), msg, broadcast), false)
    ).then(Commands.argument("id", ResourceLocationArgument.id()).suggests(
        (context, builder) -> cool.muyucloud.croparia.registry.Crops.cropSuggestions(builder)
    ).executes(context -> {
        ResourceLocation id = ResourceLocationArgument.getId(context, "id");
        return dump(id, Texts.success(context.getSource()), Texts.failure(context.getSource()), false);
    }));

    public static int dumpAll(SuccessMessenger success, boolean openFile) {
        int size = DgRegistries.CROPS.size();
        MutableComponent component = Texts.translatable("commands.croparia.dump.perform", size);
        if (openFile) {
            component.withStyle(Texts.openFile(CropariaIf.CONFIG.getFilePath().resolve("crops").toString()));
            component.withStyle(Texts.blockMouseBehavior());
        }
        success.send(component, true);
        DgRegistries.CROPS.dumpCrops();
        return size;
    }

    public static int dump(ResourceLocation id, SuccessMessenger success, FailureMessenger failure, boolean openFile) {
        Optional<Crop> optional = DgRegistries.CROPS.forName(id);
        if (optional.isEmpty()) {
            MutableComponent component = Texts.translatable("commands.croparia.dump.singular.absent", id);
            failure.send(component);
            return 0;
        }
        Path dumped = DgRegistries.CROPS.dumpCrop(optional.get());
        if (dumped != null) {
            MutableComponent nameComponent = Texts.literal(id.toString());
            if (openFile) {
                nameComponent.withStyle(Texts.openFile(dumped.toString()));
            }
            MutableComponent component = Texts.translatable("commands.croparia.dump.singular", id);
            success.send(component, true);
            return 1;
        } else {
            failure.send(Texts.translatable("commands.croparia.dump.singular.fail", id));
            return 0;
        }
    }

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return DUMP;
    }
}
