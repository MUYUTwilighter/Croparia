package cool.muyucloud.croparia.client.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.util.ResourceLocationArgument;
import cool.muyucloud.croparia.util.text.Texts;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import net.minecraft.resources.ResourceLocation;

import static cool.muyucloud.croparia.api.core.command.CreateCommand.create;

public class CreateCommand {
    private static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> CREATE =
        LiteralArgumentBuilder.literal("create");
    private static final RequiredArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack, String> TYPE =
        RequiredArgumentBuilder.argument("type", StringArgumentType.word());
    private static final RequiredArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack, String> COLOR =
        RequiredArgumentBuilder.argument("color", StringArgumentType.word());
    private static final RequiredArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack, ResourceLocation> NAME =
        RequiredArgumentBuilder.argument("id", ResourceLocationArgument.id());
    private static final LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> REPLACE =
        LiteralArgumentBuilder.literal("replace");

    static {
        CREATE.requires(s -> s.hasPermission(2));
        COLOR.executes(context -> {
            ClientCommandRegistrationEvent.ClientCommandSourceStack source = context.getSource();
            if (source.arch$getPlayer() != null) {
                return create(
                    context.getSource().arch$getPlayer(),
                    null,
                    Crop.DEFAULT_TYPE,
                    StringArgumentType.getString(context, "color"),
                    Texts.success(source),
                    Texts.failure(source),
                    true, false
                );
            } else {
                Texts.failure(source, Texts.translatable("commands.croparia.crop.not_player"));
                return -1;
            }
        });
        TYPE.suggests((context, builder) -> {
            for (String type : Crop.PRESET_TYPES) {
                builder.suggest(type);
            }
            return builder.buildFuture();
        }).executes(context -> create(
            context.getSource().arch$getPlayer(),
            null,
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            Texts.success(context.getSource()),
            Texts.failure(context.getSource()),
            true, false
        ));
        NAME.executes(context -> create(
            context.getSource().arch$getPlayer(),
            ResourceLocationArgument.getId(context, "id"),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            Texts.success(context.getSource()),
            Texts.failure(context.getSource()),
            true, false
        ));
        REPLACE.executes(context -> create(
            context.getSource().arch$getPlayer(),
            ResourceLocationArgument.getId(context, "id"),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            Texts.success(context.getSource()),
            Texts.failure(context.getSource()),
            true, true
        ));
        NAME.then(REPLACE);
        TYPE.then(NAME);
        COLOR.then(TYPE);
        CREATE.then(COLOR);
    }

    public static LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> build() {
        return CREATE;
    }
}