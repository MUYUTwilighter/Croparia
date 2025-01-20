package cool.muyucloud.croparia.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

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

    public static BlockPos lookingAt(@NotNull Player player) {
        Level world = player.getLevel();
        ClipContext context = new ClipContext(
            player.getEyePosition(), player.getEyePosition().add(player.getLookAngle().multiply(5, 5, 5)),
            ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player
        );
        BlockHitResult result = world.clip(context);
        return result.getBlockPos();
    }
}
