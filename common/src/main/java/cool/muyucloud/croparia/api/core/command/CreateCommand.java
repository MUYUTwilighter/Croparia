package cool.muyucloud.croparia.api.core.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.item.Croparia;
import cool.muyucloud.croparia.api.crop.util.Color;
import cool.muyucloud.croparia.api.crop.util.CropDependencies;
import cool.muyucloud.croparia.api.crop.util.Material;
import cool.muyucloud.croparia.registry.DgRegistries;
import cool.muyucloud.croparia.util.text.FailureMessenger;
import cool.muyucloud.croparia.util.text.SuccessMessenger;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Objects;

public class CreateCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> CREATE = Commands.literal("create");
    private static final RequiredArgumentBuilder<CommandSourceStack, String> TYPE = RequiredArgumentBuilder.argument(
        "type", StringArgumentType.word()
    );
    private static final RequiredArgumentBuilder<CommandSourceStack, String> COLOR = RequiredArgumentBuilder.argument(
        "color", StringArgumentType.word()
    );
    private static final RequiredArgumentBuilder<CommandSourceStack, String> NAME = RequiredArgumentBuilder.argument(
        "name", StringArgumentType.word()
    );
    private static final LiteralArgumentBuilder<CommandSourceStack> REPLACE = LiteralArgumentBuilder.literal("replace");

    static {
        CREATE.requires(s -> s.hasPermission(2));
        COLOR.executes(context -> create(
            context.getSource().getPlayerOrException(),
            null,
            Crop.DEFAULT_TYPE,
            StringArgumentType.getString(context, "color"),
            Texts.success(context.getSource()),
            Texts.failure(context.getSource()),
            false, false
        ));
        TYPE.suggests((context, builder) -> {
            for (String type : Crop.PRESET_TYPES) {
                builder.suggest(type);
            }
            return builder.buildFuture();
        }).executes(context -> create(
            context.getSource().getPlayerOrException(),
            null,
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            Texts.success(context.getSource()),
            Texts.failure(context.getSource()),
            false, false
        ));
        NAME.executes(context -> create(
            context.getSource().getPlayerOrException(),
            ResourceLocationArgument.getId(context, "id"),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            Texts.success(context.getSource()),
            Texts.failure(context.getSource()),
            false, false
        ));
        REPLACE.executes(context -> create(
            context.getSource().getPlayerOrException(),
            ResourceLocationArgument.getId(context, "id"),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            Texts.success(context.getSource()),
            Texts.failure(context.getSource()),
            false, true
        ));
        NAME.then(REPLACE);
        TYPE.then(NAME);
        COLOR.then(TYPE);
        CREATE.then(COLOR);
    }

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return CREATE;
    }

    public static int create(Player player, @Nullable ResourceLocation id, String type, String rawColor, SuccessMessenger success, FailureMessenger failure, boolean client, boolean forced) {
        ItemStack material = player.getMainHandItem();
        if (material.isEmpty()) {
            failure.send(Texts.translatable("commands.croparia.create.no_material"));
            return -1;
        }
        Item rawCroparia = player.getOffhandItem().getItem();
        id = id == null ? Objects.requireNonNull(material.getItem().arch$registryName()) : id;
        Color color;
        try {
            color = new Color(rawColor);
        } catch (NumberFormatException e) {
            failure.send(Texts.translatable("commands.croparia.create.invalid_color", rawColor));
            return -1;
        }
        if (!forced && DgRegistries.CROPS.exists(id)) {
            MutableComponent crop = Texts.literal(id.toString());
            crop.withStyle(Texts.runCommand(CommonCommandRoot.commandRoot(client), "crop", id.toString()))
                .withStyle(Texts.inlineMouseBehavior());
            MutableComponent rename = Texts.translatable("commands.croparia.create.duplicated.rename")
                .withStyle(Texts.suggestCommand(CommonCommandRoot.commandRoot(client), "create", rawColor, type, id + "_"))
                .withStyle(Texts.inlineMouseBehavior());
            MutableComponent replace = Texts.translatable("commands.croparia.create.duplicated.replace")
                .withStyle(Texts.suggestCommand(CommonCommandRoot.commandRoot(client), "create", rawColor, type, id.toString(), "replace"))
                .withStyle(Texts.inlineMouseBehavior());
            MutableComponent duplication = Texts.translatable("commands.croparia.create.duplicated", crop, rename, replace);
            failure.send(duplication);
            return -1;
        }
        if (rawCroparia instanceof Croparia croparia) {
            Crop crop = buildCrop(id, material, color, croparia.getTier(), type);
            Path result = DgRegistries.CROPS.dumpCrop(crop);
            MutableComponent resultComponent = Texts.literal(result.toString());
            if (client) {
                resultComponent.withStyle(Texts.openFile(result.toString())).withStyle(Texts.inlineMouseBehavior());
            }
            success.send(Texts.translatable("commands.croparia.create.success", resultComponent), true);
            return croparia.getTier();
        } else {
            failure.send(Texts.translatable("commands.croparia.create.invalid_croparia"));
            return -1;
        }
    }

    public static Crop buildCrop(ResourceLocation id, ItemStack material, Color color, int tier, String type) {
        return new Crop(id, new Material(material), color, tier, type, null, new CropDependencies(id.getNamespace(), material.getItem().getDescriptionId()));
    }
}