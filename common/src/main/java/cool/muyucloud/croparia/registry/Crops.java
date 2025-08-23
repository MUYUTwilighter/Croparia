package cool.muyucloud.croparia.registry;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.util.Color;
import cool.muyucloud.croparia.api.crop.util.CropDependencies;
import cool.muyucloud.croparia.api.crop.util.Material;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @see DgRegistries#CROPS
 */
@SuppressWarnings("unused")
public class Crops {
    public static CompletableFuture<Suggestions> cropSuggestions(SuggestionsBuilder builder) {
        DgRegistries.CROPS.forEach(crop -> builder.suggest(crop.getKey().toString()));
        return builder.buildFuture();
    }

    /**
     * Add a crop for croparia material with specified translation key.
     *
     * @param name           crop name
     * @param material       material id which the crop grows, could be item ID or item tag
     * @param color          int value of color
     * @param tier           tier
     * @param type           crop type that specifies the textures. See also {@link Crop#PRESET_TYPES}
     * @param translationKey translation key for the crop, used for formatting item & block names.
     */
    public static @NotNull Crop croparia(
        @NotNull String name, @NotNull String material, int color, int tier, @NotNull String type,
        @NotNull String translationKey
    ) {
        Crop crop = new Crop(CropariaIf.of(name), new Material(material), new Color(color), tier, type, null, new CropDependencies("croparia", translationKey));
        DgRegistries.CROPS.register(crop);
        return crop;
    }

    public static final Crop ELEMENTAL = croparia("elemental", "croparia:gem_elemental", 0x712389, 2, Crop.CROP, "item.croparia.gem_elemental");
    public static final Crop EARTH = croparia("earth", "croparia:gem_earth", 0xE5C8BB, 3, Crop.CROP, "item.croparia.gem_earth");
    public static final Crop WATER = croparia("water", "croparia:gem_water", 0x2A5AB2, 4, Crop.CROP, "item.croparia.gem_water");
    public static final Crop FIRE = croparia("fire", "croparia:gem_fire", 0xC65957, 6, Crop.CROP, "item.croparia.gem_fire");
    public static final Crop AIR = croparia("air", "croparia:gem_air", 0xA2A9B5, 7, Crop.CROP, "item.croparia.gem_air");

    /**
     * Add a crop for vanilla material with specified translation key.
     *
     * @param name     crop name
     * @param material material item which the crop grows
     * @param color    int value of color
     * @param tier     tier
     * @param type     crop type that specifies the textures. See also {@link Crop}
     */
    public static @NotNull Crop vanilla(
        @NotNull String name, Item material, int color, int tier, @NotNull String type
    ) {
        Crop crop = new Crop(
            CropariaIf.of(name), new Material(Objects.requireNonNull(material.arch$registryName()).toString()),
            new Color(color), tier, type, null, new CropDependencies("minecraft", material.getDescriptionId())
        );
        DgRegistries.CROPS.register(crop);
        return crop;
    }

    public static final Crop COAL = vanilla("coal", Items.COAL, 0x333333, 1, Crop.CROP);
    public static final Crop IRON = vanilla("iron", Items.IRON_INGOT, 0xd8d8d8, 2, Crop.CROP);
    public static final Crop GOLD = vanilla("gold", Items.GOLD_INGOT, 0xffff8b, 2, Crop.CROP);
    public static final Crop LAPIS = vanilla("lapis", Items.LAPIS_LAZULI, 0x7497ea, 3, Crop.CROP);
    public static final Crop REDSTONE = vanilla("redstone", Items.REDSTONE, 0xff2626, 3, Crop.CROP);
    public static final Crop DIAMOND = vanilla("diamond", Items.DIAMOND, 0x8cf4e2, 4, Crop.CROP);
    public static final Crop EMERALD = vanilla("emerald", Items.EMERALD, 0x17dd62, 4, Crop.CROP);
    public static final Crop CLAY = vanilla("clay", Items.CLAY_BALL, 0x9aa3b3, 1, Crop.CROP);
    public static final Crop GLOWSTONE = vanilla("glowstone", Items.GLOWSTONE, 0xffff00, 3, Crop.CROP);
    public static final Crop QUARTZ = vanilla("quartz", Items.QUARTZ, 0xdfd8cf, 3, Crop.CROP);
    public static final Crop SHARD = vanilla("shard", Items.PRISMARINE_SHARD, 0x73b5aa, 2, Crop.CROP);
    public static final Crop CRYSTAL = vanilla("crystal", Items.PRISMARINE_CRYSTALS, 0xcbe7e0, 2, Crop.CROP);
    public static final Crop ENDER = vanilla("ender", Items.ENDER_PEARL, 0x8cf4e2, 3, Crop.MONSTER);
    public static final Crop BONE = vanilla("bone", Items.BONE, 0xeceac9, 2, Crop.MONSTER);
    public static final Crop SPIDER = vanilla("eye", Items.SPIDER_EYE, 0x9b1e2c, 2, Crop.MONSTER);
    public static final Crop POWDER = vanilla("powder", Items.GUNPOWDER, 0x727272, 2, Crop.MONSTER);
    public static final Crop PAPER = vanilla("paper", Items.PAPER, 0xeaeaea, 1, Crop.CROP);
    public static final Crop SUGAR = vanilla("sugar", Items.SUGAR, 0xf5ffff, 1, Crop.CROP);
    public static final Crop CHARCOAL = vanilla("charcoal", Items.CHARCOAL, 0x53493a, 1, Crop.CROP);
    public static final Crop FLINT = vanilla("flint", Items.FLINT, 0x565656, 1, Crop.CROP);
    public static final Crop SNOWBALL = vanilla("snowball", Items.SNOWBALL, 0xfbffff, 1, Crop.CROP);
    public static final Crop FIREWORK = vanilla("firework", Items.FIREWORK_STAR, 0x575757, 1, Crop.CROP);
    public static final Crop NETHER = vanilla("nether", Items.NETHER_BRICK, 0x46262c, 3, Crop.CROP);
    public static final Crop BOTTLE = vanilla("bottle", Items.GLASS_BOTTLE, 0xe4e4e4, 1, Crop.CROP);
    public static final Crop FOOT = vanilla("foot", Items.RABBIT_FOOT, 0xcfa978, 1, Crop.ANIMAL);
    public static final Crop HIDE = vanilla("hide", Items.RABBIT_HIDE, 0xc79e67, 1, Crop.ANIMAL);
    public static final Crop LEATHER = vanilla("leather", Items.LEATHER, 0xc65c35, 1, Crop.ANIMAL);
    public static final Crop FEATHER = vanilla("feather", Items.FEATHER, 0xffffff, 1, Crop.ANIMAL);
    public static final Crop BLAZE = vanilla("blaze", Items.BLAZE_ROD, 0xffcb00, 3, Crop.MONSTER);
    public static final Crop GHAST = vanilla("ghast", Items.GHAST_TEAR, 0xe3fde8, 4, Crop.MONSTER);
    public static final Crop MAGMA = vanilla("magma", Items.MAGMA_CREAM, 0xcea025, 3, Crop.MONSTER);
    public static final Crop SHELL = vanilla("shell", Items.SHULKER_SHELL, 0x9e749e, 4, Crop.MONSTER);
    public static final Crop STAR = vanilla("star", Items.NETHER_STAR, 0xf0f3f3, 6, Crop.MONSTER);
    public static final Crop STRING = vanilla("string", Items.STRING, 0xf7f7f7, 2, Crop.MONSTER);
    public static final Crop SLIME = vanilla("slime", Items.SLIME_BALL, 0x84c873, 2, Crop.MONSTER);
    public static final Crop ZOMBIE = vanilla("zombie", Items.ROTTEN_FLESH, 0xbd5d37, 2, Crop.MONSTER);
    public static final Crop VINE = vanilla("vine", Items.VINE, 0x1b4509, 1, Crop.NATURE);
    public static final Crop WEEPING_VINES = vanilla("weeping_vines", Items.WEEPING_VINES, 0x7a0000, 1, Crop.NATURE);
    public static final Crop TWISTING_VINES = vanilla("twisting_vines", Items.TWISTING_VINES, 0x14b283, 1, Crop.NATURE);
    public static final Crop LILY_PAD = vanilla("lilypad", Items.LILY_PAD, 0xc5f14, 1, Crop.NATURE);
    public static final Crop BUSH = vanilla("bush", Items.DEAD_BUSH, 0x946428, 1, Crop.NATURE);
    public static final Crop GRASS = vanilla("grass", Items.SHORT_GRASS, 0x820510b, 1, Crop.NATURE);
    public static final Crop LARGE_FERN = vanilla("large_fern", Items.LARGE_FERN, 0x4a7240, 1, Crop.NATURE);
    public static final Crop TALL_GRASS = vanilla("tall_grass", Items.TALL_GRASS, 0x2f4728, 1, Crop.NATURE);
    public static final Crop FERN = vanilla("fern", Items.FERN, 0x1b4509, 1, Crop.NATURE);
    public static final Crop OAK = vanilla("oak", Items.OAK_PLANKS, 0x9d824c, 1, Crop.NATURE);
    public static final Crop SPRUCE = vanilla("spruce", Items.SPRUCE_PLANKS, 0x795933, 1, Crop.NATURE);
    public static final Crop BIRCH = vanilla("birch", Items.BIRCH_PLANKS, 0xc6b579, 1, Crop.NATURE);
    public static final Crop JUNGLE = vanilla("jungle", Items.JUNGLE_PLANKS, 0xbd8c6a, 1, Crop.NATURE);
    public static final Crop ACACIA = vanilla("acacia", Items.ACACIA_PLANKS, 0xb86236, 1, Crop.NATURE);
    public static final Crop DARK_OAK = vanilla("dark_oak", Items.DARK_OAK_PLANKS, 0x4e3118, 1, Crop.NATURE);
    public static final Crop MANGROVE = vanilla("mangrove", Items.MANGROVE_PLANKS, 0x7d4133, 1, Crop.NATURE);
    public static final Crop CHERRY = vanilla("cherry", Items.CHERRY_PLANKS, 0xe3b1ab, 1, Crop.NATURE);
    public static final Crop BAMBOO = vanilla("bamboo", Items.BAMBOO_PLANKS, 0xe0ca69, 1, Crop.NATURE);
    public static final Crop CRIMSON = vanilla("crimson", Items.CRIMSON_PLANKS, 0x5b2f41, 1, Crop.NATURE);
    public static final Crop WARPED = vanilla("warped", Items.WARPED_PLANKS, 0x388180, 1, Crop.NATURE);
    public static final Crop APPLE = vanilla("apple", Items.APPLE, 0xff1c2b, 1, Crop.FOOD);
    public static final Crop GOLDEN_APPLE = vanilla("golden_apple", Items.GOLDEN_APPLE, 0xffffb0, 3, Crop.FOOD);
    public static final Crop BREAD = vanilla("bread", Items.BREAD, 0x9e7325, 1, Crop.FOOD);
    public static final Crop EGG = vanilla("egg", Items.EGG, 0xdfce9b, 1, Crop.FOOD);
    public static final Crop TURTLE_EGG = vanilla("turtle_egg", Items.TURTLE_EGG, 0x58ceaf, 1, Crop.FOOD);
    public static final Crop SNIFFER_EGG = vanilla("sniffer_egg", Items.SNIFFER_EGG, 0xb1413f, 1, Crop.FOOD);
    public static final Crop TROPICAL_FISH = vanilla("clownfish", Items.TROPICAL_FISH, 0xf29965, 1, Crop.FOOD);
    public static final Crop PUFFER_FISH = vanilla("pufferfish", Items.PUFFERFISH, 0xc5b200, 1, Crop.FOOD);
    public static final Crop COOKIE = vanilla("cookie", Items.COOKIE, 0xd9833e, 1, Crop.FOOD);
    public static final Crop CHORUS = vanilla("chorus", Items.CHORUS_FRUIT, 0xaa85aa, 3, Crop.FOOD);
    public static final Crop BEEF = vanilla("raw_beef", Items.BEEF, 0xe24940, 1, Crop.FOOD);
    public static final Crop PORKSHOP = vanilla("raw_porc", Items.PORKCHOP, 0xff8c8c, 1, Crop.FOOD);
    public static final Crop COD = vanilla("fish", Items.COD, 0xc6a271, 1, Crop.FOOD);
    public static final Crop SALMON = vanilla("salmon", Items.SALMON, 0x9e4b49, 1, Crop.FOOD);
    public static final Crop RAW_CHICKEN = vanilla("raw_chicken", Items.CHICKEN, 0xefbcac, 1, Crop.FOOD);
    public static final Crop RAW_RABBIT = vanilla("raw_rabbit", Items.RABBIT, 0xedb6a6, 1, Crop.FOOD);
    public static final Crop RAW_MUTTON = vanilla("raw_mutton", Items.MUTTON, 0xe55c52, 1, Crop.FOOD);
    public static final Crop BROWN_MUSHROOM = vanilla("brown_mushroom", Items.BROWN_MUSHROOM, 0xca9777, 1, Crop.FOOD);
    public static final Crop RED_MUSHROOM = vanilla("red_mushroom", Items.RED_MUSHROOM, 0xdf1212, 1, Crop.FOOD);
    public static final Crop CRIMSON_FUNGUS = vanilla("crimson_fungus", Items.CRIMSON_FUNGUS, 0xa22428, 1, Crop.FOOD);
    public static final Crop WARPED_FUNGUS = vanilla("warped_fungus", Items.WARPED_FUNGUS, 0x14b283, 1, Crop.FOOD);
    public static final Crop ORANGE = vanilla("orange", Items.ORANGE_DYE, 0xff6a00, 1, Crop.CROP);
    public static final Crop MAGENTA = vanilla("magenta", Items.MAGENTA_DYE, 0xff00dc, 1, Crop.CROP);
    public static final Crop LIGHT_BLUE = vanilla("light_blue", Items.LIGHT_BLUE_DYE, 0x94ff, 1, Crop.CROP);
    public static final Crop YELLOW = vanilla("yellow", Items.YELLOW_DYE, 0xffd800, 1, Crop.CROP);
    public static final Crop LIME = vanilla("lime", Items.LIME_DYE, 0xb6ff00, 1, Crop.CROP);
    public static final Crop PINK = vanilla("pink", Items.PINK_DYE, 0xff7fb6, 1, Crop.CROP);
    public static final Crop GRAY = vanilla("gray", Items.GRAY_DYE, 0x404040, 1, Crop.CROP);
    public static final Crop LIGHT_GRAY = vanilla("light_gray", Items.LIGHT_GRAY_DYE, 0x808080, 1, Crop.CROP);
    public static final Crop CYAN = vanilla("cyan", Items.CYAN_DYE, 0xffff, 1, Crop.CROP);
    public static final Crop PURPLE = vanilla("purple", Items.PURPLE_DYE, 0xb200ff, 1, Crop.CROP);
    public static final Crop BROWN = vanilla("brown", Items.BROWN_DYE, 0x7f3300, 1, Crop.CROP);
    public static final Crop GREEN = vanilla("green", Items.GREEN_DYE, 0x7f0e, 1, Crop.CROP);
    public static final Crop RED = vanilla("red", Items.RED_DYE, 0xff0000, 1, Crop.CROP);
    public static final Crop BLACK = vanilla("black", Items.BLACK_DYE, 0x2d2d2d, 1, Crop.CROP);
    public static final Crop TOTEM = vanilla("totem", Items.TOTEM_OF_UNDYING, 0xf8eea5, 6, Crop.CROP);
    public static final Crop TETHER = vanilla("tether", Items.LEAD, 0xac8e79, 1, Crop.CROP);
    public static final Crop NAME_TAG = vanilla("name_tag", Items.NAME_TAG, 0x7a7162, 1, Crop.CROP);
    public static final Crop XP = vanilla("xp", Items.EXPERIENCE_BOTTLE, 0xbaff49, 4, Crop.CROP);
    public static final Crop SEA = vanilla("sea", Items.HEART_OF_THE_SEA, 0x1f96b1, 4, Crop.CROP);
    public static final Crop SCUTE = vanilla("scute", Items.TURTLE_SCUTE, 0x47bf4a, 2, Crop.ANIMAL);
    public static final Crop NAUTILUS = vanilla("nautilus", Items.NAUTILUS_SHELL, 0xd4ccc3, 3, Crop.CROP);
    public static final Crop PHANTOM = vanilla("phantom", Items.PHANTOM_MEMBRANE, 0xdcd9c0, 2, Crop.MONSTER);
    public static final Crop WITHER = vanilla("wither", Items.WITHER_ROSE, 0x2a1f19, 5, Crop.MONSTER);
    public static final Crop DRAGON = vanilla("dragon", Items.DRAGON_EGG, 0x2d0133, 7, Crop.MONSTER);
    public static final Crop BLUE = vanilla("blue", Items.BLUE_DYE, 0x26ff, 1, Crop.CROP);
    public static final Crop INK = vanilla("ink", Items.INK_SAC, 0x353451, 1, Crop.ANIMAL);
    public static final Crop WHITE = vanilla("white", Items.WHITE_DYE, 0xffffff, 1, Crop.CROP);
    public static final Crop HONEYCOMB = vanilla("honeycomb", Items.HONEYCOMB, 0xfabf29, 1, Crop.ANIMAL);
    public static final Crop NETHERITE = vanilla("netherite", Items.NETHERITE_INGOT, 0x654740, 5, Crop.CROP);
    public static final Crop GLOW_INK = vanilla("glowink", Items.GLOW_INK_SAC, 0x4bdeba, 2, Crop.ANIMAL);
    public static final Crop COPPER = vanilla("copper", Items.COPPER_INGOT, 0xfbc3b6, 2, Crop.CROP);
    public static final Crop AMETHYST = vanilla("amethyst", Items.AMETHYST_SHARD, 0xd9cbf2, 3, Crop.CROP);
    public static final Crop ECHO_SHARD = vanilla("echo_shard", Items.ECHO_SHARD, 0x3404f, 4, Crop.MONSTER);
    public static final Crop ARMADILLO = vanilla("armadillo", Items.ARMADILLO_SCUTE, 0xc48682, 2, Crop.ANIMAL);
    public static final Crop RESIN = vanilla("resin", Items.RESIN_CLUMP, 0xfcd8af, 1, Crop.NATURE);
    public static final Crop BREEZE = vanilla("breeze", Items.BREEZE_ROD, 0x7980c2, 3, Crop.NATURE);
    public static final Crop FROGSPAWN = vanilla("frogspawn", Items.FROGSPAWN, 0x7980c2, 3, Crop.ANIMAL);

    /**
     * Add a crop from a modded material.
     *
     * @param name            crop name, used to generate identifiers
     * @param material        material which the crop grows, could be item ID or item tag (# + id).<br/>
     *                        If the tag with namespace {@code c} is used, the corresponding {@code forge} tag will be generated and included
     * @param color           int value of color
     * @param tier            croparia tier
     * @param type            crop type that specifies the textures. See also {@link Crop#PRESET_TYPES}
     * @param translationKeys The mod dependencies with corresponding translation keys.
     *                        The translation key for the first available mod dependency will be used.
     * @return the intermediate data entity of the crop
     */
    public static @NotNull Crop compat(
        String name, String material, int color, int tier, String type, @NotNull Map<String, String> translationKeys
    ) {
        Crop crop = new Crop(CropariaIf.of(name), new Material(material), new Color(color), tier, type, null, new CropDependencies(translationKeys));
        DgRegistries.CROPS.register(crop);
        return crop;
    }

    public static void register() {
        CropariaIf.LOGGER.info("Loading custom crops from file definitions");
        DgRegistries.CROPS.readCrops();
    }
}
