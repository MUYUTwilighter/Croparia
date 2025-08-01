package cool.muyucloud.croparia.api.element;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.element.block.ElementalLiquidBlock;
import cool.muyucloud.croparia.api.element.fluid.ElementalFlowing;
import cool.muyucloud.croparia.api.element.fluid.ElementalSource;
import cool.muyucloud.croparia.api.element.item.ElementalBucket;
import cool.muyucloud.croparia.api.element.item.ElementalGem;
import cool.muyucloud.croparia.api.element.item.ElementalPotion;
import cool.muyucloud.croparia.api.generator.util.DgElement;
import cool.muyucloud.croparia.api.generator.util.Placeholder;
import cool.muyucloud.croparia.registry.Tabs;
import cool.muyucloud.croparia.util.CodecUtil;
import cool.muyucloud.croparia.util.Util;
import cool.muyucloud.croparia.util.supplier.LazySupplier;
import cool.muyucloud.croparia.util.supplier.SemiSupplier;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Element implements StringRepresentable, Comparable<Element>, DgElement {
    public static final Codec<Element> CODEC = ResourceLocation.CODEC.xmap(Element::valueOf, Element::getKey);
    public static final StreamCodec<RegistryFriendlyByteBuf, Element> STREAM_CODEC = CodecUtil.toStream(CODEC);
    public static final Map<ResourceLocation, Element> REGISTRY = new HashMap<>();
    public static final SemiSupplier<BiMap<String, Element>> STRING_REGISTRY = SemiSupplier.of(() -> {
        BiMap<String, Element> map = HashBiMap.create();
        REGISTRY.forEach((key, value) -> map.put(value.getSerializedName(), value));
        return Maps.unmodifiableBiMap(map);
    });

    public static final Element EMPTY = new Element();

    public static Element valueOf(ResourceLocation id) {
        return REGISTRY.getOrDefault(id, EMPTY);
    }

    public static Collection<Element> values() {
        return REGISTRY.values();
    }

    public static Collection<ResourceLocation> names() {
        return REGISTRY.keySet();
    }

    public static void forEach(BiConsumer<ResourceLocation, Element> consumer) {
        REGISTRY.forEach(consumer);
    }

    public static final Placeholder<Element> FLUID_SOURCE = Placeholder.of(
        "\\{fluid_source}", element -> element.getFluidSource().getId().toString()
    );
    public static final Placeholder<Element> FLUID_FLOWING = Placeholder.of(
        "\\{fluid_flowing}", element -> element.getFluidFlowing().getId().toString()
    );
    public static final Placeholder<Element> FLUID_BLOCK = Placeholder.of(
        "\\{fluid_block}", element -> element.getFluidBlock().getId().toString()
    );
    public static final Placeholder<Element> BUCKET = Placeholder.of(
        "\\{bucket}", element -> element.getBucket().getId().toString()
    );
    public static final Placeholder<Element> POTION = Placeholder.of(
        "\\{potion}", element -> element.getPotion().getId().toString()
    );
    public static final Placeholder<Element> GEM = Placeholder.of(
        "\\{gem}", element -> element.getGem().getId().toString()
    );

    private final ResourceLocation id;
    private final RegistrySupplier<ElementalSource> fluidSource;
    private final RegistrySupplier<ElementalFlowing> fluidFlowing;
    private final RegistrySupplier<ElementalLiquidBlock> fluidBlock;
    private final RegistrySupplier<ElementalBucket> bucket;
    private final RegistrySupplier<ElementalPotion> potion;
    private final RegistrySupplier<ElementalGem> gem;
    private final transient LazySupplier<Collection<Placeholder<? extends DgElement>>> placeholders = LazySupplier.of(() -> {
        Collection<Placeholder<? extends DgElement>> set = new HashSet<>();
        this.buildPlaceholders(set);
        return set;
    });

    private Element() {
        this.id = CropariaIf.of("empty");
        DeferredRegister<Fluid> fluids = DeferredRegister.create(CropariaIf.MOD_ID, Registries.FLUID);
        DeferredRegister<Item> items = DeferredRegister.create(CropariaIf.MOD_ID, Registries.ITEM);
        DeferredRegister<Block> blocks = DeferredRegister.create(CropariaIf.MOD_ID, Registries.BLOCK);
        this.fluidSource = fluids.register("empty", () -> null);
        this.fluidFlowing = fluids.register("empty_flowing", () -> null);
        this.fluidBlock = blocks.register("empty", () -> null);
        this.bucket = items.register("empty_bucket", () -> null);
        this.potion = items.register("empty_potion", () -> null);
        this.gem = items.register("empty_gem", () -> null);
        REGISTRY.put(this.getKey(), this);
        STRING_REGISTRY.refresh();
    }

    @SuppressWarnings("UnstableApiUsage")
    public Element(ResourceLocation id, Consumer<SimpleArchitecturyFluidAttributes> appendix) {
        this.id = id;
        DeferredRegister<Fluid> fluids = DeferredRegister.create(id.getNamespace(), Registries.FLUID);
        DeferredRegister<Item> items = DeferredRegister.create(id.getNamespace(), Registries.ITEM);
        DeferredRegister<Block> blocks = DeferredRegister.create(id.getNamespace(), Registries.BLOCK);
        SimpleArchitecturyFluidAttributes attr = SimpleArchitecturyFluidAttributes
            .of(() -> Element.this.getFluidSource().get(), () -> Element.this.getFluidFlowing().get())
            .block(() -> Optional.ofNullable(Element.this.getFluidBlock().get()))
            .bucketItem(() -> Optional.ofNullable(Element.this.getBucket().get()))
            .sourceTexture(parseId("block/%s_still"))
            .flowingTexture(parseId("block/%s_flow"));
        appendix.accept(attr);
        this.fluidSource = fluids.register(parseId("fluid_%s"), () -> new ElementalSource(this, attr));
        this.fluidFlowing = fluids.register(parseId("fluid_%s_flow"), () -> new ElementalFlowing(this, attr));
        this.fluidBlock = blocks.register(parseId("fluid_%s"), () -> new ElementalLiquidBlock(
            this, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).lightLevel(state -> 8)
            .setId(ResourceKey.create(Registries.BLOCK, parseId("fluid_%s")))
        ));
        this.potion = items.register(parseId("potion_"), () -> new ElementalPotion(this, new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, parseId("potion_")))
            .arch$tab(Tabs.MAIN).craftRemainder(Items.GLASS_BOTTLE)));
        this.bucket = items.register(parseId("%s_bucket"),
            () -> new ElementalBucket(this, this.getFluidSource(), new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, parseId("%s_bucket")))
                .arch$tab(Tabs.MAIN).stacksTo(1).craftRemainder(Items.BUCKET)));
        this.gem = items.register(parseId("gem_%s"), () -> new ElementalGem(this, new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, parseId("gem_%s")))
            .arch$tab(Tabs.MAIN)));
        fluids.register();
        blocks.register();
        items.register();
        REGISTRY.put(this.getKey(), this);
    }

    public ResourceLocation parseId(String pattern) {
        return Util.formatId(pattern, this.getKey());
    }

    public RegistrySupplier<ElementalFlowing> getFluidFlowing() {
        return fluidFlowing;
    }

    public RegistrySupplier<ElementalSource> getFluidSource() {
        return fluidSource;
    }

    public RegistrySupplier<ElementalLiquidBlock> getFluidBlock() {
        return fluidBlock;
    }

    public RegistrySupplier<ElementalBucket> getBucket() {
        return bucket;
    }

    public RegistrySupplier<ElementalPotion> getPotion() {
        return potion;
    }

    public RegistrySupplier<ElementalGem> getGem() {
        return gem;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.getKey().toString().replaceAll("[./:]", "_");
    }

    @Override
    public @NotNull ResourceLocation getKey() {
        return this.id;
    }

    @Override
    public Collection<Placeholder<? extends DgElement>> placeholders() {
        return this.placeholders.get();
    }

    @Override
    public void buildPlaceholders(Collection<Placeholder<?>> set) {
        DgElement.super.buildPlaceholders(set);
        set.add(FLUID_SOURCE);
        set.add(FLUID_FLOWING);
        set.add(FLUID_BLOCK);
        set.add(BUCKET);
        set.add(POTION);
        set.add(GEM);
    }

    @Override
    public boolean shouldLoad() {
        return this != EMPTY;
    }

    @Override
    public int compareTo(@NotNull Element o) {
        return this.getKey().compareTo(o.getKey());
    }
}
