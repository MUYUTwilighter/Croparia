package cool.muyucloud.croparia.api.core.block;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.recipe.SoakRecipe;
import cool.muyucloud.croparia.api.core.recipe.container.SoakContainer;
import cool.muyucloud.croparia.api.element.Element;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ElementalStone extends Block {
    public ElementalStone(Properties properties) {
        super(properties.randomTicks());
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource source) {
        BlockState infusorState = level.getBlockState(pos.above());
        if (infusorState.getBlock() instanceof Infusor) {
            Element element = infusorState.getValue(Infusor.ELEMENT);
            for (int i = 0; i < CropariaIf.CONFIG.getSoakAttempts(); i++) {
                int x = source.nextIntBetweenInclusive(-1, 1);
                int z = source.nextIntBetweenInclusive(-1, 1);
                if (x == 0 && z == 0) i--;
                else this.trySoak(level, pos.offset(x, 0, z), element, source);
            }
        }
    }

    protected void trySoak(ServerLevel level, BlockPos pos, Element element, RandomSource source) {
        BlockState state = level.getBlockState(pos);
        SoakContainer container = new SoakContainer(state, element, source.nextFloat());
        level.getServer().getRecipeManager().getRecipeFor(SoakRecipe.TYPED_SERIALIZER, container, level).ifPresent(holder -> {
            SoakRecipe recipe = holder.value();
            Vec3 particlePos = pos.getCenter();
            level.sendParticles(ParticleTypes.HAPPY_VILLAGER, particlePos.x, particlePos.y + 0.5, particlePos.z, 20, 0.5, 0.1, 0.5, 1);
            level.playSound(null, pos, SoundEvent.createVariableRangeEvent(CropariaIf.of("block.soak.craft")), SoundSource.BLOCKS, 0.5F, 1.0F);
            recipe.getOutput().setBlock(level, pos);
        });
    }
}
