package cool.muyucloud.croparia.api.crop.command;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.CropFileHandler;
import cool.muyucloud.croparia.api.crop.CropType;
import cool.muyucloud.croparia.api.crop.item.Croparia;
import cool.muyucloud.croparia.registry.Crops;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.Objects;

public class CreateCommand {
    private static final LiteralArgumentBuilder<CommandSourceStack> CREATE = Commands.literal("create");
    private static final LiteralArgumentBuilder<CommandSourceStack> CREATE_OVERRIDE = Commands.literal("forceCreate");

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        RequiredArgumentBuilder<CommandSourceStack, String> typeArg = RequiredArgumentBuilder.argument("type", StringArgumentType.word());
        RequiredArgumentBuilder<CommandSourceStack, String> colorArg = RequiredArgumentBuilder.argument("color", StringArgumentType.word());
        typeArg.suggests((context, builder) -> {
            for (CropType type : CropType.values()) {
                builder.suggest(type.getModelName());
            }
            return builder.buildFuture();
        }).executes(context -> create(
            context.getSource().getPlayerOrException(),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
            false, false
        ));
        colorArg.executes(context -> create(
            context.getSource().getPlayerOrException(),
            CropType.CROP.getModelName(),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
            false, false
        ));
        return CREATE.requires(s -> s.hasPermission(2)).then(colorArg.then(typeArg));
    }

    public static LiteralArgumentBuilder<CommandSourceStack> buildOverride() {
        RequiredArgumentBuilder<CommandSourceStack, String> typeArg = RequiredArgumentBuilder.argument("type", StringArgumentType.word());
        RequiredArgumentBuilder<CommandSourceStack, String> colorArg = RequiredArgumentBuilder.argument("color", StringArgumentType.word());
        typeArg.suggests((context, builder) -> {
            for (CropType type : CropType.values()) {
                builder.suggest(type.getModelName());
            }
            return builder.buildFuture();
        }).executes(context -> create(
            context.getSource().getPlayerOrException(),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
            false, true
        ));
        colorArg.executes(context -> create(
            context.getSource().getPlayerOrException(),
            CropType.CROP.getModelName(),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
            false, true
        ));
        return CREATE_OVERRIDE.requires(s -> s.hasPermission(2)).then(colorArg.then(typeArg));
    }

    public static int create(
        Player player, String rawType, String color, SuccessMessage success, FailureMessage failure,
        boolean openFile, boolean force
    ) {
        CropType type;
        try {
            type = CropType.valueOf(rawType.toUpperCase());
        } catch (IllegalArgumentException e) {
            failure.send(Component.translatable("commands.croparia.create.invalid_type", rawType));
            return -1;
        }
        ItemStack main = player.getMainHandItem();
        if (main.isEmpty()) {
            failure.send(Component.translatable("commands.croparia.create.no_material"));
        }
        Item material = main.getItem();
        String name = Objects.requireNonNull(material.arch$registryName()).getPath();
        if (force && (CropFileHandler.containsFile(name) || Crops.containsCrop(name))) {
            MutableComponent overrideComponent = Component.translatable("commands.croparia.create.force")
                .withStyle(ServerCommandRoot.suggestCommand(
                    "/croparia" + (openFile ? "" : "Server") + " createOverride %s %s".formatted(rawType, color)
                ));
            failure.send(Component.translatable("commands.croparia.create.exists", name, overrideComponent));
            return -1;
        }
        Item rawCroparia = player.getOffhandItem().getItem();
        if (rawCroparia instanceof Croparia croparia) {
            JsonObject built = buildCrop(material, color, croparia.getTier(), type);
            Path result = dump(built);
            MutableComponent resultComponent = Component.literal(result.toString());
            if (openFile) {
                resultComponent.withStyle(ServerCommandRoot.openFile(result.toString())).withStyle(ServerCommandRoot.inlineMouseBehavior());
            }
            success.send(() -> Component.translatable("commands.croparia.create.success", resultComponent), true);
            return croparia.getTier();
        } else {
            failure.send(Component.translatable("commands.croparia.create.invalid_croparia"));
            return -1;
        }
    }

    public static JsonObject buildCrop(Item material, String color, int tier, CropType type) {
        String name = Objects.requireNonNull(material.arch$registryName()).getPath();
        String materialId = Objects.requireNonNull(material.arch$registryName()).toString();
        String translationKey = material.getDescriptionId();
        String dependency = Objects.requireNonNull(material.arch$registryName()).getNamespace();
        JsonObject root = new JsonObject();
        root.addProperty("name", name);
        root.addProperty("material", materialId);
        root.addProperty("color", color);
        root.addProperty("tier", tier);
        root.addProperty("type", type.getModelName());
        root.addProperty("translationKey", translationKey);
        JsonArray inner = new JsonArray();
        inner.add(dependency);
        JsonArray outer = new JsonArray();
        outer.add(inner);
        root.add("dependency", outer);
        return root;
    }

    public static Path dump(JsonObject built) {
        Path parent = CropariaIf.CONFIG.getCropPath();
        if (!parent.toFile().isDirectory() && !parent.toFile().mkdirs()) {
            throw new IllegalStateException("Failed to establish directory \"%s\"".formatted(parent));
        }
        Path location = parent.resolve(built.get("name").getAsString() + ".json");
        try (JsonWriter writer = new JsonWriter(new FileWriter(location.toFile()))) {
            writer.setIndent("  ");
            new Gson().toJson(built, writer);
            return location;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}