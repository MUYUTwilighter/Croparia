package cool.muyucloud.croparia.mixin;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import cool.muyucloud.croparia.access.StateHolderAccess;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
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
    private ImmutableMap<Property<?>, Comparable<?>> values;
    @Unique
    private Map<String, Property<?>> croparia_if$properties;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstruct(O object, ImmutableMap<Property<?>, Comparable<?>> immutableMap, MapCodec<S> mapCodec, CallbackInfo ci) {
        Map<String, Property<?>> properties = new HashMap<>();
        for (Property<?> property : immutableMap.keySet()) {
            properties.put(property.getName(), property);
        }
        this.croparia_if$properties = ImmutableMap.copyOf(properties);
    }

    @Override
    public String croparia_if$getValue(String key) {
        Property<?> property = this.croparia_if$properties.get(key);
        Comparable<?> value = this.values.get(property);
        return value == null ? null : value.toString();
    }
}
