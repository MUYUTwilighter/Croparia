package cool.muyucloud.croparia.api.element.item;

import cool.muyucloud.croparia.api.core.block.Infusor;
import cool.muyucloud.croparia.api.element.Element;
import cool.muyucloud.croparia.api.element.ElementAccess;
import cool.muyucloud.croparia.util.ItemPlaceable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ElementalPotion extends Item implements ElementAccess {
    private static final Map<Element, ElementalPotion> POTIONS = new HashMap<>();
    private final Element element;

    public ElementalPotion(@NotNull Element element, @NotNull Properties properties) {
        super(properties);
        if (element == Element.EMPTY) {
            throw new IllegalArgumentException("ElementalPotion cannot be empty element");
        }
        this.element = element;
        POTIONS.put(element, this);
        DispenserBlock.registerBehavior(this, (blockSource, itemStack) -> {
            Level world = blockSource.level();
            BlockPos sourcePos = blockSource.pos();
            BlockState sourceState = blockSource.state();
            Direction direction = sourceState.getValue(DispenserBlock.FACING);
            BlockPos targetPos = sourcePos.offset(direction.getUnitVec3i());
            BlockState targetState = blockSource.level().getBlockState(targetPos);
            Block targetBlock = targetState.getBlock();
            if (targetBlock instanceof Infusor infusor) {
                if (!infusor.tryInfuse(world, targetPos, this, itemStack, null)) {
                    infusor.placeItem(world, targetPos, itemStack.split(1));
                }
            } else if (targetBlock instanceof ItemPlaceable placeable) {
                placeable.placeItem(world, targetPos, itemStack.split(1));
            } else {
                DefaultDispenseItemBehavior.spawnItem(world, itemStack.split(1), 1, direction, targetPos.getCenter());
            }
            return itemStack;
        });
    }

    @Override
    public @NotNull Element getElement() {
        return this.element;
    }

    public static Optional<ElementalPotion> fromElement(Element element) {
        return Optional.ofNullable(POTIONS.get(element));
    }
}
