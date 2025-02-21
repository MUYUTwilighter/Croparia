package cool.muyucloud.croparia.api.core.recipe.serializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.api.core.recipe.RitualStructure;
import cool.muyucloud.croparia.api.core.recipe.util.BlockStatePredicate;
import cool.muyucloud.croparia.api.math.Char3D;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RitualStructureSerializer implements RecipeSerializer<RitualStructure> {
    public static final MapCodec<RitualStructure> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, BlockStatePredicate.Builder.CODEC).fieldOf("keys").forGetter(RitualStructure::getKeys),
            Char3D.CODEC.fieldOf("pattern").forGetter(RitualStructure::getPattern)
        ).apply(instance, RitualStructure::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, RitualStructure> STREAM_CODEC = StreamCodec.ofMember(
        (recipe, outputBuf) -> {
            outputBuf.writeMap(recipe.getKeys(),
                (buf, character) -> buf.writeChar(character.charAt(0)),
                (buf, builder) -> buf.writeJsonWithCodec(BlockStatePredicate.Builder.CODEC, builder)
            );
            outputBuf.writeJsonWithCodec(Char3D.CODEC, recipe.getPattern());
        },
        inputBuf -> {
            Map<String, BlockStatePredicate.Builder> keys = inputBuf.readMap(
                buf -> String.valueOf(buf.readChar()), buf -> buf.readJsonWithCodec(BlockStatePredicate.Builder.CODEC)
            );
            keys = Map.copyOf(keys);
            Char3D pattern = inputBuf.readJsonWithCodec(Char3D.CODEC);
            return new RitualStructure(keys, pattern);
        }
    );

    @Override
    public @NotNull MapCodec<RitualStructure> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, RitualStructure> streamCodec() {
        return STREAM_CODEC;
    }
}
