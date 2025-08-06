package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.annotation.PostReg;
import cool.muyucloud.croparia.api.core.item.GreenhouseItem;
import cool.muyucloud.croparia.api.core.item.Placeholder;
import cool.muyucloud.croparia.api.core.item.RecipeWizard;
import cool.muyucloud.croparia.api.core.item.relic.HornPlenty;
import cool.muyucloud.croparia.api.core.item.relic.InfiniteApple;
import cool.muyucloud.croparia.api.core.item.relic.MagicRope;
import cool.muyucloud.croparia.api.core.item.relic.MidasHand;
import cool.muyucloud.croparia.api.crop.item.Croparia;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public class CropariaItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<RecipeWizard> RECIPE_WIZARD = registerItem(
        "recipe_wizard", properties -> new RecipeWizard(new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, CropariaIf.of("recipe_wizard")))
            .arch$tab(Tabs.MAIN).rarity(Rarity.UNCOMMON).stacksTo(1))
    );
    public static final RegistrySupplier<BlockItem> ACTIVATED_SHRIEKER = registerItem(
        "activated_shrieker",
        properties -> new BlockItem(CropariaBlocks.ACTIVATED_SHRIEKER.get(), properties.arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<BlockItem> PLACEHOLDER_BLOCK = registerItem(
        "placeholder_block", properties -> new BlockItem(CropariaBlocks.PLACEHOLDER.get(), properties)
    );
    public static final RegistrySupplier<Placeholder> PLACEHOLDER = registerItem(
        "placeholder", properties -> new Placeholder(properties.stacksTo(99))
    );
    public static final RegistrySupplier<BlockItem> GREENHOUSE = registerItem(
        "greenhouse",
        properties -> new GreenhouseItem(CropariaBlocks.GREENHOUSE.get(), properties.arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<BlockItem> INFUSOR = registerItem(
        "infusor", properties -> new BlockItem(CropariaBlocks.INFUSOR.get(), properties.arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<BlockItem> RITUAL_STAND = registerItem(
        "ritual_stand", properties -> new BlockItem(CropariaBlocks.RITUAL_STAND.get(), properties.arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<BlockItem> RITUAL_STAND_2 = registerItem(
        "ritual_stand_2",
        properties -> new BlockItem(CropariaBlocks.RITUAL_STAND_2.get(), properties.arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<BlockItem> RITUAL_STAND_3 = registerItem(
        "ritual_stand_3",
        properties -> new BlockItem(CropariaBlocks.RITUAL_STAND_3.get(), properties.arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<BlockItem> ELEMENTAL_STONE = registerItem(
        "elemental_stone",
        properties -> new BlockItem(CropariaBlocks.ELEMENTAL_STONE.get(), properties.arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<BlockItem> ELEMATILIUS_ORE = registerItem(
        "elematilius_ore",
        properties -> new BlockItem(CropariaBlocks.ELEMATILIUS_ORE.get(), properties.arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<BlockItem> DEEPSLATE_ELEMATILIUS_ORE = registerItem(
        "deepslate_elematilius_ore",
        properties -> new BlockItem(CropariaBlocks.DEEPSLATE_ELEMATILIUS_ORE.get(), properties.arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA = registerItem(
        "croparia", properties -> new Croparia(1, properties.arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA2 = registerItem(
        "croparia2", properties -> new Croparia(2, properties.arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA3 = registerItem(
        "croparia3", properties -> new Croparia(3, properties.arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA4 = registerItem(
        "croparia4", properties -> new Croparia(4, properties.arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA5 = registerItem(
        "croparia5", properties -> new Croparia(5, properties.arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA6 = registerItem(
        "croparia6", properties -> new Croparia(6, properties.arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA7 = registerItem(
        "croparia7", properties -> new Croparia(7, properties.arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<HornPlenty> HORN = registerItem(
        "horn_plenty",
        properties -> new HornPlenty(
            properties.arch$tab(Tabs.MAIN).stacksTo(1).rarity(Rarity.EPIC)
        )
    );
    public static final RegistrySupplier<InfiniteApple> INFINITE_APPLE = registerItem(
        "infinite_apple", properties -> new InfiniteApple(properties.food(
            new FoodProperties.Builder().alwaysEdible().nutrition(5).saturationModifier(4.0F).build(),
            Consumable.builder().onConsume(new ApplyStatusEffectsConsumeEffect(List.of(
                new MobEffectInstance(MobEffects.REGENERATION, 400, 1),
                new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0),
                new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0),
                new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3)
            ))).build()
        ).stacksTo(1).arch$tab(Tabs.MAIN).rarity(Rarity.EPIC)));
    public static final RegistrySupplier<MagicRope> MAGIC_ROPE = registerItem(
        "magic_rope", properties -> new MagicRope(properties.arch$tab(Tabs.MAIN).rarity(Rarity.EPIC).stacksTo(1))
    );
    public static final RegistrySupplier<MidasHand> MIDAS_HAND = registerItem(
        "midas_hand", properties -> new MidasHand(properties.arch$tab(Tabs.MAIN).rarity(Rarity.EPIC).stacksTo(1))
    );
    protected static final List<RegistrySupplier<Item>> CROPARIAS = List.of(
        CROPARIA, CROPARIA2, CROPARIA3, CROPARIA4, CROPARIA5, CROPARIA6, CROPARIA7
    );
    protected static final List<RegistrySupplier<BlockItem>> RITUAL_STANDS = List.of(
        RITUAL_STAND, RITUAL_STAND_2, RITUAL_STAND_3
    );

    @NotNull
    public static <T extends Item> RegistrySupplier<T> registerItem(
        @NotNull String name, @NotNull Function<Item.Properties, T> supplier
    ) {
        return ITEMS.register(name, () -> supplier.apply(
            new Item.Properties().setId(ResourceKey.create(Registries.ITEM, CropariaIf.of(name)))
        ));
    }

    public static void register() {
        CropariaIf.LOGGER.debug("Registering items");
        ITEMS.register();
    }

    public static @NotNull RegistrySupplier<Item> getCroparia(int tier) {
        return CROPARIAS.get(tier - 1);
    }

    public static int leastTier() {
        return 1;
    }

    public static int mostTier() {
        return CROPARIAS.size();
    }

    public static @NotNull RegistrySupplier<BlockItem> getRitualStand(int tier) {
        return RITUAL_STANDS.get(tier - 1);
    }
}
