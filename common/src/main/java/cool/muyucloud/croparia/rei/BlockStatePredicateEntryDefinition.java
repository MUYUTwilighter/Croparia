package cool.muyucloud.croparia.rei;

import cool.muyucloud.croparia.util.BlockStatePredicate;
import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer;
import me.shedaniel.rei.api.common.entry.EntrySerializer;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.comparison.ComparisonContext;
import me.shedaniel.rei.api.common.entry.type.EntryDefinition;
import me.shedaniel.rei.api.common.entry.type.EntryType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class BlockStatePredicateEntryDefinition implements EntryDefinition<BlockStatePredicate> {
    public static EntryType<BlockStatePredicate> BLOCK_STATE_PREDICATE = EntryType.deferred(ResourceLocation.tryParse("croparia:block_state_predicate"));

    @Override
    public Class<BlockStatePredicate> getValueType() {
        return BlockStatePredicate.class;
    }

    @Override
    public EntryType<BlockStatePredicate> getType() {
        return BLOCK_STATE_PREDICATE;
    }

    @Override
    public EntryRenderer<BlockStatePredicate> getRenderer() {
        return null;
    }

    @Override
    public @Nullable ResourceLocation getIdentifier(EntryStack<BlockStatePredicate> entry, BlockStatePredicate value) {
        return entry.getIdentifier();
    }

    @Override
    public boolean isEmpty(EntryStack<BlockStatePredicate> entry, BlockStatePredicate value) {
        return entry.isEmpty();
    }

    @Override
    public BlockStatePredicate copy(EntryStack<BlockStatePredicate> entry, BlockStatePredicate value) {
        return value;
    }

    @Override
    public BlockStatePredicate normalize(EntryStack<BlockStatePredicate> entry, BlockStatePredicate value) {
        return value;
    }

    @Override
    public BlockStatePredicate wildcard(EntryStack<BlockStatePredicate> entry, BlockStatePredicate value) {
        return BlockStatePredicate.ANY;
    }

    @Override
    public long hash(EntryStack<BlockStatePredicate> entry, BlockStatePredicate value, ComparisonContext context) {
        return value.hashCode();
    }

    @Override
    public boolean equals(BlockStatePredicate o1, BlockStatePredicate o2, ComparisonContext context) {
        return false;
    }

    @Override
    public @Nullable EntrySerializer<BlockStatePredicate> getSerializer() {
        return null;
    }

    @Override
    public Component asFormattedText(EntryStack<BlockStatePredicate> entry, BlockStatePredicate value) {
        return null;
    }

    @Override
    public Stream<? extends TagKey<?>> getTagsFor(EntryStack<BlockStatePredicate> entry, BlockStatePredicate value) {
        return Stream.empty();
    }
}
