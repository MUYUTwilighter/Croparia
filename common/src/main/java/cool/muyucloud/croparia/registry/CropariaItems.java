package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.annotation.PostReg;
import cool.muyucloud.croparia.annotation.PreReg;
import cool.muyucloud.croparia.data.ElementsEnum;
import cool.muyucloud.croparia.data.crop.Crop;
import cool.muyucloud.croparia.item.*;
import cool.muyucloud.croparia.item.relic.HornPlenty;
import cool.muyucloud.croparia.item.relic.InfiniteApple;
import cool.muyucloud.croparia.item.relic.MagicRope;
import cool.muyucloud.croparia.item.relic.MidasHand;
import dev.architectury.core.item.ArchitecturyBucketItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public class CropariaItems {
    @PreReg
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(CropariaIf.MOD_ID, Registry.ITEM_REGISTRY);

    @PostReg
    public static final RegistrySupplier<RecipeWizard> RECIPE_WIZARD = registerItem(
        "recipe_wizard", () -> new RecipeWizard(new Item.Properties().tab(Tabs.MAIN).stacksTo(1).rarity(Rarity.UNCOMMON))
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
        () -> new GreenhouseItem(CropariaBlocks.GREENHOUSE.get(), new Item.Properties().tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> INFUSOR = registerItem(
        "infusor",
        () -> new BlockItem(CropariaBlocks.INFUSOR.get(), new Item.Properties().tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> RITUAL_STAND = registerItem(
        "ritual_stand",
        () -> new BlockItem(CropariaBlocks.RITUAL_STAND.get(), new Item.Properties().tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> RITUAL_STAND_2 = registerItem(
        "ritual_stand_2",
        () -> new BlockItem(CropariaBlocks.RITUAL_STAND_2.get(), new Item.Properties().tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> RITUAL_STAND_3 = registerItem(
        "ritual_stand_3",
        () -> new BlockItem(CropariaBlocks.RITUAL_STAND_3.get(), new Item.Properties().tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> ELEMENTAL_STONE = registerItem(
        "elemental_stone",
        () -> new BlockItem(CropariaBlocks.ELEMENTAL_STONE.get(), new Item.Properties().tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> ELEMATILIUS_ORE = registerItem(
        "elematilius_ore",
        () -> new BlockItem(CropariaBlocks.ELEMATILIUS_ORE.get(), new Item.Properties().tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<BlockItem> DEEPSLATE_ELEMATILIUS_ORE = registerItem(
        "deepslate_elematilius_ore",
        () -> new BlockItem(CropariaBlocks.DEEPSLATE_ELEMATILIUS_ORE.get(), new Item.Properties().tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Item> POTION_ELEMATILIUS = registerItem(
        "potion_elematilius",
        () -> new Item(new Item.Properties().tab(Tabs.MAIN).craftRemainder(Items.GLASS_BOTTLE))
    );
    @PostReg
    public static final RegistrySupplier<Item> POTION_WATER = registerItem(
        "potion_water",
        () -> new Item(new Item.Properties().tab(Tabs.MAIN).craftRemainder(Items.GLASS_BOTTLE))
    );
    @PostReg
    public static final RegistrySupplier<Item> POTION_FIRE = registerItem(
        "potion_fire",
        () -> new Item(new Item.Properties().tab(Tabs.MAIN).craftRemainder(Items.GLASS_BOTTLE))
    );
    @PostReg
    public static final RegistrySupplier<Item> POTION_EARTH = registerItem(
        "potion_earth",
        () -> new Item(new Item.Properties().tab(Tabs.MAIN).craftRemainder(Items.GLASS_BOTTLE))
    );
    @PostReg
    public static final RegistrySupplier<Item> POTION_AIR = registerItem(
        "potion_air",
        () -> new Item(new Item.Properties().tab(Tabs.MAIN).craftRemainder(Items.GLASS_BOTTLE))
    );
    @PostReg
    public static final RegistrySupplier<Elematilius> ELEMATILIUS = registerItem(
        "elematilius",
        () -> new Elematilius(ElementsEnum.ELEMENTAL, new Item.Properties().tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Elematilius> ELEMENTAL_FIRE = registerItem(
        "elemental_fire",
        () -> new Elematilius(ElementsEnum.FIRE, new Item.Properties().tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Elematilius> ELEMENTAL_WATER = registerItem(
        "elemental_water",
        () -> new Elematilius(ElementsEnum.WATER, new Item.Properties().tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Elematilius> ELEMENTAL_EARTH = registerItem(
        "elemental_earth",
        () -> new Elematilius(ElementsEnum.EARTH, new Item.Properties().tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Elematilius> ELEMENTAL_AIR = registerItem(
        "elemental_air",
        () -> new Elematilius(ElementsEnum.AIR, new Item.Properties().tab(Tabs.MAIN))
    );
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA = registerItem(
        "croparia", () -> new Item(new Item.Properties().tab(Tabs.MAIN)));
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA2 = registerItem(
        "croparia2", () -> new Item(new Item.Properties().tab(Tabs.MAIN)));
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA3 = registerItem(
        "croparia3", () -> new Item(new Item.Properties().tab(Tabs.MAIN)));
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA4 = registerItem(
        "croparia4", () -> new Item(new Item.Properties().tab(Tabs.MAIN)));
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA5 = registerItem(
        "croparia5", () -> new Item(new Item.Properties().tab(Tabs.MAIN)));
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA6 = registerItem(
        "croparia6", () -> new Item(new Item.Properties().tab(Tabs.MAIN)));
    @PostReg
    public static final RegistrySupplier<Item> CROPARIA7 = registerItem(
        "croparia7", () -> new Item(new Item.Properties().tab(Tabs.MAIN)));
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
        () -> new ArchitecturyBucketItem(Fluids.ELEMATILIUS, new Item.Properties().tab(Tabs.MAIN).stacksTo(1).craftRemainder(Items.BUCKET))
    );
    @PostReg
    public static final RegistrySupplier<Item> EARTH_BUCKET = registerItem(
        "earth_bucket",
        () -> new ArchitecturyBucketItem(Fluids.EARTH, new Item.Properties().tab(Tabs.MAIN).stacksTo(1).craftRemainder(Items.BUCKET))
    );
    @PostReg
    public static final RegistrySupplier<Item> WATER_BUCKET = registerItem(
        "water_bucket",
        () -> new ArchitecturyBucketItem(Fluids.WATER, new Item.Properties().tab(Tabs.MAIN).stacksTo(1).craftRemainder(Items.BUCKET))
    );
    @PostReg
    public static final RegistrySupplier<Item> FIRE_BUCKET = registerItem(
        "fire_bucket",
        () -> new ArchitecturyBucketItem(Fluids.FIRE, new Item.Properties().tab(Tabs.MAIN).stacksTo(1).craftRemainder(Items.BUCKET))
    );
    @PostReg
    public static final RegistrySupplier<Item> AIR_BUCKET = registerItem(
        "air_bucket",
        () -> new ArchitecturyBucketItem(Fluids.AIR, new Item.Properties().tab(Tabs.MAIN).stacksTo(1).craftRemainder(Items.BUCKET))
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

    @PostReg
    @NotNull
    public static ElementsEnum elementFromPotion(@NotNull Item item) {
        if (item == POTION_ELEMATILIUS.get()) return ElementsEnum.ELEMENTAL;
        else if (item == POTION_WATER.get()) return ElementsEnum.WATER;
        else if (item == POTION_FIRE.get()) return ElementsEnum.FIRE;
        else if (item == POTION_EARTH.get()) return ElementsEnum.EARTH;
        else if (item == POTION_AIR.get()) return ElementsEnum.AIR;
        return ElementsEnum.EMPTY;
    }

    @PostReg
    public static @NotNull Item getPotion(@NotNull ElementsEnum element) {
        return switch (element) {
            case WATER -> POTION_WATER.get();
            case FIRE -> POTION_FIRE.get();
            case EARTH -> POTION_EARTH.get();
            case AIR -> POTION_AIR.get();
            case ELEMENTAL -> POTION_ELEMATILIUS.get();
            case EMPTY -> Items.AIR;
        };
    }

    @PostReg
    public static @NotNull Item getElementilius(@NotNull ElementsEnum element) {
        return switch (element) {
            case WATER -> ELEMENTAL_WATER.get();
            case FIRE -> ELEMENTAL_FIRE.get();
            case EARTH -> ELEMENTAL_EARTH.get();
            case AIR -> ELEMENTAL_AIR.get();
            case ELEMENTAL -> ELEMATILIUS.get();
            case EMPTY -> Items.AIR;
        };
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
