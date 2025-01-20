package cool.muyucloud.croparia.util;

import cool.muyucloud.croparia.annotation.PostGen;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;

@PostGen
public class PostConstants {
    public static final List<Item> FOODS = Registry.ITEM.stream().filter(Item::isEdible).toList();
    public static final TagKey<Block> MIDAS_HAND_IMMUNE_BLOCKS = TagKey.create(Registry.BLOCK.key(), new ResourceLocation("croparia", "midas_hand_immune"));
    public static final TagKey<EntityType<?>> MIDAS_HAND_IMMUNE_ENTITIES = TagKey.create(Registry.ENTITY_TYPE.key(), new ResourceLocation("croparia", "midas_hand_immune"));
    public static final TagKey<Item> HORN_PLENTY_BLACKLIST = TagKey.create(Registry.ITEM.key(), new ResourceLocation("croparia", "horn_plenty_blacklist"));
}
