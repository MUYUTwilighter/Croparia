package cool.muyucloud.croparia.api.core.block;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.entity.FakePlayer;
import cool.muyucloud.croparia.api.core.recipe.RitualStructure;
import cool.muyucloud.croparia.api.core.recipe.container.RitualContainer;
import cool.muyucloud.croparia.api.core.recipe.container.RitualStructureContainer;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.Recipes;
import cool.muyucloud.croparia.util.ItemPlaceable;
import cool.muyucloud.croparia.util.Util;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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

    public RitualStand(int tier, Properties properties) {
        super(properties);
        this.tier = tier;
    }

    @Override
    protected @NotNull InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (!world.isClientSide && hand == InteractionHand.MAIN_HAND) {
            if (itemStack.getItem() == CropariaItems.RECIPE_WIZARD.get()) {
                return InteractionResult.PASS;
            }
            Util.placeItem(world, pos, itemStack);
            return InteractionResult.CONSUME;
        }
        return super.useItemOn(itemStack, blockState, world, pos, player, hand, blockHitResult);
    }

    @Override
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
            Recipes.RITUAL_STRUCTURE.get(), RitualStructureContainer.INSTANCE, null, ResourceKey.create(Registries.RECIPE, Recipes.RITUAL_STRUCTURE.getId())
        ).ifPresent(result -> recipe.set(result.value()));
        return Optional.ofNullable(recipe.get());
    }

    protected void tryCraft(@NotNull RitualContainer container, @NotNull ServerLevel world, @NotNull BlockPos pos, @Nullable Player player) {
        if (!CropariaIf.CONFIG.getInfusor()) {
            return;
        }
        world.getServer().getRecipeManager().getRecipeFor(Recipes.RITUAL.get(), container, world).ifPresentOrElse(recipe -> {
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
        Texts.overlay(player, Texts.translatable(translationKey));
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
