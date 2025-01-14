package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.data.config.Config;
import cool.muyucloud.croparia.data.crop.*;
import dev.architectury.platform.Platform;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Crops {
    public static final Set<Crop> CROPS = new HashSet<>();

    /**
     * Add a simple crop. Mainly used for croparia crops.
     *
     * @param name       crop name
     * @param materialId id of material which the crop grows, could be item ID or item tag
     * @param color      int value of color
     * @param tier       tier
     * @param type       crop type that specifies the textures. See also {@link CropType}
     */
    public static @NotNull Crop registerCrop(
        @NotNull String name, @NotNull String materialId, int color, int tier, @NotNull CropType type
    ) {
        Crop crop = Crop.create(name, materialId, color, tier, type).orElseThrow(
            () -> new IllegalArgumentException(
                "Failed to create croparia crop %s".formatted(name)
            )
        );
        CROPS.add(crop);
        return crop;
    }


    /**
     * Add a simple crop with specified translation translationKey. Mainly used for vanilla crops.
     *
     * @param name           crop name
     * @param materialId     material id which the crop grows, could be item ID or item tag
     * @param color          int value of color
     * @param tier           tier
     * @param type           crop type that specifies the textures. See also {@link CropType}
     * @param translationKey translation translationKey for the crop, used for formatting item & block names.
     */
    public static @NotNull Crop registerCrop(
        @NotNull String name, @NotNull String materialId, int color, int tier, @NotNull CropType type,
        @NotNull String translationKey
    ) {
        Crop crop = Crop.create(name, materialId, color, tier, type, translationKey, Map.of()).orElseThrow(
            () -> new IllegalArgumentException("Vanilla crop %s failed to create".formatted(name))
        );
        CROPS.add(crop);
        return crop;
    }

    public static final Crop ELEMENTAL = registerCrop("elemental", "croparia:elematilius", 0x712389, 2, CropType.CROP, "item.croparia.elematilius");
    public static final Crop EARTH = registerCrop("earth", "croparia:elemental_earth", 0xe5c8bb, 3, CropType.CROP, "item.croparia.elemental_earth");
    public static final Crop WATER = registerCrop("water", "croparia:elemental_water", 0x2a5ab2, 4, CropType.CROP, "item.croparia.elemental_water");
    public static final Crop FIRE = registerCrop("fire", "croparia:elemental_fire", 0xc65957, 6, CropType.CROP, "item.croparia.elemental_fire");
    public static final Crop AIR = registerCrop("air", "croparia:elemental_air", 0xa2a9b5, 7, CropType.CROP, "item.croparia.elemental_air");
    public static final Crop COAL = registerCrop("coal", Objects.requireNonNull(Items.COAL.arch$registryName()).toString(), 0x333333, 1, CropType.CROP, Items.COAL.getDescriptionId());
    public static final Crop IRON = registerCrop("iron", Objects.requireNonNull(Items.IRON_INGOT.arch$registryName()).toString(), 0xd8d8d8, 2, CropType.CROP, Items.IRON_INGOT.getDescriptionId());
    public static final Crop GOLD = registerCrop("gold", Objects.requireNonNull(Items.GOLD_INGOT.arch$registryName()).toString(), 0xffff8b, 2, CropType.CROP, Items.GOLD_INGOT.getDescriptionId());
    public static final Crop LAPIS = registerCrop("lapis", Objects.requireNonNull(Items.LAPIS_LAZULI.arch$registryName()).toString(), 0x7497ea, 3, CropType.CROP, Items.LAPIS_LAZULI.getDescriptionId());
    public static final Crop REDSTONE = registerCrop("redstone", Objects.requireNonNull(Items.REDSTONE.arch$registryName()).toString(), 0xff2626, 3, CropType.CROP, Items.REDSTONE.getDescriptionId());
    public static final Crop DIAMOND = registerCrop("diamond", Objects.requireNonNull(Items.DIAMOND.arch$registryName()).toString(), 0x8cf4e2, 4, CropType.CROP, Items.DIAMOND.getDescriptionId());
    public static final Crop EMERALD = registerCrop("emerald", Objects.requireNonNull(Items.EMERALD.arch$registryName()).toString(), 0x17dd62, 4, CropType.CROP, Items.EMERALD.getDescriptionId());
    public static final Crop CLAY = registerCrop("clay", Objects.requireNonNull(Items.CLAY.arch$registryName()).toString(), 0x9aa3b3, 1, CropType.CROP, Items.CLAY.getDescriptionId());
    public static final Crop GLOWSTONE = registerCrop("glowstone", Objects.requireNonNull(Items.GLOWSTONE.arch$registryName()).toString(), 0xffff00, 3, CropType.CROP, Items.GLOWSTONE.getDescriptionId());
    public static final Crop QUARTZ = registerCrop("quartz", Objects.requireNonNull(Items.QUARTZ.arch$registryName()).toString(), 0xdfd8cf, 3, CropType.CROP, Items.QUARTZ.getDescriptionId());
    public static final Crop SHARD = registerCrop("shard", Objects.requireNonNull(Items.PRISMARINE_SHARD.arch$registryName()).toString(), 0x73b5aa, 2, CropType.CROP, Items.PRISMARINE_SHARD.getDescriptionId());
    public static final Crop CRYSTAL = registerCrop("crystal", Objects.requireNonNull(Items.PRISMARINE_CRYSTALS.arch$registryName()).toString(), 0xcbe7e0, 2, CropType.CROP, Items.PRISMARINE_CRYSTALS.getDescriptionId());
    public static final Crop ENDER = registerCrop("ender", Objects.requireNonNull(Items.ENDER_PEARL.arch$registryName()).toString(), 0x8cf4e2, 3, CropType.MONSTER, Items.ENDER_PEARL.getDescriptionId());
    public static final Crop BONE = registerCrop("bone", Objects.requireNonNull(Items.BONE.arch$registryName()).toString(), 0xeceac9, 2, CropType.MONSTER, Items.BONE.getDescriptionId());
    public static final Crop EYE = registerCrop("eye", Objects.requireNonNull(Items.SPIDER_EYE.arch$registryName()).toString(), 0xeceac9, 2, CropType.MONSTER, Items.SPIDER_EYE.getDescriptionId());
    public static final Crop POWDER = registerCrop("powder", Objects.requireNonNull(Items.GUNPOWDER.arch$registryName()).toString(), 0x727272, 2, CropType.MONSTER, Items.GUNPOWDER.getDescriptionId());
    public static final Crop PAPER = registerCrop("paper", Objects.requireNonNull(Items.PAPER.arch$registryName()).toString(), 0xeaeaea, 1, CropType.CROP, Items.PAPER.getDescriptionId());
    public static final Crop SUGAR = registerCrop("sugar", Objects.requireNonNull(Items.SUGAR.arch$registryName()).toString(), 0xf5ffff, 1, CropType.CROP, Items.SUGAR.getDescriptionId());
    public static final Crop CHARCOAL = registerCrop("charcoal", Objects.requireNonNull(Items.CHARCOAL.arch$registryName()).toString(), 0x53493a, 1, CropType.CROP, Items.CHARCOAL.getDescriptionId());
    public static final Crop FLINT = registerCrop("flint", Objects.requireNonNull(Items.FLINT.arch$registryName()).toString(), 0x565656, 1, CropType.CROP, Items.FLINT.getDescriptionId());
    public static final Crop SNOWBALL = registerCrop("snowball", Objects.requireNonNull(Items.SNOWBALL.arch$registryName()).toString(), 0xfbffff, 1, CropType.CROP, Items.SNOWBALL.getDescriptionId());
    public static final Crop FIREWORK = registerCrop("firework", Objects.requireNonNull(Items.FIREWORK_STAR.arch$registryName()).toString(), 0x575757, 1, CropType.CROP, Items.FIREWORK_STAR.getDescriptionId());
    public static final Crop NETHER = registerCrop("nether", Objects.requireNonNull(Items.NETHER_BRICK.arch$registryName()).toString(), 0x46262c, 3, CropType.CROP, Items.NETHER_BRICK.getDescriptionId());
    public static final Crop BOTTLE = registerCrop("bottle", Objects.requireNonNull(Items.GLASS_BOTTLE.arch$registryName()).toString(), 0xe4e4e4, 1, CropType.CROP, Items.GLASS_BOTTLE.getDescriptionId());
    public static final Crop FOOT = registerCrop("foot", Objects.requireNonNull(Items.RABBIT_FOOT.arch$registryName()).toString(), 0xcfa978, 1, CropType.ANIMAL, Items.RABBIT_FOOT.getDescriptionId());
    public static final Crop HIDE = registerCrop("hide", Objects.requireNonNull(Items.RABBIT_HIDE.arch$registryName()).toString(), 0xc79e67, 1, CropType.ANIMAL, Items.RABBIT_HIDE.getDescriptionId());
    public static final Crop LEATHER = registerCrop("leather", Objects.requireNonNull(Items.LEATHER.arch$registryName()).toString(), 0xc65c35, 1, CropType.ANIMAL, Items.LEATHER.getDescriptionId());
    public static final Crop FEATHER = registerCrop("feather", Objects.requireNonNull(Items.FEATHER.arch$registryName()).toString(), 0xffffff, 1, CropType.ANIMAL, Items.FEATHER.getDescriptionId());
    public static final Crop BLAZE = registerCrop("blaze", Objects.requireNonNull(Items.BLAZE_ROD.arch$registryName()).toString(), 0xffcb00, 3, CropType.MONSTER, Items.BLAZE_ROD.getDescriptionId());
    public static final Crop GHAST = registerCrop("ghast", Objects.requireNonNull(Items.GHAST_TEAR.arch$registryName()).toString(), 0xe3fde8, 4, CropType.MONSTER, Items.GHAST_TEAR.getDescriptionId());
    public static final Crop MAGMA = registerCrop("magma", Objects.requireNonNull(Items.MAGMA_CREAM.arch$registryName()).toString(), 0xcea025, 3, CropType.MONSTER, Items.MAGMA_CREAM.getDescriptionId());
    public static final Crop SHELL = registerCrop("shell", Objects.requireNonNull(Items.SHULKER_SHELL.arch$registryName()).toString(), 0x9e749e, 4, CropType.MONSTER, Items.SHULKER_SHELL.getDescriptionId());
    public static final Crop STAR = registerCrop("star", Objects.requireNonNull(Items.NETHER_STAR.arch$registryName()).toString(), 0xf0f3f3, 6, CropType.MONSTER, Items.NETHER_STAR.getDescriptionId());
    public static final Crop STRING = registerCrop("string", Objects.requireNonNull(Items.STRING.arch$registryName()).toString(), 0xf7f7f7, 2, CropType.MONSTER, Items.STRING.getDescriptionId());
    public static final Crop SLIME = registerCrop("slime", Objects.requireNonNull(Items.SLIME_BALL.arch$registryName()).toString(), 0x84c873, 2, CropType.MONSTER, Items.SLIME_BALL.getDescriptionId());
    public static final Crop ZOMBIE = registerCrop("zombie", Objects.requireNonNull(Items.ROTTEN_FLESH.arch$registryName()).toString(), 0xbd5d37, 2, CropType.MONSTER, Items.ROTTEN_FLESH.getDescriptionId());
    public static final Crop VINE = registerCrop("vine", Objects.requireNonNull(Items.VINE.arch$registryName()).toString(), 0x1b4509, 1, CropType.NATURE, Items.VINE.getDescriptionId());
    public static final Crop WEEPING_VINES = registerCrop("weeping_vines", Objects.requireNonNull(Items.WEEPING_VINES.arch$registryName()).toString(), 0x7a0000, 1, CropType.NATURE, Items.WEEPING_VINES.getDescriptionId());
    public static final Crop TWISTING_VINES = registerCrop("twisting_vines", Objects.requireNonNull(Items.TWISTING_VINES.arch$registryName()).toString(), 0x14b283, 1, CropType.NATURE, Items.TWISTING_VINES.getDescriptionId());
    public static final Crop LILY_PAD = registerCrop("lilypad", Objects.requireNonNull(Items.LILY_PAD.arch$registryName()).toString(), 0xc5f14, 1, CropType.NATURE, Items.LILY_PAD.getDescriptionId());
    public static final Crop BUSH = registerCrop("bush", Objects.requireNonNull(Items.DEAD_BUSH.arch$registryName()).toString(), 0x946428, 1, CropType.NATURE, Items.DEAD_BUSH.getDescriptionId());
    public static final Crop GRASS = registerCrop("grass", Objects.requireNonNull(Items.GRASS.arch$registryName()).toString(), 0x820510b, 1, CropType.NATURE, Items.GRASS.getDescriptionId());
    public static final Crop LARGE_FERN = registerCrop("large_fern", Objects.requireNonNull(Items.LARGE_FERN.arch$registryName()).toString(), 0x4a7240, 1, CropType.NATURE, Items.LARGE_FERN.getDescriptionId());
    public static final Crop TALL_GRASS = registerCrop("tall_grass", Objects.requireNonNull(Items.TALL_GRASS.arch$registryName()).toString(), 0x2f4728, 1, CropType.NATURE, Items.TALL_GRASS.getDescriptionId());
    public static final Crop FERN = registerCrop("fern", Objects.requireNonNull(Items.FERN.arch$registryName()).toString(), 0x1b4509, 1, CropType.NATURE, Items.FERN.getDescriptionId());
    public static final Crop OAK = registerCrop("oak", Objects.requireNonNull(Items.OAK_PLANKS.arch$registryName()).toString(), 0x9d824c, 1, CropType.NATURE, Items.OAK_PLANKS.getDescriptionId());
    public static final Crop SPRUCE = registerCrop("spruce", Objects.requireNonNull(Items.SPRUCE_PLANKS.arch$registryName()).toString(), 0x795933, 1, CropType.NATURE, Items.SPRUCE_PLANKS.getDescriptionId());
    public static final Crop BIRCH = registerCrop("birch", Objects.requireNonNull(Items.BIRCH_PLANKS.arch$registryName()).toString(), 0xc6b579, 1, CropType.NATURE, Items.BIRCH_PLANKS.getDescriptionId());
    public static final Crop JUNGLE = registerCrop("jungle", Objects.requireNonNull(Items.JUNGLE_PLANKS.arch$registryName()).toString(), 0xbd8c6a, 1, CropType.NATURE, Items.JUNGLE_PLANKS.getDescriptionId());
    public static final Crop ACACIA = registerCrop("acacia", Objects.requireNonNull(Items.ACACIA_PLANKS.arch$registryName()).toString(), 0xb86236, 1, CropType.NATURE, Items.ACACIA_PLANKS.getDescriptionId());
    public static final Crop DARK_OAK = registerCrop("dark_oak", Objects.requireNonNull(Items.DARK_OAK_PLANKS.arch$registryName()).toString(), 0x4e3118, 1, CropType.NATURE, Items.DARK_OAK_PLANKS.getDescriptionId());
    public static final Crop MANGROVE = registerCrop("mangrove", Objects.requireNonNull(Items.MANGROVE_PLANKS.arch$registryName()).toString(), 0x7d4133, 1, CropType.NATURE, Items.MANGROVE_PLANKS.getDescriptionId());
    public static final Crop CHERRY = registerCrop("cherry", Objects.requireNonNull(Items.CHERRY_PLANKS.arch$registryName()).toString(), 0xe3b1ab, 1, CropType.NATURE, Items.CHERRY_PLANKS.getDescriptionId());
    public static final Crop BAMBOO = registerCrop("bamboo", Objects.requireNonNull(Items.BAMBOO_PLANKS.arch$registryName()).toString(), 0xe0ca69, 1, CropType.NATURE, Items.BAMBOO_PLANKS.getDescriptionId());
    public static final Crop CRIMSON = registerCrop("crimson", Objects.requireNonNull(Items.CRIMSON_PLANKS.arch$registryName()).toString(), 0x5b2f41, 1, CropType.NATURE, Items.CRIMSON_PLANKS.getDescriptionId());
    public static final Crop WARPED = registerCrop("warped", Objects.requireNonNull(Items.WARPED_PLANKS.arch$registryName()).toString(), 0x388180, 1, CropType.NATURE, Items.WARPED_PLANKS.getDescriptionId());
    public static final Crop APPLE = registerCrop("apple", Objects.requireNonNull(Items.APPLE.arch$registryName()).toString(), 0xff1c2b, 1, CropType.FOOD, Items.APPLE.getDescriptionId());
    public static final Crop GOLDEN_APPLE = registerCrop("golden_apple", Objects.requireNonNull(Items.GOLDEN_APPLE.arch$registryName()).toString(), 0xffffb0, 3, CropType.FOOD, Items.GOLDEN_APPLE.getDescriptionId());
    public static final Crop BREAD = registerCrop("bread", Objects.requireNonNull(Items.BREAD.arch$registryName()).toString(), 0x9e7325, 1, CropType.FOOD, Items.BREAD.getDescriptionId());
    public static final Crop EGG = registerCrop("egg", Objects.requireNonNull(Items.EGG.arch$registryName()).toString(), 0xdfce9b, 1, CropType.FOOD, Items.EGG.getDescriptionId());
    public static final Crop TURTLE_EGG = registerCrop("turtle_egg", Objects.requireNonNull(Items.TURTLE_EGG.arch$registryName()).toString(), 0x58ceaf, 1, CropType.FOOD, Items.TURTLE_EGG.getDescriptionId());
    public static final Crop SNIFFER_EGG = registerCrop("sniffer_egg", Objects.requireNonNull(Items.SNIFFER_EGG.arch$registryName()).toString(), 0xb1413f, 1, CropType.FOOD, Items.SNIFFER_EGG.getDescriptionId());
    public static final Crop TROPICAL_FISH = registerCrop("clownfish", Objects.requireNonNull(Items.TROPICAL_FISH.arch$registryName()).toString(), 0xf29965, 1, CropType.FOOD, Items.TROPICAL_FISH.getDescriptionId());
    public static final Crop PUFFER_FISH = registerCrop("pufferfish", Objects.requireNonNull(Items.PUFFERFISH.arch$registryName()).toString(), 0xc5b200, 1, CropType.FOOD, Items.PUFFERFISH.getDescriptionId());
    public static final Crop COOKIE = registerCrop("cookie", Objects.requireNonNull(Items.COOKIE.arch$registryName()).toString(), 0xd9833e, 1, CropType.FOOD, Items.COOKIE.getDescriptionId());
    public static final Crop CHORUS = registerCrop("chorus", Objects.requireNonNull(Items.CHORUS_FRUIT.arch$registryName()).toString(), 0xaa85aa, 3, CropType.FOOD, Items.CHORUS_FRUIT.getDescriptionId());
    public static final Crop BEEF = registerCrop("raw_beef", Objects.requireNonNull(Items.BEEF.arch$registryName()).toString(), 0xe24940, 1, CropType.FOOD, Items.BEEF.getDescriptionId());
    public static final Crop PORKSHOP = registerCrop("raw_porc", Objects.requireNonNull(Items.PORKCHOP.arch$registryName()).toString(), 0xff8c8c, 1, CropType.FOOD, Items.PORKCHOP.getDescriptionId());
    public static final Crop COD = registerCrop("fish", Objects.requireNonNull(Items.COD.arch$registryName()).toString(), 0xc6a271, 1, CropType.FOOD, Items.COD.getDescriptionId());
    public static final Crop SALMON = registerCrop("salmon", Objects.requireNonNull(Items.SALMON.arch$registryName()).toString(), 0x9e4b49, 1, CropType.FOOD, Items.SALMON.getDescriptionId());
    public static final Crop RAW_CHICKEN = registerCrop("raw_chicken", Objects.requireNonNull(Items.CHICKEN.arch$registryName()).toString(), 0xefbcac, 1, CropType.FOOD, Items.CHICKEN.getDescriptionId());
    public static final Crop RAW_RABBIT = registerCrop("raw_rabbit", Objects.requireNonNull(Items.RABBIT.arch$registryName()).toString(), 0xedb6a6, 1, CropType.FOOD, Items.RABBIT.getDescriptionId());
    public static final Crop RAW_MUTTON = registerCrop("raw_mutton", Objects.requireNonNull(Items.MUTTON.arch$registryName()).toString(), 0xe55c52, 1, CropType.FOOD, Items.MUTTON.getDescriptionId());
    public static final Crop BROWN_MUSHROOM = registerCrop("brown_mushroom", Objects.requireNonNull(Items.BROWN_MUSHROOM.arch$registryName()).toString(), 0xca9777, 1, CropType.FOOD, Items.BROWN_MUSHROOM.getDescriptionId());
    public static final Crop RED_MUSHROOM = registerCrop("red_mushroom", Objects.requireNonNull(Items.RED_MUSHROOM.arch$registryName()).toString(), 0xdf1212, 1, CropType.FOOD, Items.RED_MUSHROOM.getDescriptionId());
    public static final Crop CRIMSON_FUNGUS = registerCrop("crimson_fungus", Objects.requireNonNull(Items.CRIMSON_FUNGUS.arch$registryName()).toString(), 0xa22428, 1, CropType.FOOD, Items.CRIMSON_FUNGUS.getDescriptionId());
    public static final Crop WARPED_FUNGUS = registerCrop("warped_fungus", Objects.requireNonNull(Items.WARPED_FUNGUS.arch$registryName()).toString(), 0x14b283, 1, CropType.FOOD, Items.WARPED_FUNGUS.getDescriptionId());
    public static final Crop ORANGE = registerCrop("orange", Objects.requireNonNull(Items.ORANGE_DYE.arch$registryName()).toString(), 0xff6a00, 1, CropType.CROP, Items.ORANGE_DYE.getDescriptionId());
    public static final Crop MAGENTA = registerCrop("magenta", Objects.requireNonNull(Items.MAGENTA_DYE.arch$registryName()).toString(), 0xff00dc, 1, CropType.CROP, Items.MAGENTA_DYE.getDescriptionId());
    public static final Crop LIGHT_BLUE = registerCrop("light_blue", Objects.requireNonNull(Items.LIGHT_BLUE_DYE.arch$registryName()).toString(), 0x94ff, 1, CropType.CROP, Items.LIGHT_BLUE_DYE.getDescriptionId());
    public static final Crop YELLOW = registerCrop("yellow", Objects.requireNonNull(Items.YELLOW_DYE.arch$registryName()).toString(), 0xffd800, 1, CropType.CROP, Items.YELLOW_DYE.getDescriptionId());
    public static final Crop LIME = registerCrop("lime", Objects.requireNonNull(Items.LIME_DYE.arch$registryName()).toString(), 0xb6ff00, 1, CropType.CROP, Items.LIME_DYE.getDescriptionId());
    public static final Crop PINK = registerCrop("pink", Objects.requireNonNull(Items.PINK_DYE.arch$registryName()).toString(), 0xff7fb6, 1, CropType.CROP, Items.PINK_DYE.getDescriptionId());
    public static final Crop GRAY = registerCrop("gray", Objects.requireNonNull(Items.GRAY_DYE.arch$registryName()).toString(), 0x404040, 1, CropType.CROP, Items.GRAY_DYE.getDescriptionId());
    public static final Crop LIGHT_GRAY = registerCrop("light_gray", Objects.requireNonNull(Items.LIGHT_GRAY_DYE.arch$registryName()).toString(), 0x808080, 1, CropType.CROP, Items.LIGHT_GRAY_DYE.getDescriptionId());
    public static final Crop CYAN = registerCrop("cyan", Objects.requireNonNull(Items.CYAN_DYE.arch$registryName()).toString(), 0xffff, 1, CropType.CROP, Items.CYAN_DYE.getDescriptionId());
    public static final Crop PURPLE = registerCrop("purple", Objects.requireNonNull(Items.PURPLE_DYE.arch$registryName()).toString(), 0xb200ff, 1, CropType.CROP, Items.PURPLE_DYE.getDescriptionId());
    public static final Crop BROWN = registerCrop("brown", Objects.requireNonNull(Items.BROWN_DYE.arch$registryName()).toString(), 0x7f3300, 1, CropType.CROP, Items.BROWN_DYE.getDescriptionId());
    public static final Crop GREEN = registerCrop("green", Objects.requireNonNull(Items.GREEN_DYE.arch$registryName()).toString(), 0x7f0e, 1, CropType.CROP, Items.GREEN_DYE.getDescriptionId());
    public static final Crop RED = registerCrop("red", Objects.requireNonNull(Items.RED_DYE.arch$registryName()).toString(), 0xff0000, 1, CropType.CROP, Items.RED_DYE.getDescriptionId());
    public static final Crop BLACK = registerCrop("black", Objects.requireNonNull(Items.BLACK_DYE.arch$registryName()).toString(), 0x2d2d2d, 1, CropType.CROP, Items.BLACK_DYE.getDescriptionId());
    public static final Crop TOTEM = registerCrop("totem", Objects.requireNonNull(Items.TOTEM_OF_UNDYING.arch$registryName()).toString(), 0xf8eea5, 6, CropType.CROP, Items.TOTEM_OF_UNDYING.getDescriptionId());
    public static final Crop TETHER = registerCrop("tether", Objects.requireNonNull(Items.LEAD.arch$registryName()).toString(), 0xac8e79, 1, CropType.CROP, Items.LEAD.getDescriptionId());
    public static final Crop NAME_TAG = registerCrop("name_tag", Objects.requireNonNull(Items.NAME_TAG.arch$registryName()).toString(), 0x7a7162, 1, CropType.CROP, Items.NAME_TAG.getDescriptionId());
    public static final Crop XP = registerCrop("xp", Objects.requireNonNull(Items.EXPERIENCE_BOTTLE.arch$registryName()).toString(), 0xbaff49, 4, CropType.CROP, Items.EXPERIENCE_BOTTLE.getDescriptionId());
    public static final Crop SEA = registerCrop("sea", Objects.requireNonNull(Items.HEART_OF_THE_SEA.arch$registryName()).toString(), 0x1f96b1, 4, CropType.CROP, Items.HEART_OF_THE_SEA.getDescriptionId());
    public static final Crop SCUTE = registerCrop("scute", Objects.requireNonNull(Items.SCUTE.arch$registryName()).toString(), 0x47bf4a, 2, CropType.ANIMAL, Items.SCUTE.getDescriptionId());
    public static final Crop NAUTILUS = registerCrop("nautilus", Objects.requireNonNull(Items.NAUTILUS_SHELL.arch$registryName()).toString(), 0xd4ccc3, 3, CropType.CROP, Items.NAUTILUS_SHELL.getDescriptionId());
    public static final Crop PHANTOM = registerCrop("phantom", Objects.requireNonNull(Items.PHANTOM_MEMBRANE.arch$registryName()).toString(), 0xdcd9c0, 2, CropType.MONSTER, Items.PHANTOM_MEMBRANE.getDescriptionId());
    public static final Crop WITHER = registerCrop("wither", Objects.requireNonNull(Items.WITHER_ROSE.arch$registryName()).toString(), 0x2a1f19, 5, CropType.MONSTER, Items.WITHER_ROSE.getDescriptionId());
    public static final Crop DRAGON = registerCrop("dragon", Objects.requireNonNull(Items.DRAGON_EGG.arch$registryName()).toString(), 0x2d0133, 7, CropType.MONSTER, Items.DRAGON_EGG.getDescriptionId());
    public static final Crop BLUE = registerCrop("blue", Objects.requireNonNull(Items.BLUE_DYE.arch$registryName()).toString(), 0x26ff, 1, CropType.CROP, Items.BLUE_DYE.getDescriptionId());
    public static final Crop INK = registerCrop("ink", Objects.requireNonNull(Items.INK_SAC.arch$registryName()).toString(), 0x353451, 1, CropType.ANIMAL, Items.INK_SAC.getDescriptionId());
    public static final Crop WHITE = registerCrop("white", Objects.requireNonNull(Items.WHITE_DYE.arch$registryName()).toString(), 0xffffff, 1, CropType.CROP, Items.WHITE_DYE.getDescriptionId());
    public static final Crop HONEYCOMB = registerCrop("honeycomb", Objects.requireNonNull(Items.HONEYCOMB.arch$registryName()).toString(), 0xfabf29, 1, CropType.ANIMAL, Items.HONEYCOMB.getDescriptionId());
    public static final Crop NETHERITE = registerCrop("netherite", Objects.requireNonNull(Items.NETHERITE_INGOT.arch$registryName()).toString(), 0x654740, 5, CropType.CROP, Items.NETHERITE_INGOT.getDescriptionId());
    public static final Crop GLOW_INK = registerCrop("glowink", Objects.requireNonNull(Items.GLOW_INK_SAC.arch$registryName()).toString(), 0x4bdeba, 2, CropType.CROP, Items.GLOW_INK_SAC.getDescriptionId());
    public static final Crop COPPER = registerCrop("copper", Objects.requireNonNull(Items.COPPER_INGOT.arch$registryName()).toString(), 0xfbc3b6, 2, CropType.CROP, Items.COPPER_INGOT.getDescriptionId());
    public static final Crop AMETHYST = registerCrop("amethyst", Objects.requireNonNull(Items.AMETHYST_SHARD.arch$registryName()).toString(), 0xd9cbf2, 3, CropType.CROP, Items.AMETHYST_SHARD.getDescriptionId());
    public static final Crop ECHO_SHARD = registerCrop("echo_shard", Objects.requireNonNull(Items.ECHO_SHARD.arch$registryName()).toString(), 0x3404f, 4, CropType.CROP, Items.ECHO_SHARD.getDescriptionId());

    private static final Map<String, CompatCrop> COMPAT_CROPS = new HashMap<>();

    /**
     * Add a crop from a modded material.
     * This method does not register the crop directly, but will save the crop in {@link Config#getCropPath()}<br/>
     * The crop registration is handled by {@code CropFileHandler.readCrops().forEach(Crops::registerFileCrop);}
     * in {@link #register()}<br/>
     *
     * @param name            crop name, used to generate identifiers
     * @param material        material which the crop grows, could be item ID or item tag (# + id).<br/>
     *                        If the tag with namespace {@code c} is used, the corresponding {@code forge} tag will be generated and included
     * @param color           int value of color
     * @param tier            croparia tier
     * @param type            crop type that specifies the textures. See also {@link CropType}
     * @param translationKeys The mod dependencies with corresponding translation keys.
     *                        The translation translationKey for the first existing mod will be used.
     * @return the intermediate data entity of the crop
     */
    public static @Nullable CompatCrop compatCrop(
        String name, String material, int color, int tier, CropType type, @NotNull Map<String, String> translationKeys
    ) {
        for (String mod : translationKeys.keySet()) {
            if (Platform.isModLoaded(mod)) {
                CompatCrop crop = COMPAT_CROPS.get(material);
                if (crop == null) {
                    crop = new CompatCrop(name, material, type, color, tier, translationKeys);
                    COMPAT_CROPS.put(material, crop);
                } else {
                    CropariaIf.LOGGER.info("Registered compat crop {} of material {}, merge with existing crops {}", name, material, crop.getName());
                }
                return crop;
            }
        }
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
        COMPAT_CROPS.forEach((material, crop) -> CropFileHandler.saveCompatCrop(crop));
        CropFileHandler.readCrops().forEach(Crops::registerFileCrop);
        for (Crop crop : CROPS) {
            CropariaItems.registerCrop(crop);
            CropariaBlocks.registerCrop(crop);
        }
    }

    private static void registerFileCrop(@NotNull RawCrop raw) {
        if (raw.dependencies() == null || shouldLoad(raw.dependencies())) {
            Crop.of(raw).ifPresentOrElse(
                CROPS::add,
                () -> CropariaIf.LOGGER.error("Failed to create custom crop %s".formatted(raw.name()))
            );
        } else {
            CropariaIf.LOGGER.warn("Crop {} is skipped due to missing dependencies", raw.name());
        }
    }
}
