package cool.muyucloud.croparia.api.core.item.relic;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class InfiniteApple extends Item {
    public InfiniteApple(Properties properties) {
        super(properties);
    }

    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (user instanceof Player player) {
            player.getAbilities().instabuild = true;
            super.finishUsingItem(stack, world, user);
            player.getAbilities().instabuild = false;
            if (!world.isClientSide) {
                player.getCooldowns().addCooldown(stack, 200);
            }
        }
        return stack;
    }
}
