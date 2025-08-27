package cool.muyucloud.croparia.api.crop.block;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.CropAccess;
import cool.muyucloud.croparia.registry.DgRegistries;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.NotNull;

public class CropariaCropBlock extends CropBlock implements CropAccess<Crop> {
    private final ResourceLocation cropId;

    public CropariaCropBlock(Crop crop) {
        super(Properties.of().noCollission().sound(SoundType.CROP).setId(ResourceKey.create(Registries.BLOCK, crop.getBlockId())));
        this.cropId = crop.getKey();
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return this.asItem();
    }

    @Override
    public @NotNull MutableComponent getName() {
        MutableComponent cropName = Texts.translatable(this.getCrop().getTranslationKey());
        return Texts.translatable("block." + CropariaIf.MOD_ID + ".crop.block", cropName);
    }

    public int getTier() {
        return this.getCrop().getTier();
    }

    @Override
    public @NotNull Item asItem() {
        if (this.getCrop().getCropSeed().isEmpty()) return Items.AIR;
        return this.getCrop().getCropSeed().get();
    }

    @Override
    public Crop getCrop() {
        return DgRegistries.CROPS.forName(this.cropId).orElseThrow();
    }
}
