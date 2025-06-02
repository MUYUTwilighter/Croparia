package cool.muyucloud.croparia.api.core.recipe;

import com.mojang.serialization.MapCodec;
import cool.muyucloud.croparia.registry.Recipes;
import cool.muyucloud.croparia.util.CodecUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TypedSerializer<R extends DisplayableRecipe<? extends RecipeInput>> extends RecipeBookCategory implements RecipeType<R>, RecipeSerializer<R> {
    private final MapCodec<R> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, R> streamCodec;
    private transient final RecipeDisplay.Type<R> displayType;

    public TypedSerializer(final MapCodec<R> codec) {
        this(codec, CodecUtil.toStream(codec));
    }

    public TypedSerializer(final MapCodec<R> codec, final StreamCodec<RegistryFriendlyByteBuf, R> streamCodec) {
        this.codec = codec;
        this.streamCodec = streamCodec;
        this.displayType = new RecipeDisplay.Type<>(codec, streamCodec);
    }

    @Override
    @NotNull
    public MapCodec<R> codec() {
        return codec;
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
        return Recipes.get(this);
    }
}
