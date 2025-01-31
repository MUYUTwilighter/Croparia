package cool.muyucloud.croparia.api.core.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import cool.muyucloud.croparia.api.core.recipe.container.RitualStructureContainer;
import cool.muyucloud.croparia.registry.RecipeSerializers;
import cool.muyucloud.croparia.registry.RecipeTypes;
import cool.muyucloud.croparia.api.math.Char3D;
import cool.muyucloud.croparia.api.math.Char3DWithMark;
import cool.muyucloud.croparia.api.core.recipe.predicate.BlockStatePredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RitualStructure implements Recipe<RitualStructureContainer> {
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private final Map<Character, BlockStatePredicate> keys;
    @NotNull
    protected final List<Char3DWithMark> patterns;

    protected RitualStructure(@NotNull ResourceLocation id, @NotNull Map<Character, BlockStatePredicate> keys, Char3D rawPattern) {
        this.id = id;
        this.patterns = new ArrayList<>(8);
        Vec3i ritualOffset = rawPattern.find('*').orElseThrow(() -> new IllegalArgumentException("Invalid pattern, missing ritual marker"));
        Char3DWithMark pattern = new Char3DWithMark(rawPattern, ritualOffset);
        int i = 0;
        do {
            patterns.add(pattern);
            pattern = pattern.rotate();
            i++;
        } while (i < 4);
        pattern = pattern.mirror();
        i = 0;
        do {
            patterns.add(pattern);
            pattern = pattern.rotate();
            i++;
        } while (i < 4);
        for (Character c : keys.keySet()) {
            if (!Character.isUpperCase(c) || !Character.isAlphabetic(c) || c == '*' || c == '$' || c == ' ' || c == '.') {
                throw new IllegalArgumentException("Invalid translationKey '%s' in %s, must be uppercase letter".formatted(c, id));
            }
        }
        keys = new HashMap<>(keys);
        this.keys = keys;
    }

    public Collection<BlockStatePredicate> getPredicates() {
        return this.keys.values();
    }

    public Optional<BlockStatePredicate> getPredicate(char key) {
        return Optional.ofNullable(this.keys.get(key));
    }

    public Optional<BlockStatePredicate> getPredicate(int x, int y, int z) {
        return getPredicate(getChar(x, y, z));
    }

    public char getChar(int x, int y, int z) {
        return this.patterns.get(0).get(x, y, z);
    }

    public int maxX() {
        return this.patterns.get(0).maxX();
    }

    public int maxY() {
        return this.patterns.get(0).maxY();
    }

    public int maxZ() {
        return this.patterns.get(0).maxZ();
    }

    public @Nullable BlockState matchTransformed(BlockPos origin, Level level, Char3D pattern, BlockState ritualBlock, boolean destroy) {
        List<BlockPos> inputPositions = new LinkedList<>();
        BlockState inputBlock = null;
        for (int x = 0; x < pattern.maxX(); x++) {
            for (int y = 0; y < pattern.maxY(); y++) {
                for (int z = 0; z < pattern.maxZ(); z++) {
                    BlockPos pos = origin.offset(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    char key = pattern.get(x, y, z);
                    if (key == '$') {
                        if (inputBlock != null && !state.equals(inputBlock)) {
                            return null;
                        }
                        inputPositions.add(pos);
                        inputBlock = state;
                    } else if (key == '*') {
                        if (!state.equals(ritualBlock)) {
                            return null;
                        }
                    } else if (key == ' ') {
                        BlockStatePredicate.ANY.test(state);
                    } else if (key == '.') {
                        if (!state.isAir()) {
                            return null;
                        }
                    } else {
                        BlockStatePredicate predicate = this.keys.get(key);
                        if (predicate == null || !predicate.test(state)) {
                            return null;
                        }
                    }
                }
            }
        }
        if (destroy) {
            for (BlockPos pos : inputPositions) {
                level.destroyBlock(pos, false);
            }
        }
        return inputBlock;
    }

    public Optional<BlockState> matchesAndDestroy(BlockPos ritualPos, Level level) {
        BlockState ritualBlock = level.getBlockState(ritualPos);
        for (Char3DWithMark pattern : patterns) {
            BlockState inputBlock = matchTransformed(pattern.getOriginInWorld(ritualPos), level, pattern, ritualBlock, true);
            if (inputBlock != null) {
                return Optional.of(inputBlock);
            }
        }
        return Optional.empty();
    }

    public Optional<BlockState> matches(BlockPos ritualPos, Level level) {
        BlockState ritualBlock = level.getBlockState(ritualPos);
        for (Char3DWithMark pattern : patterns) {
            BlockState inputBlock = matchTransformed(pattern.getOriginInWorld(ritualPos), level, pattern, ritualBlock, false);
            if (inputBlock != null) {
                return Optional.of(inputBlock);
            }
        }
        return Optional.empty();
    }

    public static RitualStructure fromJson(ResourceLocation resourceLocation, JsonObject json) {
        JsonObject rawKeys = GsonHelper.getAsJsonObject(json, "keys");
        Map<Character, BlockStatePredicate> keys = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : rawKeys.entrySet()) {
            String rawKey = entry.getKey();
            if (rawKey.length() != 1) {
                throw new IllegalArgumentException("Invalid translationKey: " + rawKey);
            }
            Character key = rawKey.charAt(0);
            JsonObject rawPredicate = entry.getValue().getAsJsonObject();
            BlockStatePredicate predicate = BlockStatePredicate.Builder.CODEC.parse(JsonOps.INSTANCE, rawPredicate).getOrThrow(false, msg -> {
                throw new IllegalArgumentException(msg);
            }).build();
            keys.put(key, predicate);
        }
        keys = Map.copyOf(keys);

        Char3D pattern = Char3D.CODEC.parse(
            JsonOps.INSTANCE, GsonHelper.getAsJsonArray(json, "pattern")
        ).getOrThrow(false, msg -> {
            throw new IllegalArgumentException(msg);
        });
        return new RitualStructure(resourceLocation, keys, pattern);
    }

    public static RitualStructure fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf inputBuf) {
        Map<Character, BlockStatePredicate> keys = inputBuf.readMap(
            FriendlyByteBuf::readChar, buf -> buf.readJsonWithCodec(BlockStatePredicate.Builder.CODEC).build()
        );
        keys = Map.copyOf(keys);
        Char3D pattern = inputBuf.readJsonWithCodec(Char3D.CODEC);
        return new RitualStructure(resourceLocation, keys, pattern);
    }

    public void toNetwork(FriendlyByteBuf outputBuf) {
        outputBuf.writeMap(this.keys,
            (buf, character) -> buf.writeChar(character),
            (buf, predicate) -> buf.writeJsonWithCodec(BlockStatePredicate.Builder.CODEC, predicate.getBuilder())
        );
        outputBuf.writeJsonWithCodec(Char3D.CODEC, this.patterns.get(0));
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.RITUAL_STRUCTURE.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypes.RITUAL_STRUCTURE.get();
    }

    /**
     * Please use {@link #matchesAndDestroy(BlockPos, Level)} instead
     */
    @Deprecated
    @Override
    public boolean matches(RitualStructureContainer container, Level level) {
        return true;
    }

    @Deprecated
    @Override
    public @NotNull ItemStack assemble(RitualStructureContainer container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Deprecated
    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return false;
    }

    @Deprecated
    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }
}
