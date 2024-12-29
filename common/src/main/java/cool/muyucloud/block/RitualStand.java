package cool.muyucloud.block;

import cool.muyucloud.CropariaIf;
import cool.muyucloud.entity.FakePlayer;
import cool.muyucloud.recipe.RitualStructure;
import cool.muyucloud.recipe.container.RitualContainer;
import cool.muyucloud.recipe.container.RitualStructureContainer;
import cool.muyucloud.registry.RecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class RitualStand extends Block {
    protected final VoxelShape SHAPE = Block.box(0.0, 0.3, 0.0, 16.0, 6.0, 16.0);
    private final int tier;

    public RitualStand(int tier) {
        super(Properties.of().strength(1.0F, 1.0F).sound(SoundType.ANVIL).requiresCorrectToolForDrops());
        this.tier = tier;
    }

    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof ItemEntity itemEntity && world instanceof ServerLevel serverWorld && CropariaIf.CONFIG.getRitual()) {
            ItemStack stack = itemEntity.getItem();
            RecipeManager recipeManager = serverWorld.getServer().getRecipeManager();
            this.getRitualStructure(recipeManager).flatMap(
                structure -> structure.matches(pos, world)
            ).ifPresentOrElse(inputBlock -> {
                RitualContainer container = this.getRitualContainer(stack, inputBlock);
                this.tryCraft(container, serverWorld, pos);
            }, () -> {
                @Nullable Entity thrower = itemEntity.getOwner();
                if (thrower instanceof Player player) {
                    this.bad(player, "chat.croparia.ritual.bad");
                }
            });
        }
    }

    protected Optional<RitualStructure> getRitualStructure(@NotNull RecipeManager recipeManager) {
        AtomicReference<RitualStructure> recipe = new AtomicReference<>();
        recipeManager.getRecipeFor(
            RecipeTypes.RITUAL_STRUCTURE, RitualStructureContainer.INSTANCE, null, this.arch$registryName()
        ).ifPresent(result -> recipe.set(result.getSecond()));
        return Optional.ofNullable(recipe.get());
    }

    protected void tryCraft(@NotNull RitualContainer container, @NotNull ServerLevel world, @NotNull BlockPos pos) {
        world.getServer().getRecipeManager().getRecipeFor(RecipeTypes.RITUAL, container, world).ifPresent(recipe -> {
            ItemStack result = recipe.assemble(container, world.registryAccess());
            if (result.getItem() instanceof SpawnEggItem) {
                FakePlayer.useAllItemsOn(world, pos, result);
            } else {
                world.addFreshEntity(new ItemEntity(
                    world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, result
                ));
            }
        });
    }

    public @NotNull RitualContainer getRitualContainer(@NotNull ItemStack input, @NotNull BlockState block) {
        return new RitualContainer(this.tier, input, block);
    }

    public void bad(Player player, String translationKey) {
        player.displayClientMessage(Component.translatable(translationKey), true);
    }

    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.SHAPE;
    }

    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.SHAPE;
    }
}
