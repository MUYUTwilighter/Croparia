from json import load, dump
from os import listdir, makedirs
from os.path import exists

src = "./1.20.1-arch/data/croparia/recipes/crafting/"
dst = "./1.21-arch/data/croparia/recipe/crafting/"

if not exists(dst):
    makedirs(dst)

for file in listdir(src):
    with open(src + file, "r") as i:
        old = load(i)
        print(old)
        i.close()
        old["result"]["id"] = old["result"]["item"]
        del old["result"]["item"]
        new = old
        with open(dst + file, "w") as o:
            dump(new, o, indent=2)
            o.close()