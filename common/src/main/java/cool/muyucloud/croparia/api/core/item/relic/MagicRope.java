package cool.muyucloud.croparia.api.core.item.relic;

import cool.muyucloud.croparia.api.core.component.TargetPos;
import cool.muyucloud.croparia.registry.CropariaComponents;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagicRope extends Item {
    public MagicRope(Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide && context.getPlayer() instanceof ServerPlayer player && player.getServer() != null) {
            MinecraftServer server = player.getServer();
            ItemStack itemStack = context.getItemInHand();
            if (player.isShiftKeyDown()) {
                itemStack.shrink(1);
                ItemStack newStack = CropariaItems.MAGIC_ROPE.get().getDefaultInstance();
                TargetPos targetPos = new TargetPos(player);
                newStack.set(CropariaComponents.TARGET_POS.get(), targetPos);
                player.addItem(newStack);
                player.displayClientMessage(targetPos.getTooltip(), true);
                return InteractionResult.SUCCESS;
            }
            @Nullable TargetPos targetPos = itemStack.get(CropariaComponents.TARGET_POS.get());
            if (targetPos == null) {
                player.displayClientMessage(Component.translatable("overlay.croparia.magic_rope.no_target"), true);
                return InteractionResult.FAIL;
            } else {
                targetPos.teleport(player, server);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }
}
