package cool.muyucloud.croparia.api.crop.item;

import cool.muyucloud.croparia.api.crop.util.TierAccess;
import net.minecraft.world.item.Item;

public class Croparia extends Item implements TierAccess {
    private final int tier;

    public Croparia(int tier, Properties properties) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public int getTier() {
        return tier;
    }
}