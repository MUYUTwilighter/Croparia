package cool.muyucloud.croparia.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

public class Util {
    public static boolean anyNull(Object... objects) {
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

    public static BlockPos lookingAt(@NotNull Player player) {
        Level world = player.level();
        ClipContext context = new ClipContext(
            player.getEyePosition(),
            player.getEyePosition().add(player.getLookAngle().multiply(5, 5, 5)),
            ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player
        );
        BlockHitResult result = world.clip(context);
        return result.getBlockPos();
    }
}
