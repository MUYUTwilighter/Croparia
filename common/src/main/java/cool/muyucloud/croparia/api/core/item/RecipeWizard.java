package cool.muyucloud.croparia.api.core.item;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.JsonOps;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.block.Infusor;
import cool.muyucloud.croparia.api.core.block.RitualStand;
import cool.muyucloud.croparia.api.core.recipe.container.RitualStructureContainer;
import cool.muyucloud.croparia.api.core.recipe.util.BlockStatePredicate;
import cool.muyucloud.croparia.api.core.recipe.util.GenericIngredient;
import cool.muyucloud.croparia.api.crop.command.CommonCommandRoot;
import cool.muyucloud.croparia.api.element.ElementsEnum;
import cool.muyucloud.croparia.registry.RecipeTypes;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class RecipeWizard extends Item {
    private static final Gson GSON = new Gson();

    public RecipeWizard(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (!level.isClientSide() || player == null || player.isLocalPlayer()) {
            return InteractionResult.PASS;
        }
        BlockPos targetPos = context.getClickedPos();
        BlockState state = level.getBlockState(targetPos);
        Block target = state.getBlock();
        if (target instanceof RitualStand ritualStand) {
            ResourceLocation id = ritualStand.arch$registryName();
            Optional<BlockState> optionalBlock = getRitualInputBlock(id, context.getLevel(), targetPos);
            if (optionalBlock.isEmpty() || optionalBlock.get().isAir()) {
                player.displayClientMessage(Component.translatable("overlay.croparia.recipe_wizard.ritual.missing.block"), true);
                return InteractionResult.FAIL;
            }
            Optional<ItemStack> optionalIngredient = getItemInput(level, targetPos);
            if (optionalIngredient.isEmpty()) {
                player.displayClientMessage(Component.translatable("overlay.croparia.recipe_wizard.ritual.missing.ingredient"), true);
                return InteractionResult.FAIL;
            }
            ItemStack result = player.getOffhandItem();
            if (result.isEmpty()) {
                player.displayClientMessage(Component.translatable("overlay.croparia.recipe_wizard.ritual.missing.result"), true);
                return InteractionResult.FAIL;
            }
            JsonObject recipe = assembleRitual(ritualStand, optionalBlock.get(), optionalIngredient.get(), result);
            Path path = this.dumpRecipe(RecipeTypes.RITUAL.getId(), recipe);
            this.sendFeedback("chat.croparia.recipe_wizard.ritual", path, player);
            this.addCooldown(player);
            return InteractionResult.SUCCESS;
        } else if (target instanceof Infusor) {
            Optional<ItemStack> optionalIngredient = getItemInput(level, targetPos);
            if (optionalIngredient.isEmpty()) {
                player.displayClientMessage(Component.translatable("overlay.croparia.recipe_wizard.infusor.missing.ingredient"), true);
                return InteractionResult.FAIL;
            }
            ElementsEnum element = Infusor.getElement(state);
            if (element == ElementsEnum.EMPTY) {
                player.displayClientMessage(Component.translatable("overlay.croparia.recipe_wizard.infusor.missing.element"), true);
                return InteractionResult.FAIL;
            }
            ItemStack result = player.getOffhandItem();
            if (result.isEmpty()) {
                player.displayClientMessage(Component.translatable("overlay.croparia.recipe_wizard.infusor.missing.result"), true);
                return InteractionResult.FAIL;
            }
            JsonObject recipe = assembleInfusor(element, optionalIngredient.get(), result);
            Path path = this.dumpRecipe(RecipeTypes.INFUSOR.getId(), recipe);
            this.sendFeedback("chat.croparia.recipe_wizard.infusor", path, player);
            this.addCooldown(player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public void addCooldown(Player player) {
        player.getCooldowns().addCooldown(this, 5);
    }

    public Path dumpRecipe(ResourceLocation recipeType, JsonObject recipe) {
        Path dir = CropariaIf.CONFIG.getDumpPath().resolve(recipeType.getPath());
        File dirFile = dir.toFile();
        if (!dirFile.isDirectory() && !dirFile.mkdirs()) {
            throw new IllegalStateException("Failed to create directory " + dir);
        }
        String recipeName = Util.getFilenameFormattedDateTime();
        Path location = dir.resolve(recipeName + ".json");
        try (JsonWriter writer = new JsonWriter(new FileWriter(location.toFile()))) {
            writer.setIndent("  ");
            GSON.toJson(recipe, writer);
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Failed to dump recipe {}", recipeType, e);
        }
        return location;
    }

    public void sendFeedback(String key, Path path, Player player) {
        MutableComponent location = Component.literal(path.getFileName().toString()).withStyle(
            CommonCommandRoot.openFile(path.toString())
        ).withStyle(CommonCommandRoot.inlineMouseBehavior());
        MutableComponent main = Component.translatable(key, location);
        player.displayClientMessage(main, false);
    }

    public @NotNull JsonObject assembleRitual(RitualStand ritualStand, BlockState block, ItemStack ingredient, ItemStack result) {
        JsonObject root = new JsonObject();
        root.addProperty("type", "croparia:ritual");
        root.addProperty("tier", ritualStand.getTier());
        JsonElement encodeBlock = BlockStatePredicate.Builder.CODEC.encodeStart(
            JsonOps.INSTANCE, BlockStatePredicate.ofState(block)
        ).getOrThrow();
        root.add("block", encodeBlock);
        JsonElement encodeIngredient = GenericIngredient.CODEC.encodeStart(
            JsonOps.INSTANCE, new GenericIngredient(ingredient)
        ).getOrThrow();
        root.add("ingredient", encodeIngredient);
        JsonElement encodeResult = ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, result).getOrThrow();
        root.add("result", encodeResult);
        return root;
    }

    public @NotNull JsonObject assembleInfusor(ElementsEnum element, ItemStack ingredient, ItemStack result) {
        JsonObject root = new JsonObject();
        root.addProperty("type", "croparia:infusor");
        root.addProperty("element", element.getSerializedName());
        JsonElement encodeIngredient = GenericIngredient.CODEC.encodeStart(
            JsonOps.INSTANCE, new GenericIngredient(ingredient)
        ).getOrThrow();
        root.add("ingredient", encodeIngredient);
        JsonElement encodeResult = ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, result).getOrThrow();
        root.add("result", encodeResult);
        return root;
    }

    public @NotNull Optional<BlockState> getRitualInputBlock(ResourceLocation id, Level world, BlockPos pos) {
        RecipeManager recipeManager = world.getRecipeManager();
        AtomicReference<BlockState> result = new AtomicReference<>();
        recipeManager.getRecipeFor(RecipeTypes.RITUAL_STRUCTURE.get(), RitualStructureContainer.INSTANCE, world, id).flatMap(
            recipe -> recipe.value().matches(pos, world)
        ).ifPresent(result::set);
        return Optional.ofNullable(result.get());
    }

    public @NotNull Optional<ItemStack> getItemInput(Level world, BlockPos pos) {
        List<ItemEntity> itemEntities = world.getEntitiesOfClass(ItemEntity.class, new AABB(pos));
        if (itemEntities.isEmpty() || itemEntities.getFirst().isRemoved() || itemEntities.getFirst().getItem().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(itemEntities.getFirst().getItem());
        }
    }
}
