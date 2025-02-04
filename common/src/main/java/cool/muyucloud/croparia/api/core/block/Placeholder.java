package cool.muyucloud.croparia.api.core.block;

import cool.muyucloud.croparia.registry.CropariaItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class Placeholder extends Block {
    public Placeholder(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Item asItem() {
        return CropariaItems.PLACEHOLDER_BLOCK.get();
    }
}
