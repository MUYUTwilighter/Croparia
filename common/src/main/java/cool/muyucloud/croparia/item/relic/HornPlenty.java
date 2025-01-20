package cool.muyucloud.croparia.item.relic;

import cool.muyucloud.croparia.registry.Tabs;
import cool.muyucloud.croparia.util.Constants;
import cool.muyucloud.croparia.util.PostConstants;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HornPlenty extends Item {
    private static final int MAX_ATTEMPT = 5;

    public HornPlenty() {
        super(new Properties().stacksTo(1).tab(Tabs.MAIN).rarity(Rarity.EPIC));
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }
        int index = context.getLevel().random.nextInt(PostConstants.FOODS.size());
        ItemStack food = null;
        for (int i = 0; i < MAX_ATTEMPT; ++i) {
            food = PostConstants.FOODS.get((int) (Math.random() % PostConstants.FOODS.size())).getDefaultInstance();
            if (food.is(PostConstants.HORN_PLENTY_BLACKLIST)) {
                food = null;
            } else {
                break;
            }
        }
        if (food == null) {
            return InteractionResult.FAIL;
        }
        int xp = Objects.requireNonNull(food.getItem().getFoodProperties()).getNutrition();
        if (xp > player.totalExperience) {
            player.displayClientMessage(Constants.INSUFFICIENT_XP, true);
            return InteractionResult.FAIL;
        }
        player.giveExperiencePoints(-xp);
        player.getCooldowns().addCooldown(this, 100);
        context.getLevel().addFreshEntity(new ItemEntity(
            context.getLevel(),
            context.getClickedPos().getX() + 0.5D,
            context.getClickedPos().getY() + 1D,
            context.getClickedPos().getZ() + 0.5D,
            PostConstants.FOODS.get(index).getDefaultInstance()
        ));

        return InteractionResult.SUCCESS;
    }
}
