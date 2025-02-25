package cool.muyucloud.croparia.api.crop.command;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.CropType;
import cool.muyucloud.croparia.api.crop.item.Croparia;
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
    private static final RequiredArgumentBuilder<CommandSourceStack, String> TYPE = RequiredArgumentBuilder.argument(
        "type", StringArgumentType.word()
    );
    private static final RequiredArgumentBuilder<CommandSourceStack, String> COLOR = RequiredArgumentBuilder.argument(
        "color", StringArgumentType.word()
    );

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        TYPE.suggests((context, builder) -> {
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
            false
        ));
        COLOR.executes(context -> create(
            context.getSource().getPlayerOrException(),
            CropType.CROP.getModelName(),
            StringArgumentType.getString(context, "color"),
            context.getSource()::sendSuccess,
            context.getSource()::sendFailure,
            false
        ));
        return CREATE.requires(s -> s.hasPermission(2)).then(COLOR.then(TYPE));
    }

    public static int create(Player player, String rawType, String color, SuccessMessage success, FailureMessage failure, boolean openFile) {
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