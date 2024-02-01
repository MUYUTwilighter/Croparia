package com.defacto34.croparia.mixin;

import com.defacto34.croparia.init.LootTableInit;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.loot.LootDataKey;
import net.minecraft.loot.LootDataLookup;
import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LootManager.class)
public abstract class LootManagerMixin implements ResourceReloader, LootDataLookup {
    @Inject(method = "validate(Ljava/util/Map;)V", at = @At(value = "INVOKE", target = "Ljava/util/Map;forEach(Ljava/util/function/BiConsumer;)V", shift = At.Shift.BEFORE))
    private void addCropariaLoots(CallbackInfo ci, @Local ImmutableMap.Builder<LootDataKey<?>, Object> builder, @Local ImmutableMultimap.Builder<LootDataType<?>, Identifier> builder2) {
        for (Map.Entry<LootDataKey<?>, Object> entry : LootTableInit.LOOT_TABLES.entrySet()) {
            builder.put(entry.getKey(), entry.getValue());
            LootDataKey<?> lootDataKey = entry.getKey();
            builder2.put(lootDataKey.type(), lootDataKey.id());
        }
    }
}
