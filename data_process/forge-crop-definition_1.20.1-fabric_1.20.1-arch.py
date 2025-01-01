ingot_translations = {
    "mekanism": lambda material: "item.mekanism.ingot_" + material,
    "ad_astra": lambda material: "item.ad_astra.ingot_" + material,
    "create": lambda material: f"item.create.{material}_ingot",
    "aoa3": lambda material: f"item.aoa3.{material}_ingot"
}

ingots = {
    "tin": {
        "color": 14935008,
        "dependencies": [
            "mekanism"
        ]
    },
    "zinc": {
        "color": 15593196,
        "dependencies": [
            "create"
        ]
    },
    "nickel": {
        "color": 11447436,
        "dependencies": []
    },
    "bronze": {
        "color": 12879187,
        "dependencies": [
            "mekanism"
        ]
    },
    "advanced_alloy": {
        "color": 14393730,
        "dependencies": []
    },
    "refined_iron": {
        "color": 14015454,
        "dependencies": []
    },
    "steel": {
        "color": 10526880,
        "dependencies": [
            "ad_astra",
            "mekanism"
        ]
    },
    "lead": {
        "color": 7302007,
        "dependencies": [
            "mekanism"
        ]
    },
    "silver": {
        "color": 13951458,
        "dependencies": []
    },
    "electrum": {
        "color": 13415278,
        "dependencies": []
    },
    "iridium": {
        "color": 9412250,
        "dependencies": []
    },
    "platinum": {
        "color": 11189191,
        "dependencies": []
    },
    "tungsten": {
        "color": 7961984,
        "dependencies": []
    },
    "hot_tungstensteel": {
        "color": 15454094,
        "dependencies": []
    },
    "aluminum": {
        "color": 14277852,
        "dependencies": []
    },
    "titanium": {
        "color": 14540259,
        "dependencies": []
    },
    "chromium": {
        "color": 14536658,
        "dependencies": []
    },
    "sapphire": {
        "color": 7183340,
        "dependencies": []
    },
    "red_garnet": {
        "color": 15101031,
        "dependencies": []
    },
    "yellow_garnet": {
        "color": 15387487,
        "dependencies": []
    },
    "ruby": {
        "color": 12869224,
        "dependencies": []
    },
    "invar": {
        "color": 8819340,
        "dependencies": []
    },
    "tungstensteel": {
        "color": 5135720,
        "dependencies": []
    },
    "peridot": {
        "color": 11194991,
        "dependencies": []
    },
    "adamantite": {
        "color": 11341337,
        "dependencies": []
    },
    "aquarium": {
        "color": 4428508,
        "dependencies": []
    },
    "banglum": {
        "color": 7556136,
        "dependencies": []
    },
    "carmot": {
        "color": 12658751,
        "dependencies": []
    },
    "celestium": {
        "color": 16241590,
        "dependencies": []
    },
    "durasteel": {
        "color": 4934475,
        "dependencies": []
    },
    "hallowed": {
        "color": 16578713,
        "dependencies": []
    },
    "kyber": {
        "color": 11695575,
        "dependencies": []
    },
    "manganese": {
        "color": 15449814,
        "dependencies": []
    },
    "metallurgium": {
        "color": 5511092,
        "dependencies": []
    },
    "midas_gold": {
        "color": 16572032,
        "dependencies": []
    },
    "mythril": {
        "color": 6547448,
        "dependencies": []
    },
    "orichalcum": {
        "color": 10416549,
        "dependencies": []
    },
    "osmium": {
        "color": 10400200,
        "dependencies": [
            "mekanism"
        ]
    },
    "palladium": {
        "color": 15571238,
        "dependencies": []
    },
    "prometheum": {
        "color": 3762517,
        "dependencies": []
    },
    "quadrillum": {
        "color": 6450798,
        "dependencies": []
    },
    "runite": {
        "color": 44750,
        "dependencies": []
    },
    "star_platinum": {
        "color": 10590675,
        "dependencies": []
    },
    "stormyx": {
        "color": 14903004,
        "dependencies": []
    },
    "certus": {
        "color": 12114172,
        "dependencies": []
    },
    "fluix": {
        "color": 9395403,
        "dependencies": []
    },
    "silicon": {
        "color": 6706285,
        "dependencies": []
    },
    "uranium": {
        "color": 3329536,
        "dependencies": [
            "mekanism"
        ]
    },
    "rosite": {
        "color": 15821657,
        "dependencies": [
            "aoa3"
        ]
    },
    "emberstone": {
        "color": 15826722,
        "dependencies": [
            "aoa3"
        ]
    },
    "skeletal": {
        "color": 11774359,
        "dependencies": [
            "aoa3"
        ]
    },
    "baronyte": {
        "color": 15033668,
        "dependencies": [
            "aoa3"
        ]
    },
    "limonite": {
        "color": 15176531,
        "dependencies": [
            "aoa3"
        ]
    },
    "varsium": {
        "color": 14335833,
        "dependencies": [
            "aoa3"
        ]
    },
    "lunar": {
        "color": 10694557,
        "dependencies": [
            "aoa3"
        ]
    },
    "blazium": {
        "color": 16575343,
        "dependencies": [
            "aoa3"
        ]
    },
    "shyrestone": {
        "color": 10611452,
        "dependencies": [
            "aoa3"
        ]
    },
    "mystite": {
        "color": 11795652,
        "dependencies": [
            "aoa3"
        ]
    },
    "elecanium": {
        "color": 3452126,
        "dependencies": [
            "aoa3"
        ]
    },
    "ghastly": {
        "color": 16317596,
        "dependencies": [
            "aoa3"
        ]
    },
    "ghoulish": {
        "color": 8300796,
        "dependencies": [
            "aoa3"
        ]
    }
}

for material, properties in ingots.items():
    field = material.upper()
    name = material
    color = properties["color"]
    mods = properties["dependencies"]
    if len(mods) == 0:
        continue
    result = f'''    @Nullable
    public static final CompatCrop {field} = Crops.compatCrop("{name}", "#forge:ingots/{material}", 0x{color:X}, 3, CropType.CROP, Map.of(
{",\n".join([f'        "{mod}", "{ingot_translations[mod](material)}"' for mod in mods])}
    ));'''
    print(result)

gem_translations = {
    "aoa3": lambda material: f"item.aoa3.{material}",
    "ae2": lambda material: f"item.ae2.{material}_crystal"
}