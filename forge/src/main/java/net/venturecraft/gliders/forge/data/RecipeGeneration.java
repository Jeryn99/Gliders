package net.venturecraft.gliders.forge.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.venturecraft.gliders.common.item.ItemRegistry;

import java.util.function.Consumer;

public class RecipeGeneration extends RecipeProvider {

    public RecipeGeneration(DataGenerator arg) {
        super(arg.getPackOutput());
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // Inforced Paper
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.REINFORCED_PAPER.get()).pattern("LPL").pattern("PLP").pattern("LPL").define('L', Items.LEATHER).define('P', Items.PAPER).group("gliders").unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.REINFORCED_PAPER_IRON.get()).pattern("III").pattern("IPI").pattern("III").define('I', Items.IRON_INGOT).define('P', ItemRegistry.REINFORCED_PAPER.get()).group("gliders").unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.REINFORCED_PAPER_GOLD.get()).pattern("GGG").pattern("GPG").pattern("GGG").define('G', Items.GOLD_INGOT).define('P', ItemRegistry.REINFORCED_PAPER.get()).group("gliders").unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.REINFORCED_PAPER_DIAMOND.get()).pattern("DDD").pattern("DPD").pattern("DDD").define('D', Items.DIAMOND).define('P', ItemRegistry.REINFORCED_PAPER.get()).group("gliders").unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.REINFORCED_PAPER_NETHERITE.get()).pattern("NNN").pattern("NPN").pattern("NNN").define('N', Items.NETHERITE_SCRAP).define('P', ItemRegistry.REINFORCED_PAPER.get()).group("gliders").unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE)).save(consumer);

        // Gliders
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ItemRegistry.PARAGLIDER_WOOD.get()).pattern("RRR").pattern("SWS").pattern("WOW").define('O', Items.FEATHER).define('R', ItemRegistry.REINFORCED_PAPER.get()).define('W', Items.STICK).define('S', Items.STRING).group("gliders").unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ItemRegistry.PARAGLIDER_IRON.get()).pattern("RRR").pattern("SWS").pattern("WOW").define('O', Items.FEATHER).define('R', ItemRegistry.REINFORCED_PAPER_IRON.get()).define('W', Items.STICK).define('S', Items.STRING).group("gliders").unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ItemRegistry.PARAGLIDER_GOLD.get()).pattern("RRR").pattern("SWS").pattern("WOW").define('O', Items.FEATHER).define('R', ItemRegistry.REINFORCED_PAPER_GOLD.get()).define('W', Items.STICK).define('S', Items.STRING).group("gliders").unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ItemRegistry.PARAGLIDER_DIAMOND.get()).pattern("RRR").pattern("SWS").pattern("WOW").define('O', Items.FEATHER).define('R', ItemRegistry.REINFORCED_PAPER_DIAMOND.get()).define('W', Items.STICK).define('S', Items.STRING).group("gliders").unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ItemRegistry.PARAGLIDER_NETHERITE.get()).pattern("RRR").pattern("SWS").pattern("WOW").define('O', Items.FEATHER).define('R', ItemRegistry.REINFORCED_PAPER_NETHERITE.get()).define('W', Items.STICK).define('S', Items.STRING).group("gliders").unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE)).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.NETHER_UPGRADE.get()).pattern("   ").pattern("RBR").pattern("FUF").define('R', Items.BLAZE_ROD).define('B', Items.BLAZE_POWDER).define('U', Items.CRYING_OBSIDIAN).define('F', Blocks.NETHER_BRICK_FENCE).unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.COPPER_UPGRADE.get()).pattern("CCC").pattern("SPS").pattern("HCH").define('C', Blocks.COPPER_BLOCK).define('S', Items.STRING).define('P', Blocks.LIGHTNING_ROD).define('H', Items.AMETHYST_SHARD).unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE)).save(consumer);

        // Glider Upgrading
       /* new GliderUpgradingRecipeBuilder(Ingredient.of(ItemRegistry.PARAGLIDER_WOOD.get()), Ingredient.of(ItemRegistry.COPPER_UPGRADE.get()), "copper").unlocks(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT)).save(consumer, VCGliders.id("wood_glider_copper_upgrade"));
        new GliderUpgradingRecipeBuilder(Ingredient.of(ItemRegistry.PARAGLIDER_IRON.get()), Ingredient.of(ItemRegistry.COPPER_UPGRADE.get()), "copper").unlocks(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT)).save(consumer, VCGliders.id("iron_glider_copper_upgrade"));
        new GliderUpgradingRecipeBuilder(Ingredient.of(ItemRegistry.PARAGLIDER_GOLD.get()), Ingredient.of(ItemRegistry.COPPER_UPGRADE.get()), "copper").unlocks(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT)).save(consumer, VCGliders.id("gold_glider_copper_upgrade"));
        new GliderUpgradingRecipeBuilder(Ingredient.of(ItemRegistry.PARAGLIDER_DIAMOND.get()), Ingredient.of(ItemRegistry.COPPER_UPGRADE.get()), "copper").unlocks(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT)).save(consumer, VCGliders.id("diamond_glider_copper_upgrade"));
        new GliderUpgradingRecipeBuilder(Ingredient.of(ItemRegistry.PARAGLIDER_NETHERITE.get()), Ingredient.of(ItemRegistry.COPPER_UPGRADE.get()), "copper").unlocks(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT)).save(consumer, VCGliders.id("netherite_glider_copper_upgrade"));
        new GliderUpgradingRecipeBuilder(Ingredient.of(ItemRegistry.PARAGLIDER_WOOD.get()), Ingredient.of(ItemRegistry.NETHER_UPGRADE.get()), "nether").unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT)).save(consumer, VCGliders.id("wood_glider_nether_upgrade"));
        new GliderUpgradingRecipeBuilder(Ingredient.of(ItemRegistry.PARAGLIDER_IRON.get()), Ingredient.of(ItemRegistry.NETHER_UPGRADE.get()), "nether").unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT)).save(consumer, VCGliders.id("iron_glider_nether_upgrade"));
        new GliderUpgradingRecipeBuilder(Ingredient.of(ItemRegistry.PARAGLIDER_GOLD.get()), Ingredient.of(ItemRegistry.NETHER_UPGRADE.get()), "nether").unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT)).save(consumer, VCGliders.id("gold_glider_nether_upgrade"));
        new GliderUpgradingRecipeBuilder(Ingredient.of(ItemRegistry.PARAGLIDER_DIAMOND.get()), Ingredient.of(ItemRegistry.NETHER_UPGRADE.get()), "nether").unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT)).save(consumer, VCGliders.id("diamond_glider_nether_upgrade"));
        new GliderUpgradingRecipeBuilder(Ingredient.of(ItemRegistry.PARAGLIDER_NETHERITE.get()), Ingredient.of(ItemRegistry.NETHER_UPGRADE.get()), "nether").unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT)).save(consumer, VCGliders.id("netherite_glider_nether_upgrade"));
*/
    }

}
