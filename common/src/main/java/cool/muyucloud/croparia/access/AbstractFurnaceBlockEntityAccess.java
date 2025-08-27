package cool.muyucloud.croparia.access;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Unique;

public interface AbstractFurnaceBlockEntityAccess {
    @Unique
    int cif$getBurnDuration(Level level, ItemStack stack);
}
