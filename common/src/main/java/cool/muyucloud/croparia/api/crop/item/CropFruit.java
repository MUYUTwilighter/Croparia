package cool.muyucloud.croparia.api.crop.item;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.CropAccess;
import cool.muyucloud.croparia.registry.DgRegistries;
import cool.muyucloud.croparia.registry.Tabs;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class CropFruit extends Item implements CropAccess<Crop> {
    private final ResourceLocation cropId;

    public CropFruit(Crop crop) {
        super(new Properties().arch$tab(Tabs.CROPS).setId(ResourceKey.create(Registries.ITEM, crop.getFruitId())));
        this.cropId = crop.getKey();
    }

    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        if (CropariaIf.CONFIG.getFruitUse() > 0) {
            return InteractionResult.PASS;
        }
        if (!context.getLevel().isClientSide) {
            Item material = getCrop().getResult();
            context.getLevel().addFreshEntity(new ItemEntity(
                context.getLevel(),
                context.getClickedPos().getX() + 0.5,
                context.getClickedPos().getY() + 1,
                context.getClickedPos().getZ() + 0.5,
                new ItemStack(material, Math.min(material.getDefaultMaxStackSize(), CropariaIf.CONFIG.getFruitUse()))
            ));
            context.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        MutableComponent cropName = Texts.translatable(this.getCrop().getTranslationKey());
        return Texts.translatable("item." + CropariaIf.MOD_ID + ".crop.fruit", cropName);
    }


    @Override
    public Crop getCrop() {
        return DgRegistries.CROPS.forName(this.cropId).orElseThrow();
    }
}
