package cool.muyucloud.croparia.item;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.access.CropAccess;
import cool.muyucloud.croparia.data.crop.Crop;
import cool.muyucloud.croparia.registry.Tabs;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class CropFruit extends Item implements CropAccess {
    public Crop crop;

    public CropFruit(Crop crop) {
        super(new Properties().arch$tab(Tabs.CROPS));
        this.crop = crop;
    }

    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        if (!CropariaIf.CONFIG.getFruitUse()) {
            return InteractionResult.PASS;
        }
        if (!context.getLevel().isClientSide) {
            Item material = crop.getMaterialItem();
            context.getLevel().addFreshEntity(new ItemEntity(
                context.getLevel(),
                context.getClickedPos().getX() + 0.5,
                context.getClickedPos().getY() + 1,
                context.getClickedPos().getZ() + 0.5,
                new ItemStack(material, Math.min(material.getDefaultMaxStackSize(), 2))
            ));
            context.getItemInHand().shrink(1);
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

    @Override
    public Crop getCrop() {
        return this.crop;
    }
}
