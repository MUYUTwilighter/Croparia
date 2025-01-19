from json import load, dump
from os import listdir, makedirs
from os.path import exists, isdir

src = ".\\1.21-arch\\data\\croparia\\recipe\\"
dst = ".\\1.20.1-arch\\data\\croparia\\recipes\\"


def recursive(parent):
    if (parent[-1] != "\\"):
        parent = parent + "\\"
    for file in listdir(parent):
        src_p = parent + file
        if isdir(src_p):
            recursive(src_p)
        else:
            with open(src_p, "r") as src_f:
                recipe = load(src_f)
                mods = [*recipe["fabric:load_conditions"][0]["values"]]
                del recipe["fabric:load_conditions"]
                del recipe["neoforge:conditions"]
                forge_recipe = {
                    "type": "forge:conditional",
                    "recipes": [
                        {
                            "conditions": [
                                {
                                    "type": "forge:mod_loaded",
                                    "modid": mods[0]
                                }
                            ],
                            "recipe": recipe
                        },
                    ]
                }
                src_f.close()
                dst_p = dst + parent[len(src):]
                if not exists(dst_p):
                    makedirs(dst_p)
                with open(dst_p + file, "w") as dst_f:
                    dump(forge_recipe, dst_f, indent="  ")
                    dst_f.close()


recursive(src)
