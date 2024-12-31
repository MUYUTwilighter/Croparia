from json import load, dump
from os import listdir, makedirs
from os.path import exists

src = "./1.20.1-fabric/data/croparia/recipes/"
output = "./1.20.1-arch/data/croparia/recipes/ritual/"

if not exists(output):
    makedirs(output)

'''Old Recipe
{
  "type": "croparia:ritual_recipe",
  "tier": 1,
  "input": "minecraft:nether_wart_block",
  "block": "minecraft:spruce_wood",
  "output": "croparia:horn_plenty",
  "count": 1
}
'''
'''New Recipe
{
  "type": "croparia:ritual",
  "tier": 1,
  "ingredient": {
    "id": "minecraft:nether_wart_block",
    "Count": 1
  },
  "block": {
    "block": "minecraft:spruce_wood"
  },
  "result": {
    "id": "croparia:horn_plenty",
    "Count": 1
  }
}
'''

for file in listdir(src):
    with open(src + file, "r") as f:
        old = load(f)
        f.close()
        if old['type'] != 'croparia:ritual_recipe':
            continue
        else:
            new = {
                "type": "croparia:ritual",
                "tier": old["tier"],
                "ingredient": {
                    "id": old["input"],
                    "Count": old["count"]
                },
                "block": {
                    "block": old["block"]
                },
                "result": {
                    "id": old["output"],
                    "Count": old["count"]
                }
            }
            dump(new, open(output + file, "w"), indent=2)
