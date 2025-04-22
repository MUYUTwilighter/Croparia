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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.io.FileWriter;
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
            CropType.CROP.getModelName(),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
            false, false
        ));
        TYPE.suggests((context, builder) -> {
            for (CropType type : CropType.values()) {
                builder.suggest(type.getModelName());
            }
            return builder.buildFuture();
        }).executes(context -> create(
            context.getSource().getPlayerOrException(),
            null,
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
            false, false
        ));
        NAME.executes(context -> create(
            context.getSource().getPlayerOrException(),
            StringArgumentType.getString(context, "name"),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
            false, false
        ));
        REPLACE.executes(context -> create(
            context.getSource().getPlayerOrException(),
            StringArgumentType.getString(context, "name"),
            StringArgumentType.getString(context, "type"),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
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

    public static int create(Player player, String name, String rawType, String color, SuccessMessage success, FailureMessage failure, boolean client, boolean replaced) {
        CropType type;
        try {
            type = CropType.valueOf(rawType.toUpperCase());
        } catch (IllegalArgumentException e) {
            failure.send(new TranslatableComponent("commands.croparia.create.invalid_type", rawType));
            return -1;
        }
        ItemStack main = player.getMainHandItem();
        if (main.isEmpty()) {
            failure.send(new TranslatableComponent("commands.croparia.create.no_material"));
        }
        Item material = main.getItem();
        Item rawCroparia = player.getOffhandItem().getItem();
        name = name == null ? Objects.requireNonNull(material.arch$registryName()).getPath() : name;
        if (ResourceLocation.tryParse(name) == null) {
            failure.send(new TranslatableComponent("commands.croparia.create.invalid_name", name));
            return -1;
        }
        try {
            if (color.startsWith("#")) {
                Integer.parseInt(color.substring(1), 16);
            } else if (color.startsWith("0x")) {
                Integer.parseInt(color.substring(2), 16);
            } else {
                Integer.parseInt(color, 10);
            }
        } catch (NumberFormatException e) {
            failure.send(new TranslatableComponent("commands.croparia.create.invalid_color", color));
            return -1;
        }
        if (!replaced && (Crops.containsCrop(name) || CropFileHandler.containsFile(name))) {
            MutableComponent crop = new TextComponent(name);
            if (Crops.containsCrop(name)) {
                crop.withStyle(CommonCommandRoot.runCommand(CommonCommandRoot.commandRoot(client), "crop", name))
                    .withStyle(CommonCommandRoot.inlineMouseBehavior());
            }
            MutableComponent rename = new TranslatableComponent("commands.croparia.create.duplicated.rename", name)
                .withStyle(CommonCommandRoot.suggestCommand(CommonCommandRoot.commandRoot(client), "create", color, rawType, name + "_"))
                .withStyle(CommonCommandRoot.inlineMouseBehavior());
            MutableComponent replace = new TranslatableComponent("commands.croparia.create.duplicated.replace")
                .withStyle(CommonCommandRoot.suggestCommand(CommonCommandRoot.commandRoot(client), "create", color, rawType, name, "replace"))
                .withStyle(CommonCommandRoot.inlineMouseBehavior());
            MutableComponent duplication = new TranslatableComponent("commands.croparia.create.duplicated", crop, rename, replace);
            failure.send(duplication);
            return -1;
        }
        if (rawCroparia instanceof Croparia croparia) {
            JsonObject built = buildCrop(name, material, color, croparia.getTier(), type);
            Path result = dump(built);
            MutableComponent resultComponent = new TextComponent(result.toString());
            if (client) {
                resultComponent.withStyle(CommonCommandRoot.openFile(result.toString())).withStyle(CommonCommandRoot.inlineMouseBehavior());
            }
            success.send(new TranslatableComponent("commands.croparia.create.success", resultComponent), true);
            return croparia.getTier();
        } else {
            failure.send(new TranslatableComponent("commands.croparia.create.invalid_croparia"));
            return -1;
        }
    }

    public static JsonObject buildCrop(String name, Item material, String color, int tier, CropType type) {
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
