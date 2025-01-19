package cool.muyucloud.croparia.fabric;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.data.crop.Crop;
import cool.muyucloud.croparia.data.crop.CropType;
import cool.muyucloud.croparia.registry.Crops;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@SuppressWarnings("unused")
public class CompatCrops {
    @Nullable
    public static final Crop TIN = Crops.compat("tin", "#c:tin_ingots", 0xE3E3E0, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.tin_ingot",
        "indrev", "item.indrev.tin_ingot",
        "modern_industrialization", "item.modern_industrialization.tin_ingot",
        "mythicmetals", "item.mythicmetals.tin_ingot"
    ));
    @Nullable
    public static final Crop ZINC = Crops.compat("zinc", "#c:zinc_ingots", 0xEDEEEC, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.zinc_ingot"
    ));
    @Nullable
    public static final Crop NICKEL = Crops.compat("nickel", "#c:nickel_ingots", 0xAEAC8C, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.nickel_ingot",
        "modern_industrialization", "item.modern_industrialization.nickel_ingot"
    ));
    @Nullable
    public static final Crop BRONZE = Crops.compat("bronze", "#c:bronze_ingots", 0xC48553, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.bronze_ingot",
        "indrev", "item.indrev.bronze_ingot",
        "modern_industrialization", "item.modern_industrialization.bronze_ingot",
        "mythicmetals", "item.mythicmetals.bronze_ingot"
    ));
    @Nullable
    public static final Crop ADVANCED_ALLOY = Crops.compat("advanced_alloy", "#c:advanced_alloy_ingots", 0xDBA182, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.advanced_alloy_ingot"
    ));
    @Nullable
    public static final Crop REFINED_IRON = Crops.compat("refined_iron", "#c:refined_iron_ingots", 0xD5DBDE, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.refined_iron_ingot"
    ));
    @Nullable
    public static final Crop STEEL = Crops.compat("steel", "#c:steel_ingots", 0xA0A0A0, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.steel_ingot",
        "indrev", "item.indrev.steel_ingot",
        "modern_industrialization", "item.modern_industrialization.steel_ingot",
        "mythicmetals", "item.mythicmetals.steel_ingot",
        "ad_astra", "item.ad_astra.ingot_steel"
    ));
    @Nullable
    public static final Crop LEAD = Crops.compat("lead", "#c:lead_ingots", 0x6F6B77, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.lead_ingot",
        "indrev", "item.indrev.lead_ingot",
        "modern_industrialization", "item.modern_industrialization.lead_ingot"
    ));
    @Nullable
    public static final Crop SILVER = Crops.compat("silver", "#c:silver_ingots", 0xD4E1E2, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.silver_ingot",
        "indrev", "item.indrev.silver_ingot",
        "modern_industrialization", "item.modern_industrialization.silver_ingot",
        "mythicmetals", "item.mythicmetals.silver_ingot"
    ));
    @Nullable
    public static final Crop ELECTRUM = Crops.compat("electrum", "#c:electrum_ingots", 0xCCB36E, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.electrum_ingot",
        "indrev", "item.indrev.electrum_ingot",
        "modern_industrialization", "item.modern_industrialization.electrum_ingot"
    ));
    @Nullable
    public static final Crop IRIDIUM = Crops.compat("iridium", "#c:iridium_ingots", 0x8F9E9A, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.iridium_ingot",
        "modern_industrialization", "item.modern_industrialization.iridium_ingot"
    ));
    @Nullable
    public static final Crop PLATINUM = Crops.compat("platinum", "#c:platinum_ingots", 0xAABBC7, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.platinum_ingot",
        "mythicmetals", "item.mythicmetals.platinum_ingot"
    ));
    @Nullable
    public static final Crop TUNGSTEN = Crops.compat("tungsten", "#c:tungsten_ingots", 0x797D80, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.tungsten_ingot",
        "indrev", "item.indrev.tungsten_ingot",
        "modern_industrialization", "item.modern_industrialization.tungsten_ingot"
    ));
    @Nullable
    public static final Crop HOT_TUNGSTENSTEEL = Crops.compat("hot_tungstensteel", "#c:hot_tungstensteel_ingots", 0xEBCF8E, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.hot_tungstensteel_ingot"
    ));
    @Nullable
    public static final Crop ALUMINIUM = Crops.compat("aluminum", "#c:aluminum_ingots", 0xD9DCDC, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.aluminum_ingot",
        "modern_industrialization", "item.modern_industrialization.aluminum_ingot"
    ));
    @Nullable
    public static final Crop TITANIUM = Crops.compat("titanium", "#c:titanium_ingots", 0xDDDDE3, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.titanium_ingot",
        "modern_industrialization", "item.modern_industrialization.titanium_ingot"
    ));
    @Nullable
    public static final Crop CHROMIUM = Crops.compat("chromium", "#c:chromium_ingots", 0xDDCFD2, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.chromium_ingot",
        "modern_industrialization", "item.modern_industrialization.chromium_ingot"
    ));
    @Nullable
    public static final Crop SAPPHIRE = Crops.compat("sapphire", "#c:sapphires", 0x6D9BEC, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.sapphire_gem"
    ));
    @Nullable
    public static final Crop RED_GARNET = Crops.compat("red_garnet", "#c:red_garnet_gems", 0xE66C67, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.red_garnet_gem"
    ));
    @Nullable
    public static final Crop YELLOW_GARNET = Crops.compat("yellow_garnet", "#c:yellow_garnet_gems", 0xEACB5F, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.yellow_garnet_gem"
    ));
    @Nullable
    public static final Crop RUBY = Crops.compat("ruby", "#c:rubies", 0xC45E68, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.ruby_gem"
    ));
    @Nullable
    public static final Crop INVAR = Crops.compat("invar", "#c:invar_ingots", 0x86928C, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.invar_ingot",
        "modern_industrialization", "item.modern_industrialization.invar_ingot"
    ));
    @Nullable
    public static final Crop TUNGSTENSTEEL = Crops.compat("tungstensteel", "#c:tungstensteel_ingots", 0x4E5D68, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.tungstensteel_ingot"
    ));
    @Nullable
    public static final Crop PERIDOT = Crops.compat("peridot", "#c:peridot_gems", 0xAAD26F, 3, CropType.CROP, Map.of(
        "techreborn", "item.techreborn.peridot_gem"
    ));
    @Nullable
    public static final Crop ADAMANTITE = Crops.compat("adamantite", "#c:adamantite_ingots", 0xAD0E19, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.adamantite_ingot"
    ));
    @Nullable
    public static final Crop AQUARIUM = Crops.compat("aquarium", "#c:aquarium_ingots", 0x4392DC, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.aquarium_ingot"
    ));
    @Nullable
    public static final Crop BANGLUM = Crops.compat("banglum", "#c:banglum_ingots", 0x734C28, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.banglum_ingot"
    ));
    @Nullable
    public static final Crop CARMOT = Crops.compat("carmot", "#c:carmot_ingots", 0xC1283F, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.carmot_ingot"
    ));
    @Nullable
    public static final Crop CELESTIUM = Crops.compat("celestium", "#c:celestium_ingots", 0xF7D3B6, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.celestium_ingot"
    ));
    @Nullable
    public static final Crop DURASTEEL = Crops.compat("durasteel", "#c:durasteel_ingots", 0x4B4B4B, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.durasteel_ingot"
    ));
    @Nullable
    public static final Crop HALLOWED = Crops.compat("hallowed", "#c:hallowed_ingots", 0xFCF899, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.hallowed_ingot"
    ));
    @Nullable
    public static final Crop KYBER = Crops.compat("kyber", "#c:kyber_ingots", 0xB275D7, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.kyber_ingot"
    ));
    @Nullable
    public static final Crop MANGANESE = Crops.compat("manganese", "#c:manganese_ingots", 0xEBBED6, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.manganese_ingot"
    ));
    @Nullable
    public static final Crop METALLURGIUM = Crops.compat("metallurgium", "#c:metallurgium_ingots", 0x5417B4, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.metallurgium_ingot"
    ));
    @Nullable
    public static final Crop MIDAS_GOLD = Crops.compat("midas_gold", "#c:midas_gold_ingots", 0xFCDE80, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.midas_gold_ingot"
    ));
    @Nullable
    public static final Crop MYTHRIL = Crops.compat("mythril", "#c:mythril_ingots", 0x63E7F8, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.mythril_ingot"
    ));
    @Nullable
    public static final Crop ORICHALCUM = Crops.compat("orichalcum", "#c:orichalcum_ingots", 0x9EF1A5, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.orichalcum_ingot"
    ));
    @Nullable
    public static final Crop OSMIUM = Crops.compat("osmium", "#c:osmium_ingots", 0x9EB1C8, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.osmium_ingot"
    ));
    @Nullable
    public static final Crop PALLADIUM = Crops.compat("palladium", "#c:palladium_ingots", 0xED9926, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.palladium_ingot"
    ));
    @Nullable
    public static final Crop PROMETHEUM = Crops.compat("prometheum", "#c:prometheum_ingots", 0x396955, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.prometheum_ingot"
    ));
    @Nullable
    public static final Crop QUADRILLUM = Crops.compat("quadrillum", "#c:quadrillum_ingots", 0x626E6E, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.quadrillum_ingot"
    ));
    @Nullable
    public static final Crop RUNITE = Crops.compat("runite", "#c:runite_ingots", 0x00AECE, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.runite_ingot"
    ));
    @Nullable
    public static final Crop STAR_PLATINUM = Crops.compat("star_platinum", "#c:star_platinum", 0xA199D3, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.star_platinum"
    ));
    @Nullable
    public static final Crop STORMYX = Crops.compat("stormyx", "#c:stormyx_ingots", 0xE366DC, 3, CropType.CROP, Map.of(
        "mythicmetals", "item.mythicmetals.stormyx_ingot"
    ));
    @Nullable
    public static final Crop CERTUS = Crops.compat("certus", "#c:certus_quartz", 0xB8D8FC, 3, CropType.CROP, Map.of(
        "ae2", "item.ae2.certus_quartz_crystal"
    ));
    @Nullable
    public static final Crop FLUIX = Crops.compat("fluix", "#c:fluix", 0x8F5CCB, 3, CropType.CROP, Map.of(
        "ae2", "item.ae2.fluix_crystal"
    ));
    @Nullable
    public static final Crop SILICON = Crops.compat("silicon", "#c:silicon", 0x66546D, 3, CropType.CROP, Map.of(
        "ae2", "item.ae2.silicon"
    ));
    @Nullable
    public static final Crop URANIUM = Crops.compat("uranium", "#c:uranium_ingots", 0x32CE00, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.uranium_ingot"
    ));


    public static void init() {
        CropariaIf.LOGGER.debug("Initializing fabric CompatCrops");
    }
}