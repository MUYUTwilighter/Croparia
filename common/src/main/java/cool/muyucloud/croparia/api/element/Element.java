package cool.muyucloud.croparia.api.element;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.util.Color;
import cool.muyucloud.croparia.api.element.block.ElementalLiquidBlock;
import cool.muyucloud.croparia.api.element.fluid.ElementalFlowing;
import cool.muyucloud.croparia.api.element.fluid.ElementalSource;
import cool.muyucloud.croparia.api.element.item.ElementalBucket;
import cool.muyucloud.croparia.api.element.item.ElementalGem;
import cool.muyucloud.croparia.api.element.item.ElementalPotion;
import cool.muyucloud.croparia.api.generator.util.DgElement;
import cool.muyucloud.croparia.api.generator.util.Placeholder;
import cool.muyucloud.croparia.registry.Tabs;
import cool.muyucloud.croparia.util.Util;
import cool.muyucloud.croparia.util.codec.CodecUtil;
import cool.muyucloud.croparia.util.supplier.HolderSupplier;
import cool.muyucloud.croparia.util.supplier.LazySupplier;
import cool.muyucloud.croparia.util.supplier.SemiSupplier;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

    public static final Placeholder<Element> NAME = Placeholder.of("\\{name}", Element::getSerializedName);
    public static final Placeholder<Element> COLOR = Placeholder.of("\\{color}", element -> element.getColor().toDecString());
    public static final Placeholder<Element> FLUID_SOURCE = Placeholder.of(
        "\\{fluid_source}", element -> element.getFluidSource().getId().toString()
    );
    public static final Placeholder<Element> FLUID_SOURCE_PATH = Placeholder.of(
        "\\{fluid_source_path}", element -> element.getFluidSource().getId().getPath()
    );
    public static final Placeholder<Element> FLUID_FLOWING = Placeholder.of(
        "\\{fluid_flowing}", element -> element.getFluidFlowing().getId().toString()
    );
    public static final Placeholder<Element> FLUID_FLOWING_PATH = Placeholder.of(
        "\\{fluid_flowing_path}", element -> element.getFluidFlowing().getId().getPath()
    );
    public static final Placeholder<Element> LIQUID_BLOCK = Placeholder.of(
        "\\{liquid_block}", element -> element.getFluidBlock().getId().toString()
    );
    public static final Placeholder<Element> LIQUID_BLOCK_PATH = Placeholder.of(
        "\\{liquid_block_path}", element -> element.getFluidBlock().getId().getPath()
    );
    public static final Placeholder<Element> BUCKET = Placeholder.of(
        "\\{bucket}", element -> element.getBucket().getId().toString()
    );
    public static final Placeholder<Element> BUCKET_PATH = Placeholder.of(
        "\\{bucket_path}", element -> element.getBucket().getId().getPath()
    );
    public static final Placeholder<Element> POTION = Placeholder.of(
        "\\{potion}", element -> element.getPotion().getId().toString()
    );
    public static final Placeholder<Element> POTION_PATH = Placeholder.of(
        "\\{potion_path}", element -> element.getPotion().getId().getPath()
    );
    public static final Placeholder<Element> GEM = Placeholder.of(
        "\\{gem}", element -> element.getGem().getId().toString()
    );
    public static final Placeholder<Element> GEM_PATH = Placeholder.of(
        "\\{gem_path}", element -> element.getGem().getId().getPath()
    );

    private final ResourceLocation id;
    private final Color color;
    private final HolderSupplier<ElementalSource> fluidSource;
    private final HolderSupplier<ElementalFlowing> fluidFlowing;
    private final HolderSupplier<ElementalLiquidBlock> fluidBlock;
    private final HolderSupplier<ElementalBucket> bucket;
    private final HolderSupplier<ElementalPotion> potion;
    private final HolderSupplier<ElementalGem> gem;
    private final transient LazySupplier<Collection<Placeholder<? extends DgElement>>> placeholders = LazySupplier.of(() -> {
        ArrayList<Placeholder<? extends DgElement>> list = new ArrayList<>();
        this.buildPlaceholders(list);
        return ImmutableList.copyOf(list);
    });

    private Element() {
        this.id = CropariaIf.of("empty");
        this.color = new Color(-1);
        this.fluidSource = HolderSupplier.of(() -> null, CropariaIf.of("fluid_empty"), Registries.FLUID);
        this.fluidFlowing = HolderSupplier.of(() -> null, CropariaIf.of("fluid_empty_flow"), Registries.FLUID);
        this.fluidBlock = HolderSupplier.of(() -> null, CropariaIf.of("fluid_empty"), Registries.BLOCK);
        this.bucket = HolderSupplier.of(() -> null, CropariaIf.of("bucket_empty"), Registries.ITEM);
        this.potion = HolderSupplier.of(() -> null, CropariaIf.of("potion_empty"), Registries.ITEM);
        this.gem = HolderSupplier.of(() -> null, CropariaIf.of("gem_empty"), Registries.ITEM);
        REGISTRY.put(this.getKey(), this);
        STRING_REGISTRY.refresh();
    }

    @SuppressWarnings("UnstableApiUsage")
    public Element(ResourceLocation id, Color color, Consumer<SimpleArchitecturyFluidAttributes> appendix) {
        this.id = id;
        this.color = color;
        SimpleArchitecturyFluidAttributes attr = SimpleArchitecturyFluidAttributes
            .of(() -> Element.this.getFluidFlowing().get(), () -> Element.this.getFluidSource().get())
            .block(() -> Optional.ofNullable(Element.this.getFluidBlock().get()))
            .bucketItem(() -> Optional.ofNullable(Element.this.getBucket().get()))
            .sourceTexture(parseId("block/%s_still"))
            .flowingTexture(parseId("block/%s_flow"));
        appendix.accept(attr);
        this.fluidSource = HolderSupplier.of(() -> new ElementalSource(this, attr), parseId("fluid_%s"), Registries.FLUID);
        this.fluidFlowing = HolderSupplier.of(() -> new ElementalFlowing(this, attr), parseId("fluid_%s_flow"), Registries.FLUID);
        this.fluidBlock = HolderSupplier.of(() -> new ElementalLiquidBlock(
            this, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).lightLevel(state -> 8)
            .setId(ResourceKey.create(Registries.BLOCK, parseId("fluid_%s")))
        ), parseId("fluid_%s"), Registries.BLOCK);
        this.bucket = HolderSupplier.of(() -> new ElementalBucket(this, this.getFluidSource(), new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, parseId("bucket_%s")))
            .arch$tab(Tabs.MAIN).craftRemainder(Items.GLASS_BOTTLE)), parseId("bucket_%s"), Registries.ITEM);
        this.potion = HolderSupplier.of(() -> new ElementalPotion(this, new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, parseId("potion_%s")))
            .arch$tab(Tabs.MAIN).craftRemainder(Items.GLASS_BOTTLE)), parseId("potion_%s"), Registries.ITEM);
        this.gem = HolderSupplier.of(() -> new ElementalGem(this, new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, parseId("gem_%s")))
            .arch$tab(Tabs.MAIN).craftRemainder(Items.GLASS_BOTTLE)), parseId("gem_%s"), Registries.ITEM);
        this.getFluidSource().tryRegister();
        this.getFluidFlowing().tryRegister();
        this.getFluidBlock().tryRegister();
        this.getBucket().tryRegister();
        this.getPotion().tryRegister();
        this.getGem().tryRegister();
        REGISTRY.put(this.getKey(), this);
        STRING_REGISTRY.refresh();
    }

    public ResourceLocation parseId(String pattern) {
        return Util.formatId(pattern, this.getKey());
    }

    public Color getColor() {
        return color;
    }

    public HolderSupplier<ElementalFlowing> getFluidFlowing() {
        return fluidFlowing;
    }

    public HolderSupplier<ElementalSource> getFluidSource() {
        return fluidSource;
    }

    public HolderSupplier<ElementalLiquidBlock> getFluidBlock() {
        return fluidBlock;
    }

    public HolderSupplier<ElementalBucket> getBucket() {
        return bucket;
    }

    public HolderSupplier<ElementalPotion> getPotion() {
        return potion;
    }

    public HolderSupplier<ElementalGem> getGem() {
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
    public void buildPlaceholders(Collection<Placeholder<? extends DgElement>> list) {
        DgElement.super.buildPlaceholders(list);
        list.add(NAME);
        list.add(COLOR);
        list.add(FLUID_SOURCE);
        list.add(FLUID_SOURCE_PATH);
        list.add(FLUID_FLOWING);
        list.add(FLUID_FLOWING_PATH);
        list.add(LIQUID_BLOCK);
        list.add(LIQUID_BLOCK_PATH);
        list.add(BUCKET);
        list.add(BUCKET_PATH);
        list.add(POTION);
        list.add(POTION_PATH);
        list.add(GEM);
        list.add(GEM_PATH);
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
