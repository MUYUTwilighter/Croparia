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
    private final RegistrySupplier<LayeredCauldronBlock> cauldron;

    public Elematilius(@NotNull ElementsEnum element, @NotNull Properties properties) {
        super(properties);
        this.cauldron = CropariaBlocks.getCauldron(element);
    }

    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        if (!context.getLevel().isClientSide) {
            Level world = context.getLevel();
            BlockPos pos = context.getClickedPos();
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof LayeredCauldronBlock block) {
                if (block.isFull(state)) {
                    world.setBlockAndUpdate(pos, this.cauldron.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3));
                    context.getItemInHand().shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.FAIL;
    }
}
