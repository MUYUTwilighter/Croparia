package cool.muyucloud.croparia.block;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.data.ElementsEnum;
import cool.muyucloud.croparia.recipe.InfusorRecipe;
import cool.muyucloud.croparia.recipe.container.InfusorContainer;
import cool.muyucloud.croparia.registry.CropariaBlocks;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.RecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

public class Infusor extends Block {
    protected final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    public static final EnumProperty<ElementsEnum> TYPE = EnumProperty.create("infusor_type", ElementsEnum.class);

    public Infusor() {
        super(Properties.of().strength(1.0F, 1.0F).requiresCorrectToolForDrops());
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, ElementsEnum.EMPTY));
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (world.isClientSide || !CropariaIf.CONFIG.getInfusor()) {
            return ItemInteractionResult.FAIL;
        } else {
            ItemStack itemstack = player.getItemInHand(hand);
            Item item = itemstack.getItem();
            ElementsEnum element = CropariaItems.elementFromPotion(item);
            if (state.getValue(TYPE) == ElementsEnum.EMPTY && element != ElementsEnum.EMPTY) {
                world.setBlockAndUpdate(pos, this.defaultBlockState().setValue(TYPE, element));
                player.getMainHandItem().shrink(1);
                world.addFreshEntity(new ItemEntity(
                    world, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5,
                    new ItemStack(Items.GLASS_BOTTLE)
                ));
                if (world instanceof ServerLevel serverWorld) {
                    world.getEntities(
                        EntityTypeTest.forClass(ItemEntity.class),
                        AABB.of(new BoundingBox(pos)), entity -> !entity.getItem().isEmpty()
                    ).forEach(entity -> {
                        ItemStack input = entity.getItem();
                        this.tryCraft(serverWorld, pos, input, element);
                    });
                }
            } else if (state.getValue(TYPE) != ElementsEnum.EMPTY && player.getMainHandItem().getItem() == Items.GLASS_BOTTLE) {
                world.setBlockAndUpdate(pos, this.defaultBlockState().setValue(TYPE, ElementsEnum.EMPTY));
                player.getMainHandItem().shrink(1);
                world.addFreshEntity(new ItemEntity(world, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, new ItemStack(CropariaItems.getPotion(state.getValue(TYPE)))));
            }
            return ItemInteractionResult.SUCCESS;
        }
    }

    public static ElementsEnum getElement(BlockState state) {
        return state.getBlock() != CropariaBlocks.INFUSOR.get() ? ElementsEnum.EMPTY : state.getValue(TYPE);
    }

    public void onCrafting(InfusorRecipe recipe, InfusorContainer container, Level world, BlockPos pos) {
        ItemStack stack = recipe.assemble(container);
        world.addFreshEntity(new ItemEntity(
            world, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, stack
        ));
        world.setBlockAndUpdate(pos, this.defaultBlockState());
    }

    public void tryCraft(ServerLevel world, BlockPos pos, ItemStack input, ElementsEnum element) {
        RecipeManager manager = world.getServer().getRecipeManager();
        InfusorContainer container = InfusorContainer.of(element, input);
        manager.getRecipeFor(RecipeTypes.INFUSOR.get(), container, world).ifPresent(
            recipe -> onCrafting(recipe.value(), container, world, pos)
        );
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof ItemEntity itemEntity && world instanceof ServerLevel serverWorld) {
            ItemStack input = itemEntity.getItem();
            ElementsEnum element = state.getValue(TYPE);
            this.tryCraft(serverWorld, pos, input, element);
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
}
