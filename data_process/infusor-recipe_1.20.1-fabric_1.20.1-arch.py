from json import load, dump
from os import listdir, makedirs
from os.path import exists

src = "./1.20.1-fabric/data/croparia/recipes/"
output = "./1.20.1-arch/data/croparia/recipes/"

if not exists(output):
    makedirs(output)

for file in listdir(src):
    with open(src + file, "r") as f:
        old = load(f)
        f.close()
        if old['type'] != 'croparia:infusor_recipe':
            continue
        else:
            new = {
                "type": "croparia:infusor",
                "element": old["element"],
                "ingredient": {
                    "id": old["input"],
                    "Count": old["count"]
                },
                "result": {
                    "id": old["output"],
                    "Count": old["count"]
                }
            }
            dump(new, open(output + file, "w"), indent=2)
