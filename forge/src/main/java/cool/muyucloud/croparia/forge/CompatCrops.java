package cool.muyucloud.croparia.forge;

import cool.muyucloud.croparia.data.crop.CompatCrop;
import cool.muyucloud.croparia.data.crop.CropType;
import cool.muyucloud.croparia.registry.Crops;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class CompatCrops {
    @Nullable
    public static final CompatCrop TIN = Crops.compatCrop("tin", "#forge:ingots/tin", 0xE3E3E0, 3, CropType.CROP, Map.of(
        "mekanism", "item.mekanism.ingot_tin"
    ));
    @Nullable
    public static final CompatCrop ZINC = Crops.compatCrop("zinc", "#forge:ingots/zinc", 0xEDEEEC, 3, CropType.CROP, Map.of(
        "create", "item.create.zinc_ingot"
    ));
    @Nullable
    public static final CompatCrop BRONZE = Crops.compatCrop("bronze", "#forge:ingots/bronze", 0xC48553, 3, CropType.CROP, Map.of(
        "mekanism", "item.mekanism.ingot_bronze"
    ));
    @Nullable
    public static final CompatCrop STEEL = Crops.compatCrop("steel", "#forge:ingots/steel", 0xA0A0A0, 3, CropType.CROP, Map.of(
        "ad_astra", "item.ad_astra.ingot_steel",
        "mekanism", "item.mekanism.ingot_steel"
    ));
    @Nullable
    public static final CompatCrop LEAD = Crops.compatCrop("lead", "#forge:ingots/lead", 0x6F6B77, 3, CropType.CROP, Map.of(
        "mekanism", "item.mekanism.ingot_lead"
    ));
    @Nullable
    public static final CompatCrop OSMIUM = Crops.compatCrop("osmium", "#forge:ingots/osmium", 0x9EB1C8, 3, CropType.CROP, Map.of(
        "mekanism", "item.mekanism.ingot_osmium"
    ));
    @Nullable
    public static final CompatCrop URANIUM = Crops.compatCrop("uranium", "#forge:ingots/uranium", 0x32CE00, 3, CropType.CROP, Map.of(
        "mekanism", "item.mekanism.ingot_uranium"
    ));
    @Nullable
    public static final CompatCrop ROSITE = Crops.compatCrop("rosite", "#forge:ingots/rosite", 0xF16B59, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.rosite_ingot"
    ));
    @Nullable
    public static final CompatCrop EMBERSTONE = Crops.compatCrop("emberstone", "#forge:ingots/emberstone", 0xF17F22, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.emberstone_ingot"
    ));
    @Nullable
    public static final CompatCrop SKELETAL = Crops.compatCrop("skeletal", "#forge:ingots/skeletal", 0xB3A997, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.skeletal_ingot"
    ));
    @Nullable
    public static final CompatCrop BARONYTE = Crops.compatCrop("baronyte", "#forge:ingots/baronyte", 0xE56544, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.baronyte_ingot"
    ));
    @Nullable
    public static final CompatCrop LIMONITE = Crops.compatCrop("limonite", "#forge:ingots/limonite", 0xE79353, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.limonite_ingot"
    ));
    @Nullable
    public static final CompatCrop VARSIUM = Crops.compatCrop("varsium", "#forge:ingots/varsium", 0xDABF59, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.varsium_ingot"
    ));
    @Nullable
    public static final CompatCrop LUNAR = Crops.compatCrop("lunar", "#forge:ingots/lunar", 0xA32F9D, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.lunar_ingot"
    ));
    @Nullable
    public static final CompatCrop BLAZIUM = Crops.compatCrop("blazium", "#forge:ingots/blazium", 0xFCEB6F, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.blazium_ingot"
    ));
    @Nullable
    public static final CompatCrop SHYRESTONE = Crops.compatCrop("shyrestone", "#forge:ingots/shyrestone", 0xA1EAFC, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.shyrestone_ingot"
    ));
    @Nullable
    public static final CompatCrop MYSTITE = Crops.compatCrop("mystite", "#forge:ingots/mystite", 0xB3FCC4, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.mystite_ingot"
    ));
    @Nullable
    public static final CompatCrop ELECANIUM = Crops.compatCrop("elecanium", "#forge:ingots/elecanium", 0x34ACDE, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.elecanium_ingot"
    ));
    @Nullable
    public static final CompatCrop GHASTLY = Crops.compatCrop("ghastly", "#forge:ingots/ghastly", 0xF8FC9C, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.ghastly_ingot"
    ));
    @Nullable
    public static final CompatCrop GHOULISH = Crops.compatCrop("ghoulish", "#forge:ingots/ghoulish", 0x7EA8FC, 3, CropType.CROP, Map.of(
        "aoa3", "item.aoa3.ghoulish_ingot"
    ));
    @Nullable
    public static final CompatCrop CERTUS = Crops.compatCrop("certus", "#forge:gems/certus_quartz", 0xB8D8FC, 3, CropType.CROP, Map.of(
        "ae2", "item.ae2.certus_quartz_crystal"
    ));
    @Nullable
    public static final CompatCrop FLUIX = Crops.compatCrop("fluix", "#forge:gems/fluix", 0x8F5CCB, 3, CropType.CROP, Map.of(
        "ae2", "item.ae2.fluix_crystal"
    ));
    @Nullable
    public static final CompatCrop SILICON = Crops.compatCrop("silicon", "#forge:silicon", 0x66546D, 3, CropType.CROP, Map.of(
        "ae2", "item.ae2.silicon"
    ));

    public static void init() {
    }
}
