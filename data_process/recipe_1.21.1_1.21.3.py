from json import load, dump
from os import makedirs, listdir
from os.path import isdir

src = "./1.21-arch/data/croparia/recipe/"
dst = "./1.21.3-arch/data/croparia/recipe/"


def recursive(src_p):
    if src_p[-1] != "/":
        src_p += "/"
    for file in listdir(src_p):
        src_f = src_p + file
        if isdir(src_f):
            recursive(src_f)
        else:
            dst_p = dst + src_p[len(src):]
            dst_f = dst_p + file
            if not isdir(dst_p):
                makedirs(dst_p)
            else:
                with open(src_f, "r") as s:
                    with open(dst_f, "w") as d:
                        old = load(s)
                        if old["type"] == "minecraft:crafting_shaped":
                            dump(crafting_shaped(old), d, indent="  ")
                        elif old["type"] == "minecraft:crafting_shapeless":
                            dump(crafting_shapeless(old), d, indent="  ")
                        else:
                            print(f"Unknown type: {old['type']}")
                        d.close()
                    s.close()


def crafting_shaped(old: dict) -> dict:
    return {
        "type": "minecraft:crafting_shaped",
        "category": old["category"],
        "key": {
            k: v["item"] for k, v in old["key"].items()
        },
        "pattern": old["pattern"],
        "result": old["result"]
    }


def crafting_shapeless(old: dict):
    return {
        "type": "minecraft:crafting_shapeless",
        "category": old["category"],
        "ingredients": [
            i["item"] for i in old["ingredients"]
        ],
        "result": old["result"]
    }


recursive(src)
