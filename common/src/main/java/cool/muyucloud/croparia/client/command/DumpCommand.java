package cool.muyucloud.croparia.client.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.command.ServerCommandRoot;
import cool.muyucloud.croparia.data.crop.Crop;
import cool.muyucloud.croparia.data.crop.CropFileHandler;
import cool.muyucloud.croparia.registry.Crops;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class DumpCommand {
    private static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> DUMP =
        LiteralArgumentBuilder.literal("dump");
    private static final RequiredArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack, String> CROP =
        RequiredArgumentBuilder.argument("crop", StringArgumentType.greedyString());

    public static LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> build() {
        CROP.executes(context -> {
            String name = StringArgumentType.getString(context, "crop");
            Crop crop = Crops.forName(name);
            if (crop == null) {
                MutableComponent component = Component.translatable("commands.croparia.dump.singular.absent", name);
                context.getSource().arch$sendFailure(component);
                return 0;
            }
            if (CropFileHandler.dumpCrop(crop)) {
                MutableComponent component = Component.translatable("commands.croparia.dump.singular", Component.literal(name).withStyle(
                    ClientCommandRoot.openFile(CropariaIf.CONFIG.getDumpPath().resolve(crop.getName() + ".json").toString())
                ).withStyle(ServerCommandRoot.inlineMouseBehavior()));
                context.getSource().arch$sendSuccess(() -> component, true);
                return 1;
            } else {
                context.getSource().arch$sendFailure(Component.translatable("commands.croparia.dump.singular.fail", name));
                return 0;
            }
        });
        DUMP.requires(s -> s.hasPermission(2));
        DUMP.executes(context -> {
            int size = Crops.size();
            MutableComponent component = Component.translatable("commands.croparia.dump.perform", size).withStyle(
                ClientCommandRoot.openFile(CropariaIf.CONFIG.getDumpPath().toString())
            ).withStyle(ServerCommandRoot.blockMouseBehavior());
            context.getSource().arch$sendSuccess(() -> component, false);
            CropFileHandler.dumpCrops();
            return size;
        });
        DUMP.then(CROP);
        return DUMP;
    }
}
