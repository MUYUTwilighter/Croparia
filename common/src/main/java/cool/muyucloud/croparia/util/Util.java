package cool.muyucloud.croparia.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

public class Util {
    public static boolean hasNull(Object... objects) {
        return Arrays.stream(objects).anyMatch(Objects::isNull);
    }

    public static void deleteDir(File dir) throws IOException {
        if (dir.isDirectory()) {
            for (File child : Objects.requireNonNull(dir.listFiles())) {
                deleteDir(child);
            }
        }
        Files.delete(dir.toPath());
    }

    public static boolean isEdible(ItemStack item) {
        return item.has(DataComponents.FOOD);
    }

    @Nullable
    public static FoodProperties getFoodProperties(ItemStack item) {
        if (isEdible(item)) {
            return item.get(DataComponents.FOOD);
        }
        return null;
    }

    public static ServerLevel getLevel(ResourceLocation id, MinecraftServer server) {
        return server.getLevel(ResourceKey.create(Registries.DIMENSION, id));
    }
}
