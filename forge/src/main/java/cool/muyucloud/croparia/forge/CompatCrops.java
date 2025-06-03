package cool.muyucloud.croparia.forge;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.CropType;
import cool.muyucloud.croparia.registry.Crops;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@SuppressWarnings("unused")
public class CompatCrops {
    @Nullable
    public static final Crop ALUMINUM = Crops.compat("aluminum", "#forge:ingots/aluminum", 0x9E9E9E, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.aluminum_ingot",
        "gtceu", "material.gtceu.aluminum"
    ));
    @Nullable
    public static final Crop AMERICIUM = Crops.compat("americium", "#forge:ingots/americium", 0x7F8C8D, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.americium"
    ));
    @Nullable
    public static final Crop ANTIMONY = Crops.compat("antimony", "#forge:ingots/antimony", 0x8A8A8A, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.antimony_ingot",
        "gtceu", "material.gtceu.antimony"
    ));
    @Nullable
    public static final Crop BARONYTE = Crops.compat("baronyte", "#forge:ingots/baronyte", 0xE56544, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.baronyte"
    ));
    @Nullable
    public static final Crop BERYLLIUM = Crops.compat("beryllium", "#forge:ingots/beryllium", 0xA4C639, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.beryllium"
    ));
    @Nullable
    public static final Crop BISMUTH = Crops.compat("bismuth", "#forge:ingots/bismuth", 0xB87333, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.bismuth"
    ));
    @Nullable
    public static final Crop BLAZIUM = Crops.compat("blazium", "#forge:ingots/blazium", 0xFCEB6F, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.blazium_ingot"
    ));
    @Nullable
    public static final Crop BLOODSTONE = Crops.compat("bloodstone", "#forge:gems/bloodstone", 0x9B0000, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.bloodstone"
    ));
    @Nullable
    public static final Crop CADMIUM = Crops.compat("cadmium", "#forge:ingots/cadmium", 0x9E9E9E, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.cadmium_ingot"
    ));
    @Nullable
    public static final Crop CERTUS = Crops.compat("certus", "#forge:gems/certus_quartz", 0xB8D8FC, 3, CropType.CROP, Map.of(
        "ae2", "item.ae2.certus_quartz_crystal"
    ));
    @Nullable
    public static final Crop CALORITE = Crops.compat("calorite", "#forge:ingots/calorite", 0x9E9E9E, 3, CropType.CROP, Map.of(
        "ad_astra", "item.ad_astral.calorite_ingot"
    ));
    @Nullable
    public static final Crop CHROMIUM = Crops.compat("chromium", "#forge:ingots/chromium", 0xE0E0E0, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.chromium"
    ));
    @Nullable
    public static final Crop COBALT = Crops.compat("cobalt", "#forge:ingots/cobalt", 0x1E90FF, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.cobalt"
    ));
    @Nullable
    public static final Crop CRYSTALLITE = Crops.compat("crystallite", "#forge:gems/crystallite", 0x9EB1C8, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.crystallite"
    ));
    @Nullable
    public static final Crop DARMSTADTIUM = Crops.compat("darmstadtium", "#forge:ingots/darmstadtium", 0xB67A56, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.darmstadtium"
    ));
    @Nullable
    public static final Crop DRACONIUM = Crops.compat("draconium", "#forge:ingots/draconium", 0x9E9E9E, 3, CropType.CROP, Map.of(
        "draconicevolution", "item.draconicevolution.draconium_ingot"
    ));
    @Nullable
    public static final Crop DESH = Crops.compat("desh", "#forge:ingots/desh", 0x8B0000, 3, CropType.CROP, Map.of(
        "ad_astra", "item.ad_astra.desh_ingot"
    ));
    @Nullable
    public static final Crop ELECANIUM = Crops.compat("elecanium", "#forge:ingots/elecanium", 0x34ACDE, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.elecanium_ingot"
    ));
    @Nullable
    public static final Crop EMBERSTONE = Crops.compat("emberstone", "#forge:ingots/emberstone", 0xF17F22, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.emberstone_ingot"
    ));
    @Nullable
    public static final Crop EUROPIUM = Crops.compat("europium", "#forge:ingots/europium", 0xFFD700, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.europium"
    ));
    @Nullable
    public static final Crop FLUIX = Crops.compat("fluix", "#forge:gems/fluix", 0x8F5CCB, 3, CropType.CROP, Map.of(
        "ae2", "item.ae2.fluix_crystal"
    ));
    @Nullable
    public static final Crop GALLIUM = Crops.compat("gallium", "#forge:ingots/gallium", 0xBCD2E8, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.gallium"
    ));
    @Nullable
    public static final Crop GEMENYTE = Crops.compat("gemenyte", "#forge:gems/gemenyte", 0x8F5CCB, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.gemenyte"
    ));
    @Nullable
    public static final Crop GHASTLY = Crops.compat("ghastly", "#forge:ingots/ghastly", 0xF8FC9C, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.ghastly_ingot"
    ));
    @Nullable
    public static final Crop GHOULISH = Crops.compat("ghoulish", "#forge:ingots/ghoulish", 0x7EA8FC, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.ghoulish_ingot"
    ));
    @Nullable
    public static final Crop INDIUM = Crops.compat("indium", "#forge:ingots/indium", 0x4A7190, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.indium"
    ));
    @Nullable
    public static final Crop JADE = Crops.compat("jade", "#forge:gems/jade", 0x8F5CCB, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.jade"
    ));
    @Nullable
    public static final Crop JEWELYTE = Crops.compat("jewelyte", "#forge:gems/jewelyte", 0x8F5CCB, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.jewelyte"
    ));
    @Nullable
    public static final Crop LEAD = Crops.compat("lead_conventional", "#forge:ingots/lead", 0x6F6B77, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.lead_ingot",
        "mekanism", "item.mekanism.ingot_lead",
        "thermal_foundation", "item.thermal.lead_ingot",
        "gtceu", "material.gtceu.lead"
    ));
    @Nullable
    public static final Crop LIMONITE = Crops.compat("limonite", "#forge:ingots/limonite", 0xE79353, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.limonite_ingot"
    ));
    @Nullable
    public static final Crop LITHIUM = Crops.compat("lithium", "#forge:ingots/lithium", 0xC0C0C0, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.lithium"
    ));
    @Nullable
    public static final Crop LUNAR = Crops.compat("lunar", "#forge:ingots/lunar", 0xA32F9D, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.lunar_ingot"
    ));
    @Nullable
    public static final Crop MANGANESE = Crops.compat("manganese", "#forge:ingots/manganese", 0xFF9EA3, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.manganese"
    ));
    @Nullable
    public static final Crop MOLYBDENUM = Crops.compat("molybdenum", "#forge:ingots/molybdenum", 0x708090, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.molybdenum"
    ));
    @Nullable
    public static final Crop MONAZITE = Crops.compat("monazite", "#forge:dusts/monazite", 0xFCC4B3, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.monazite_dust"
    ));
    @Nullable
    public static final Crop MYSTITE = Crops.compat("mystite", "#forge:ingots/mystite", 0xB3FCC4, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.mystite_ingot"
    ));
    @Nullable
    public static final Crop NAQUADAH = Crops.compat("naquadah", "#forge:ingots/naquadah", 0x556B2F, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.naquadah"
    ));
    @Nullable
    public static final Crop NEODYMIUM = Crops.compat("neodymium", "#forge:ingots/neodymium", 0x9977BB, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.neodymium"
    ));
    @Nullable
    public static final Crop NICKEL = Crops.compat("nickel", "#forge:ingots/nickel", 0x8F9E9A, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.nickel_ingot",
        "thermal_foundation", "item.thermal.nickel_ingot",
        "gtceu", "material.gtceu.nickel"
    ));
    @Nullable
    public static final Crop NIOBIUM = Crops.compat("niobium", "#forge:ingots/niobium", 0x8E44AD, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.niobium"
    ));
    @Nullable
    public static final Crop IRIDIUM = Crops.compat("iridium", "#forge:ingots/iridium", 0x8F9E9A, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.iridium_ingot"
    ));
    @Nullable
    public static final Crop ORNAMYTE = Crops.compat("ornamyte", "#forge:gems/ornamyte", 0x8F5CCB, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.ornamyte"
    ));
    @Nullable
    public static final Crop OSMIUM = Crops.compat("osmium", "#forge:ingots/osmium", 0x9EB1C8, 3, CropType.CROP, Map.of(
        "mekanism", "item.mekanism.ingot_osmium"
    ));
    @Nullable
    public static final Crop OSTRUM = Crops.compat("ostrum", "#forge:ingots/ostrum", 0x7F7F7F, 3, CropType.CROP, Map.of(
        "ad_astra", "item.ad_astra.ostrum_ingot"
    ));
    @Nullable
    public static final Crop PALLADIUM = Crops.compat("palladium", "#forge:ingots/palladium", 0xA569BD, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.palladium"
    ));
    @Nullable
    public static final Crop PLATINUM = Crops.compat("platinum", "#forge:ingots/platinum", 0x9EB1C8, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.platinum_ingot",
        "gtceu", "material.gtceu.platinum"
    ));
    @Nullable
    public static final Crop PLUTONIUM = Crops.compat("plutonium", "#forge:ingots/plutonium", 0x4CFF00, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.plutonium"
    ));
    @Nullable
    public static final Crop RHODIUM = Crops.compat("rhodium", "#forge:ingots/rhodium", 0xB5BFC6, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.rhodium"
    ));
    @Nullable
    public static final Crop ROSITE = Crops.compat("rosite", "#forge:ingots/rosite", 0xF16B59, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.rosite_ingot"
    ));
    @Nullable
    public static final Crop RUTHENIUM = Crops.compat("ruthenium", "#forge:ingots/ruthenium", 0x838B8B, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.ruthenium"
    ));
    @Nullable
    public static final Crop SALT = Crops.compat("salt", "#forge:dusts/salt", 0x8F9E9A, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.salt_dust"
    ));
    @Nullable
    public static final Crop SAMARIUM = Crops.compat("samarium", "#forge:ingots/samarium", 0xFF4500, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.samarium"
    ));
    @Nullable
    public static final Crop SHYRESTONE = Crops.compat("shyrestone", "#forge:ingots/shyrestone", 0xA1EAFC, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.shyrestone_ingot"
    ));
    @Nullable
    public static final Crop SHYREGEM = Crops.compat("shyregem", "#forge:gems/shyregem", 0xA1EAFC, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.shyregem"
    ));
    @Nullable
    public static final Crop SILVER = Crops.compat("silver", "#forge:ingots/silver", 0x9E9E9E, 3, CropType.CROP, Map.of(
        "thermal_foundation", "item.thermal.silver_ingot",
        "gtceu", "material.gtceu.silver"
    ));
    @Nullable
    public static final Crop SILICON = Crops.compat("silicon", "#forge:silicon", 0x66546D, 3, CropType.CROP, Map.of(
        "ae2", "item.ae2.silicon"
    ));
    @Nullable
    public static final Crop SKELETAL = Crops.compat("skeletal", "#forge:ingots/skeletal", 0xB3A997, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.skeletal_ingot"
    ));
    @Nullable
    public static final Crop TANTALUM = Crops.compat("tantalum", "#forge:ingots/tantalum", 0xA9A9A9, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.tantalum"
    ));
    @Nullable
    public static final Crop THORIUM = Crops.compat("thorium", "#forge:ingots/thorium", 0x00CED1, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.thorium"
    ));
    @Nullable
    public static final Crop TIN = Crops.compat("tin_conventional", "#forge:ingots/tin", 0xE3E3E0, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.tin_ingot",
        "mekanism", "item.mekanism.ingot_tin",
        "thermal_foundation", "item.thermal.tin_ingot",
        "gtceu", "material.gtceu.tin"
    ));
    @Nullable
    public static final Crop TITANIUM = Crops.compat("titanium", "#forge:ingots/titanium", 0x8F9E9A, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.titanium_ingot",
        "gtceu", "material.gtceu.titanium"
    ));
    @Nullable
    public static final Crop TRINIUM = Crops.compat("trinium", "#forge:ingots/trinium", 0x4682B4, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.trinium"
    ));
    @Nullable
    public static final Crop TUNGSTEN = Crops.compat("tungsten", "#forge:ingots/tungsten", 0x8F9E9A, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.tungsten_ingot",
        "gtceu", "material.gtceu.tungsten"
    ));
    @Nullable
    public static final Crop URANIUM = Crops.compat("uranium_conventional", "#forge:ingots/uranium", 0x32CE00, 3, CropType.CROP, Map.of(
        "modern_industrialization", "item.modern_industrialization.uranium_ingot",
        "mekanism", "item.mekanism.ingot_uranium"
    ));
    @Nullable
    public static final Crop VANADIUM = Crops.compat("vanadium", "#forge:ingots/vanadium", 0x228B22, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.vanadium"
    ));
    @Nullable
    public static final Crop VARSIUM = Crops.compat("varsium", "#forge:ingots/varsium", 0xDABF59, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.varsium_ingot"
    ));
    @Nullable
    public static final Crop YTTRIUM = Crops.compat("yttrium", "#forge:ingots/yttrium", 0xFF6347, 3, CropType.CROP, Map.of(
        "gtceu", "material.gtceu.yttrium"
    ));
    @Nullable
    public static final Crop ZINC = Crops.compat("zinc", "#forge:ingots/zinc", 0xEDEEEC, 3, CropType.CROP, Map.of(
        "create", "item.create.zinc_ingot"
    ));

    public static void init() {
        CropariaIf.LOGGER.debug("Initializing neoforge CompatCrops");
    }
}