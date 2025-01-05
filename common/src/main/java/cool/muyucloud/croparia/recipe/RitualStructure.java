package cool.muyucloud.croparia.recipe;

import cool.muyucloud.croparia.recipe.container.RitualStructureContainer;
import cool.muyucloud.croparia.registry.RecipeSerializers;
import cool.muyucloud.croparia.registry.RecipeTypes;
import cool.muyucloud.croparia.util.math.Char3D;
import cool.muyucloud.croparia.util.math.Char3DWithMark;
import cool.muyucloud.croparia.util.predicate.BlockStatePredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Vec3i;
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
    private final Map<Character, BlockStatePredicate> keys;
    @NotNull
    protected final List<Char3DWithMark> patterns;

    public RitualStructure(@NotNull Map<String, BlockStatePredicate.Builder> keys, Char3D rawPattern) {
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
        HashMap<Character, BlockStatePredicate> raw = new HashMap<>();
        keys.forEach((string, builder) -> {
            char c = string.charAt(0);
            if (!Character.isUpperCase(c) || !Character.isAlphabetic(c) || c == '*' || c == '$' || c == ' ' || c == '.') {
                throw new IllegalArgumentException("Invalid key '%s' in %s, must be uppercase letter".formatted(c, this));
            }
            raw.put(string.charAt(0), builder.build());
        });
        this.keys = Map.copyOf(raw);
    }

    public Map<String, BlockStatePredicate.Builder> getKeys() {
        Map<String, BlockStatePredicate.Builder> map = new HashMap<>();
        this.keys.forEach((character, predicate) -> map.put(character.toString(), predicate.getBuilder()));
        return map;
    }

    public Char3DWithMark getPattern() {
        return this.patterns.get(0);
    }

    public Collection<BlockStatePredicate> getPredicates() {
        return this.keys.values();
    }

    public Optional<BlockStatePredicate> getPredicate(char key) {
        return Optional.ofNullable(this.keys.get(key));
    }

    public char getChar(int x, int y, int z) {
        return this.patterns.get(0).get(x, y, z);
    }

    public int maxX() {
        return this.patterns.getFirst().maxX();
    }

    public int maxY() {
        return this.patterns.getFirst().maxY();
    }

    public int maxZ() {
        return this.patterns.getFirst().maxZ();
    }

    public @Nullable BlockState matchTransformed(BlockPos origin, Level level, Char3D pattern, BlockState ritualBlock) {
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
                    } else if (key == '.') {
                        if (!state.isAir()) {
                            return null;
                        }
                    } else if (key == ' ') {
                    } else {
                        BlockStatePredicate predicate = this.keys.get(key);
                        if (predicate == null || !predicate.test(state)) {
                            return null;
                        }
                    }
                }
            }
        }
        for (BlockPos pos : inputPositions) {
            level.destroyBlock(pos, false);
        }
        return inputBlock;
    }

    public Optional<BlockState> matches(BlockPos ritualPos, Level level) {
        BlockState ritualBlock = level.getBlockState(ritualPos);
        for (Char3DWithMark pattern : patterns) {
            BlockState inputBlock = matchTransformed(pattern.getOriginInWorld(ritualPos), level, pattern, ritualBlock);
            if (inputBlock != null) {
                return Optional.of(inputBlock);
            }
        }
        return Optional.empty();
    }

    @Override
    @Deprecated
    public boolean matches(RitualStructureContainer recipeInput, Level level) {
        return true;
    }

    @Override
    @Deprecated
    public ItemStack assemble(RitualStructureContainer recipeInput, HolderLookup.Provider provider) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    @Override
    @Deprecated
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    @Deprecated
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.RITUAL_STRUCTURE.get();
    }

    @Override
    @Deprecated
    public @NotNull RecipeType<?> getType() {
        return RecipeTypes.RITUAL_STRUCTURE.get();
    }
}
