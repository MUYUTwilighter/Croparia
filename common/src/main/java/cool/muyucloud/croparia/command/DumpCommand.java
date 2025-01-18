package cool.muyucloud.croparia.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.data.crop.Crop;
import cool.muyucloud.croparia.data.crop.CropFileHandler;
import cool.muyucloud.croparia.registry.Crops;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

public class DumpCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> DUMP = Commands.literal("dump")
        .requires(s -> s.hasPermission(2))
        .executes(context -> {
            int size = Crops.size();
            MutableComponent component = Component.translatable("commands.croparia.dump.perform", size);
            if (context.getSource().isPlayer()) {
                Player player = context.getSource().getPlayerOrException();
                if (player.isLocalPlayer()) {
                    component.withStyle(ServerRoot.openUrl(CropariaIf.CONFIG.getDumpPath().toString()))
                        .withStyle(ServerRoot.blockMouseBehavior());
                }
            }
            context.getSource().sendSuccess(() -> component, true);
            CropFileHandler.dumpCrops();
            return Crops.size();
        }).then(
            Commands.argument("crop", StringArgumentType.greedyString()).suggests(
                (context, builder) -> {
                    SuggestionsBuilder suggestionsBuilder = new SuggestionsBuilder(context.getInput(), builder.getStart());
                    Crops.cropNames().forEach(suggestionsBuilder::suggest);
                    return suggestionsBuilder.buildFuture();
                }
            ).executes(context -> {
                String name = StringArgumentType.getString(context, "crop");
                Crop crop = Crops.forName(name);
                if (crop == null) {
                    MutableComponent component = Component.translatable("commands.croparia.dump.singular.absent", name);
                    context.getSource().sendFailure(component);
                    return 0;
                }
                if (CropFileHandler.dumpCrop(crop)) {
                    context.getSource().sendSuccess(() -> Component.translatable(
                        "commands.croparia.dump.singular", Component.literal(name).setStyle(
                            ServerRoot.openUrl(CropariaIf.CONFIG.getDumpPath().resolve(crop.getName() + ".json").toString())
                        ).withStyle(ServerRoot.blockMouseBehavior())
                    ), true);
                    return 1;
                } else {
                    context.getSource().sendFailure(Component.translatable("commands.croparia.dump.singular.fail", name));
                    return 0;
                }
            })
        );

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return DUMP;
    }
}
