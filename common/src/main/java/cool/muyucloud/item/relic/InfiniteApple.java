package cool.muyucloud.item.relic;

import cool.muyucloud.registry.Tabs;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class InfiniteApple extends Item {
    public InfiniteApple() {
        super((new Properties()).food(Foods.GOLDEN_CARROT).stacksTo(1).arch$tab(Tabs.MAIN));
    }

    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        super.finishUsingItem(stack, world, user);
        if (user instanceof Player entityplayer) {
            world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
        }

        return stack;
    }
}
