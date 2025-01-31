package cool.muyucloud.croparia.api.core.entity;

import com.mojang.authlib.GameProfile;
import cool.muyucloud.croparia.CropariaIf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FakePlayer extends Player {
    private static final Map<ServerLevel, FakePlayer> FAKE_PLAYERS = new HashMap<>();
    private static final int MAX_USES = 64;

    public static void useAllItemsOn(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull ItemStack item) {
        int uses = MAX_USES;
        while (!item.isEmpty() && uses > 0) {
            InteractionResult result = useItemOn(world, pos, item);
            if (result != InteractionResult.FAIL && result != InteractionResult.PASS) {
                finishUseItem(item, world);
                if (result == InteractionResult.CONSUME) {
                    item.shrink(1);
                }
            } else {
                break;
            }
            uses--;
        }
    }

    public static FakePlayer getPlayer(@NotNull ServerLevel world) {
        FakePlayer fakePlayer = FAKE_PLAYERS.getOrDefault(world, new FakePlayer(world));
        FAKE_PLAYERS.put(world, fakePlayer);
        return fakePlayer;
    }

    public static InteractionResult useItemOn(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull ItemStack item) {
        return getPlayer(world).useItemOn(pos, item);
    }

    public static void finishUseItem(@NotNull ItemStack item, @NotNull ServerLevel world) {
        FakePlayer fakePlayer = getPlayer(world);
        item.finishUsingItem(world, fakePlayer);
    }

    public FakePlayer(@NotNull Level level) {
        super(level, BlockPos.ZERO, 0, new GameProfile(UUID.randomUUID(), "FakePlayer"));
        CropariaIf.LOGGER.debug("Created fake player for {}", level.dimension());
    }

    public InteractionResult useItemOn(@NotNull BlockPos pos, @NotNull ItemStack item) {
        BlockHitResult hit = new BlockHitResult(pos.getCenter(), Direction.UP, pos, false);
        UseOnContext context = new UseOnContext(this, InteractionHand.MAIN_HAND, hit);
        return item.useOn(context);
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
