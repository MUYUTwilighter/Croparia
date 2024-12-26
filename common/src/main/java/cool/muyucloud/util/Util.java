package cool.muyucloud.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import cool.muyucloud.CropariaIf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class Util {
    public static boolean hasNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack parseItemStack(JsonObject json) {
        ItemStack itemStack;
        try {
            String itemId = json.get("item").getAsString();
            Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(itemId));

            if (item == null) {
                throw new JsonParseException("Unknown item ID: " + itemId);
            }
            int count = GsonHelper.getAsInt(json, "count", 1);
            itemStack = new ItemStack(item, count);
            if (json.has("nbt")) {
                try {
                    CompoundTag nbt = TagParser.parseTag(json.get("nbt").getAsString());
                    itemStack.setTag(nbt);
                } catch (CommandSyntaxException e) {
                    throw new JsonParseException("Failed to parse NBT data", e);
                }
            }
        } catch (Exception e) {
            CropariaIf.LOGGER.error("Failed to deserialize item stack", e);
            itemStack = new ItemStack(Items.AIR);
        }
        return itemStack;
    }
}
