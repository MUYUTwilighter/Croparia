//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cool.muyucloud.croparia.item;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.data.crop.Crop;
import cool.muyucloud.croparia.registry.Tabs;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class CropSeed extends BlockItem {
    public Crop crop;

    public CropSeed(Crop crop) {
        super(crop.getCropBlock(), new Properties().arch$tab(Tabs.CROPS));
        this.crop = crop;
    }

    @Override
    public Component getName(ItemStack itemStack) {
        MutableComponent cropName = Component.translatable(this.crop.getTranslationKey());
        return Component.translatable(this.getDescriptionId(itemStack), cropName);
    }

    @Override
    public String getDescriptionId() {
        return "item." + CropariaIf.MOD_ID + ".crop.seed";
    }
}
