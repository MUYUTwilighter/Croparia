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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class CropariaItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> GREENHOUSE = registerItem(
        "greenhouse",
        () -> new GreenhouseItem(CropariaBlocks.GREENHOUSE.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Item> ELEMENTAL_STONE = registerItem(
        "elemental_stone",
        () -> new BlockItem(CropariaBlocks.ELEMENTAL_STONE.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Item> ELEMATILIUS_ORE = registerItem(
        "elematilius_ore",
        () -> new BlockItem(CropariaBlocks.ELEMATILIUS_ORE.get(), new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Item> DEEPSLATE_ELEMATILIUS_ORE = registerItem(
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
    public static final RegistrySupplier<Item> ELEMATILIUS = registerItem(
        "elematilius",
        () -> new Elematilius(ElementsEnum.ELEMENTAL, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Item> ELEMENTAL_FIRE = registerItem(
        "elemental_fire",
        () -> new Elematilius(ElementsEnum.FIRE, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Item> ELEMENTAL_WATER = registerItem(
        "elemental_water",
        () -> new Elematilius(ElementsEnum.WATER, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Item> ELEMENTAL_EARTH = registerItem(
        "elemental_earth",
        () -> new Elematilius(ElementsEnum.EARTH, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Item> ELEMENTAL_AIR = registerItem(
        "elemental_air",
        () -> new Elematilius(ElementsEnum.AIR, new Item.Properties().arch$tab(Tabs.MAIN))
    );
    public static final RegistrySupplier<Item> CROPARIA = registerItem(
        "croparia", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> CROPARIA2 = registerItem(
        "croparia2", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> CROPARIA3 = registerItem(
        "croparia3", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> CROPARIA4 = registerItem(
        "croparia4", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> CROPARIA5 = registerItem(
        "croparia5", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> CROPARIA6 = registerItem(
        "croparia6", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> CROPARIA7 = registerItem(
        "croparia7", () -> new Item(new Item.Properties()));

    public static void registerCrop(Crop crop) {
        ITEMS.register(crop.getSeedId(), () -> new CropSeed(crop));
        ITEMS.register(crop.getFruitId(), () -> new CropFruit(crop));
    }

    public static RegistrySupplier<Item> registerItem(String name, Supplier<Item> supplier) {
        return ITEMS.register(name, supplier);
    }

    public static void register() {
        ITEMS.register();
    }

    public static @Nullable RegistrySupplier<Item> getPotion(@NotNull ElementsEnum element) {
        return switch (element) {
            case WATER -> POTION_WATER;
            case FIRE -> POTION_FIRE;
            case EARTH -> POTION_EARTH;
            case AIR -> POTION_AIR;
            case ELEMENTAL -> POTION_ELEMATILIUS;
            case EMPTY -> null;
        };
    }

    public static @Nullable RegistrySupplier<Item> getElementilius(@NotNull ElementsEnum element) {
        return switch (element) {
            case WATER -> ELEMENTAL_WATER;
            case FIRE -> ELEMENTAL_FIRE;
            case EARTH -> ELEMENTAL_EARTH;
            case AIR -> ELEMENTAL_AIR;
            case ELEMENTAL -> ELEMATILIUS;
            case EMPTY -> null;
        };
    }
}
