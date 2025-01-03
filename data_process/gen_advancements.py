pattern = '''{
  "parent": "croparia:root",
  "criteria": {
    "{name}": {
      "conditions": {
        "items": [
          {
            "items": [
              "croparia:{name}"
            ]
          }
        ]
      },
      "trigger": "minecraft:inventory_changed"
    }
  },
  "display": {
    "announce_to_chat": true,
    "description": {
      "translate": "advancements.croparia.{name}.description"
    },
    "frame": "task",
    "hidden": false,
    "icon": {
      "item": "croparia:{name}"
    },
    "show_toast": true,
    "title": {
      "translate": "advancements.croparia.{name}.title"
    }
  },
  "requirements": [
    [
      "{name}"
    ]
  ],
  "sends_telemetry_event": true
}'''

items = [
    "croparia",
    "croparia2",
    "croparia3",
    "croparia4",
    "croparia5",
    "croparia6",
    "croparia7",
    "elematilius",
    "elemental_fire",
    "elemental_water",
    "elemental_air",
    "elemental_earth",
    "potion_elematilius",
    "infusor",
    "ritual_stand",
    "ritual_stand_2",
    "ritual_stand_3",
    "greenhouse",
    "infinite_apple",
    "magic_rope",
    "midas_hand",
    "horn_plenty"
]

for item in items:
    with open(f"./advancements/{item}.json", "w") as f:
        f.write(pattern.replace("{name}", item))
    print(f"\"advancements.croparia.{item}.title\": \"\"")
    print(f"\"advancements.croparia.{item}.description\": \"\"")
