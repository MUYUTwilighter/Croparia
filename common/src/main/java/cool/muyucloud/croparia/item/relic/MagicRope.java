package cool.muyucloud.croparia.item.relic;

import cool.muyucloud.croparia.registry.CropariaComponents;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.Tabs;
import cool.muyucloud.croparia.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagicRope extends Item {
    public MagicRope() {
        super(new Properties().arch$tab(Tabs.MAIN).rarity(Rarity.EPIC));
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide && context.getPlayer() instanceof ServerPlayer player) {
            ServerLevel world = (ServerLevel) player.level();
            MinecraftServer server = player.getServer();
            ItemStack itemStack = context.getItemInHand();
            if (player.isShiftKeyDown()) {
                itemStack.shrink(1);
                ItemStack newStack = CropariaItems.MAGIC_ROPE.get().getDefaultInstance();
                BlockPos targetPos = player.blockPosition();
                ResourceLocation targetWorld = world.dimension().location();
                newStack.set(CropariaComponents.TARGET_WORLD.get(), targetWorld);
                newStack.set(CropariaComponents.TARGET_POSITION.get(), targetPos);
                player.addItem(newStack);
                player.displayClientMessage(Component.literal("%s[x=%s, y=%s, z=%s]".formatted(targetWorld, targetPos.getX(), targetPos.getY(), targetPos.getZ())), true);
                return InteractionResult.SUCCESS;
            }
            @NotNull ResourceLocation targetWorld = itemStack.getOrDefault(
                CropariaComponents.TARGET_WORLD.get(), ResourceLocation.tryParse("minecraft:overworld")
            );
            @Nullable BlockPos targetPos = itemStack.getOrDefault(CropariaComponents.TARGET_POSITION.get(), null);
            if (targetPos != null) {
                ServerLevel target = Util.getLevel(targetWorld, server);
                player.teleportTo(target, targetPos.getX(), targetPos.getY(), targetPos.getZ(), 0.0F, 0.0F);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }
}
