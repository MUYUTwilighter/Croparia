package cool.muyucloud.croparia.api.crop.item;

import net.minecraft.world.item.Item;

public class Croparia extends Item {
    private final int tier;

    public Croparia(int tier, Properties properties) {
        super(properties);
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }
}
