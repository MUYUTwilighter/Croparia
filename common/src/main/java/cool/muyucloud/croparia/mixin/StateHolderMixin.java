package cool.muyucloud.croparia.mixin;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import cool.muyucloud.croparia.access.StateHolderAccess;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(StateHolder.class)
public abstract class StateHolderMixin<O, S> implements StateHolderAccess {
    @Shadow
    @Final
    private Reference2ObjectArrayMap<Property<?>, Comparable<?>> values;
    @Unique
    private Map<String, Property<?>> croparia_if$properties;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstruct(@Nullable Object object, Reference2ObjectArrayMap<Property<?>, Comparable<?>> map, @Nullable MapCodec mapCodec, @Nullable CallbackInfo ci) {
        if (map == null) {
            this.croparia_if$properties = ImmutableMap.of();
            return;
        }

        Map<String, Property<?>> properties = new HashMap<>();
        for (Property<?> property : map.keySet()) {
            properties.put(property.getName(), property);
        }
        this.croparia_if$properties = ImmutableMap.copyOf(properties);
    }

    @Override
    public String croparia_if$getValue(String key) {
        Property<?> property = this.croparia_if$properties.get(key);
        Comparable<?> value = this.values.get(property);
        if (value == null) {
            return null;
        } else if (value instanceof StringRepresentable enumVal) {
            return enumVal.getSerializedName();
        } else {
            return value.toString();
        }
    }
}
