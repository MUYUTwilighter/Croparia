package cool.muyucloud.croparia.registry;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.CropFileHandler;
import cool.muyucloud.croparia.api.crop.CropType;
import cool.muyucloud.croparia.api.crop.RawCrop;
import dev.architectury.injectables.targets.ArchitecturyTarget;
import dev.architectury.platform.Platform;
import net.minecraft.world.item.Item;
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

    public static CompletableFuture<Suggestions> cropSuggestions(SuggestionsBuilder builder) {
        Crops.cropNames().forEach(builder::suggest);
        return builder.buildFuture();
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

    public static boolean containsCrop(@NotNull String name) {
        return CROPS.containsKey(name);
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
     *
     * @return {@code true} if the crop is recorded, {@code false} if it already exists
     */
    public static boolean recordCustom(Crop crop) {
        boolean r = true;
        if (CROPS.containsKey(crop.getName())) {
            CropariaIf.LOGGER.info("Replace existing crop \"{}\"", crop);
            r = false;
        }
        CROPS.put(crop.getName(), crop);
        return r;
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
     * @param material       material item which the crop grows
     * @param color          int value of color
     * @param tier           tier
     * @param type           crop type that specifies the textures. See also {@link CropType}
     */
    public static @NotNull Crop vanilla(
        @NotNull String name, Item material, int color, int tier, @NotNull CropType type
    ) {
        Crop crop = Crop.create(
            name, Objects.requireNonNull(material.arch$registryName()).toString(), color, tier, type,
            material.getDescriptionId(), Map.of()
        ).orElseThrow(
            () -> new IllegalArgumentException("Vanilla crop %s failed to create".formatted(name))
        );
        if (CropariaIf.CONFIG.inBlacklist(name, "minecraft")) {
            CropariaIf.LOGGER.debug("Skipped vanilla crop \"{}\" due to blacklist", name);
        } else {
            recordBuiltin(crop);
        }
        return crop;
    }

    public static final Crop COAL = vanilla("coal", Items.COAL, 0x333333, 1, CropType.CROP);
    public static final Crop IRON = vanilla("iron", Items.IRON_INGOT, 0xd8d8d8, 2, CropType.CROP);
    public static final Crop GOLD = vanilla("gold", Items.GOLD_INGOT, 0xffff8b, 2, CropType.CROP);
    public static final Crop LAPIS = vanilla("lapis", Items.LAPIS_LAZULI, 0x7497ea, 3, CropType.CROP);
    public static final Crop REDSTONE = vanilla("redstone", Items.REDSTONE, 0xff2626, 3, CropType.CROP);
    public static final Crop DIAMOND = vanilla("diamond", Items.DIAMOND, 0x8cf4e2, 4, CropType.CROP);
    public static final Crop EMERALD = vanilla("emerald", Items.EMERALD, 0x17dd62, 4, CropType.CROP);
    public static final Crop CLAY = vanilla("clay", Items.CLAY, 0x9aa3b3, 1, CropType.CROP);
    public static final Crop GLOWSTONE = vanilla("glowstone", Items.GLOWSTONE, 0xffff00, 3, CropType.CROP);
    public static final Crop QUARTZ = vanilla("quartz", Items.QUARTZ, 0xdfd8cf, 3, CropType.CROP);
    public static final Crop SHARD = vanilla("shard", Items.PRISMARINE_SHARD, 0x73b5aa, 2, CropType.CROP);
    public static final Crop CRYSTAL = vanilla("crystal", Items.PRISMARINE_CRYSTALS, 0xcbe7e0, 2, CropType.CROP);
    public static final Crop ENDER = vanilla("ender", Items.ENDER_PEARL, 0x8cf4e2, 3, CropType.MONSTER);
    public static final Crop BONE = vanilla("bone", Items.BONE, 0xeceac9, 2, CropType.MONSTER);
    public static final Crop EYE = vanilla("eye", Items.SPIDER_EYE, 0xeceac9, 2, CropType.MONSTER);
    public static final Crop POWDER = vanilla("powder", Items.GUNPOWDER, 0x727272, 2, CropType.MONSTER);
    public static final Crop PAPER = vanilla("paper", Items.PAPER, 0xeaeaea, 1, CropType.CROP);
    public static final Crop SUGAR = vanilla("sugar", Items.SUGAR, 0xf5ffff, 1, CropType.CROP);
    public static final Crop CHARCOAL = vanilla("charcoal", Items.CHARCOAL, 0x53493a, 1, CropType.CROP);
    public static final Crop FLINT = vanilla("flint", Items.FLINT, 0x565656, 1, CropType.CROP);
    public static final Crop SNOWBALL = vanilla("snowball", Items.SNOWBALL, 0xfbffff, 1, CropType.CROP);
    public static final Crop FIREWORK = vanilla("firework", Items.FIREWORK_STAR, 0x575757, 1, CropType.CROP);
    public static final Crop NETHER = vanilla("nether", Items.NETHER_BRICK, 0x46262c, 3, CropType.CROP);
    public static final Crop BOTTLE = vanilla("bottle", Items.GLASS_BOTTLE, 0xe4e4e4, 1, CropType.CROP);
    public static final Crop FOOT = vanilla("foot", Items.RABBIT_FOOT, 0xcfa978, 1, CropType.ANIMAL);
    public static final Crop HIDE = vanilla("hide", Items.RABBIT_HIDE, 0xc79e67, 1, CropType.ANIMAL);
    public static final Crop LEATHER = vanilla("leather", Items.LEATHER, 0xc65c35, 1, CropType.ANIMAL);
    public static final Crop FEATHER = vanilla("feather", Items.FEATHER, 0xffffff, 1, CropType.ANIMAL);
    public static final Crop BLAZE = vanilla("blaze", Items.BLAZE_ROD, 0xffcb00, 3, CropType.MONSTER);
    public static final Crop GHAST = vanilla("ghast", Items.GHAST_TEAR, 0xe3fde8, 4, CropType.MONSTER);
    public static final Crop MAGMA = vanilla("magma", Items.MAGMA_CREAM, 0xcea025, 3, CropType.MONSTER);
    public static final Crop SHELL = vanilla("shell", Items.SHULKER_SHELL, 0x9e749e, 4, CropType.MONSTER);
    public static final Crop STAR = vanilla("star", Items.NETHER_STAR, 0xf0f3f3, 6, CropType.MONSTER);
    public static final Crop STRING = vanilla("string", Items.STRING, 0xf7f7f7, 2, CropType.MONSTER);
    public static final Crop SLIME = vanilla("slime", Items.SLIME_BALL, 0x84c873, 2, CropType.MONSTER);
    public static final Crop ZOMBIE = vanilla("zombie", Items.ROTTEN_FLESH, 0xbd5d37, 2, CropType.MONSTER);
    public static final Crop VINE = vanilla("vine", Items.VINE, 0x1b4509, 1, CropType.NATURE);
    public static final Crop WEEPING_VINES = vanilla("weeping_vines", Items.WEEPING_VINES, 0x7a0000, 1, CropType.NATURE);
    public static final Crop TWISTING_VINES = vanilla("twisting_vines", Items.TWISTING_VINES, 0x14b283, 1, CropType.NATURE);
    public static final Crop LILY_PAD = vanilla("lilypad", Items.LILY_PAD, 0xc5f14, 1, CropType.NATURE);
    public static final Crop BUSH = vanilla("bush", Items.DEAD_BUSH, 0x946428, 1, CropType.NATURE);
    public static final Crop GRASS = vanilla("grass", Items.SHORT_GRASS, 0x820510b, 1, CropType.NATURE);
    public static final Crop LARGE_FERN = vanilla("large_fern", Items.LARGE_FERN, 0x4a7240, 1, CropType.NATURE);
    public static final Crop TALL_GRASS = vanilla("tall_grass", Items.TALL_GRASS, 0x2f4728, 1, CropType.NATURE);
    public static final Crop FERN = vanilla("fern", Items.FERN, 0x1b4509, 1, CropType.NATURE);
    public static final Crop OAK = vanilla("oak", Items.OAK_PLANKS, 0x9d824c, 1, CropType.NATURE);
    public static final Crop SPRUCE = vanilla("spruce", Items.SPRUCE_PLANKS, 0x795933, 1, CropType.NATURE);
    public static final Crop BIRCH = vanilla("birch", Items.BIRCH_PLANKS, 0xc6b579, 1, CropType.NATURE);
    public static final Crop JUNGLE = vanilla("jungle", Items.JUNGLE_PLANKS, 0xbd8c6a, 1, CropType.NATURE);
    public static final Crop ACACIA = vanilla("acacia", Items.ACACIA_PLANKS, 0xb86236, 1, CropType.NATURE);
    public static final Crop DARK_OAK = vanilla("dark_oak", Items.DARK_OAK_PLANKS, 0x4e3118, 1, CropType.NATURE);
    public static final Crop MANGROVE = vanilla("mangrove", Items.MANGROVE_PLANKS, 0x7d4133, 1, CropType.NATURE);
    public static final Crop CHERRY = vanilla("cherry", Items.CHERRY_PLANKS, 0xe3b1ab, 1, CropType.NATURE);
    public static final Crop BAMBOO = vanilla("bamboo", Items.BAMBOO_PLANKS, 0xe0ca69, 1, CropType.NATURE);
    public static final Crop CRIMSON = vanilla("crimson", Items.CRIMSON_PLANKS, 0x5b2f41, 1, CropType.NATURE);
    public static final Crop WARPED = vanilla("warped", Items.WARPED_PLANKS, 0x388180, 1, CropType.NATURE);
    public static final Crop APPLE = vanilla("apple", Items.APPLE, 0xff1c2b, 1, CropType.FOOD);
    public static final Crop GOLDEN_APPLE = vanilla("golden_apple", Items.GOLDEN_APPLE, 0xffffb0, 3, CropType.FOOD);
    public static final Crop BREAD = vanilla("bread", Items.BREAD, 0x9e7325, 1, CropType.FOOD);
    public static final Crop EGG = vanilla("egg", Items.EGG, 0xdfce9b, 1, CropType.FOOD);
    public static final Crop TURTLE_EGG = vanilla("turtle_egg", Items.TURTLE_EGG, 0x58ceaf, 1, CropType.FOOD);
    public static final Crop SNIFFER_EGG = vanilla("sniffer_egg", Items.SNIFFER_EGG, 0xb1413f, 1, CropType.FOOD);
    public static final Crop TROPICAL_FISH = vanilla("clownfish", Items.TROPICAL_FISH, 0xf29965, 1, CropType.FOOD);
    public static final Crop PUFFER_FISH = vanilla("pufferfish", Items.PUFFERFISH, 0xc5b200, 1, CropType.FOOD);
    public static final Crop COOKIE = vanilla("cookie", Items.COOKIE, 0xd9833e, 1, CropType.FOOD);
    public static final Crop CHORUS = vanilla("chorus", Items.CHORUS_FRUIT, 0xaa85aa, 3, CropType.FOOD);
    public static final Crop BEEF = vanilla("raw_beef", Items.BEEF, 0xe24940, 1, CropType.FOOD);
    public static final Crop PORKSHOP = vanilla("raw_porc", Items.PORKCHOP, 0xff8c8c, 1, CropType.FOOD);
    public static final Crop COD = vanilla("fish", Items.COD, 0xc6a271, 1, CropType.FOOD);
    public static final Crop SALMON = vanilla("salmon", Items.SALMON, 0x9e4b49, 1, CropType.FOOD);
    public static final Crop RAW_CHICKEN = vanilla("raw_chicken", Items.CHICKEN, 0xefbcac, 1, CropType.FOOD);
    public static final Crop RAW_RABBIT = vanilla("raw_rabbit", Items.RABBIT, 0xedb6a6, 1, CropType.FOOD);
    public static final Crop RAW_MUTTON = vanilla("raw_mutton", Items.MUTTON, 0xe55c52, 1, CropType.FOOD);
    public static final Crop BROWN_MUSHROOM = vanilla("brown_mushroom", Items.BROWN_MUSHROOM, 0xca9777, 1, CropType.FOOD);
    public static final Crop RED_MUSHROOM = vanilla("red_mushroom", Items.RED_MUSHROOM, 0xdf1212, 1, CropType.FOOD);
    public static final Crop CRIMSON_FUNGUS = vanilla("crimson_fungus", Items.CRIMSON_FUNGUS, 0xa22428, 1, CropType.FOOD);
    public static final Crop WARPED_FUNGUS = vanilla("warped_fungus", Items.WARPED_FUNGUS, 0x14b283, 1, CropType.FOOD);
    public static final Crop ORANGE = vanilla("orange", Items.ORANGE_DYE, 0xff6a00, 1, CropType.CROP);
    public static final Crop MAGENTA = vanilla("magenta", Items.MAGENTA_DYE, 0xff00dc, 1, CropType.CROP);
    public static final Crop LIGHT_BLUE = vanilla("light_blue", Items.LIGHT_BLUE_DYE, 0x94ff, 1, CropType.CROP);
    public static final Crop YELLOW = vanilla("yellow", Items.YELLOW_DYE, 0xffd800, 1, CropType.CROP);
    public static final Crop LIME = vanilla("lime", Items.LIME_DYE, 0xb6ff00, 1, CropType.CROP);
    public static final Crop PINK = vanilla("pink", Items.PINK_DYE, 0xff7fb6, 1, CropType.CROP);
    public static final Crop GRAY = vanilla("gray", Items.GRAY_DYE, 0x404040, 1, CropType.CROP);
    public static final Crop LIGHT_GRAY = vanilla("light_gray", Items.LIGHT_GRAY_DYE, 0x808080, 1, CropType.CROP);
    public static final Crop CYAN = vanilla("cyan", Items.CYAN_DYE, 0xffff, 1, CropType.CROP);
    public static final Crop PURPLE = vanilla("purple", Items.PURPLE_DYE, 0xb200ff, 1, CropType.CROP);
    public static final Crop BROWN = vanilla("brown", Items.BROWN_DYE, 0x7f3300, 1, CropType.CROP);
    public static final Crop GREEN = vanilla("green", Items.GREEN_DYE, 0x7f0e, 1, CropType.CROP);
    public static final Crop RED = vanilla("red", Items.RED_DYE, 0xff0000, 1, CropType.CROP);
    public static final Crop BLACK = vanilla("black", Items.BLACK_DYE, 0x2d2d2d, 1, CropType.CROP);
    public static final Crop TOTEM = vanilla("totem", Items.TOTEM_OF_UNDYING, 0xf8eea5, 6, CropType.CROP);
    public static final Crop TETHER = vanilla("tether", Items.LEAD, 0xac8e79, 1, CropType.CROP);
    public static final Crop NAME_TAG = vanilla("name_tag", Items.NAME_TAG, 0x7a7162, 1, CropType.CROP);
    public static final Crop XP = vanilla("xp", Items.EXPERIENCE_BOTTLE, 0xbaff49, 4, CropType.CROP);
    public static final Crop SEA = vanilla("sea", Items.HEART_OF_THE_SEA, 0x1f96b1, 4, CropType.CROP);
    public static final Crop SCUTE = vanilla("scute", Items.TURTLE_SCUTE, 0x47bf4a, 2, CropType.ANIMAL);
    public static final Crop NAUTILUS = vanilla("nautilus", Items.NAUTILUS_SHELL, 0xd4ccc3, 3, CropType.CROP);
    public static final Crop PHANTOM = vanilla("phantom", Items.PHANTOM_MEMBRANE, 0xdcd9c0, 2, CropType.MONSTER);
    public static final Crop WITHER = vanilla("wither", Items.WITHER_ROSE, 0x2a1f19, 5, CropType.MONSTER);
    public static final Crop DRAGON = vanilla("dragon", Items.DRAGON_EGG, 0x2d0133, 7, CropType.MONSTER);
    public static final Crop BLUE = vanilla("blue", Items.BLUE_DYE, 0x26ff, 1, CropType.CROP);
    public static final Crop INK = vanilla("ink", Items.INK_SAC, 0x353451, 1, CropType.ANIMAL);
    public static final Crop WHITE = vanilla("white", Items.WHITE_DYE, 0xffffff, 1, CropType.CROP);
    public static final Crop HONEYCOMB = vanilla("honeycomb", Items.HONEYCOMB, 0xfabf29, 1, CropType.ANIMAL);
    public static final Crop NETHERITE = vanilla("netherite", Items.NETHERITE_INGOT, 0x654740, 5, CropType.CROP);
    public static final Crop GLOW_INK = vanilla("glowink", Items.GLOW_INK_SAC, 0x4bdeba, 2, CropType.CROP);
    public static final Crop COPPER = vanilla("copper", Items.COPPER_INGOT, 0xfbc3b6, 2, CropType.CROP);
    public static final Crop AMETHYST = vanilla("amethyst", Items.AMETHYST_SHARD, 0xd9cbf2, 3, CropType.CROP);
    public static final Crop ECHO_SHARD = vanilla("echo_shard", Items.ECHO_SHARD, 0x3404f, 4, CropType.CROP);

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

    public static String forIngot(String name) {
        String platform = ArchitecturyTarget.getCurrentTarget();
        return "#c:ingots/" + name;
    }

    public static String forGem(String name) {
        String platform = ArchitecturyTarget.getCurrentTarget();
        return "#c:gems/" + name;
    }

    public static String forDust(String name) {
        String platform = ArchitecturyTarget.getCurrentTarget();
        return "#c:dusts/" + name;
    }

    public static final Crop ADAMANTITE = compat("adamantite", forIngot("adamantite"), 0xAD0E19, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.adamantite_ingot"
    ));
    public static final Crop ALUMINUM = compat("aluminum", forIngot("aluminum"), 0x9E9E9E, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.aluminum_ingot",
        "modern_industrialization", "item.modern_industrialization.aluminum_ingot",
        "gtceu", "material.gtceu.aluminum"
    ));
    public static final Crop AMERICIUM = compat("americium", forIngot("americium"), 0x7F8C8D, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.americium"
    ));
    public static final Crop ANTIMONY = compat("antimony", forIngot("antimony"), 0x8A8A8A, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.antimony_ingot",
        "gtceu", "material.gtceu.antimony"
    ));
    public static final Crop AQUARIUM = compat("aquarium", forIngot("aquarium"), 0x4392DC, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.aquarium_ingot"
    ));
    public static final Crop BARONYTE = compat("baronyte", forIngot("baronyte"), 0xE56544, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.baronyte"
    ));
    public static final Crop BANGLUM = compat("banglum", forIngot("banglum"), 0x734C28, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.banglum_ingot"
    ));
    public static final Crop BERYLLIUM = compat("beryllium", forIngot("beryllium"), 0xA4C639, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.beryllium_ingot",
        "gtceu", "material.gtceu.beryllium"
    ));
    public static final Crop BISMUTH = compat("bismuth", forIngot("bismuth"), 0xB87333, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.bismuth"
    ));
    public static final Crop BLAZIUM = compat("blazium", forIngot("blazium"), 0xFCEB6F, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.blazium_ingot"
    ));
    public static final Crop BLOODSTONE = compat("bloodstone", forGem("bloodstone"), 0x9B0000, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.bloodstone"
    ));
    public static final Crop CADMIUM = compat("cadmium", forIngot("cadmium"), 0x8B0000, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.cadmium_ingot"
    ));
    public static final Crop CALORITE = compat("calorite", forIngot("calorite"), 0x8B0000, 3, CropType.CROP, Map.of(
        "ad_astra", "item.ad_astra.calorite_ingot"
    ));
    public static final Crop CARMOT = compat("carmot", forIngot("carmot"), 0xC1283F, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.carmot_ingot"
    ));
    public static final Crop CELESTIUM = compat("celestium", forIngot("celestium"), 0xF7D3B6, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.celestium_ingot"
    ));
    public static final Crop CERTUS = compat("certus", forGem("certus_quartz"), 0xB8D8FC, 3, CropType.CROP, Map.of(
        "ae2", "item.ae2.certus_quartz_crystal"
    ));
    public static final Crop CHROMIUM = compat("chromium", forIngot("chromium"), 0xE0E0E0, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.chrome_ingot",
        "modern_industrialization", "item.modern_industrialization.chromium_ingot",
        "gtceu", "material.gtceu.chromium"
    ));
    public static final Crop COBALT = compat("cobalt", forIngot("cobalt"), 0x1E90FF, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.cobalt"
    ));
    public static final Crop CRYSTALLITE = compat("crystallite", forGem("crystallite"), 0x9EB1C8, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.crystallite"
    ));
    public static final Crop DARMSTADTIUM = compat("darmstadtium", forIngot("darmstadtium"), 0xB67A56, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.darmstadtium"
    ));
    public static final Crop DESH = compat("desh", forIngot("desh"), 0x8B0000, 3, CropType.CROP, Map.of(
        "ad_astra", "item.ad_astra.desh_ingot"
    ));
    public static final Crop DURASTEEL = compat("durasteel", forIngot("durasteel"), 0x4B4B4B, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.durasteel_ingot"
    ));
    public static final Crop ELECANIUM = compat("elecanium", forIngot("elecanium"), 0x34ACDE, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.elecanium_ingot"
    ));
    public static final Crop EMBERSTONE = compat("emberstone", forIngot("emberstone"), 0xF17F22, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.emberstone_ingot"
    ));
    public static final Crop EUROPIUM = compat("europium", forIngot("europium"), 0xFFD700, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.europium"
    ));
    public static final Crop FLUIX = compat("fluix", forGem("fluix"), 0x8F5CCB, 3, CropType.CROP, Map.of(
        "ae2", "item.ae2.fluix_crystal"
    ));
    public static final Crop GALLIUM = compat("gallium", forIngot("gallium"), 0xBCD2E8, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.gallium"
    ));
    public static final Crop GEMENYTE = compat("gemenyte", forGem("gemenyte"), 0x8F5CCB, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.gemenyte"
    ));
    public static final Crop GHASTLY = compat("ghastly", forIngot("ghastly"), 0xF8FC9C, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.ghastly_ingot"
    ));
    public static final Crop GHOULISH = compat("ghoulish", forIngot("ghoulish"), 0x7EA8FC, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.ghoulish_ingot"
    ));
    public static final Crop HALLOWED = compat("hallowed", forIngot("hallowed"), 0xFCF899, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.hallowed_ingot"
    ));
    public static final Crop INDIUM = compat("indium", forIngot("indium"), 0x4A7190, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.indium"
    ));
    public static final Crop JADE = compat("jade", forGem("jade"), 0x8F5CCB, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.jade"
    ));
    public static final Crop JEWELYTE = compat("jewelyte", forGem("jewelyte"), 0x8F5CCB, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.jewelyte"
    ));
    public static final Crop KYBER = compat("kyber", forIngot("kyber"), 0xB275D7, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.kyber_ingot"
    ));
    public static final Crop LEAD = compat("lead", forIngot("lead"), 0x6F6B77, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.lead_ingot",
        "indrev", "item.indrev.lead_ingot",
        "modern_industrialization", "item.modern_industrialization.lead_ingot",
        "thermal_foundation", "item.thermal.lead_ingot",
        "gtceu", "material.gtceu.lead"
    ));
    public static final Crop LIMONITE = compat("limonite", forIngot("limonite"), 0xE79353, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.limonite_ingot"
    ));
    public static final Crop LITHIUM = compat("lithium", forIngot("lithium"), 0xC0C0C0, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.lithium"
    ));
    public static final Crop LUNAR = compat("lunar", forIngot("lunar"), 0xA32F9D, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.lunar_ingot"
    ));
    public static final Crop MANGANESE = compat("manganese", forIngot("manganese"), 0xEBBED6, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.manganese_ingot",
        "gtceu", "material.gtceu.manganese"
    ));
    public static final Crop METALLURGIUM = compat("metallurgium", forIngot("metallurgium"), 0x5417B4, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.metallurgium_ingot"
    ));
    public static final Crop MIDAS_GOLD = compat("midas_gold", forIngot("midas_gold"), 0xFCDE80, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.midas_gold_ingot"
    ));
    public static final Crop MOLYBDENUM = compat("molybdenum", forIngot("molybdenum"), 0x708090, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.molybdenum"
    ));
    public static final Crop MONAZITE = compat("monazite", "#c:dusts/monazite", 0xFCC4B3, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.monazite_dust"
    ));
    public static final Crop MYSTITE = compat("mystite", forIngot("mystite"), 0xB3FCC4, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.mystite_ingot"
    ));
    public static final Crop MYTHRIL = compat("mythril", forIngot("mythril"), 0x63E7F8, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.mythril_ingot"
    ));
    public static final Crop NAQUADAH = compat("naquadah", forIngot("naquadah"), 0x556B2F, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.naquadah"
    ));
    public static final Crop NEODYMIUM = compat("neodymium", forIngot("neodymium"), 0x7F7F7F, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.neodymium"
    ));
    public static final Crop NICKEL = compat("nickel", forIngot("nickel"), 0xAEAC8C, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.nickel_ingot",
        "modern_industrialization", "item.modern_industrialization.nickel_ingot",
        "thermal_foundation", "item.thermal.nickel_ingot",
        "gtceu", "material.gtceu.nickel"
    ));
    public static final Crop NIOBIUM = compat("niobium", forIngot("niobium"), 0x8E44AD, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.niobium"
    ));
    public static final Crop IRIDIUM = compat("iridium", forIngot("iridium"), 0x8F9E9A, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.iridium_ingot",
        "modern_industrialization", "item.modern_industrialization.iridium_ingot"
    ));
    public static final Crop ORICHALCUM = compat("orichalcum", forIngot("orichalcum"), 0x9EF1A5, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.orichalcum_ingot"
    ));
    public static final Crop ORNAMYTE = compat("ornamyte", forGem("ornamyte"), 0x8F5CCB, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.ornamyte"
    ));
    public static final Crop OSMIUM = compat("osmium", forIngot("osmium"), 0x9EB1C8, 3, CropType.CROP, Map.of(
        "mekanism", "item.mekanism.ingot_osmium",
        "mythicmetals", "item.mythicmetals.osmium_ingot"
    ));
    public static final Crop OSTRUM = compat("ostrum", forIngot("ostrum"), 0x7F7F7F, 3, CropType.CROP, Map.of(
        "ad_astra", "item.ad_astra.ostrum_ingot"
    ));
    public static final Crop PALLADIUM = compat("palladium", forIngot("palladium"), 0xED9926, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.palladium_ingot",
        "gtceu", "material.gtceu.palladium"
    ));
    public static final Crop PERIDOT = compat("peridot", forGem("peridot"), 0xAAD26F, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.peridot_gem"
    ));
    public static final Crop PLATINUM = compat("platinum", forIngot("platinum"), 0xAABBC7, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.platinum_ingot",
        "mythicmetals", "item.mythicmetals.platinum_ingot",
        "modern_industrialization", "item.modern_industrialization.platinum_ingot",
        "gtceu", "material.gtceu.platinum"
    ));
    public static final Crop PLUTONIUM = compat("plutonium", forIngot("plutonium"), 0x4CFF00, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.plutonium"
    ));
    public static final Crop PROMETHEUM = compat("prometheum", forIngot("prometheum"), 0x396955, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.prometheum_ingot"
    ));
    public static final Crop QUADRILLUM = compat("quadrillum", forIngot("quadrillum"), 0x626E6E, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.quadrillum_ingot"
    ));
    public static final Crop RED_GARNET = compat("red_garnet", forGem("red_garnet"), 0xE66C67, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.red_garnet_gem"
    ));
    public static final Crop RHODIUM = compat("rhodium", forIngot("rhodium"), 0xB5BFC6, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.rhodium"
    ));
    public static final Crop ROSITE = compat("rosite", forIngot("rosite"), 0xF16B59, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.rosite_ingot"
    ));
    public static final Crop RUBY = compat("ruby", forGem("ruby"), 0xC45E68, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.ruby_gem"
    ));
    public static final Crop RUNITE = compat("runite", forIngot("runite"), 0x00AECE, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.runite_ingot"
    ));
    public static final Crop RUTHENIUM = compat("ruthenium", forIngot("ruthenium"), 0x838B8B, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.ruthenium"
    ));
    // special
    public static final Crop SALT = compat("salt", "#c:dusts/salt", 0x8F9E9A, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.salt_dust"
    ));
    public static final Crop SAMARIUM = compat("samarium", forIngot("samarium"), 0xFF4500, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.samarium"
    ));
    public static final Crop SAPPHIRE = compat("sapphire", forGem("sapphire"), 0x6D9BEC, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.sapphire_gem"
    ));
    public static final Crop SHYRESTONE = compat("shyrestone", forIngot("shyrestone"), 0xA1EAFC, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.shyrestone_ingot"
    ));
    public static final Crop SHYREGEM = compat("shyregem", forGem("shyregem"), 0xA1EAFC, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.shyregem"
    ));
    public static final Crop SILVER = compat("silver", forIngot("silver"), 0xD4E1E2, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.silver_ingot",
        "indrev", "item.indrev.silver_ingot",
        "modern_industrialization", "item.modern_industrialization.silver_ingot",
        "thermal_foundation", "item.thermal.silver_ingot",
        "mythicmetals", "item.mythicmetals.silver_ingot",
        "gtceu", "material.gtceu.silver"
    ));
    public static final Crop SILICON = compat("silicon", "#c:silicon", 0x66546D, 3, CropType.CROP, Map.of(
        "ae2", "item.ae2.silicon"
    ));
    public static final Crop SKELETAL = compat("skeletal", forIngot("skeletal"), 0xB3A997, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.skeletal_ingot"
    ));
    public static final Crop STAR_PLATINUM = compat("star_platinum", forIngot("star_platinum"), 0xA199D3, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.star_platinum"
    ));
    public static final Crop STORMYX = compat("stormyx", forIngot("stormyx"), 0xE366DC, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.stormyx_ingot"
    ));
    public static final Crop TANTALUM = compat("tantalum", forIngot("tantalum"), 0xA9A9A9, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.tantalum"
    ));
    public static final Crop THORIUM = compat("thorium", forIngot("thorium"), 0x00CED1, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.thorium"
    ));
    public static final Crop TIN = compat("tin", forIngot("tin"), 0xE3E3E0, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.tin_ingot",
        "indrev", "item.indrev.tin_ingot",
        "modern_industrialization", "item.modern_industrialization.tin_ingot",
        "mekanism", "item.mekanism.ingot_tin",
        "thermal_foundation", "item.thermal.tin_ingot",
        "mythicmetals", "item.mythicmetals.tin_ingot",
        "gtceu", "material.gtceu.tin"
    ));
    public static final Crop TITANIUM = compat("titanium", forIngot("titanium"), 0xDDDDE3, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.titanium_ingot",
        "modern_industrialization", "item.modern_industrialization.titanium_ingot",
        "gtceu", "material.gtceu.titanium"
    ));
    public static final Crop TRINIUM = compat("trinium", forIngot("trinium"), 0x4682B4, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.trinium"
    ));
    public static final Crop TUNGSTEN = compat("tungsten", forIngot("tungsten"), 0x797D80, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.tungsten_ingot",
        "indrev", "item.indrev.tungsten_ingot",
        "modern_industrialization", "item.modern_industrialization.tungsten_ingot",
        "gtceu", "material.gtceu.tungsten"
    ));
    public static final Crop URANIUM = compat("uranium", forIngot("uranium"), 0x32CE00, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.uranium_ingot",
        "mekanism", "item.mekanism.ingot_uranium"
    ));
    public static final Crop VANADIUM = compat("vanadium", forIngot("vanadium"), 0x228B22, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.vanadium"
    ));
    public static final Crop VARSIUM = compat("varsium", forIngot("varsium"), 0xDABF59, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.varsium_ingot"
    ));
    public static final Crop YELLOW_GARNET = compat("yellow_garnet", forGem("yellow_garnet"), 0xEACB5F, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.yellow_garnet_gem"
    ));
    public static final Crop YTTRIUM = compat("yttrium", forIngot("yttrium"), 0xFF6347, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.yttrium"
    ));
    public static final Crop ZINC = compat("zinc", forIngot("zinc"), 0xEDEEEC, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.zinc_ingot",
        "create", "item.create.zinc_ingot"
    ));

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
                    }
                },
                () -> CropariaIf.LOGGER.error("Inadequate or invalid arguments for custom crop \"{}\" from file definition", raw.name())
            );
        } else {
            CropariaIf.LOGGER.debug("Skipped custom crop \"{}\" from file definition due to missing dependencies {}", raw.name(), raw.dependencies());
        }
    }
}