package cool.muyucloud.croparia.rei;

import me.shedaniel.rei.api.common.entry.type.EntryType;
import me.shedaniel.rei.api.common.entry.type.EntryTypeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class ReiServer {
    public static EntryType<BlockState> BLOCK_STATE = EntryType.deferred(ResourceLocation.tryParse("croparia:block"));

    public static void registerEntryTypes(EntryTypeRegistry registry) {
        BLOCK_STATE = registry.register(ResourceLocation.tryParse("croparia:block"), );
    }
}
