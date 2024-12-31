import re
from os import makedirs
from os.path import exists

src = "./1.20.1-fabric/"
dst = "./1.20.1-arch/"
file = "crop_definition_vanilla"
if not exists(src):
    makedirs(src)
    raise FileExistsError("Source file not exists")
if not exists(dst):
    makedirs(dst)

pattern = r"(\w+)\s*=\s*new\s+Crop\(\"([^\"]+)\",\s*([\w.]+),\s*(\d+),\s*([\w.]+),\s*(\d+)\);"

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
    field, name, type_, tier, material, color = match.groups()
    # public static final Crop APPLE = registerCrop("apple", Objects.requireNonNull(Items.APPLE.arch$registryName()).toString(), 0xFF1C2B, 1, CropType.NATURE, Items.APPLE.getDescriptionId());
    result += f"public static final Crop {field} = registerCrop(\"{name}\", Objects.requireNonNull({material}.arch$registryName()).toString(), 0x{int(color):x}, {tier}, {type_}, {material}.getDescriptionId());\n"
dst_f.write(result)

src_f.close()
dst_f.close()