package cool.muyucloud.croparia.util;

import cool.muyucloud.croparia.annotation.PostGen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;

@PostGen
public class PostConstants {
    public static final List<Item> FOODS = BuiltInRegistries.ITEM.stream().filter(item -> Util.isEdible(item.getDefaultInstance())).toList();
    public static final TagKey<Block> MIDAS_HAND_IMMUNE_BLOCKS = TagKey.create(Registries.BLOCK, ResourceLocation.tryBuild("croparia", "midas_hand_immune"));
    public static final TagKey<EntityType<?>> MIDAS_HAND_IMMUNE_ENTITIES = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.tryBuild("croparia", "midas_hand_immune"));
    public static final TagKey<Item> HORN_PLENTY_BLACKLIST = TagKey.create(Registries.ITEM, ResourceLocation.tryBuild("croparia", "horn_plenty_blacklist"));
}
