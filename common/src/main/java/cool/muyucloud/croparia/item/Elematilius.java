package cool.muyucloud.croparia.item;

import cool.muyucloud.croparia.data.ElementsEnum;
import cool.muyucloud.croparia.registry.CropariaBlocks;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class Elematilius extends Item {
    public Elematilius(@NotNull ElementsEnum element, @NotNull Properties properties) {
        super(properties);
    }
}
