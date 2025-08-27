package cool.muyucloud.croparia.api.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import cool.muyucloud.croparia.util.codec.CodecUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TypedSerializer<R extends DisplayableRecipe<? extends RecipeInput>>
    extends RecipeBookCategory implements RecipeType<R>, RecipeSerializer<R> {
    private final Codec<R> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, R> streamCodec;
    private transient final RecipeDisplay.Type<R> displayType;

    public TypedSerializer(final MapCodec<R> codec) {
        this(codec.codec(), CodecUtil.toStream(codec));
    }

    public TypedSerializer(final MapCodec<R> codec, final StreamCodec<RegistryFriendlyByteBuf, R> streamCodec) {
        this(codec.codec(), streamCodec);
    }

    @SuppressWarnings("unused")
    public TypedSerializer(final Codec<R> codec) {
        this(codec, CodecUtil.toStream(codec));
    }

    public TypedSerializer(final Codec<R> codec, final StreamCodec<RegistryFriendlyByteBuf, R> streamCodec) {
        this.codec = codec;
        this.streamCodec = streamCodec;
        this.displayType = new RecipeDisplay.Type<>(CodecUtil.toMap(codec), streamCodec);
    }

    @Override
    public @NotNull MapCodec<R> codec() {
        return CodecUtil.toMap(codec);
    }

    @Override
    @NotNull
    public StreamCodec<RegistryFriendlyByteBuf, R> streamCodec() {
        return streamCodec;
    }

    @NotNull
    public RecipeDisplay.Type<R> displayType() {
        return displayType;
    }

    public Optional<ResourceLocation> getId() {
        return BuiltInRegistries.RECIPE_TYPE.getResourceKey(this).map(ResourceKey::location);
    }
}
