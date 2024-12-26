package cool.muyucloud.item;

import cool.muyucloud.data.crop.Crop;
import cool.muyucloud.CropariaIf;
import cool.muyucloud.registry.Tabs;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class CropFruit extends Item {
    public Crop crop;

    public CropFruit(Crop crop) {
        super(new Properties().arch$tab(Tabs.CROPS));
        this.crop = crop;
    }

    public InteractionResult useOn(UseOnContext context) {
//        if (!Croparia.CONFIG.getFruitUse()) {
//            return InteractionResult.PASS;
//        }
        if (!context.getLevel().isClientSide) {
            context.getLevel().addFreshEntity(new ItemEntity(context.getLevel(), (double) context.getClickedPos().getX() + 0.5, context.getClickedPos().getY() + 1, (double) context.getClickedPos().getZ() + 0.5, new ItemStack(crop.getMaterialItem(), 2)));
            context.getPlayer().getMainHandItem().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        MutableComponent cropName = Component.translatable(this.crop.getTranslationKey());
        return Component.translatable(this.getDescriptionId(itemStack), cropName);
    }

    @Override
    public @NotNull String getDescriptionId() {
        return "item." + CropariaIf.MOD_ID + ".crop.fruit";
    }
}
