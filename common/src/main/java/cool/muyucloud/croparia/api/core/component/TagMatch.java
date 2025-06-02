package cool.muyucloud.croparia.api.core.component;

import com.mojang.serialization.Codec;
import cool.muyucloud.croparia.util.CodecUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TagMatch implements TooltipProvider {
    public static final Codec<TagMatch> CODEC = ResourceLocation.CODEC.xmap(TagMatch::new, TagMatch::getId);
    public static final StreamCodec<RegistryFriendlyByteBuf, TagMatch> STREAM_CODEC = CodecUtil.toStream(CODEC);

    private final ResourceLocation id;

    public TagMatch(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, @NotNull Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        consumer.accept(Component.translatable("tooltip.croparia.registry_tag", this.id));
    }
}
