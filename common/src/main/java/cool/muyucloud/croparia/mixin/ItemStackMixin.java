package cool.muyucloud.croparia.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import cool.muyucloud.croparia.registry.CropariaComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow protected abstract void addToTooltip(DataComponentType<?> dataComponentType, Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag);

    @Inject(method = "getTooltipLines", at = @At(
        value = "INVOKE", ordinal = 0,
        target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V"))
    public void onTooltips(Item.TooltipContext tooltipContext, Player player, TooltipFlag tooltipFlag,
                           CallbackInfoReturnable<List<Component>> cir, @Local Consumer<Component> consumer) {
        CropariaComponents.forEach(type -> this.addToTooltip(type, tooltipContext, consumer, tooltipFlag));
    }
}
