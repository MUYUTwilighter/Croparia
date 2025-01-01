package cool.muyucloud.croparia.item.relic;

import cool.muyucloud.croparia.registry.Tabs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MagicRope extends Item {
    public MagicRope() {
        super(new Properties().arch$tab(Tabs.MAIN));
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            Player player = context.getPlayer();
            ItemStack itemStack = context.getItemInHand();
            CompoundTag tag = itemStack.getOrCreateTag();
            int[] position;
            assert player != null;
            if (player.isShiftKeyDown()) {
                position = new int[]{player.blockPosition().getX(), player.blockPosition().getY(), player.blockPosition().getZ()};
                tag.putIntArray("targetPos", position);
                player.displayClientMessage(Component.nullToEmpty("x = " + position[0] + " y = " + position[1] + " z = " + position[2]), true);
                return InteractionResult.SUCCESS;
            }

            if (tag.contains("targetPos")) {
                position = tag.getIntArray("targetPos");
                player.teleportToWithTicket((double)position[0] + 0.5, (double)position[1], (double)position[2] + 0.5);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }
}
