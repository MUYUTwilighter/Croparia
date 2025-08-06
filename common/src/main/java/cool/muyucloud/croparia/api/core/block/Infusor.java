package cool.muyucloud.croparia.api.core.block;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.item.RecipeWizard;
import cool.muyucloud.croparia.api.core.recipe.InfusorRecipe;
import cool.muyucloud.croparia.api.core.recipe.container.InfusorContainer;
import cool.muyucloud.croparia.api.element.Element;
import cool.muyucloud.croparia.api.element.item.ElementalPotion;
import cool.muyucloud.croparia.registry.CropariaBlocks;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.Recipes;
import cool.muyucloud.croparia.util.DynamicProperty;
import cool.muyucloud.croparia.util.ItemPlaceable;
import cool.muyucloud.croparia.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Infusor extends Block implements ItemPlaceable {
    protected final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    public static final DynamicProperty<Element> ELEMENT = new DynamicProperty<>("element", Element.class, Element.STRING_REGISTRY);

    public Infusor(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ELEMENT, Element.EMPTY));
    }

    @Override
    protected @NotNull InteractionResult useItemOn(
        ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
        BlockHitResult blockHitResult
    ) {
        if (!world.isClientSide) {
            Item item = itemStack.getItem();
            if (item instanceof ElementalPotion potion && this.tryInfuse(world, pos, potion, itemStack, player)) {
                if (world instanceof ServerLevel serverWorld) {
                    this.forceCraft(serverWorld, pos, player);
                }
                return InteractionResult.SUCCESS;
            } else if (
                ElementalPotion.fromElement(state.getValue(ELEMENT)).map(potion -> potion.getCraftingRemainder().getItem() == item).orElse(false)
                    && this.tryDefuse(world, pos, itemStack, player)
            ) {
                return InteractionResult.SUCCESS;
            } else if (!(item instanceof RecipeWizard) && hand == InteractionHand.MAIN_HAND) {
                Util.placeItem(world, pos, itemStack);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    public boolean tryInfuse(Level world, BlockPos pos, ElementalPotion potion, @NotNull ItemStack stack, @Nullable Player player) {
        BlockState state = world.getBlockState(pos);
        if (state.getValue(ELEMENT) == Element.EMPTY) {
            world.setBlockAndUpdate(pos, CropariaBlocks.INFUSOR.get().defaultBlockState().setValue(ELEMENT, potion.getElement()));
            world.playSound(null, pos, SoundEvent.createVariableRangeEvent(CropariaIf.of("block.infusor.infuse")), SoundSource.BLOCKS, 1.0F, 1.0F);
        } else {
            return false;
        }
        if (player != null && player.getAbilities().instabuild) {
            return true;
        }
        stack.shrink(1);
        ItemStack returnStack = potion.getCraftingRemainder();
        Util.exportItem(world, pos, returnStack, player);
        return true;
    }

    public void forceCraft(ServerLevel world, BlockPos pos, @Nullable Player player) {
        Element element = world.getBlockState(pos).getValue(ELEMENT);
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
        Element element = state.getValue(ELEMENT);
        if (element != Element.EMPTY && ElementalPotion.fromElement(element).orElseThrow().getCraftingRemainder().getItem() == item) {
            world.setBlockAndUpdate(pos, CropariaBlocks.INFUSOR.get().defaultBlockState().setValue(ELEMENT, Element.EMPTY));
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

    public static Element getElement(BlockState state) {
        return state.getBlock() != CropariaBlocks.INFUSOR.get() ? Element.EMPTY : state.getValue(ELEMENT);
    }

    public void onCrafting(InfusorRecipe recipe, InfusorContainer container, Level world, BlockPos pos, @Nullable Player player) {
        ItemStack stack = recipe.assemble(container);
        Util.exportItem(world, pos, stack, player);
        world.setBlockAndUpdate(pos, this.defaultBlockState());
        world.playSound(null, pos, SoundEvent.createVariableRangeEvent(CropariaIf.of("block.infusor.craft")), SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    public void tryCraft(ServerLevel world, BlockPos pos, ItemStack input, Element element, Player player) {
        if (!CropariaIf.CONFIG.getInfusor()) {
            return;
        }
        RecipeManager manager = world.getServer().getRecipeManager();
        InfusorContainer container = InfusorContainer.of(element, input);
        manager.getRecipeFor(Recipes.INFUSOR.get(), container, world).ifPresent(
            recipe -> onCrafting(recipe.value(), container, world, pos, player)
        );
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof ItemEntity itemEntity && world instanceof ServerLevel serverWorld && CropariaIf.CONFIG.getInfusor()) {
            ItemStack input = itemEntity.getItem();
            Element element = state.getValue(ELEMENT);
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
        builder.add(ELEMENT);
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
