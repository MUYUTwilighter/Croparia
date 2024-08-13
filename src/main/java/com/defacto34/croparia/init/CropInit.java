//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.init;

import com.defacto34.croparia.api.crop.Crop;
import com.defacto34.croparia.api.crop.CropType;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CropInit {
    public static List<Crop> cropList = new ArrayList();
    public static List<Crop> compatCropList = new ArrayList();
    public static List<JsonObject> recipes = new ArrayList();
    public static final Crop ELEMENTAL;
    public static final Crop COAL;
    public static final Crop IRON;
    public static final Crop GOLD;
    public static final Crop LAPIS;
    public static final Crop REDSTONE;
    public static final Crop DIAMOND;
    public static final Crop EMERALD;
    public static final Crop CLAY;
    public static final Crop GLOWSTONE;
    public static final Crop QUARTZ;
    public static final Crop SHARD;
    public static final Crop CRYSTAL;
    public static final Crop ENDER;
    public static final Crop BONE;
    public static final Crop EYE;
    public static final Crop POWDER;
    public static final Crop PAPER;
    public static final Crop SUGAR;
    public static final Crop CHARCOAL;
    public static final Crop FLINT;
    public static final Crop SNOWBALL;
    public static final Crop FIREWORK;
    public static final Crop NETHER;
    public static final Crop BOTTLE;
    public static final Crop FOOT;
    public static final Crop HIDE;
    public static final Crop LEATHER;
    public static final Crop FEATHER;
    public static final Crop BLAZE;
    public static final Crop GHAST;
    public static final Crop MAGMA;
    public static final Crop SHELL;
    public static final Crop STAR;
    public static final Crop STRING;
    public static final Crop SLIME;
    public static final Crop ZOMBIE;
    public static final Crop VINE;
    public static final Crop WEEPING_VINES;
    public static final Crop TWISTING_VINES;
    public static final Crop LILY_PAD;
    public static final Crop BUSH;
    public static final Crop GRASS;
    public static final Crop LARGE_FERN;
    public static final Crop TALL_GRASS;
    public static final Crop FERN;
    public static final Crop OAK;
    public static final Crop SPRUCE;
    public static final Crop BIRCH;
    public static final Crop JUNGLE;
    public static final Crop ACACIA;
    public static final Crop DARK_OAK;
    public static final Crop MANGROVE;
    public static final Crop CHERRY;
    public static final Crop BAMBOO;
    public static final Crop CRIMSON;
    public static final Crop WARPED;
    public static final Crop APPLE;
    public static final Crop GOLDEN_APPLE;
    public static final Crop BREAD;
    public static final Crop EGG;
    public static final Crop TURTLE_EGG;
    public static final Crop SNIFFER_EGG;
    public static final Crop TROPICAL_FISH;
    public static final Crop PUFFER_FISH;
    public static final Crop COOKIE;
    public static final Crop CHORUS;
    public static final Crop BEEF;
    public static final Crop PORKSHOP;
    public static final Crop COD;
    public static final Crop SALMON;
    public static final Crop RAW_CHICKEN;
    public static final Crop RAW_RABBIT;
    public static final Crop RAW_MUTTON;
    public static final Crop BROWN_MUSHROOM;
    public static final Crop RED_MUSHROOM;
    public static final Crop CRIMSON_FUNGUS;
    public static final Crop WARPED_FUNGUS;
    public static final Crop ORANGE;
    public static final Crop MAGENTA;
    public static final Crop LIGHT_BLUE;
    public static final Crop YELLOW;
    public static final Crop LIME;
    public static final Crop PINK;
    public static final Crop GRAY;
    public static final Crop LIGHT_GRAY;
    public static final Crop CYAN;
    public static final Crop PURPLE;
    public static final Crop BROWN;
    public static final Crop GREEN;
    public static final Crop RED;
    public static final Crop BLACK;
    public static final Crop TOTEM;
    public static final Crop LEAD;
    public static final Crop NAME_TAG;
    public static final Crop XP;
    public static final Crop SEA;
    public static final Crop SCUTE;
    public static final Crop NAUTILUS;
    public static final Crop PHANTOM;
    public static final Crop WITHER;
    public static final Crop DRAGON;
    public static final Crop BLUE;
    public static final Crop INK;
    public static final Crop WHITE;
    public static final Crop HONEYCOMB;
    public static final Crop NETHERITE;
    public static final Crop GLOW_INK;
    public static final Crop COPPER;
    public static final Crop AMETHYST;
    public static final Crop ECHO_SHARD;
    public static final Crop TIN;
    public static final Crop ZINC;
    public static final Crop NICKEL;
    public static final Crop BRONZE;
    public static final Crop ADVANCED_ALLOY;
    public static final Crop REFINED_IRON;
    public static final Crop STEEL;
    public static final Crop LEAD_METAL;
    public static final Crop SILVER_METAL;
    public static final Crop ELECTRUM;
    public static final Crop IRIDIUM;
    public static final Crop PLATINUM;
    public static final Crop TUNGSTEN;
    public static final Crop HOT_TUNGSTENSTEEL;
    public static final Crop ALUMINIUM;
    public static final Crop TITANIUM;
    public static final Crop CHROMIUM;
    public static final Crop SAPPHIRE;
    public static final Crop RED_GARNET;
    public static final Crop YELLOW_GARNET;
    public static final Crop RUBY;
    public static final Crop INVAR;
    public static final Crop TUNGSTENSTEEL;
    public static final Crop PERIDOT;
    public static final Crop ADAMANTITE;
    public static final Crop AQUARIUM;
    public static final Crop BANGLUM;
    public static final Crop CARMOT;
    public static final Crop CELESTIUM;
    public static final Crop DURASTEEL;
    public static final Crop HALLOWED;
    public static final Crop KYBER;
    public static final Crop MANGANESE;
    public static final Crop METALLURGIUM;
    public static final Crop MIDAS_GOLD;
    public static final Crop MYTHRIL;
    public static final Crop ORICHALCUM;
    public static final Crop OSMIUM;
    public static final Crop PALLADIUM;
    public static final Crop PROMETHEUM;
    public static final Crop QUADRILLUM;
    public static final Crop RUNITE;
    public static final Crop STAR_PLATINUM;
    public static final Crop STORMYX;
    public static final Crop CERTUS;
    public static final Crop FLUIX;
    public static final Crop SILICON;

    public static Crop compatCrops(String name, CropType cropType, int tier, String item, int color, String... modId) {
        AtomicBoolean shouldLoad = new AtomicBoolean(false);
        Arrays.stream(modId).forEach((id) -> {
            if (FabricLoader.getInstance().isModLoaded(id)) {
                shouldLoad.set(true);
            }
        });
        if (shouldLoad.get()) {
            Crop crop = new Crop(name, cropType, tier, item, color);
            compatCropList.add(crop);
            return crop;
        } else {
            return null;
        }
    }

    public static void registerCrops() {
        cropList.forEach(CropInit::register);
    }

    public static void register(Crop crop) {
        BlockInit.registerCrop(crop);
        ItemInit.registerCrop(crop);
        LootTableInit.registerCrop(crop);
        recipes.add(RecipesInit.genRawSeedRecipe(crop));
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.putIfAbsent(crop.seed, 0.3F);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.putIfAbsent(crop.fruit, 0.65F);
    }

    static {
        ELEMENTAL = new Crop("elemental", CropType.CUSTOM, 3, ItemInit.ELEMATILIUS, 7414665);
        COAL = new Crop("coal", CropType.BASIC, 1, Items.COAL, 3355443);
        IRON = new Crop("iron", CropType.BASIC, 2, Items.IRON_INGOT, 14211288);
        GOLD = new Crop("gold", CropType.BASIC, 2, Items.GOLD_INGOT, 16777099);
        LAPIS = new Crop("lapis", CropType.BASIC, 3, Items.LAPIS_LAZULI, 7641066);
        REDSTONE = new Crop("redstone", CropType.BASIC, 3, Items.REDSTONE, 16721446);
        DIAMOND = new Crop("diamond", CropType.BASIC, 4, Items.DIAMOND, 9237730);
        EMERALD = new Crop("emerald", CropType.BASIC, 4, Items.EMERALD, 1564002);
        CLAY = new Crop("clay", CropType.BASIC, 1, Items.CLAY, 10134451);
        GLOWSTONE = new Crop("glowstone", CropType.BASIC, 3, Items.GLOWSTONE, 16776960);
        QUARTZ = new Crop("quartz", CropType.BASIC, 3, Items.QUARTZ, 14670031);
        SHARD = new Crop("shard", CropType.BASIC, 2, Items.PRISMARINE_SHARD, 7583146);
        CRYSTAL = new Crop("crystal", CropType.BASIC, 2, Items.PRISMARINE_CRYSTALS, 13363168);
        ENDER = new Crop("ender", CropType.MONSTER, 3, Items.ENDER_PEARL, 9237730);
        BONE = new Crop("bone", CropType.MONSTER, 2, Items.BONE, 15526601);
        EYE = new Crop("eye", CropType.MONSTER, 2, Items.SPIDER_EYE, 15526601);
        POWDER = new Crop("powder", CropType.MONSTER, 2, Items.GUNPOWDER, 7500402);
        PAPER = new Crop("paper", CropType.BASIC, 1, Items.PAPER, 15395562);
        SUGAR = new Crop("sugar", CropType.BASIC, 1, Items.SUGAR, 16121855);
        CHARCOAL = new Crop("charcoal", CropType.BASIC, 1, Items.CHARCOAL, 5458234);
        FLINT = new Crop("flint", CropType.BASIC, 1, Items.FLINT, 5658198);
        SNOWBALL = new Crop("snowball", CropType.BASIC, 1, Items.SNOWBALL, 16515071);
        FIREWORK = new Crop("firework", CropType.BASIC, 1, Items.FIREWORK_STAR, 5723991);
        NETHER = new Crop("nether", CropType.BASIC, 3, Items.NETHER_BRICK, 4597292);
        BOTTLE = new Crop("bottle", CropType.BASIC, 1, Items.GLASS_BOTTLE, 15000804);
        FOOT = new Crop("foot", CropType.ANIMAL, 1, Items.RABBIT_FOOT, 13609336);
        HIDE = new Crop("hide", CropType.ANIMAL, 1, Items.RABBIT_HIDE, 13082215);
        LEATHER = new Crop("leather", CropType.ANIMAL, 1, Items.LEATHER, 12999733);
        FEATHER = new Crop("feather", CropType.ANIMAL, 1, Items.FEATHER, 16777215);
        BLAZE = new Crop("blaze", CropType.MONSTER, 3, Items.BLAZE_ROD, 16763648);
        GHAST = new Crop("ghast", CropType.MONSTER, 4, Items.GHAST_TEAR, 14941672);
        MAGMA = new Crop("magma", CropType.MONSTER, 3, Items.MAGMA_CREAM, 13541413);
        SHELL = new Crop("shell", CropType.MONSTER, 4, Items.SHULKER_SHELL, 10384542);
        STAR = new Crop("star", CropType.MONSTER, 6, Items.NETHER_STAR, 15791091);
        STRING = new Crop("string", CropType.MONSTER, 2, Items.STRING, 16250871);
        SLIME = new Crop("slime", CropType.MONSTER, 2, Items.SLIME_BALL, 8702067);
        ZOMBIE = new Crop("zombie", CropType.MONSTER, 2, Items.ROTTEN_FLESH, 12410167);
        VINE = new Crop("vine", CropType.NATURE, 1, Items.VINE, 1787145);
        WEEPING_VINES = new Crop("weeping_vines", CropType.NATURE, 1, Items.WEEPING_VINES, 7995392);
        TWISTING_VINES = new Crop("twisting_vines", CropType.NATURE, 1, Items.TWISTING_VINES, 1356419);
        LILY_PAD = new Crop("lilypad", CropType.NATURE, 1, Items.LILY_PAD, 810772);
        BUSH = new Crop("bush", CropType.NATURE, 1, Items.DEAD_BUSH, 9724968);
        GRASS = new Crop("grass", CropType.NATURE, 1, Items.SHORT_GRASS, 136335627);
        LARGE_FERN = new Crop("largefern", CropType.NATURE, 1, Items.LARGE_FERN, 4878912);
        TALL_GRASS = new Crop("tallgrass", CropType.NATURE, 1, Items.TALL_GRASS, 3098408);
        FERN = new Crop("fern", CropType.NATURE, 1, Items.FERN, 1787145);
        OAK = new Crop("oak", CropType.NATURE, 1, Items.OAK_PLANKS, 10322508);
        SPRUCE = new Crop("spruce", CropType.NATURE, 1, Items.SPRUCE_PLANKS, 7952691);
        BIRCH = new Crop("birch", CropType.NATURE, 1, Items.BIRCH_PLANKS, 13022585);
        JUNGLE = new Crop("jungle", CropType.NATURE, 1, Items.JUNGLE_PLANKS, 12422250);
        ACACIA = new Crop("acacia", CropType.NATURE, 1, Items.ACACIA_PLANKS, 12083766);
        DARK_OAK = new Crop("dark_oak", CropType.NATURE, 1, Items.DARK_OAK_PLANKS, 5124376);
        MANGROVE = new Crop("mangrove", CropType.NATURE, 1, Items.MANGROVE_PLANKS, 8208691);
        CHERRY = new Crop("cherry", CropType.NATURE, 1, Items.CHERRY_PLANKS, 14922155);
        BAMBOO = new Crop("bamboo", CropType.NATURE, 1, Items.BAMBOO_PLANKS, 14731881);
        CRIMSON = new Crop("crimson", CropType.NATURE, 1, Items.CRIMSON_PLANKS, 5975873);
        WARPED = new Crop("warped", CropType.NATURE, 1, Items.WARPED_PLANKS, 3703168);
        APPLE = new Crop("apple", CropType.FOOD, 1, Items.APPLE, 16718891);
        GOLDEN_APPLE = new Crop("golden_apple", CropType.FOOD, 3, Items.GOLDEN_APPLE, 16777136);
        BREAD = new Crop("bread", CropType.FOOD, 1, Items.BREAD, 10384165);
        EGG = new Crop("egg", CropType.FOOD, 1, Items.EGG, 14667419);
        TURTLE_EGG = new Crop("turtle_egg", CropType.FOOD, 1, Items.TURTLE_EGG, 5820079);
        SNIFFER_EGG = new Crop("sniffer_egg", CropType.FOOD, 1, Items.SNIFFER_EGG, 11616575);
        TROPICAL_FISH = new Crop("clownfish", CropType.FOOD, 1, Items.TROPICAL_FISH, 15898981);
        PUFFER_FISH = new Crop("pufferfish", CropType.FOOD, 1, Items.PUFFERFISH_SPAWN_EGG, 12956160);
        COOKIE = new Crop("cookie", CropType.FOOD, 1, Items.COOKIE, 14254910);
        CHORUS = new Crop("chorus", CropType.FOOD, 3, Items.CHORUS_FRUIT, 11175338);
        BEEF = new Crop("raw_beef", CropType.FOOD, 1, Items.BEEF, 14829888);
        PORKSHOP = new Crop("raw_porc", CropType.FOOD, 1, Items.PORKCHOP, 16747660);
        COD = new Crop("fish", CropType.FOOD, 1, Items.COD, 13017713);
        SALMON = new Crop("salmon", CropType.FOOD, 1, Items.SALMON, 10373961);
        RAW_CHICKEN = new Crop("raw_chicken", CropType.FOOD, 1, Items.CHICKEN, 15711404);
        RAW_RABBIT = new Crop("raw_rabbit", CropType.FOOD, 1, Items.RABBIT, 15578790);
        RAW_MUTTON = new Crop("raw_mutton", CropType.FOOD, 1, Items.MUTTON, 15031378);
        BROWN_MUSHROOM = new Crop("brown_mushroom", CropType.FOOD, 1, Items.BROWN_MUSHROOM, 13277047);
        RED_MUSHROOM = new Crop("red_mushroom", CropType.FOOD, 1, Items.RED_MUSHROOM, 14619154);
        CRIMSON_FUNGUS = new Crop("crimson_fungus", CropType.FOOD, 1, Items.CRIMSON_FUNGUS, 10626088);
        WARPED_FUNGUS = new Crop("warped_fungus", CropType.FOOD, 1, Items.WARPED_FUNGUS, 1356419);
        ORANGE = new Crop("orange", CropType.BASIC, 1, Items.ORANGE_DYE, 16738816);
        MAGENTA = new Crop("magenta", CropType.BASIC, 1, Items.MAGENTA_DYE, 16711900);
        LIGHT_BLUE = new Crop("light_blue", CropType.BASIC, 1, Items.LIGHT_BLUE_DYE, 38143);
        YELLOW = new Crop("yellow", CropType.BASIC, 1, Items.YELLOW_DYE, 16766976);
        LIME = new Crop("lime", CropType.BASIC, 1, Items.LIME_DYE, 11992832);
        PINK = new Crop("pink", CropType.BASIC, 1, Items.PINK_DYE, 16744374);
        GRAY = new Crop("gray", CropType.BASIC, 1, Items.GRAY_DYE, 4210752);
        LIGHT_GRAY = new Crop("silver", CropType.BASIC, 1, Items.LIGHT_GRAY_DYE, 8421504);
        CYAN = new Crop("cyan", CropType.BASIC, 1, Items.CYAN_DYE, 65535);
        PURPLE = new Crop("purple", CropType.BASIC, 1, Items.PURPLE_DYE, 11665663);
        BROWN = new Crop("brown", CropType.BASIC, 1, Items.BROWN_DYE, 8336128);
        GREEN = new Crop("green", CropType.BASIC, 1, Items.GREEN_DYE, 32526);
        RED = new Crop("red", CropType.BASIC, 1, Items.RED_DYE, 16711680);
        BLACK = new Crop("black", CropType.BASIC, 1, Items.BLACK_DYE, 2960685);
        TOTEM = new Crop("totem", CropType.BASIC, 6, Items.TOTEM_OF_UNDYING, 16314021);
        LEAD = new Crop("lead", CropType.BASIC, 1, Items.LEAD, 11308665);
        NAME_TAG = new Crop("name_tag", CropType.BASIC, 1, Items.NAME_TAG, 8024418);
        XP = new Crop("xp", CropType.BASIC, 4, Items.EXPERIENCE_BOTTLE, 12255049);
        SEA = new Crop("sea", CropType.BASIC, 4, Items.HEART_OF_THE_SEA, 2070193);
        SCUTE = new Crop("scute", CropType.ANIMAL, 2, Items.TURTLE_SCUTE, 4702026);
        NAUTILUS = new Crop("nautilus", CropType.BASIC, 3, Items.NAUTILUS_SHELL, 13946051);
        PHANTOM = new Crop("phantom", CropType.MONSTER, 2, Items.PHANTOM_MEMBRANE, 14473664);
        WITHER = new Crop("wither", CropType.MONSTER, 5, Items.WITHER_ROSE, 2760473);
        DRAGON = new Crop("dragon", CropType.MONSTER, 7, Items.DRAGON_EGG, 2949427);
        BLUE = new Crop("blue", CropType.BASIC, 1, Items.BLUE_DYE, 9983);
        INK = new Crop("ink", CropType.ANIMAL, 1, Items.INK_SAC, 3486801);
        WHITE = new Crop("white", CropType.BASIC, 1, Items.WHITE_DYE, 16777215);
        HONEYCOMB = new Crop("honeycomb", CropType.ANIMAL, 1, Items.HONEYCOMB, 16432937);
        NETHERITE = new Crop("netherite", CropType.BASIC, 5, Items.NETHERITE_INGOT, 6637376);
        GLOW_INK = new Crop("glowink", CropType.BASIC, 2, Items.GLOW_INK_SAC, 4972218);
        COPPER = new Crop("copper", CropType.BASIC, 2, Items.COPPER_INGOT, 16499638);
        AMETHYST = new Crop("amethyst", CropType.BASIC, 3, Items.AMETHYST_SHARD, 14273522);
        ECHO_SHARD = new Crop("echo_shard", CropType.BASIC, 4, Items.ECHO_SHARD, 213071);
        TIN = compatCrops("tin", CropType.BASIC, 3, "c:tin_ingots", 14935008, "techreborn", "indrev", "modern_industrialization", "mythicmetals");
        ZINC = compatCrops("zinc", CropType.BASIC, 3, "c:zinc_ingots", 15593196, "techreborn");
        NICKEL = compatCrops("nickel", CropType.BASIC, 3, "c:nickel_ingots", 11447436, "techreborn", "modern_industrialization");
        BRONZE = compatCrops("bronze", CropType.BASIC, 3, "c:bronze_ingots", 12879187, "techreborn", "indrev", "modern_industrialization", "mythicmetals");
        ADVANCED_ALLOY = compatCrops("advanced_alloy", CropType.BASIC, 3, "c:advanced_alloy_ingots", 14393730, "techreborn");
        REFINED_IRON = compatCrops("refined_iron", CropType.BASIC, 3, "c:refined_iron_ingots", 14015454, "techreborn");
        STEEL = compatCrops("steel", CropType.BASIC, 3, "c:steel_ingots", 10526880, "techreborn", "indrev", "modern_industrialization", "mythicmetals", "ad_astra");
        LEAD_METAL = compatCrops("lead_metal", CropType.BASIC, 3, "c:lead_ingots", 7302007, "techreborn", "indrev", "modern_industrialization");
        SILVER_METAL = compatCrops("silver_metal", CropType.BASIC, 3, "c:silver_ingots", 13951458, "techreborn", "indrev", "modern_industrialization", "mythicmetals");
        ELECTRUM = compatCrops("electrum", CropType.BASIC, 3, "c:electrum_ingots", 13415278, "techreborn", "indrev", "modern_industrialization");
        IRIDIUM = compatCrops("iridium", CropType.BASIC, 3, "c:idridium_ingots", 9412250, "techreborn", "modern_industrialization");
        PLATINUM = compatCrops("platinum", CropType.BASIC, 3, "c:platinum_ingots", 11189191, "techreborn", "mythicmetals");
        TUNGSTEN = compatCrops("tungsten", CropType.BASIC, 3, "c:tungsten_ingots", 7961984, "techreborn", "indrev", "modern_industrialization");
        HOT_TUNGSTENSTEEL = compatCrops("hot_tungstensteel", CropType.BASIC, 3, "c:hot_tungstensteel_ingots", 15454094, "techreborn");
        ALUMINIUM = compatCrops("aluminum", CropType.BASIC, 3, "c:aluminum_ingots", 14277852, "techreborn", "modern_industrialization");
        TITANIUM = compatCrops("titanium", CropType.BASIC, 3, "c:titanium_ingots", 14540259, "techreborn", "modern_industrialization");
        CHROMIUM = compatCrops("chromium", CropType.BASIC, 3, "c:chromium_ingots", 14536658, "techreborn", "modern_industrialization");
        SAPPHIRE = compatCrops("sapphire", CropType.BASIC, 3, "c:sapphires", 7183340, "techreborn");
        RED_GARNET = compatCrops("red_garnet", CropType.BASIC, 3, "c:red_garnet_gems", 15101031, "techreborn");
        YELLOW_GARNET = compatCrops("yellow_garnet", CropType.BASIC, 3, "c:yellow_garnet_gems", 15387487, "techreborn");
        RUBY = compatCrops("ruby", CropType.BASIC, 3, "c:rubies", 12869224, "techreborn");
        INVAR = compatCrops("invar", CropType.BASIC, 3, "c:invar_ingots", 8819340, "techreborn", "modern_industrialization");
        TUNGSTENSTEEL = compatCrops("tungstensteel", CropType.BASIC, 3, "c:tungstensteel_ingots", 5135720, "techreborn");
        PERIDOT = compatCrops("peridot", CropType.BASIC, 3, "c:peridot_gems", 11194991, "techreborn");
        ADAMANTITE = compatCrops("adamantite", CropType.BASIC, 3, "c:adamantite_ingots", 11341337, "mythicmetals");
        AQUARIUM = compatCrops("aquarium", CropType.BASIC, 3, "c:aquarium_ingots", 4428508, "mythicmetals");
        BANGLUM = compatCrops("banglum", CropType.BASIC, 3, "c:banglum_ingots", 7556136, "mythicmetals");
        CARMOT = compatCrops("carmot", CropType.BASIC, 3, "c:carmot_ingots", 12658751, "mythicmetals");
        CELESTIUM = compatCrops("celestium", CropType.BASIC, 3, "c:celestium_ingots", 16241590, "mythicmetals");
        DURASTEEL = compatCrops("durasteel", CropType.BASIC, 3, "c:durasteel_ingots", 4934475, "mythicmetals");
        HALLOWED = compatCrops("hallowed", CropType.BASIC, 3, "c:hallowed_ingots", 16578713, "mythicmetals");
        KYBER = compatCrops("kyber", CropType.BASIC, 3, "c:kyber_ingots", 11695575, "mythicmetals");
        MANGANESE = compatCrops("manganese", CropType.BASIC, 3, "c:manganese_ingots", 15449814, "mythicmetals");
        METALLURGIUM = compatCrops("metallurgium", CropType.BASIC, 3, "c:metallurgium_ingots", 5511092, "mythicmetals");
        MIDAS_GOLD = compatCrops("midas_gold", CropType.BASIC, 3, "c:midas_gold_ingots", 16572032, "mythicmetals");
        MYTHRIL = compatCrops("mythril", CropType.BASIC, 3, "c:mythril_ingots", 6547448, "mythicmetals");
        ORICHALCUM = compatCrops("orichalcum", CropType.BASIC, 3, "c:orichalcum_ingots", 10416549, "mythicmetals");
        OSMIUM = compatCrops("osmium", CropType.BASIC, 3, "c:osmium_ingots", 10400200, "mythicmetals");
        PALLADIUM = compatCrops("palladium", CropType.BASIC, 3, "c:palladium_ingots", 15571238, "mythicmetals");
        PROMETHEUM = compatCrops("prometheum", CropType.BASIC, 3, "c:prometheum_ingots", 3762517, "mythicmetals");
        QUADRILLUM = compatCrops("quadrillum", CropType.BASIC, 3, "c:quadrillum_ingots", 6450798, "mythicmetals");
        RUNITE = compatCrops("runite", CropType.BASIC, 3, "c:runite_ingots", 44750, "mythicmetals");
        STAR_PLATINUM = compatCrops("star_platinum", CropType.BASIC, 3, "c:star_platinum_ingots", 10590675, "mythicmetals");
        STORMYX = compatCrops("stormyx", CropType.BASIC, 3, "c:stormyx_ingots", 14903004, "mythicmetals");
        CERTUS = compatCrops("certus", CropType.BASIC, 3, "c:certus_quartz", 12114172, "ae2");
        FLUIX = compatCrops("fluix", CropType.BASIC, 3, "c:fluix", 9395403, "ae2");
        SILICON = compatCrops("silicon", CropType.BASIC, 3, "c:silicon", 6706285, "ae2");
    }
}
