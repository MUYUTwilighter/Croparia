package cool.muyucloud.block;

import cool.muyucloud.CropariaIf;
import cool.muyucloud.data.crop.Crop;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.NotNull;

public class CropariaCropBlock extends CropBlock {
    private final Crop crop;

    public CropariaCropBlock(Crop crop) {
        super(Properties.of().noCollission().sound(SoundType.CROP));
        this.crop = crop;
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return this.crop.getSeedItem();
    }

    @Override
    public @NotNull MutableComponent getName() {
        MutableComponent cropName = Component.translatable(this.crop.getTranslationKey());
        return Component.translatable(this.getDescriptionId(), cropName);
    }

    @Override
    public @NotNull String getDescriptionId() {
        return "block." + CropariaIf.MOD_ID + ".crop.block";
    }

    public int getTier() {
        return this.crop.getTier();
    }
}
