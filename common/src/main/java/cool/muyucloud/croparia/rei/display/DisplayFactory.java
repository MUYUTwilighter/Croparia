package cool.muyucloud.croparia.rei.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public interface DisplayFactory<D extends Display, R extends Recipe<?>> {
    D createDisplay(R recipe, ResourceLocation id);

    static <D extends SimpleDisplay<R>, R extends Recipe<?>> MapCodec<D> codec(Codec<R> recipeCodec, DisplayFactory<D, R> constructor) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            recipeCodec.fieldOf("recipe").forGetter(SimpleDisplay::getRecipe),
            ResourceLocation.CODEC.fieldOf("id").forGetter(SimpleDisplay::getRecipeId)
        ).apply(instance, constructor::createDisplay));
    }

    static <B extends RegistryFriendlyByteBuf, D extends SimpleDisplay<R>, R extends Recipe<?>> StreamCodec<B, D> streamCodec(Codec<R> recipeCodec, DisplayFactory<D, R> constructor) {
        return StreamCodec.ofMember(
            (display, buf) -> {
                buf.writeJsonWithCodec(recipeCodec, display.getRecipe());
                buf.writeResourceLocation(display.getRecipeId());
            },
            (buf) -> {
                R recipe = buf.readJsonWithCodec(recipeCodec);
                ResourceLocation id = buf.readResourceLocation();
                return constructor.createDisplay(recipe, id);
            }
        );
    }

    static <D extends SimpleDisplay<R>, R extends Recipe<?>> DisplaySerializer<D> serializer(Codec<R> recipeCodec, DisplayFactory<D, R> constructor) {
        return DisplaySerializer.of(codec(recipeCodec, constructor), streamCodec(recipeCodec, constructor));
    }
}
