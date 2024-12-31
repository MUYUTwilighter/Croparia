import re
from os import makedirs
from os.path import exists

src = "./1.20.1-fabric/"
dst = "./1.20.1-arch/"
file = "crop_definition_modded"
if not exists(src):
    makedirs(src)
    raise FileExistsError("Source file not exists")
if not exists(dst):
    makedirs(dst)

# TIN = compatCrops("tin", CropType.BASIC, 3, "c:tin_ingots", 14935008, "techreborn", "indrev", "modern_industrialization", "mythicmetals");
pattern = r'(\w+)\s*=\s*compatCrops\("([^"]+)",\s*([\w.]+),\s*(\d+),\s*"([^"]+)",\s*(\d+),\s*([^;]+)\);'

src_f = open("./1.20.1-fabric/" + file, "r")
dst_f = open("./1.20.1-arch/" + file, "w")

result = ""
i = 0
for line in src_f.readlines():
    i += 1
    match = re.match(pattern, line)
    if not match:
        print(f"Invalid line {i}: {line}")
        continue
    field, name, crop_type, tier, tag, color, mods = match.groups()
    mods = [mod.strip().strip('"') for mod in mods.split(",")]
    color = f"0x{int(color):06X}"
    # @Nullable
    # public static final Crop TIN = compatCrop("tin", "c:tin_ingots", 0xE3E3E0, 3, CropType.CROP, Map.of(
    #     "techreborn", "",
    #     "indrev", "",
    #     "modern_industrialization", "",
    #     "mythicmetals", ""
    # ));
    result += f'''    @Nullable
    public static final Crop {field} = compatCrop("{name}", "{tag}", {color}, {tier}, {crop_type}, Map.of(
{",\n".join([f'        "{mod}", ""' for mod in mods])}
    ));
'''
dst_f.write(result)

src_f.close()
dst_f.close()