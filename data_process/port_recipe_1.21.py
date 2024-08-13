from json import load
from os import listdir

for file in listdir("src\\main\\resources\\data\\croparia\\recipe"):
    recipe = load(file)
    id = recipe["result"]["item"]
    del recipe["result"]["item"]
    recipe["result"]["id"] = id
