package cool.muyucloud.croparia.api.core.item.relic;

import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.Tabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class MagicRope extends Item {
    public MagicRope() {
        super(new Properties().arch$tab(Tabs.MAIN).rarity(Rarity.EPIC));
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getLevel() instanceof ServerLevel world && context.getPlayer() instanceof ServerPlayer player) {
            ItemStack itemStack = context.getItemInHand();
            int[] position;
            if (player.isShiftKeyDown()) {
                player.getMainHandItem().shrink(1);
                ItemStack newStack = CropariaItems.MAGIC_ROPE.get().getDefaultInstance();
                CompoundTag tag = newStack.getOrCreateTag();
                position = new int[]{player.blockPosition().getX(), player.blockPosition().getY(), player.blockPosition().getZ()};
                String targetWorld = world.dimension().location().toString();
                tag.putIntArray("targetPos", position);
                tag.putString("targetWorld", targetWorld);
                player.displayClientMessage(Component.literal("%s[x=%d y=%d z=%d]".formatted(targetWorld, position[0], position[1], position[2])), true);
                player.addItem(newStack);
                return InteractionResult.CONSUME;
            }
            CompoundTag tag = itemStack.getOrCreateTag();
            if (tag.contains("targetPos")) {
                String targetWorld = tag.contains("targetWorld") ? tag.getString("targetWorld") : "minecraft:overworld";
                world = world.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(targetWorld)));
                position = tag.getIntArray("targetPos");
                player.teleportTo(world, position[0], position[1], position[2], 0, 0);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }
}
