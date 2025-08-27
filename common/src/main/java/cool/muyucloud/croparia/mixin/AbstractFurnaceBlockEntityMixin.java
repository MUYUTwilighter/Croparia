package cool.muyucloud.croparia.mixin;

import cool.muyucloud.croparia.access.AbstractFurnaceBlockEntityAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.FuelValues;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin implements AbstractFurnaceBlockEntityAccess {
    @Shadow protected abstract int getBurnDuration(FuelValues fuelValues, ItemStack stack);

    @Override
    @Unique
    public int cif$getBurnDuration(Level level, ItemStack stack) {
        return this.getBurnDuration(level.fuelValues(), stack);
    }
}
