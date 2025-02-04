package cool.muyucloud.croparia.api.crop.block;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.CropAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.NotNull;

public class CropariaCropBlock extends CropBlock implements CropAccess {
    private final Crop crop;

    public CropariaCropBlock(Crop crop) {
        super(Properties.of().noCollission().sound(SoundType.CROP).setId(ResourceKey.create(Registries.BLOCK, crop.getBlockId())));
        this.crop = crop;
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return this.crop.getSeedItem();
    }

    @Override
    public @NotNull MutableComponent getName() {
        MutableComponent cropName = Component.translatable(this.crop.getTranslationKey());
        return Component.translatable("block." + CropariaIf.MOD_ID + ".crop.block", cropName);
    }

    public int getTier() {
        return this.crop.getTier();
    }

    @Override
    public @NotNull Item asItem() {
        return crop.getSeedItem();
    }

    @Override
    public Crop getCrop() {
        return this.crop;
    }
}
