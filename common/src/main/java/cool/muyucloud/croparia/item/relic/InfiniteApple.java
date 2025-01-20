package cool.muyucloud.croparia.item.relic;

import cool.muyucloud.croparia.registry.Tabs;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class InfiniteApple extends Item {

    public InfiniteApple() {
        super((new Properties()).food(
            new FoodProperties.Builder().alwaysEdible().nutrition(5).saturationModifier(4.0F)
                .effect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F)
                .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 0), 1.0F)
                .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0), 1.0F)
                .effect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 3), 1.0F)
                .build()
        ).stacksTo(1).arch$tab(Tabs.MAIN).rarity(Rarity.EPIC));
    }

    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (user instanceof Player player) {
            player.getAbilities().instabuild = true;
            super.finishUsingItem(stack, world, user);
            player.getAbilities().instabuild = false;
            if (!world.isClientSide) {
                player.getCooldowns().addCooldown(this, 200);
            }
        }
        return stack;
    }
}
