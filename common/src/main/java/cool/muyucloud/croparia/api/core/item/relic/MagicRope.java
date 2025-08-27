package cool.muyucloud.croparia.api.core.item.relic;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.component.TargetPos;
import cool.muyucloud.croparia.registry.CropariaComponents;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
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
                Texts.overlay(player, targetPos.getTooltip());
                return InteractionResult.SUCCESS;
            }
            @Nullable TargetPos targetPos = itemStack.get(CropariaComponents.TARGET_POS.get());
            if (targetPos == null) {
                Texts.overlay(player, Texts.translatable("overlay.croparia.magic_rope.no_target"));
                return InteractionResult.FAIL;
            } else {
                level.playSound(null, player.getOnPos(), SoundEvent.createVariableRangeEvent(CropariaIf.of("ambient.magic_rope.teleport")), SoundSource.AMBIENT, 1.0F, 1.0F);
                targetPos.teleport(player, server);
                targetPos.getLevel(server).ifPresent(targetLevel -> targetLevel.playSound(
                    null, targetPos.getPos(),
                    SoundEvent.createVariableRangeEvent(CropariaIf.of("ambient.magic_rope.teleport")),
                    SoundSource.AMBIENT, 1.0F, 1.0F
                ));
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }
}
