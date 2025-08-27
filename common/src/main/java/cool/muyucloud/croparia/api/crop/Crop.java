package cool.muyucloud.croparia.api.crop;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.block.CropariaCropBlock;
import cool.muyucloud.croparia.api.crop.item.CropFruit;
import cool.muyucloud.croparia.api.crop.item.CropSeed;
import cool.muyucloud.croparia.api.crop.util.Color;
import cool.muyucloud.croparia.api.crop.util.CropDependencies;
import cool.muyucloud.croparia.api.crop.util.Material;
import cool.muyucloud.croparia.api.crop.util.TierAccess;
import cool.muyucloud.croparia.api.generator.util.DgElement;
import cool.muyucloud.croparia.api.generator.util.Placeholder;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.Util;
import cool.muyucloud.croparia.util.codec.AnyCodec;
import cool.muyucloud.croparia.util.supplier.HolderSupplier;
import cool.muyucloud.croparia.util.supplier.LazySupplier;
import cool.muyucloud.croparia.util.supplier.OnLoadSupplier;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SuppressWarnings("unused")
public class Crop extends AbstractCrop implements TierAccess {
    public static final Set<String> PRESET_TYPES = new HashSet<>();
    public static final String ANIMAL = addType("animal");
    public static final String CROP = addType("crop");
    public static final String FOOD = addType("food");
    public static final String MONSTER = addType("monster");
    public static final String NATURE = addType("nature");
    public static final String DEFAULT_TYPE = CROP;

    public static String addType(String type) {
        PRESET_TYPES.add(type);
        return type;
    }

    public static final MapCodec<Crop> CODEC_NEW = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("id").forGetter(Crop::getKey),
        Material.CODEC.fieldOf("material").forGetter(Crop::getMaterial),
        Color.CODEC.fieldOf("color").forGetter(Crop::getColor),
        Codec.INT.fieldOf("tier").forGetter(Crop::getTier),
        Codec.STRING.optionalFieldOf("type").forGetter(Crop::getTypeOptional),
        Codec.unboundedMap(Codec.STRING, Codec.STRING).optionalFieldOf("translations").forGetter(Crop::getTranslationsOptional),
        CropDependencies.CODEC_ANY.optionalFieldOf("dependencies").forGetter(Crop::getDependenciesOptional)
    ).apply(instance, (name, material, color, tier, type, translations, dependencies) -> new Crop(name, material, color, tier,
        type.orElse(null), translations.orElse(null), dependencies.orElse(null))
    ));
    public static final MapCodec<Crop> CODEC_OLD = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("name").forGetter(Crop::getKey),
        Codec.STRING.fieldOf("tag").forGetter(Crop::getMaterialName),
        Color.CODEC.fieldOf("color").forGetter(Crop::getColor),
        Codec.INT.fieldOf("tier").forGetter(Crop::getTier),
        Codec.STRING.optionalFieldOf("type").forGetter(Crop::getTypeOptional),
        Codec.unboundedMap(Codec.STRING, Codec.STRING).optionalFieldOf("translations").forGetter(Crop::getTranslationsOptional),
        CropDependencies.CODEC_ANY.optionalFieldOf("dependencies").forGetter(Crop::getDependenciesOptional)
    ).apply(instance,
        (name, material, color, tier, type, translations, dependencies)
            -> new Crop(name, new Material("#" + material), color, tier, type.orElse(DEFAULT_TYPE),
            translations.orElse(null), dependencies.orElse(null))
    ));
    public static final AnyCodec<Crop> CODEC = new AnyCodec<>(CODEC_NEW.codec(), CODEC_OLD.codec());

    public static final Placeholder<Crop> COLOR = Placeholder.of("\\{color}", Crop::getColorDec);
    public static final Placeholder<Crop> COLOR_HEX = Placeholder.of("\\{color_hex}", Crop::getColorHex);
    public static final Placeholder<Crop> TYPE = Placeholder.of("\\{type}", Crop::getType);
    public static final Placeholder<Crop> TIER = Placeholder.of("\\{tier}", crop -> String.valueOf(crop.getTier()));
    public static final Placeholder<Crop> SEED = Placeholder.of("\\{seed}", crop -> crop.getSeedId().toString());
    public static final Placeholder<Crop> SEED_PATH = Placeholder.of("\\{seed_path}", crop -> crop.getSeedId().getPath());
    public static final Placeholder<Crop> FRUIT = Placeholder.of("\\{fruit}", crop -> crop.getFruitId().toString());
    public static final Placeholder<Crop> FRUIT_PATH = Placeholder.of("\\{fruit_path}", crop -> crop.getFruitId().getPath());
    public static final Placeholder<Crop> CROP_BLOCK = Placeholder.of("\\{crop_block}", crop -> crop.getBlockId().toString());
    public static final Placeholder<Crop> CROP_BLOCK_PATH = Placeholder.of("\\{crop_block_path}", crop -> crop.getBlockId().getPath());
    public static final Placeholder<Crop> RESULT = Placeholder.of("\\{result}", crop -> Objects.requireNonNull(crop.getResult().arch$registryName()).toString());
    public static final Placeholder<Crop> RESULT_PATH = Placeholder.of("\\{result_path}", crop -> Objects.requireNonNull(crop.getResult().arch$registryName()).getPath());
    public static final Placeholder<Crop> RESULT_COUNT = Placeholder.of("\\{result_count\\.(\\d+)}", ((matcher, crop) -> {
        int count = Integer.parseInt(matcher.group(1));
        return String.valueOf(Math.min(crop.getResult().getDefaultMaxStackSize(), count));
    }));
    public static final Placeholder<Crop> CROPARIA = Placeholder.of("\\{croparia}", crop -> CropariaItems.getCroparia(crop.getTier()).getId().toString());
    public static final Placeholder<Crop> CROPARIA_PATH = Placeholder.of("\\{croparia_path}", crop -> CropariaItems.getCroparia(crop.getTier()).getId().getPath());

    public static String defaultTranslation(ResourceLocation id) {
        String name = id.getPath();
        name = name.replaceAll("_", " ").trim();
        StringBuilder builder = new StringBuilder();
        for (String token : name.split(" ")) {
            builder.append(Character.toUpperCase(token.charAt(0))).append(token.substring(1)).append(" ");
        }
        return builder.toString().trim();
    }

    public static String defaultTranslationKey(ResourceLocation id) {
        return "crop.%s.%s".formatted(id.getNamespace(), id.getPath());
    }

    @NotNull
    private final ResourceLocation id;
    @NotNull
    private final Material material;
    @NotNull
    private final Color color;
    private final int tier;
    @NotNull
    private final String type;
    @NotNull
    private final ImmutableMap<String, String> translations;
    @NotNull
    private final CropDependencies dependencies;
    @NotNull
    private final transient HolderSupplier<CropariaCropBlock> block;
    @NotNull
    private final transient HolderSupplier<CropSeed> seed;
    @NotNull
    private final transient HolderSupplier<CropFruit> fruit;
    private transient final String defaultTranslation;
    private transient final String defaultTranslationKey;
    private transient final LazySupplier<Boolean> load = LazySupplier.of(
        () -> this.getDependencies().shouldLoad() && CropariaIf.CONFIG.isCropValid(this.getKey())
    );
    private transient final OnLoadSupplier<List<Item>> items = OnLoadSupplier.of(() -> {
        List<Item> items = new ArrayList<>();
        for (Item item : this.getMaterial().getItems()) {
            if (CropariaIf.CONFIG.isModValid(Objects.requireNonNull(item.arch$registryName()).getNamespace())) {
                items.add(item);
            }
        }
        if (items.isEmpty()) items.add(Items.AIR);
        return ImmutableList.copyOf(items);
    });

    public Crop(
        @NotNull ResourceLocation id, @NotNull Material material, @NotNull Color color, int tier, @Nullable String type,
        @Nullable Map<String, String> translations, @Nullable CropDependencies dependencies
    ) {
        this.id = id;
        this.material = material;
        this.color = color;
        this.tier = tier;
        this.defaultTranslationKey = defaultTranslationKey(id);
        this.dependencies = dependencies == null || dependencies.isEmpty() ? new CropDependencies(CropariaIf.MOD_ID, this.getDefaultTranslationKey()) : dependencies;
        this.defaultTranslation = defaultTranslation(id);
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        this.translations = builder.put("en_us", this.getDefaultTranslation()).putAll(translations == null ? Collections.emptyMap() : translations).build();
        this.type = type == null ? DEFAULT_TYPE : type;
        this.block = HolderSupplier.of(() -> new CropariaCropBlock(this), Util.formatId("block_crop_%s", this.getKey()), Registries.BLOCK);
        this.seed = HolderSupplier.of(() -> new CropSeed(this), Util.formatId("crop_seed_%s", this.getKey()), Registries.ITEM);
        this.fruit = HolderSupplier.of(() -> new CropFruit(this), Util.formatId("fruit_%s", this.getKey()), Registries.ITEM);
    }

    @Override
    public @NotNull ResourceLocation getKey() {
        return this.id;
    }

    @Override
    public @NotNull Material getMaterial() {
        return material;
    }

    public DataComponentPatch getPatch() {
        return this.getMaterial().getComponents();
    }

    public @NotNull Color getColor() {
        return color;
    }

    public int getColorInt() {
        return this.getColor().getValue();
    }

    public String getColorDec() {
        return this.getColor().toDecString();
    }

    public String getColorHex() {
        return this.getColor().toHexString();
    }

    public String getColorForm() {
        return this.getColor().toString();
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public Collection<String> getLangs() {
        return this.getTranslations().keySet();
    }

    public @NotNull String getTranslationKey() {
        return dependencies.getKey();
    }

    public Optional<String> getTranslationKeyOptional() {
        if (this.getTranslationKey().equals(defaultTranslationKey)) {
            return Optional.empty();
        } else {
            return Optional.of(this.getTranslationKey());
        }
    }

    public @NotNull ImmutableMap<String, String> getTranslations() {
        return translations;
    }

    @Override
    @Nullable
    public String translate(String lang) {
        return this.getTranslations().get(lang);
    }

    public Optional<Map<String, String>> getTranslationsOptional() {
        Map<String, String> translations = this.getTranslations();
        if (translations.size() == 1 && translations.containsValue(this.getDefaultTranslationKey())) {
            return Optional.empty();
        } else {
            return Optional.of(translations);
        }
    }

    public @NotNull String getType() {
        return type;
    }

    public Optional<String> getTypeOptional() {
        return this.getType().equals(DEFAULT_TYPE) ? Optional.empty() : Optional.of(this.getType());
    }

    public @NotNull CropDependencies getDependencies() {
        return this.dependencies;
    }

    public Optional<CropDependencies> getDependenciesOptional() {
        return this.getDependencies().size() <= 1 && this.getDependencies().getKey(CropariaIf.MOD_ID) != null ? Optional.empty() :
            Optional.of(this.getDependencies());
    }

    public @NotNull ResourceLocation getBlockId() {
        return block.getId();
    }

    public Optional<CropariaCropBlock> getCropBlock() {
        return block.toOptional();
    }

    public @NotNull ResourceLocation getSeedId() {
        return seed.getId();
    }

    public Optional<CropSeed> getCropSeed() {
        return seed.toOptional();
    }

    public @NotNull ResourceLocation getFruitId() {
        return fruit.getId();
    }

    public Optional<CropFruit> getCropFruit() {
        return fruit.toOptional();
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public String getDefaultTranslationKey() {
        return defaultTranslationKey;
    }

    @Override
    public void buildPlaceholders(Collection<Placeholder<? extends DgElement>> list) {
        super.buildPlaceholders(list);
        list.add(COLOR);
        list.add(COLOR_HEX);
        list.add(CROPARIA);
        list.add(CROPARIA_PATH);
        list.add(CROP_BLOCK);
        list.add(CROP_BLOCK_PATH);
        list.add(FRUIT);
        list.add(FRUIT_PATH);
        list.add(RESULT);
        list.add(RESULT_COUNT);
        list.add(RESULT_PATH);
        list.add(SEED);
        list.add(SEED_PATH);
        list.add(TYPE);
        list.add(TIER);
    }

    @Override
    public boolean shouldLoad() {
        return load.get();
    }

    public Item getResult() {
        return this.getResults().getFirst();
    }

    public List<Item> getResults() {
        return items.get();
    }

    @Override
    public void onRegister() {
        this.block.tryRegister();
        this.seed.tryRegister();
        this.fruit.tryRegister();
    }
}
