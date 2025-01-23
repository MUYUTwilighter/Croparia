package cool.muyucloud.croparia.block;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.entity.FakePlayer;
import cool.muyucloud.croparia.recipe.RitualStructure;
import cool.muyucloud.croparia.recipe.container.RitualContainer;
import cool.muyucloud.croparia.recipe.container.RitualStructureContainer;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.RecipeTypes;
import cool.muyucloud.croparia.util.ItemPlaceable;
import cool.muyucloud.croparia.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class RitualStand extends Block implements ItemPlaceable {
    protected final VoxelShape SHAPE = Block.box(0.0, 0.3, 0.0, 16.0, 6.0, 16.0);
    private final int tier;
    private LinkedList<ItemEntity> items = new LinkedList<>();

    public RitualStand(int tier) {
        super(Properties.of().strength(1.0F, 1.0F).sound(SoundType.ANVIL).requiresCorrectToolForDrops());
        this.tier = tier;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level world, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!world.isClientSide) {
            if (itemStack.getItem() == CropariaItems.RECIPE_WIZARD.get()) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
            Util.placeItem(world, pos, itemStack);
            return ItemInteractionResult.CONSUME;
        }
        return super.useItemOn(itemStack, blockState, world, pos, player, interactionHand, blockHitResult);
    }

    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        LinkedList<ItemEntity> filtered = new LinkedList<>();
        items.stream().filter(item -> !item.isRemoved()).forEach(filtered::add);
        items = filtered;
        if (entity instanceof ItemEntity itemEntity && !this.items.contains(itemEntity)
            && world instanceof ServerLevel serverWorld && CropariaIf.CONFIG.getRitual()) {
            this.items.add(itemEntity);
            ItemStack stack = itemEntity.getItem();
            RecipeManager recipeManager = serverWorld.getServer().getRecipeManager();
            this.getRitualStructure(recipeManager).flatMap(
                structure -> structure.matchesAndDestroy(pos, world)
            ).ifPresentOrElse(inputBlock -> {
                RitualContainer container = this.getRitualContainer(stack, inputBlock);
                if (itemEntity.getOwner() instanceof Player player) {
                    this.tryCraft(container, serverWorld, pos, player);
                } else {
                    this.tryCraft(container, serverWorld, pos, null);
                }
            }, () -> {
                if (itemEntity.getOwner() != null && itemEntity.getOwner() instanceof Player player) {
                    this.bad("overlay.croparia.ritual.bad", player);
                }
            });
        }
    }

    protected Optional<RitualStructure> getRitualStructure(@NotNull RecipeManager recipeManager) {
        AtomicReference<RitualStructure> recipe = new AtomicReference<>();
        recipeManager.getRecipeFor(
            RecipeTypes.RITUAL_STRUCTURE.get(), RitualStructureContainer.INSTANCE, null, this.arch$registryName()
        ).ifPresent(result -> recipe.set(result.value()));
        return Optional.ofNullable(recipe.get());
    }

    protected void tryCraft(@NotNull RitualContainer container, @NotNull ServerLevel world, @NotNull BlockPos pos, @Nullable Player player) {
        world.getServer().getRecipeManager().getRecipeFor(RecipeTypes.RITUAL.get(), container, world).ifPresentOrElse(recipe -> {
            ItemStack result = recipe.value().assemble(container);
            if (result.getItem() instanceof SpawnEggItem) {
                FakePlayer.useAllItemsOn(world, pos, result);
            } else {
                Util.exportItem(world, pos, result, player);
            }
        }, () -> {
            if (player != null) {
                this.bad("overlay.croparia.ritual.rejected", player);
            }
        });
    }

    public @NotNull RitualContainer getRitualContainer(@NotNull ItemStack input, @NotNull BlockState block) {
        return new RitualContainer(this.tier, input, block);
    }

    public void bad(@NotNull String translationKey, @NotNull Player player) {
        player.displayClientMessage(Component.translatable(translationKey), true);
    }

    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.SHAPE;
    }

    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.SHAPE;
    }

    public int getTier() {
        return this.tier;
    }

    @Override
    public void placeItem(Level world, BlockPos pos, ItemStack stack) {
        Util.placeItem(world, pos, stack);
    }
}
