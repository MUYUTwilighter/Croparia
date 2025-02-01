package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.annotation.PostReg;
import cool.muyucloud.croparia.annotation.PreReg;
import cool.muyucloud.croparia.api.core.item.GreenhouseItem;
import cool.muyucloud.croparia.api.core.item.Placeholder;
import cool.muyucloud.croparia.api.core.item.RecipeWizard;
import cool.muyucloud.croparia.api.crop.item.CropFruit;
import cool.muyucloud.croparia.api.crop.item.CropSeed;
import cool.muyucloud.croparia.api.element.item.Elematilius;
import cool.muyucloud.croparia.api.element.item.ElementalBucket;
import cool.muyucloud.croparia.api.element.item.ElementalPotion;
import cool.muyucloud.croparia.api.element.ElementsEnum;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.core.item.relic.HornPlenty;
import cool.muyucloud.croparia.api.core.item.relic.InfiniteApple;
import cool.muyucloud.croparia.api.core.item.relic.MagicRope;
import cool.muyucloud.croparia.api.core.item.relic.MidasHand;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public class CropariaItems {
    @PreReg
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.ITEM);

    @PostReg
    public static final RegistrySupplier<RecipeWizard> RECIPE_WIZARD = registerItem(
        "recipe_wizard", () -> new RecipeWizard(new Item.Properties().arch$tab(Tabs.MAIN).stacksTo(1).rarity(Rarity.UNCOMMON))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> ACTIVATED_SHRIEKER = registerItem(
        "activated_shrieker",
        () -> new BlockItem(CropariaBlocks.ACTIVATED_SHRIEKER.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> PLACEHOLDER_BLOCK = registerItem(
        "placeholder_block", () -> new BlockItem(CropariaBlocks.PLACEHOLDER.get(), new Item.Properties())
    );
    @PostReg
    public static final RegistrySupplier<Placeholder> PLACEHOLDER = registerItem("placeholder", Placeholder::new);
    @PostReg
    public static final RegistrySupplier<BlockItem> GREENHOUSE = registerItem(
        "greenhouse",
        () -> new GreenhouseItem(CropariaBlocks.GREENHOUSE.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> INFUSOR = registerItem(
        "infusor",
        () -> new BlockItem(CropariaBlocks.INFUSOR.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> RITUAL_STAND = registerItem(
        "ritual_stand",
        () -> new BlockItem(CropariaBlocks.RITUAL_STAND.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> RITUAL_STAND_2 = registerItem(
        "ritual_stand_2",
        () -> new BlockItem(CropariaBlocks.RITUAL_STAND_2.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> RITUAL_STAND_3 = registerItem(
        "ritual_stand_3",
        () -> new BlockItem(CropariaBlocks.RITUAL_STAND_3.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> ELEMENTAL_STONE = registerItem(
        "elemental_stone",
        () -> new BlockItem(CropariaBlocks.ELEMENTAL_STONE.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> ELEMATILIUS_ORE = registerItem(
        "elematilius_ore",
        () -> new BlockItem(CropariaBlocks.ELEMATILIUS_ORE.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> DEEPSLATE_ELEMATILIUS_ORE = registerItem(
        "deepslate_elematilius_ore",
        () -> new BlockItem(CropariaBlocks.DEEPSLATE_ELEMATILIUS_ORE.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<ElementalPotion> POTION_ELEMATILIUS = registerItem(
        "potion_elematilius",
        () -> new ElementalPotion(ElementsEnum.ELEMENTAL, new Item.Properties().arch$tab(Tabs.MAIN).craftRemainder(Items.GLASS_BOTTLE))
    );
    public static final RegistrySupplier<ElementalPotion> POTION_WATER = registerItem(
        "potion_water",
        () -> new ElementalPotion(ElementsEnum.WATER, new Item.Properties().arch$tab(Tabs.MAIN).craftRemainder(Items.GLASS_BOTTLE))
    );
    public static final RegistrySupplier<ElementalPotion> POTION_FIRE = registerItem(
        "potion_fire",
        () -> new ElementalPotion(ElementsEnum.FIRE, new Item.Properties().arch$tab(Tabs.MAIN).craftRemainder(Items.GLASS_BOTTLE))
    );
    public static final RegistrySupplier<ElementalPotion> POTION_EARTH = registerItem(
        "potion_earth",
        () -> new ElementalPotion(ElementsEnum.EARTH, new Item.Properties().arch$tab(Tabs.MAIN).craftRemainder(Items.GLASS_BOTTLE))
    );
    public static final RegistrySupplier<ElementalPotion> POTION_AIR = registerItem(
        "potion_air",
        () -> new ElementalPotion(ElementsEnum.AIR, new Item.Properties().arch$tab(Tabs.MAIN).craftRemainder(Items.GLASS_BOTTLE))
    );
    @PostReg
    public static final RegistrySupplier<Elematilius> ELEMATILIUS = registerItem(
        "elematilius",
        () -> new Elematilius(ElementsEnum.ELEMENTAL, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Elematilius> ELEMENTAL_FIRE = registerItem(
        "elemental_fire",
        () -> new Elematilius(ElementsEnum.FIRE, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Elematilius> ELEMENTAL_WATER = registerItem(
        "elemental_water",
        () -> new Elematilius(ElementsEnum.WATER, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Elematilius> ELEMENTAL_EARTH = registerItem(
        "elemental_earth",
        () -> new Elematilius(ElementsEnum.EARTH, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Elematilius> ELEMENTAL_AIR = registerItem(
        "elemental_air",
        () -> new Elematilius(ElementsEnum.AIR, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA = registerItem(
        "croparia", () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN)));
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA2 = registerItem(
        "croparia2", () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN)));
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA3 = registerItem(
        "croparia3", () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN)));
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA4 = registerItem(
        "croparia4", () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN)));
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA5 = registerItem(
        "croparia5", () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN)));
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA6 = registerItem(
        "croparia6", () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN)));
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA7 = registerItem(
        "croparia7", () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN)));
    @PostReg
    public static final RegistrySupplier<HornPlenty> HORN = registerItem("horn_plenty", HornPlenty::new);
    @PostReg
    public static final RegistrySupplier<InfiniteApple> INFINITE_APPLE = registerItem("infinite_apple", InfiniteApple::new);
    @PostReg
    public static final RegistrySupplier<MagicRope> MAGIC_ROPE = registerItem("magic_rope", MagicRope::new);
    @PostReg
    public static final RegistrySupplier<MidasHand> MIDAS_HAND = registerItem("midas_hand", MidasHand::new);
    @PostReg
    public static final RegistrySupplier<Item> ELEMATILIUS_BUCKET = registerItem(
        "elematilius_bucket",
        () -> new ElementalBucket(ElementsEnum.ELEMENTAL, Fluids.ELEMATILIUS, new Item.Properties().arch$tab(Tabs.MAIN).stacksTo(1).craftRemainder(Items.BUCKET))
    );
    @PostReg
    public static final RegistrySupplier<Item> EARTH_BUCKET = registerItem(
        "earth_bucket",
        () -> new ElementalBucket(ElementsEnum.EARTH, Fluids.EARTH, new Item.Properties().arch$tab(Tabs.MAIN).stacksTo(1).craftRemainder(Items.BUCKET))
    );
    @PostReg
    public static final RegistrySupplier<Item> WATER_BUCKET = registerItem(
        "water_bucket",
        () -> new ElementalBucket(ElementsEnum.WATER, Fluids.WATER, new Item.Properties().arch$tab(Tabs.MAIN).stacksTo(1).craftRemainder(Items.BUCKET))
    );
    @PostReg
    public static final RegistrySupplier<Item> FIRE_BUCKET = registerItem(
        "fire_bucket",
        () -> new ElementalBucket(ElementsEnum.FIRE, Fluids.FIRE, new Item.Properties().arch$tab(Tabs.MAIN).stacksTo(1).craftRemainder(Items.BUCKET))
    );
    @PostReg
    public static final RegistrySupplier<Item> AIR_BUCKET = registerItem(
        "air_bucket",
        () -> new ElementalBucket(ElementsEnum.AIR, Fluids.AIR, new Item.Properties().arch$tab(Tabs.MAIN).stacksTo(1).craftRemainder(Items.BUCKET))
    );

    @PostReg
    protected static final List<RegistrySupplier<Item>> CROPARIAS = List.of(
        CROPARIA, CROPARIA2, CROPARIA3, CROPARIA4, CROPARIA5, CROPARIA6, CROPARIA7
    );
    @PostReg
    protected static final List<RegistrySupplier<BlockItem>> RITUAL_STANDS = List.of(
        RITUAL_STAND, RITUAL_STAND_2, RITUAL_STAND_3
    );

    @PreReg
    public static void registerCrop(@NotNull Crop crop) {
        ITEMS.register(crop.getSeedId(), () -> new CropSeed(crop));
        ITEMS.register(crop.getFruitId(), () -> new CropFruit(crop));
    }

    @PreReg
    @NotNull
    public static @PostReg <T extends Item> RegistrySupplier<T> registerItem(@NotNull String name, @NotNull Supplier<T> supplier) {
        return ITEMS.register(name, supplier);
    }

    @PreReg
    public static void register() {
        CropariaIf.LOGGER.debug("Registering items");
        ITEMS.register();
    }

    public static @NotNull @PostReg RegistrySupplier<Item> getCroparia(int tier) {
        return CROPARIAS.get(tier - 1);
    }

    public static int leastTier() {
        return 1;
    }

    public static int mostTier() {
        return CROPARIAS.size();
    }

    public static @NotNull @PostReg RegistrySupplier<BlockItem> getRitualStand(int tier) {
        return RITUAL_STANDS.get(tier - 1);
    }
}
