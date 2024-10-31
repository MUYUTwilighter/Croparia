from json import load, dump
from os import listdir

dir = "..\\src\\main\\resources\\data\\croparia\\recipe"

for file in listdir(dir):
    recipe = None
    with open(dir + '\\' + file, 'r') as f:
        recipe = load(f)
    if recipe["type"] == "minecraft:crafting_shaped":
        key = recipe["key"]
        newkey = {}
        for item in key.keys():
            newkey[item] = key[item]["item"]
        recipe["key"] = newkey
        with open('output\\' + file, 'w') as f:
            dump(recipe, f)
    elif recipe["type"] == "minecraft:crafting_shapeless":
        newIngredients = []
        for ingredient in recipe["ingredients"]:
            newIngredients.append(ingredient["item"])
        recipe["ingredients"] = newIngredients
        with open('output\\' + file, 'w') as f:
            dump(recipe, f)
