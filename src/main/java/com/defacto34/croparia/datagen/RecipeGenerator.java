//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.datagen;

import com.defacto34.croparia.init.BlockInit;
import com.defacto34.croparia.init.CropInit;
import com.defacto34.croparia.init.ItemInit;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    public void generate(Consumer<RecipeJsonProvider> exporter) {
        CropInit.cropList.forEach((crop) -> {
            if (!CropInit.compatCropList.contains(crop) && crop.cropName != CropInit.ELEMENTAL.cropName) {
                ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, crop.seed).pattern("MSM").pattern("SES").pattern("MSM").input('S', Items.WHEAT_SEEDS).input('M', crop.material).input('E', (ItemConvertible)ItemInit.croparias.get(crop.tier - 1)).criterion(RecipeProvider.hasItem(Items.WHEAT_SEEDS), RecipeProvider.conditionsFromItem(Items.WHEAT_SEEDS)).criterion(RecipeProvider.hasItem(crop.material), RecipeProvider.conditionsFromItem(crop.material)).criterion(RecipeProvider.hasItem((ItemConvertible)ItemInit.croparias.get(crop.tier - 1)), RecipeProvider.conditionsFromItem((ItemConvertible)ItemInit.croparias.get(crop.tier - 1))).offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(crop.seed)));
            }

        });
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemInit.ELEMATILIUS).pattern("PIP").pattern("IRI").pattern("PIP").input('I', Items.DIAMOND).input('R', Items.EMERALD).input('P', Items.LAPIS_LAZULI).criterion(RecipeProvider.hasItem(Items.DIAMOND), RecipeProvider.conditionsFromItem(Items.DIAMOND)).criterion(RecipeProvider.hasItem(Items.EMERALD), RecipeProvider.conditionsFromItem(Items.EMERALD)).criterion(RecipeProvider.hasItem(Items.LAPIS_LAZULI), RecipeProvider.conditionsFromItem(Items.LAPIS_LAZULI)).offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ItemInit.ELEMATILIUS)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemInit.CROPARIA).pattern("PIP").pattern("IPI").pattern("PIP").input('P', Items.WHEAT_SEEDS).input('I', ItemInit.ELEMATILIUS).criterion(RecipeProvider.hasItem(Items.WHEAT_SEEDS), RecipeProvider.conditionsFromItem(Items.WHEAT_SEEDS)).criterion(RecipeProvider.hasItem(ItemInit.ELEMATILIUS), RecipeProvider.conditionsFromItem(ItemInit.ELEMATILIUS)).offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ItemInit.CROPARIA)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, CropInit.ELEMENTAL.seed).pattern("MSM").pattern("SES").pattern("MSM").input('S', Items.WHEAT_SEEDS).input('M', ItemInit.ELEMATILIUS).input('E', (ItemConvertible)ItemInit.croparias.get(CropInit.ELEMENTAL.tier - 1)).criterion(RecipeProvider.hasItem(Items.WHEAT_SEEDS), RecipeProvider.conditionsFromItem(Items.WHEAT_SEEDS)).criterion(RecipeProvider.hasItem(ItemInit.ELEMATILIUS), RecipeProvider.conditionsFromItem(ItemInit.ELEMATILIUS)).criterion(RecipeProvider.hasItem((ItemConvertible)ItemInit.croparias.get(CropInit.ELEMENTAL.tier - 1)), RecipeProvider.conditionsFromItem((ItemConvertible)ItemInit.croparias.get(CropInit.ELEMENTAL.tier - 1))).offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(CropInit.ELEMENTAL.seed)));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ItemInit.POTION_ELEMATILIUS, 1).input(Items.GLASS_BOTTLE).input(ItemInit.ELEMATILIUS).criterion(RecipeProvider.hasItem(ItemInit.ELEMATILIUS), RecipeProvider.conditionsFromItem(ItemInit.ELEMATILIUS)).criterion(RecipeProvider.hasItem(Items.GLASS_BOTTLE), RecipeProvider.conditionsFromItem(Items.GLASS_BOTTLE)).offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ItemInit.POTION_ELEMATILIUS)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BlockInit.GREENHOUSE).pattern("SGS").pattern("P P").pattern("P P").input('S', Items.STICK).input('G', Items.GLOWSTONE_DUST).input('P', Blocks.GLASS_PANE).criterion(RecipeProvider.hasItem(Items.STICK), RecipeProvider.conditionsFromItem(Items.STICK)).criterion(RecipeProvider.hasItem(Items.GLOWSTONE_DUST), RecipeProvider.conditionsFromItem(Items.GLOWSTONE_DUST)).criterion(RecipeProvider.hasItem(Blocks.GLASS_PANE), RecipeProvider.conditionsFromItem(Blocks.GLASS_PANE)).offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(BlockInit.GREENHOUSE)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BlockInit.INFUSOR).pattern("P P").pattern("PPP").input('P', Blocks.STONE_BRICKS).criterion(RecipeProvider.hasItem(Blocks.STONE_BRICKS), RecipeProvider.conditionsFromItem(Blocks.STONE_BRICKS)).offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(BlockInit.INFUSOR)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BlockInit.RITUAL_STAND).pattern("BBB").pattern("P P").input('P', Items.IRON_INGOT).input('B', Blocks.IRON_BLOCK).criterion(RecipeProvider.hasItem(Items.IRON_INGOT), RecipeProvider.conditionsFromItem(Items.IRON_INGOT)).criterion(RecipeProvider.hasItem(Blocks.IRON_BLOCK), RecipeProvider.conditionsFromItem(Blocks.IRON_BLOCK)).offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(BlockInit.RITUAL_STAND)));
    }
}
