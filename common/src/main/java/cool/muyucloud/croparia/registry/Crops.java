package cool.muyucloud.croparia.registry;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.data.crop.Crop;
import cool.muyucloud.croparia.data.crop.CropFileHandler;
import cool.muyucloud.croparia.data.crop.CropType;
import cool.muyucloud.croparia.data.crop.RawCrop;
import dev.architectury.platform.Platform;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Crops {
    // All crops, including KubeJS crops and file crops
    protected static final Map<String, Crop> CROPS = new HashMap<>();
    // Crops including here and CompatCrops, but not KubeJS definition and file definition
    protected static final Map<String, Crop> BUILTIN_CROPS = new HashMap<>();

    public static CompletableFuture<Suggestions> cropSuggestions(String input, int start) {
        SuggestionsBuilder suggestionsBuilder = new SuggestionsBuilder(input, start);
        Crops.cropNames().forEach(suggestionsBuilder::suggest);
        return suggestionsBuilder.buildFuture();
    }

    public static int size() {
        return CROPS.size();
    }

    public static int builtinSize() {
        return BUILTIN_CROPS.size();
    }

    @Nullable
    public static Crop forName(@NotNull String name) {
        return CROPS.get(name);
    }

    public static Collection<String> cropNames() {
        return Collections.unmodifiableSet(CROPS.keySet());
    }

    public static void forEachCrop(@NotNull Consumer<Crop> consumer) {
        CROPS.values().forEach(consumer);
    }

    public static void forEachBuiltinCrop(@NotNull Consumer<Crop> consumer) {
        BUILTIN_CROPS.values().forEach(consumer);
    }

    /**
     * Record to both {@link #CROPS} and {@link #BUILTIN_CROPS}, it will be registered by {@link #register()}
     */
    protected static void recordBuiltin(@NotNull Crop crop) {
        if (CROPS.containsKey(crop.getName())) {
            CropariaIf.LOGGER.error("Built-in crop \"{}\" already exists", crop.getName());
            return;
        }
        BUILTIN_CROPS.put(crop.getName(), crop);
        CROPS.put(crop.getName(), crop);
    }

    /**
     * Only record to CROPS, it will not be registered by {@link Crops#register()}
     * @return {@code true} if the crop is recorded, {@code false} if it already exists
     */
    public static boolean recordCustom(@NotNull Crop crop) {
        if (CROPS.containsKey(crop.getName())) {
            return false;
        }
        CROPS.put(crop.getName(), crop);
        return true;
    }

    /**
     * Add a crop for croparia material with specified translation key.
     *
     * @param name           crop name
     * @param material       material id which the crop grows, could be item ID or item tag
     * @param color          int value of color
     * @param tier           tier
     * @param type           crop type that specifies the textures. See also {@link CropType}
     * @param translationKey translation key for the crop, used for formatting item & block names.
     */
    public static @NotNull Crop croparia(
        @NotNull String name, @NotNull String material, int color, int tier, @NotNull CropType type,
        @NotNull String translationKey
    ) {
        Crop crop = Crop.create(name, material, color, tier, type, translationKey, Map.of()).orElseThrow(
            () -> new IllegalArgumentException("Vanilla crop %s failed to create".formatted(name))
        );
        if (CropariaIf.CONFIG.inBlacklist(name, "croparia")) {
            CropariaIf.LOGGER.debug("Skipped croparia crop \"{}\" due to blacklist", name);
        } else {
            recordBuiltin(crop);
        }
        return crop;
    }

    public static final Crop ELEMENTAL = croparia("elemental", "croparia:elematilius", 0x712389, 2, CropType.CROP, "item.croparia.elematilius");
    public static final Crop EARTH = croparia("earth", "croparia:elemental_earth", 0xE5C8BB, 3, CropType.CROP, "item.croparia.elemental_earth");
    public static final Crop WATER = croparia("water", "croparia:elemental_water", 0x2A5AB2, 4, CropType.CROP, "item.croparia.elemental_water");
    public static final Crop FIRE = croparia("fire", "croparia:elemental_fire", 0xC65957, 6, CropType.CROP, "item.croparia.elemental_fire");
    public static final Crop AIR = croparia("air", "croparia:elemental_air", 0xA2A9B5, 7, CropType.CROP, "item.croparia.elemental_air");

    /**
     * Add a crop for vanilla material with specified translation key.
     *
     * @param name           crop name
     * @param material       material id which the crop grows, could be item ID or item tag
     * @param color          int value of color
     * @param tier           tier
     * @param type           crop type that specifies the textures. See also {@link CropType}
     * @param translationKey translation key for the crop, used for formatting item & block names.
     */
    public static @NotNull Crop vanilla(
        @NotNull String name, @NotNull String material, int color, int tier, @NotNull CropType type,
        @NotNull String translationKey
    ) {
        Crop crop = Crop.create(name, material, color, tier, type, translationKey, Map.of()).orElseThrow(
            () -> new IllegalArgumentException("Vanilla crop %s failed to create".formatted(name))
        );
        if (CropariaIf.CONFIG.inBlacklist(name, "minecraft")) {
            CropariaIf.LOGGER.debug("Skipped vanilla crop \"{}\" due to blacklist", name);
        } else {
            recordBuiltin(crop);
        }
        return crop;
    }

    public static final Crop COAL = vanilla("coal", Objects.requireNonNull(Items.COAL.arch$registryName()).toString(), 0x333333, 1, CropType.CROP, Items.COAL.getDescriptionId());
    public static final Crop IRON = vanilla("iron", Objects.requireNonNull(Items.IRON_INGOT.arch$registryName()).toString(), 0xd8d8d8, 2, CropType.CROP, Items.IRON_INGOT.getDescriptionId());
    public static final Crop GOLD = vanilla("gold", Objects.requireNonNull(Items.GOLD_INGOT.arch$registryName()).toString(), 0xffff8b, 2, CropType.CROP, Items.GOLD_INGOT.getDescriptionId());
    public static final Crop LAPIS = vanilla("lapis", Objects.requireNonNull(Items.LAPIS_LAZULI.arch$registryName()).toString(), 0x7497ea, 3, CropType.CROP, Items.LAPIS_LAZULI.getDescriptionId());
    public static final Crop REDSTONE = vanilla("redstone", Objects.requireNonNull(Items.REDSTONE.arch$registryName()).toString(), 0xff2626, 3, CropType.CROP, Items.REDSTONE.getDescriptionId());
    public static final Crop DIAMOND = vanilla("diamond", Objects.requireNonNull(Items.DIAMOND.arch$registryName()).toString(), 0x8cf4e2, 4, CropType.CROP, Items.DIAMOND.getDescriptionId());
    public static final Crop EMERALD = vanilla("emerald", Objects.requireNonNull(Items.EMERALD.arch$registryName()).toString(), 0x17dd62, 4, CropType.CROP, Items.EMERALD.getDescriptionId());
    public static final Crop CLAY = vanilla("clay", Objects.requireNonNull(Items.CLAY.arch$registryName()).toString(), 0x9aa3b3, 1, CropType.CROP, Items.CLAY.getDescriptionId());
    public static final Crop GLOWSTONE = vanilla("glowstone", Objects.requireNonNull(Items.GLOWSTONE.arch$registryName()).toString(), 0xffff00, 3, CropType.CROP, Items.GLOWSTONE.getDescriptionId());
    public static final Crop QUARTZ = vanilla("quartz", Objects.requireNonNull(Items.QUARTZ.arch$registryName()).toString(), 0xdfd8cf, 3, CropType.CROP, Items.QUARTZ.getDescriptionId());
    public static final Crop SHARD = vanilla("shard", Objects.requireNonNull(Items.PRISMARINE_SHARD.arch$registryName()).toString(), 0x73b5aa, 2, CropType.CROP, Items.PRISMARINE_SHARD.getDescriptionId());
    public static final Crop CRYSTAL = vanilla("crystal", Objects.requireNonNull(Items.PRISMARINE_CRYSTALS.arch$registryName()).toString(), 0xcbe7e0, 2, CropType.CROP, Items.PRISMARINE_CRYSTALS.getDescriptionId());
    public static final Crop ENDER = vanilla("ender", Objects.requireNonNull(Items.ENDER_PEARL.arch$registryName()).toString(), 0x8cf4e2, 3, CropType.MONSTER, Items.ENDER_PEARL.getDescriptionId());
    public static final Crop BONE = vanilla("bone", Objects.requireNonNull(Items.BONE.arch$registryName()).toString(), 0xeceac9, 2, CropType.MONSTER, Items.BONE.getDescriptionId());
    public static final Crop EYE = vanilla("eye", Objects.requireNonNull(Items.SPIDER_EYE.arch$registryName()).toString(), 0xeceac9, 2, CropType.MONSTER, Items.SPIDER_EYE.getDescriptionId());
    public static final Crop POWDER = vanilla("powder", Objects.requireNonNull(Items.GUNPOWDER.arch$registryName()).toString(), 0x727272, 2, CropType.MONSTER, Items.GUNPOWDER.getDescriptionId());
    public static final Crop PAPER = vanilla("paper", Objects.requireNonNull(Items.PAPER.arch$registryName()).toString(), 0xeaeaea, 1, CropType.CROP, Items.PAPER.getDescriptionId());
    public static final Crop SUGAR = vanilla("sugar", Objects.requireNonNull(Items.SUGAR.arch$registryName()).toString(), 0xf5ffff, 1, CropType.CROP, Items.SUGAR.getDescriptionId());
    public static final Crop CHARCOAL = vanilla("charcoal", Objects.requireNonNull(Items.CHARCOAL.arch$registryName()).toString(), 0x53493a, 1, CropType.CROP, Items.CHARCOAL.getDescriptionId());
    public static final Crop FLINT = vanilla("flint", Objects.requireNonNull(Items.FLINT.arch$registryName()).toString(), 0x565656, 1, CropType.CROP, Items.FLINT.getDescriptionId());
    public static final Crop SNOWBALL = vanilla("snowball", Objects.requireNonNull(Items.SNOWBALL.arch$registryName()).toString(), 0xfbffff, 1, CropType.CROP, Items.SNOWBALL.getDescriptionId());
    public static final Crop FIREWORK = vanilla("firework", Objects.requireNonNull(Items.FIREWORK_STAR.arch$registryName()).toString(), 0x575757, 1, CropType.CROP, Items.FIREWORK_STAR.getDescriptionId());
    public static final Crop NETHER = vanilla("nether", Objects.requireNonNull(Items.NETHER_BRICK.arch$registryName()).toString(), 0x46262c, 3, CropType.CROP, Items.NETHER_BRICK.getDescriptionId());
    public static final Crop BOTTLE = vanilla("bottle", Objects.requireNonNull(Items.GLASS_BOTTLE.arch$registryName()).toString(), 0xe4e4e4, 1, CropType.CROP, Items.GLASS_BOTTLE.getDescriptionId());
    public static final Crop FOOT = vanilla("foot", Objects.requireNonNull(Items.RABBIT_FOOT.arch$registryName()).toString(), 0xcfa978, 1, CropType.ANIMAL, Items.RABBIT_FOOT.getDescriptionId());
    public static final Crop HIDE = vanilla("hide", Objects.requireNonNull(Items.RABBIT_HIDE.arch$registryName()).toString(), 0xc79e67, 1, CropType.ANIMAL, Items.RABBIT_HIDE.getDescriptionId());
    public static final Crop LEATHER = vanilla("leather", Objects.requireNonNull(Items.LEATHER.arch$registryName()).toString(), 0xc65c35, 1, CropType.ANIMAL, Items.LEATHER.getDescriptionId());
    public static final Crop FEATHER = vanilla("feather", Objects.requireNonNull(Items.FEATHER.arch$registryName()).toString(), 0xffffff, 1, CropType.ANIMAL, Items.FEATHER.getDescriptionId());
    public static final Crop BLAZE = vanilla("blaze", Objects.requireNonNull(Items.BLAZE_ROD.arch$registryName()).toString(), 0xffcb00, 3, CropType.MONSTER, Items.BLAZE_ROD.getDescriptionId());
    public static final Crop GHAST = vanilla("ghast", Objects.requireNonNull(Items.GHAST_TEAR.arch$registryName()).toString(), 0xe3fde8, 4, CropType.MONSTER, Items.GHAST_TEAR.getDescriptionId());
    public static final Crop MAGMA = vanilla("magma", Objects.requireNonNull(Items.MAGMA_CREAM.arch$registryName()).toString(), 0xcea025, 3, CropType.MONSTER, Items.MAGMA_CREAM.getDescriptionId());
    public static final Crop SHELL = vanilla("shell", Objects.requireNonNull(Items.SHULKER_SHELL.arch$registryName()).toString(), 0x9e749e, 4, CropType.MONSTER, Items.SHULKER_SHELL.getDescriptionId());
    public static final Crop STAR = vanilla("star", Objects.requireNonNull(Items.NETHER_STAR.arch$registryName()).toString(), 0xf0f3f3, 6, CropType.MONSTER, Items.NETHER_STAR.getDescriptionId());
    public static final Crop STRING = vanilla("string", Objects.requireNonNull(Items.STRING.arch$registryName()).toString(), 0xf7f7f7, 2, CropType.MONSTER, Items.STRING.getDescriptionId());
    public static final Crop SLIME = vanilla("slime", Objects.requireNonNull(Items.SLIME_BALL.arch$registryName()).toString(), 0x84c873, 2, CropType.MONSTER, Items.SLIME_BALL.getDescriptionId());
    public static final Crop ZOMBIE = vanilla("zombie", Objects.requireNonNull(Items.ROTTEN_FLESH.arch$registryName()).toString(), 0xbd5d37, 2, CropType.MONSTER, Items.ROTTEN_FLESH.getDescriptionId());
    public static final Crop VINE = vanilla("vine", Objects.requireNonNull(Items.VINE.arch$registryName()).toString(), 0x1b4509, 1, CropType.NATURE, Items.VINE.getDescriptionId());
    public static final Crop WEEPING_VINES = vanilla("weeping_vines", Objects.requireNonNull(Items.WEEPING_VINES.arch$registryName()).toString(), 0x7a0000, 1, CropType.NATURE, Items.WEEPING_VINES.getDescriptionId());
    public static final Crop TWISTING_VINES = vanilla("twisting_vines", Objects.requireNonNull(Items.TWISTING_VINES.arch$registryName()).toString(), 0x14b283, 1, CropType.NATURE, Items.TWISTING_VINES.getDescriptionId());
    public static final Crop LILY_PAD = vanilla("lilypad", Objects.requireNonNull(Items.LILY_PAD.arch$registryName()).toString(), 0xc5f14, 1, CropType.NATURE, Items.LILY_PAD.getDescriptionId());
    public static final Crop BUSH = vanilla("bush", Objects.requireNonNull(Items.DEAD_BUSH.arch$registryName()).toString(), 0x946428, 1, CropType.NATURE, Items.DEAD_BUSH.getDescriptionId());
    public static final Crop GRASS = vanilla("grass", Objects.requireNonNull(Items.SHORT_GRASS.arch$registryName()).toString(), 0x820510b, 1, CropType.NATURE, Items.SHORT_GRASS.getDescriptionId());
    public static final Crop LARGE_FERN = vanilla("large_fern", Objects.requireNonNull(Items.LARGE_FERN.arch$registryName()).toString(), 0x4a7240, 1, CropType.NATURE, Items.LARGE_FERN.getDescriptionId());
    public static final Crop TALL_GRASS = vanilla("tall_grass", Objects.requireNonNull(Items.TALL_GRASS.arch$registryName()).toString(), 0x2f4728, 1, CropType.NATURE, Items.TALL_GRASS.getDescriptionId());
    public static final Crop FERN = vanilla("fern", Objects.requireNonNull(Items.FERN.arch$registryName()).toString(), 0x1b4509, 1, CropType.NATURE, Items.FERN.getDescriptionId());
    public static final Crop OAK = vanilla("oak", Objects.requireNonNull(Items.OAK_PLANKS.arch$registryName()).toString(), 0x9d824c, 1, CropType.NATURE, Items.OAK_PLANKS.getDescriptionId());
    public static final Crop SPRUCE = vanilla("spruce", Objects.requireNonNull(Items.SPRUCE_PLANKS.arch$registryName()).toString(), 0x795933, 1, CropType.NATURE, Items.SPRUCE_PLANKS.getDescriptionId());
    public static final Crop BIRCH = vanilla("birch", Objects.requireNonNull(Items.BIRCH_PLANKS.arch$registryName()).toString(), 0xc6b579, 1, CropType.NATURE, Items.BIRCH_PLANKS.getDescriptionId());
    public static final Crop JUNGLE = vanilla("jungle", Objects.requireNonNull(Items.JUNGLE_PLANKS.arch$registryName()).toString(), 0xbd8c6a, 1, CropType.NATURE, Items.JUNGLE_PLANKS.getDescriptionId());
    public static final Crop ACACIA = vanilla("acacia", Objects.requireNonNull(Items.ACACIA_PLANKS.arch$registryName()).toString(), 0xb86236, 1, CropType.NATURE, Items.ACACIA_PLANKS.getDescriptionId());
    public static final Crop DARK_OAK = vanilla("dark_oak", Objects.requireNonNull(Items.DARK_OAK_PLANKS.arch$registryName()).toString(), 0x4e3118, 1, CropType.NATURE, Items.DARK_OAK_PLANKS.getDescriptionId());
    public static final Crop MANGROVE = vanilla("mangrove", Objects.requireNonNull(Items.MANGROVE_PLANKS.arch$registryName()).toString(), 0x7d4133, 1, CropType.NATURE, Items.MANGROVE_PLANKS.getDescriptionId());
    public static final Crop CHERRY = vanilla("cherry", Objects.requireNonNull(Items.CHERRY_PLANKS.arch$registryName()).toString(), 0xe3b1ab, 1, CropType.NATURE, Items.CHERRY_PLANKS.getDescriptionId());
    public static final Crop BAMBOO = vanilla("bamboo", Objects.requireNonNull(Items.BAMBOO_PLANKS.arch$registryName()).toString(), 0xe0ca69, 1, CropType.NATURE, Items.BAMBOO_PLANKS.getDescriptionId());
    public static final Crop CRIMSON = vanilla("crimson", Objects.requireNonNull(Items.CRIMSON_PLANKS.arch$registryName()).toString(), 0x5b2f41, 1, CropType.NATURE, Items.CRIMSON_PLANKS.getDescriptionId());
    public static final Crop WARPED = vanilla("warped", Objects.requireNonNull(Items.WARPED_PLANKS.arch$registryName()).toString(), 0x388180, 1, CropType.NATURE, Items.WARPED_PLANKS.getDescriptionId());
    public static final Crop APPLE = vanilla("apple", Objects.requireNonNull(Items.APPLE.arch$registryName()).toString(), 0xff1c2b, 1, CropType.FOOD, Items.APPLE.getDescriptionId());
    public static final Crop GOLDEN_APPLE = vanilla("golden_apple", Objects.requireNonNull(Items.GOLDEN_APPLE.arch$registryName()).toString(), 0xffffb0, 3, CropType.FOOD, Items.GOLDEN_APPLE.getDescriptionId());
    public static final Crop BREAD = vanilla("bread", Objects.requireNonNull(Items.BREAD.arch$registryName()).toString(), 0x9e7325, 1, CropType.FOOD, Items.BREAD.getDescriptionId());
    public static final Crop EGG = vanilla("egg", Objects.requireNonNull(Items.EGG.arch$registryName()).toString(), 0xdfce9b, 1, CropType.FOOD, Items.EGG.getDescriptionId());
    public static final Crop TURTLE_EGG = vanilla("turtle_egg", Objects.requireNonNull(Items.TURTLE_EGG.arch$registryName()).toString(), 0x58ceaf, 1, CropType.FOOD, Items.TURTLE_EGG.getDescriptionId());
    public static final Crop SNIFFER_EGG = vanilla("sniffer_egg", Objects.requireNonNull(Items.SNIFFER_EGG.arch$registryName()).toString(), 0xb1413f, 1, CropType.FOOD, Items.SNIFFER_EGG.getDescriptionId());
    public static final Crop TROPICAL_FISH = vanilla("clownfish", Objects.requireNonNull(Items.TROPICAL_FISH.arch$registryName()).toString(), 0xf29965, 1, CropType.FOOD, Items.TROPICAL_FISH.getDescriptionId());
    public static final Crop PUFFER_FISH = vanilla("pufferfish", Objects.requireNonNull(Items.PUFFERFISH.arch$registryName()).toString(), 0xc5b200, 1, CropType.FOOD, Items.PUFFERFISH.getDescriptionId());
    public static final Crop COOKIE = vanilla("cookie", Objects.requireNonNull(Items.COOKIE.arch$registryName()).toString(), 0xd9833e, 1, CropType.FOOD, Items.COOKIE.getDescriptionId());
    public static final Crop CHORUS = vanilla("chorus", Objects.requireNonNull(Items.CHORUS_FRUIT.arch$registryName()).toString(), 0xaa85aa, 3, CropType.FOOD, Items.CHORUS_FRUIT.getDescriptionId());
    public static final Crop BEEF = vanilla("raw_beef", Objects.requireNonNull(Items.BEEF.arch$registryName()).toString(), 0xe24940, 1, CropType.FOOD, Items.BEEF.getDescriptionId());
    public static final Crop PORKSHOP = vanilla("raw_porc", Objects.requireNonNull(Items.PORKCHOP.arch$registryName()).toString(), 0xff8c8c, 1, CropType.FOOD, Items.PORKCHOP.getDescriptionId());
    public static final Crop COD = vanilla("fish", Objects.requireNonNull(Items.COD.arch$registryName()).toString(), 0xc6a271, 1, CropType.FOOD, Items.COD.getDescriptionId());
    public static final Crop SALMON = vanilla("salmon", Objects.requireNonNull(Items.SALMON.arch$registryName()).toString(), 0x9e4b49, 1, CropType.FOOD, Items.SALMON.getDescriptionId());
    public static final Crop RAW_CHICKEN = vanilla("raw_chicken", Objects.requireNonNull(Items.CHICKEN.arch$registryName()).toString(), 0xefbcac, 1, CropType.FOOD, Items.CHICKEN.getDescriptionId());
    public static final Crop RAW_RABBIT = vanilla("raw_rabbit", Objects.requireNonNull(Items.RABBIT.arch$registryName()).toString(), 0xedb6a6, 1, CropType.FOOD, Items.RABBIT.getDescriptionId());
    public static final Crop RAW_MUTTON = vanilla("raw_mutton", Objects.requireNonNull(Items.MUTTON.arch$registryName()).toString(), 0xe55c52, 1, CropType.FOOD, Items.MUTTON.getDescriptionId());
    public static final Crop BROWN_MUSHROOM = vanilla("brown_mushroom", Objects.requireNonNull(Items.BROWN_MUSHROOM.arch$registryName()).toString(), 0xca9777, 1, CropType.FOOD, Items.BROWN_MUSHROOM.getDescriptionId());
    public static final Crop RED_MUSHROOM = vanilla("red_mushroom", Objects.requireNonNull(Items.RED_MUSHROOM.arch$registryName()).toString(), 0xdf1212, 1, CropType.FOOD, Items.RED_MUSHROOM.getDescriptionId());
    public static final Crop CRIMSON_FUNGUS = vanilla("crimson_fungus", Objects.requireNonNull(Items.CRIMSON_FUNGUS.arch$registryName()).toString(), 0xa22428, 1, CropType.FOOD, Items.CRIMSON_FUNGUS.getDescriptionId());
    public static final Crop WARPED_FUNGUS = vanilla("warped_fungus", Objects.requireNonNull(Items.WARPED_FUNGUS.arch$registryName()).toString(), 0x14b283, 1, CropType.FOOD, Items.WARPED_FUNGUS.getDescriptionId());
    public static final Crop ORANGE = vanilla("orange", Objects.requireNonNull(Items.ORANGE_DYE.arch$registryName()).toString(), 0xff6a00, 1, CropType.CROP, Items.ORANGE_DYE.getDescriptionId());
    public static final Crop MAGENTA = vanilla("magenta", Objects.requireNonNull(Items.MAGENTA_DYE.arch$registryName()).toString(), 0xff00dc, 1, CropType.CROP, Items.MAGENTA_DYE.getDescriptionId());
    public static final Crop LIGHT_BLUE = vanilla("light_blue", Objects.requireNonNull(Items.LIGHT_BLUE_DYE.arch$registryName()).toString(), 0x94ff, 1, CropType.CROP, Items.LIGHT_BLUE_DYE.getDescriptionId());
    public static final Crop YELLOW = vanilla("yellow", Objects.requireNonNull(Items.YELLOW_DYE.arch$registryName()).toString(), 0xffd800, 1, CropType.CROP, Items.YELLOW_DYE.getDescriptionId());
    public static final Crop LIME = vanilla("lime", Objects.requireNonNull(Items.LIME_DYE.arch$registryName()).toString(), 0xb6ff00, 1, CropType.CROP, Items.LIME_DYE.getDescriptionId());
    public static final Crop PINK = vanilla("pink", Objects.requireNonNull(Items.PINK_DYE.arch$registryName()).toString(), 0xff7fb6, 1, CropType.CROP, Items.PINK_DYE.getDescriptionId());
    public static final Crop GRAY = vanilla("gray", Objects.requireNonNull(Items.GRAY_DYE.arch$registryName()).toString(), 0x404040, 1, CropType.CROP, Items.GRAY_DYE.getDescriptionId());
    public static final Crop LIGHT_GRAY = vanilla("light_gray", Objects.requireNonNull(Items.LIGHT_GRAY_DYE.arch$registryName()).toString(), 0x808080, 1, CropType.CROP, Items.LIGHT_GRAY_DYE.getDescriptionId());
    public static final Crop CYAN = vanilla("cyan", Objects.requireNonNull(Items.CYAN_DYE.arch$registryName()).toString(), 0xffff, 1, CropType.CROP, Items.CYAN_DYE.getDescriptionId());
    public static final Crop PURPLE = vanilla("purple", Objects.requireNonNull(Items.PURPLE_DYE.arch$registryName()).toString(), 0xb200ff, 1, CropType.CROP, Items.PURPLE_DYE.getDescriptionId());
    public static final Crop BROWN = vanilla("brown", Objects.requireNonNull(Items.BROWN_DYE.arch$registryName()).toString(), 0x7f3300, 1, CropType.CROP, Items.BROWN_DYE.getDescriptionId());
    public static final Crop GREEN = vanilla("green", Objects.requireNonNull(Items.GREEN_DYE.arch$registryName()).toString(), 0x7f0e, 1, CropType.CROP, Items.GREEN_DYE.getDescriptionId());
    public static final Crop RED = vanilla("red", Objects.requireNonNull(Items.RED_DYE.arch$registryName()).toString(), 0xff0000, 1, CropType.CROP, Items.RED_DYE.getDescriptionId());
    public static final Crop BLACK = vanilla("black", Objects.requireNonNull(Items.BLACK_DYE.arch$registryName()).toString(), 0x2d2d2d, 1, CropType.CROP, Items.BLACK_DYE.getDescriptionId());
    public static final Crop TOTEM = vanilla("totem", Objects.requireNonNull(Items.TOTEM_OF_UNDYING.arch$registryName()).toString(), 0xf8eea5, 6, CropType.CROP, Items.TOTEM_OF_UNDYING.getDescriptionId());
    public static final Crop TETHER = vanilla("tether", Objects.requireNonNull(Items.LEAD.arch$registryName()).toString(), 0xac8e79, 1, CropType.CROP, Items.LEAD.getDescriptionId());
    public static final Crop NAME_TAG = vanilla("name_tag", Objects.requireNonNull(Items.NAME_TAG.arch$registryName()).toString(), 0x7a7162, 1, CropType.CROP, Items.NAME_TAG.getDescriptionId());
    public static final Crop XP = vanilla("xp", Objects.requireNonNull(Items.EXPERIENCE_BOTTLE.arch$registryName()).toString(), 0xbaff49, 4, CropType.CROP, Items.EXPERIENCE_BOTTLE.getDescriptionId());
    public static final Crop SEA = vanilla("sea", Objects.requireNonNull(Items.HEART_OF_THE_SEA.arch$registryName()).toString(), 0x1f96b1, 4, CropType.CROP, Items.HEART_OF_THE_SEA.getDescriptionId());
    public static final Crop SCUTE = vanilla("scute", Objects.requireNonNull(Items.TURTLE_SCUTE.arch$registryName()).toString(), 0x47bf4a, 2, CropType.ANIMAL, Items.TURTLE_SCUTE.getDescriptionId());
    public static final Crop NAUTILUS = vanilla("nautilus", Objects.requireNonNull(Items.NAUTILUS_SHELL.arch$registryName()).toString(), 0xd4ccc3, 3, CropType.CROP, Items.NAUTILUS_SHELL.getDescriptionId());
    public static final Crop PHANTOM = vanilla("phantom", Objects.requireNonNull(Items.PHANTOM_MEMBRANE.arch$registryName()).toString(), 0xdcd9c0, 2, CropType.MONSTER, Items.PHANTOM_MEMBRANE.getDescriptionId());
    public static final Crop WITHER = vanilla("wither", Objects.requireNonNull(Items.WITHER_ROSE.arch$registryName()).toString(), 0x2a1f19, 5, CropType.MONSTER, Items.WITHER_ROSE.getDescriptionId());
    public static final Crop DRAGON = vanilla("dragon", Objects.requireNonNull(Items.DRAGON_EGG.arch$registryName()).toString(), 0x2d0133, 7, CropType.MONSTER, Items.DRAGON_EGG.getDescriptionId());
    public static final Crop BLUE = vanilla("blue", Objects.requireNonNull(Items.BLUE_DYE.arch$registryName()).toString(), 0x26ff, 1, CropType.CROP, Items.BLUE_DYE.getDescriptionId());
    public static final Crop INK = vanilla("ink", Objects.requireNonNull(Items.INK_SAC.arch$registryName()).toString(), 0x353451, 1, CropType.ANIMAL, Items.INK_SAC.getDescriptionId());
    public static final Crop WHITE = vanilla("white", Objects.requireNonNull(Items.WHITE_DYE.arch$registryName()).toString(), 0xffffff, 1, CropType.CROP, Items.WHITE_DYE.getDescriptionId());
    public static final Crop HONEYCOMB = vanilla("honeycomb", Objects.requireNonNull(Items.HONEYCOMB.arch$registryName()).toString(), 0xfabf29, 1, CropType.ANIMAL, Items.HONEYCOMB.getDescriptionId());
    public static final Crop NETHERITE = vanilla("netherite", Objects.requireNonNull(Items.NETHERITE_INGOT.arch$registryName()).toString(), 0x654740, 5, CropType.CROP, Items.NETHERITE_INGOT.getDescriptionId());
    public static final Crop GLOW_INK = vanilla("glowink", Objects.requireNonNull(Items.GLOW_INK_SAC.arch$registryName()).toString(), 0x4bdeba, 2, CropType.CROP, Items.GLOW_INK_SAC.getDescriptionId());
    public static final Crop COPPER = vanilla("copper", Objects.requireNonNull(Items.COPPER_INGOT.arch$registryName()).toString(), 0xfbc3b6, 2, CropType.CROP, Items.COPPER_INGOT.getDescriptionId());
    public static final Crop AMETHYST = vanilla("amethyst", Objects.requireNonNull(Items.AMETHYST_SHARD.arch$registryName()).toString(), 0xd9cbf2, 3, CropType.CROP, Items.AMETHYST_SHARD.getDescriptionId());
    public static final Crop ECHO_SHARD = vanilla("echo_shard", Objects.requireNonNull(Items.ECHO_SHARD.arch$registryName()).toString(), 0x3404f, 4, CropType.CROP, Items.ECHO_SHARD.getDescriptionId());

    /**
     * Add a crop from a modded material.
     *
     * @param name            crop name, used to generate identifiers
     * @param material        material which the crop grows, could be item ID or item tag (# + id).<br/>
     *                        If the tag with namespace {@code c} is used, the corresponding {@code forge} tag will be generated and included
     * @param color           int value of color
     * @param tier            croparia tier
     * @param type            crop type that specifies the textures. See also {@link CropType}
     * @param translationKeys The mod dependencies with corresponding translation keys.
     *                        The translation key for the first available mod dependency will be used.
     * @return the intermediate data entity of the crop
     */
    public static @Nullable Crop compat(
        String name, String material, int color, int tier, CropType type, @NotNull Map<String, String> translationKeys
    ) {
        for (String mod : translationKeys.keySet()) {
            if (Platform.isModLoaded(mod) && !CropariaIf.CONFIG.inBlacklist(name, mod)) {
                Crop crop = Crop.create(name, material, color, tier, type, translationKeys.get(mod), Map.of()).orElseThrow(
                    () -> new AssertionError("Compat crop %s failed to create".formatted(name))
                );
                recordBuiltin(crop);
                return crop;
            }
        }
        CropariaIf.LOGGER.debug("Skipped compat crop \"{}\" due to blacklist or missing dependencies", name);
        return null;
    }

    public static boolean shouldLoad(@NotNull List<List<String>> dependencies) {
        return dependencies.isEmpty() || dependencies.stream().allMatch(list -> list.isEmpty() || list.stream().anyMatch(Platform::isModLoaded));
    }

    @SafeVarargs
    public static boolean shouldLoad(@NotNull List<String>... dependencies) {
        return dependencies.length == 0 || Arrays.stream(dependencies).allMatch(list -> list.isEmpty() || list.stream().anyMatch(Platform::isModLoaded));
    }

    public static void register() {
        CropariaIf.LOGGER.info("Loading custom crops from file definitions");
        CropFileHandler.readCrops().forEach(Crops::registerFileCrop);
        CropariaIf.LOGGER.info("Registering built-in crops");
        for (Crop crop : BUILTIN_CROPS.values()) {
            CropariaItems.registerCrop(crop);
            CropariaBlocks.registerCrop(crop);
        }
    }

    private static void registerFileCrop(@NotNull RawCrop raw) {
        if (raw.dependencies() == null || shouldLoad(raw.dependencies())) {
            Crop.of(raw).ifPresentOrElse(
                crop -> {
                    if (recordCustom(crop)) {
                        CropariaItems.registerCrop(crop);
                        CropariaBlocks.registerCrop(crop);
                    } else {
                        CropariaIf.LOGGER.error("Duplicated custom crop \"{}\" from file definition", raw.name());
                    }
                },
                () -> CropariaIf.LOGGER.error("Failed to create custom crop \"{}\" from file definition", raw.name())
            );
        } else {
            CropariaIf.LOGGER.info("Skipped custom crop \"{}\" from file definition due to missing dependencies {}", raw.name(), raw.dependencies());
        }
    }
}
