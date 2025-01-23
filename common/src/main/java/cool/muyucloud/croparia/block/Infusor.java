package cool.muyucloud.croparia.block;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.data.ElementsEnum;
import cool.muyucloud.croparia.item.ElementalPotion;
import cool.muyucloud.croparia.item.RecipeWizard;
import cool.muyucloud.croparia.recipe.InfusorRecipe;
import cool.muyucloud.croparia.recipe.container.InfusorContainer;
import cool.muyucloud.croparia.registry.CropariaBlocks;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.RecipeTypes;
import cool.muyucloud.croparia.util.ItemPlaceable;
import cool.muyucloud.croparia.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Infusor extends Block implements ItemPlaceable {
    protected final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    public static final EnumProperty<ElementsEnum> TYPE = EnumProperty.create("infusor_type", ElementsEnum.class);

    public Infusor() {
        super(Properties.of().strength(1.0F, 1.0F).requiresCorrectToolForDrops());
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, ElementsEnum.EMPTY));
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(
        ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
        BlockHitResult blockHitResult
    ) {
        if (!world.isClientSide) {
            Item item = itemStack.getItem();
            if (item instanceof ElementalPotion potion && this.tryInfuse(world, pos, potion, itemStack, player)) {
                if (world instanceof ServerLevel serverWorld) {
                    this.forceCraft(serverWorld, pos, player);
                }
                return ItemInteractionResult.SUCCESS;
            } else if (
                player.getItemInHand(hand).getItem() == ElementalPotion.fromElement(state.getValue(TYPE)).orElseThrow().getCraftingRemainingItem()
                    && this.tryDefuse(world, pos, itemStack, player)
            ) {
                return ItemInteractionResult.SUCCESS;
            } else if (!(item instanceof RecipeWizard)) {
                Util.placeItem(world, pos, itemStack);
                return ItemInteractionResult.CONSUME;
            }
        }
        return ItemInteractionResult.FAIL;
    }

    public boolean tryInfuse(Level world, BlockPos pos, ElementalPotion potion, @NotNull ItemStack stack, @Nullable Player player) {
        BlockState state = world.getBlockState(pos);
        if (state.getValue(TYPE) == ElementsEnum.EMPTY) {
            world.setBlockAndUpdate(pos, CropariaBlocks.INFUSOR.get().defaultBlockState().setValue(TYPE, potion.getElement()));
        } else {
            return false;
        }
        if (player != null && player.getAbilities().instabuild) {
            return true;
        }
        stack.shrink(1);
        ItemStack returnStack = Objects.requireNonNull(potion.getCraftingRemainingItem()).getDefaultInstance();
        Util.exportItem(world, pos, returnStack, player);
        return true;
    }

    public void forceCraft(ServerLevel world, BlockPos pos, @Nullable Player player) {
        ElementsEnum element = world.getBlockState(pos).getValue(TYPE);
        world.getEntities(EntityTypeTest.forClass(ItemEntity.class),
            AABB.of(new BoundingBox(pos)), entity -> !entity.getItem().isEmpty()
        ).forEach(entity -> {
            ItemStack input = entity.getItem();
            this.tryCraft(world, pos, input, element, player != null ? player : entity.getOwner() instanceof Player owner ? owner : null);
        });
    }

    public boolean tryDefuse(Level world, BlockPos pos, ItemStack stack, @Nullable Player player) {
        Item item = stack.getItem();
        BlockState state = world.getBlockState(pos);
        ElementsEnum element = state.getValue(TYPE);
        if (element != ElementsEnum.EMPTY && ElementalPotion.fromElement(element).orElseThrow().getCraftingRemainingItem() == item) {
            world.setBlockAndUpdate(pos, CropariaBlocks.INFUSOR.get().defaultBlockState().setValue(TYPE, ElementsEnum.EMPTY));
        } else {
            return false;
        }
        if (player == null || !player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        ItemStack returnStack = ElementalPotion.fromElement(element).orElseThrow().getDefaultInstance();
        Util.exportItem(world, pos, returnStack, player);
        return false;
    }

    public static ElementsEnum getElement(BlockState state) {
        return state.getBlock() != CropariaBlocks.INFUSOR.get() ? ElementsEnum.EMPTY : state.getValue(TYPE);
    }

    public void onCrafting(InfusorRecipe recipe, InfusorContainer container, Level world, BlockPos pos, @Nullable Player player) {
        ItemStack stack = recipe.assemble(container);
        Util.exportItem(world, pos, stack, player);
        world.setBlockAndUpdate(pos, this.defaultBlockState());
    }

    public void tryCraft(ServerLevel world, BlockPos pos, ItemStack input, ElementsEnum element, Player player) {
        RecipeManager manager = world.getServer().getRecipeManager();
        InfusorContainer container = InfusorContainer.of(element, input);
        manager.getRecipeFor(RecipeTypes.INFUSOR.get(), container, world).ifPresent(
            recipe -> onCrafting(recipe.value(), container, world, pos, player)
        );
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof ItemEntity itemEntity && world instanceof ServerLevel serverWorld && CropariaIf.CONFIG.getInfusor()) {
            ItemStack input = itemEntity.getItem();
            ElementsEnum element = state.getValue(TYPE);
            this.tryCraft(serverWorld, pos, input, element, itemEntity.getOwner() instanceof Player player ? player : null);
        }
    }

    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.SHAPE;
    }

    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.SHAPE;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    @Override
    public @NotNull Item asItem() {
        return CropariaItems.INFUSOR.get();
    }

    @Override
    public void placeItem(Level world, BlockPos pos, ItemStack stack) {
        Util.placeItem(world, pos, stack);
    }
}
