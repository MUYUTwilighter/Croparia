package cool.muyucloud.registry;

import cool.muyucloud.data.crop.Crop;
import cool.muyucloud.CropariaIf;
import cool.muyucloud.data.ElementsEnum;
import cool.muyucloud.item.CropFruit;
import cool.muyucloud.item.CropSeed;
import cool.muyucloud.item.Elematilius;
import cool.muyucloud.item.GreenhouseItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class CropariaItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<BlockItem> GREENHOUSE = registerItem(
        "greenhouse",
        () -> new GreenhouseItem(CropariaBlocks.GREENHOUSE.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<BlockItem> INFUSOR = registerItem(
        "infusor",
        () -> new BlockItem(CropariaBlocks.INFUSOR.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<BlockItem> ELEMENTAL_STONE = registerItem(
        "elemental_stone",
        () -> new BlockItem(CropariaBlocks.ELEMENTAL_STONE.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<BlockItem> ELEMATILIUS_ORE = registerItem(
        "elematilius_ore",
        () -> new BlockItem(CropariaBlocks.ELEMATILIUS_ORE.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<BlockItem> DEEPSLATE_ELEMATILIUS_ORE = registerItem(
        "deepslate_elematilius_ore",
        () -> new BlockItem(CropariaBlocks.DEEPSLATE_ELEMATILIUS_ORE.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Item> POTION_ELEMATILIUS = registerItem(
        "potion_elematilius",
        () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Item> POTION_WATER = registerItem(
        "potion_water",
        () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Item> POTION_FIRE = registerItem(
        "potion_fire",
        () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Item> POTION_EARTH = registerItem(
        "potion_earth",
        () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Item> POTION_AIR = registerItem(
        "potion_air",
        () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Elematilius> ELEMATILIUS = registerItem(
        "elematilius",
        () -> new Elematilius(ElementsEnum.ELEMENTAL, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Elematilius> ELEMENTAL_FIRE = registerItem(
        "elemental_fire",
        () -> new Elematilius(ElementsEnum.FIRE, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Elematilius> ELEMENTAL_WATER = registerItem(
        "elemental_water",
        () -> new Elematilius(ElementsEnum.WATER, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Elematilius> ELEMENTAL_EARTH = registerItem(
        "elemental_earth",
        () -> new Elematilius(ElementsEnum.EARTH, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Elematilius> ELEMENTAL_AIR = registerItem(
        "elemental_air",
        () -> new Elematilius(ElementsEnum.AIR, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Item> CROPARIA = registerItem(
        "croparia", () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN)));
    public static final RegistrySupplier<Item> CROPARIA2 = registerItem(
        "croparia2", () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN)));
    public static final RegistrySupplier<Item> CROPARIA3 = registerItem(
        "croparia3", () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN)));
    public static final RegistrySupplier<Item> CROPARIA4 = registerItem(
        "croparia4", () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN)));
    public static final RegistrySupplier<Item> CROPARIA5 = registerItem(
        "croparia5", () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN)));
    public static final RegistrySupplier<Item> CROPARIA6 = registerItem(
        "croparia6", () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN)));
    public static final RegistrySupplier<Item> CROPARIA7 = registerItem(
        "croparia7", () -> new Item(new Item.Properties().arch$tab(Tabs.MAIN)));

    public static void registerCrop(Crop crop) {
        ITEMS.register(crop.getSeedId(), () -> new CropSeed(crop));
        ITEMS.register(crop.getFruitId(), () -> new CropFruit(crop));
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String name, Supplier<T> supplier) {
        return ITEMS.register(name, supplier);
    }

    public static void register() {
        ITEMS.register();
    }

    public static ElementsEnum elementFromPotion(@NotNull Item item) {
        if (item == POTION_ELEMATILIUS.get()) return ElementsEnum.ELEMENTAL;
        else if (item == POTION_WATER.get()) return ElementsEnum.WATER;
        else if (item == POTION_FIRE.get()) return ElementsEnum.FIRE;
        else if (item == POTION_EARTH.get()) return ElementsEnum.EARTH;
        else if (item == POTION_AIR.get()) return ElementsEnum.AIR;
        return ElementsEnum.EMPTY;
    }

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
}
