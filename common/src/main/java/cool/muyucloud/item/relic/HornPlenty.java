package cool.muyucloud.item.relic;

import cool.muyucloud.registry.Tabs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HornPlenty extends Item {
    private static List<Item> food = new ArrayList<>();

    public HornPlenty() {
        super(new Properties().stacksTo(1).arch$tab(Tabs.MAIN));
    }

    public static void initFood() {
        food = BuiltInRegistries.ITEM.stream().filter(Item::isEdible).collect(Collectors.toList());
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        int index = context.getLevel().random.nextInt(food.size() - 1);
        context.getLevel().addFreshEntity(new ItemEntity(context.getLevel(), (double)context.getClickedPos().getX() + 0.5, (double)(context.getClickedPos().getY() + 1), (double)context.getClickedPos().getZ() + 0.5, new ItemStack((ItemLike)food.get(index))));
        return InteractionResult.SUCCESS;
    }
}
