package cool.muyucloud.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.access.StateHolderAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class BlockStatePredicate implements Predicate<BlockState> {
    public static final BlockStatePredicate ANY = new BlockStatePredicate(b -> true, b -> true, 0, Builder.create());

    @NotNull
    private transient final Predicate<BlockState> block;
    @NotNull
    private transient final Predicate<BlockState> properties;
    private transient final int hashCode;
    private final Builder builder;

    public BlockStatePredicate(
        @NotNull Predicate<BlockState> block, @NotNull Predicate<BlockState> properties, int hashCode, Builder builder
    ) {
        this.block = block;
        this.properties = properties;
        this.hashCode = hashCode;
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

    @Override
    public boolean test(BlockState state) {
        return this.block.test(state) && this.properties.test(state);
    }

    public static Builder builder() {
        return Builder.create();
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    public static class Builder {
        public static final Codec<Builder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("block").forGetter(o -> Optional.ofNullable(o.getBlock())),
            CompoundTag.CODEC.optionalFieldOf("properties").forGetter(o -> Optional.of(o.properties))
        ).apply(instance, (block, properties) ->
            create().properties(properties.orElse(new CompoundTag())).block(block.orElse(null))
        ));


        private boolean built = false;
        @Nullable
        private String block = null;
        @NotNull
        private CompoundTag properties = new CompoundTag();

        public static Builder create() {
            return new Builder();
        }

        public BlockStatePredicate build() {
            Predicate<BlockState> blockPredicate;
            if (block == null) {
                blockPredicate = b -> true;
            } else if (block.startsWith("#")) {
                block = block.substring(1);
                TagKey<Block> tag = TagKey.create(Registries.BLOCK, ResourceLocation.tryParse(block));
                blockPredicate = b -> b.is(tag);
            } else {
                Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(this.block));
                blockPredicate = b -> b.is(block);
            }
            Predicate<BlockState> propertiesPredicate = this.properties.isEmpty() ? b -> true : b -> {
                for (String key : this.properties.getAllKeys()) {
                    StateHolderAccess state = (StateHolderAccess) b;
                    String value = state.croparia_if$getValue(key);
                    String target = this.properties.getString(key);
                    if (Objects.equals(value, target)) {
                        return false;
                    }
                }
                return true;
            };
            built = true;
            return new BlockStatePredicate(
                blockPredicate, propertiesPredicate, Objects.hash(block, properties), this
            );
        }

        public @Nullable String getBlock() {
            return block;
        }

        protected Builder properties(@NotNull CompoundTag properties) {
            if (built) {
                throw new IllegalStateException("Builder already built");
            }
            this.properties = properties;
            return this;
        }

        public Builder block(@Nullable String block) {
            if (built) {
                throw new IllegalStateException("Builder already built");
            }
            this.block = block;
            return this;
        }

        public Builder property(String property, String value) {
            if (built) {
                throw new IllegalStateException("Builder already built");
            }
            this.properties.putString(property, value);
            return this;
        }
    }
}
