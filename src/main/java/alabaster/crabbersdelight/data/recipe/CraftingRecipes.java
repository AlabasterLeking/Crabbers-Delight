package alabaster.crabbersdelight.data.recipe;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.registry.CDModItems;
import alabaster.crabbersdelight.common.recipe.BottledNoteRecipe;
import alabaster.crabbersdelight.common.recipe.NoteCloningRecipe;
import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.common.crafting.FoodServingRecipe;
import vectorwing.farmersdelight.common.registry.ModItems;

import static alabaster.crabbersdelight.common.tags.CDModTags.*;

public class CraftingRecipes {
    public static void register(RecipeOutput output) {
        recipesTools(output);
        recipesPalmWood(output);
        recipesBlocks(output);
        recipesMaterials(output);
        recipesCraftedMeals(output);
        recipesNotes(output);
        SpecialRecipeBuilder.special(FoodServingRecipe::new).save(output, "food_serving");
    }

    private static void recipesTools(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CDModItems.PEARL_NECKLACE.get(), 1)
                .pattern(" P ")
                .pattern("PSP")
                .pattern(" P ")
                .define('P', CDModItems.PEARL.get())
                .define('S', Items.STRING)
                .unlockedBy("has_pearl", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PEARL.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CDModItems.DOUBLE_LURE.get(), 1)
                .pattern(" i ")
                .pattern(" i ")
                .pattern("iii")
                .define('i', Items.IRON_NUGGET)
                .unlockedBy("has_iron_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_NUGGET))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CDModItems.BARBED_LURE.get(), 1)
                .pattern("  i")
                .pattern("i i")
                .pattern("iii")
                .define('i', Items.IRON_NUGGET)
                .unlockedBy("has_iron_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_NUGGET))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CDModItems.MAGNETIC_LURE.get(), 1)
                .pattern(" i ")
                .pattern(" I ")
                .pattern("rib")
                .define('i', Items.IRON_NUGGET)
                .define('I', Items.IRON_INGOT)
                .define('r', Items.RED_DYE)
                .define('b', Items.BLUE_DYE)
                .unlockedBy("has_iron_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_NUGGET))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CDModItems.AUTOMATIC_LURE.get(), 1)
                .pattern(" i ")
                .pattern(" i ")
                .pattern("rot")
                .define('i', Items.IRON_NUGGET)
                .define('t', Items.TRIPWIRE_HOOK)
                .define('r', Items.REDSTONE)
                .define('o', Items.OBSERVER)
                .unlockedBy("has_iron_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_NUGGET))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CDModItems.STORM_LURE.get(), 1)
                .pattern("  i")
                .pattern("l i")
                .pattern(" i ")
                .define('i', Items.IRON_NUGGET)
                .define('l', Items.LIGHTNING_ROD)
                .unlockedBy("has_iron_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_NUGGET))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CDModItems.FISHING_LINE.get(), 1)
                .pattern("sss")
                .pattern("sSs")
                .pattern("sss")
                .define('s', Items.STRING)
                .define('S', Items.STICK)
                .unlockedBy("has_string", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STRING))
                .save(output);
    }

    private static void recipesPalmWood(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.PALM_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', CDModItems.PALM_LOG.get())
                .unlockedBy("has_palm_log", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PALM_LOG.get()))
                .group("bark")
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.STRIPPED_PALM_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', CDModItems.STRIPPED_PALM_LOG.get())
                .unlockedBy("has_stripped_palm_log", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.STRIPPED_PALM_LOG.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, CDModItems.PALM_PLANKS.get(), 4)
                .requires(PALM_LOGS)
                .unlockedBy("has_palm_logs", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PALM_LOG.get()))
                .group("planks")
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.PALM_STAIRS.get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', CDModItems.PALM_PLANKS.get())
                .unlockedBy("has_palm_planks", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PALM_PLANKS.get()))
                .group("wooden_stairs")
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.PALM_SLAB.get(), 6)
                .pattern("###")
                .define('#', CDModItems.PALM_PLANKS.get())
                .unlockedBy("has_palm_planks", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PALM_PLANKS.get()))
                .group("wooden_slab")
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CDModItems.PALM_FENCE.get(), 3)
                .pattern("W#W")
                .pattern("W#W")
                .define('W', CDModItems.PALM_PLANKS.get())
                .define('#', Items.STICK)
                .unlockedBy("has_palm_planks", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PALM_PLANKS.get()))
                .group("wooden_fence")
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, CDModItems.PALM_FENCE_GATE.get(), 1)
                .pattern("#W#")
                .pattern("#W#")
                .define('W', CDModItems.PALM_PLANKS.get())
                .define('#', Items.STICK)
                .unlockedBy("has_palm_planks", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PALM_PLANKS.get()))
                .group("wooden_fence_gate")
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, CDModItems.PALM_DOOR.get(), 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', CDModItems.PALM_PLANKS.get())
                .unlockedBy("has_palm_planks", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PALM_PLANKS.get()))
                .group("wooden_door")
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, CDModItems.PALM_TRAPDOOR.get(), 2)
                .pattern("###")
                .pattern("###")
                .define('#', CDModItems.PALM_PLANKS.get())
                .unlockedBy("has_palm_planks", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PALM_PLANKS.get()))
                .group("wooden_trapdoor")
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, CDModItems.PALM_PRESSURE_PLATE.get(), 1)
                .pattern("##")
                .define('#', CDModItems.PALM_PLANKS.get())
                .unlockedBy("has_palm_planks", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PALM_PLANKS.get()))
                .group("wooden_pressure_plate")
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, CDModItems.PALM_BUTTON.get(), 1)
                .requires(CDModItems.PALM_PLANKS.get())
                .unlockedBy("has_palm_planks", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PALM_PLANKS.get()))
                .group("wooden_button")
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CDModItems.PALM_SIGN.get(), 3)
                .pattern("WWW")
                .pattern("WWW")
                .pattern(" # ")
                .define('W', CDModItems.PALM_PLANKS.get())
                .define('#', Items.STICK)
                .unlockedBy("has_palm_planks", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PALM_PLANKS.get()))
                .group("wooden_sign")
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CDModItems.PALM_HANGING_SIGN.get(), 6)
                .pattern("# #")
                .pattern("WWW")
                .pattern("WWW")
                .define('W', CDModItems.STRIPPED_PALM_LOG.get())
                .define('#', Items.CHAIN)
                .unlockedBy("has_stripped_palm_log", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.STRIPPED_PALM_LOG.get()))
                .group("hanging_sign")
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CDModItems.PALM_BOAT.get(), 1)
                .pattern("# #")
                .pattern("###")
                .define('#', CDModItems.PALM_PLANKS.get())
                .unlockedBy("has_palm_planks", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PALM_PLANKS.get()))
                .group("boat")
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CDModItems.PALM_CHEST_BOAT.get(), 1)
                .requires(CDModItems.PALM_BOAT.get())
                .requires(Items.CHEST)
                .unlockedBy("has_palm_boat", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PALM_BOAT.get()))
                .group("chest_boat")
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, CDModItems.PALM_CABINET.get())
                .pattern("___")
                .pattern("D D")
                .pattern("___")
                .define('_', CDModItems.PALM_SLAB.get())
                .define('D', CDModItems.PALM_TRAPDOOR.get())
                .unlockedBy("has_palm_trapdoor", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PALM_TRAPDOOR.get()))
                .group("fd_cabinet")
                .save(output);
    }

    private static void recipesBlocks(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.CRAB_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', CDModItems.RAW_CRAB.get())
                .unlockedBy("has_crab", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.RAW_CRAB.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.CLAM_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', CDModItems.CLAM.get())
                .unlockedBy("has_clam", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CLAM.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.CLAWSTER_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', CDModItems.RAW_CLAWSTER.get())
                .unlockedBy("has_clawster", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.RAW_CLAWSTER.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.SHRIMP_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', CDModItems.RAW_SHRIMP.get())
                .unlockedBy("has_shrimp", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.RAW_SHRIMP.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.COD_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.COD)
                .unlockedBy("has_cod", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COD))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.SALMON_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.SALMON)
                .unlockedBy("has_salmon", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SALMON))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.PUFFERFISH_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.PUFFERFISH)
                .unlockedBy("has_pufferfish", InventoryChangeTrigger.TriggerInstance.hasItems(Items.PUFFERFISH))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.TROPICAL_FISH_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.TROPICAL_FISH)
                .unlockedBy("has_tropical_fish", InventoryChangeTrigger.TriggerInstance.hasItems(Items.TROPICAL_FISH))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.SQUID_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', CDModItems.RAW_SQUID_TENTACLES.get())
                .unlockedBy("has_raw_squid_tentacles", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.RAW_SQUID_TENTACLES.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.GLOW_SQUID_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', CDModItems.RAW_GLOW_SQUID_TENTACLES.get())
                .unlockedBy("has_raw_glow_squid_tentacles", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.RAW_GLOW_SQUID_TENTACLES.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.FROG_LEG_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', CDModItems.RAW_FROG_LEG.get())
                .unlockedBy("has_raw_frog_leg", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.RAW_FROG_LEG.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.COCONUT_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', CDModItems.COCONUT.get())
                .unlockedBy("has_coconut", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.COCONUT.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.NAUTILUS_SHELL_BLOCK.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.NAUTILUS_SHELL)
                .unlockedBy("has_nautilus_shell", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NAUTILUS_SHELL))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.PEARL_BLOCK.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', CDModItems.PEARL.get())
                .unlockedBy("has_pearl", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PEARL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.SCUTE_BLOCK.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.TURTLE_SCUTE)
                .unlockedBy("has_scute", InventoryChangeTrigger.TriggerInstance.hasItems(Items.TURTLE_SCUTE))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CDModItems.SEA_PICKLE_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.SEA_PICKLE)
                .unlockedBy("has_sea_pickle", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SEA_PICKLE))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, CDModItems.CRAB_TRAP.get(), 1)
                .pattern("nsn")
                .pattern("s s")
                .pattern("www")
                .define('n', ModItems.SAFETY_NET.get())
                .define('s', Items.STICK)
                .define('w', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_stick", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STICK))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, CDModItems.FISH_PLAQUE.get(), 2)
                .pattern(" s ")
                .pattern("sps")
                .pattern(" g ")
                .define('p', ItemTags.PLANKS)
                .define('s', Items.STICK)
                .define('g', Items.GOLD_NUGGET)
                .unlockedBy("has_stick", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STICK))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, CDModItems.TACKLE_BOX.get(), 1)
                .pattern("iii")
                .pattern("ici")
                .define('c', Items.CHEST)
                .define('i', Items.IRON_INGOT)
                .unlockedBy("has_chest", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CHEST))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, CDModItems.WORM_BIN.get(), 1)
                .pattern("iwi")
                .pattern("ici")
                .define('i', Items.IRON_INGOT)
                .define('c', Items.COMPOSTER)
                .define('w', CDModItems.WORM.get())
                .unlockedBy("has_worm", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.WORM.get()))
                .unlockedBy("has_composter", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COMPOSTER))
                .save(output);
    }

    private static void recipesMaterials(RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.RAW_CRAB.get(), 9)
                .requires(CDModItems.CRAB_BARREL.get())
                .unlockedBy("has_crab_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CRAB_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "crab_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.CLAM.get(), 9)
                .requires(CDModItems.CLAM_BARREL.get())
                .unlockedBy("has_clam_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CLAM_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "clam_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.RAW_CLAWSTER.get(), 9)
                .requires(CDModItems.CLAWSTER_BARREL.get())
                .unlockedBy("has_clawster_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CLAWSTER_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "clawster_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.RAW_SHRIMP.get(), 9)
                .requires(CDModItems.SHRIMP_BARREL.get())
                .unlockedBy("has_shrimp_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.SHRIMP_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "shrimp_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.COD, 9)
                .requires(CDModItems.COD_BARREL.get())
                .unlockedBy("has_cod_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.COD_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "cod_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.SALMON, 9)
                .requires(CDModItems.SALMON_BARREL.get())
                .unlockedBy("has_salmon_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.SALMON_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "salmon_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.PUFFERFISH, 9)
                .requires(CDModItems.PUFFERFISH_BARREL.get())
                .unlockedBy("has_pufferfish_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PUFFERFISH_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "pufferfish_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.TROPICAL_FISH, 9)
                .requires(CDModItems.TROPICAL_FISH_BARREL.get())
                .unlockedBy("has_tropical_fish_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.TROPICAL_FISH_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "tropical_fish_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.RAW_SQUID_TENTACLES.get(), 9)
                .requires(CDModItems.SQUID_BARREL.get())
                .unlockedBy("has_squid_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.SQUID_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "squid_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.RAW_GLOW_SQUID_TENTACLES.get(), 9)
                .requires(CDModItems.GLOW_SQUID_BARREL.get())
                .unlockedBy("has_glow_squid_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.GLOW_SQUID_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "glow_squid_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.RAW_FROG_LEG.get(), 9)
                .requires(CDModItems.FROG_LEG_BARREL.get())
                .unlockedBy("has_frog_leg_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.FROG_LEG_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "frog_leg_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.COCONUT.get(), 9)
                .requires(CDModItems.COCONUT_CRATE.get())
                .unlockedBy("has_coconut_crate", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.COCONUT_CRATE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "coconut_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.NAUTILUS_SHELL, 9)
                .requires(CDModItems.NAUTILUS_SHELL_BLOCK.get())
                .unlockedBy("has_nautilus_shell_block", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.NAUTILUS_SHELL_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "nautilus_shell_from_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CDModItems.PEARL.get(), 9)
                .requires(CDModItems.PEARL_BLOCK.get())
                .unlockedBy("has_pearl_block", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.PEARL_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "pearl_from_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.TURTLE_SCUTE, 9)
                .requires(CDModItems.SCUTE_BLOCK.get())
                .unlockedBy("has_scute_block", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.SCUTE_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "scute_from_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.SEA_PICKLE, 9)
                .requires(CDModItems.SEA_PICKLE_CRATE.get())
                .unlockedBy("has_sea_pickle_block", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.SEA_PICKLE_CRATE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "sea_pickle_from_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 3)
                .requires(CDModItems.FISH_BONES.get())
                .unlockedBy("has_fish_bones", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.FISH_BONES.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "bone_meal_from_fish_bones"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CDModItems.SEASHELLS.get(), 8)
                .requires(CDModItems.CORAL_FRAGMENTS.get())
                .requires(CDModItems.CLAM.get())
                .requires(Items.NAUTILUS_SHELL)
                .unlockedBy("has_clam", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CLAM.get()))
                .unlockedBy("has_coral_fragments", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .unlockedBy("has_nautilus_shell", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NAUTILUS_SHELL))
                .save(output);

    }

    private static void recipesNotes(RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CDModItems.NOTE.get(), 2)
                .requires(Items.PAPER)
                .requires(Items.INK_SAC)
                .requires(Items.FEATHER)
                .unlockedBy("has_paper", InventoryChangeTrigger.TriggerInstance.hasItems(Items.PAPER))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "note"));

        SpecialRecipeBuilder.special(BottledNoteRecipe::new).save(output, "bottled_note");

        SpecialRecipeBuilder.special(NoteCloningRecipe::new).save(output, "note_cloning");
    }

    private static void recipesCraftedMeals(RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.SHRIMP_SKEWER.get())
                .requires(Items.STICK)
                .requires(CDModItems.COOKED_SHRIMP.get())
                .requires(CDModItems.COOKED_SHRIMP.get())
                .requires(vectorwing.farmersdelight.common.registry.ModItems.TOMATO.get())
                .unlockedBy("has_cooked_shrimp", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.COOKED_SHRIMP.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.FISH_STICK.get())
                .requires(Items.STICK)
                .requires(Tags.Items.FOODS_COOKED_FISH)
                .unlockedBy("has_cooked_fish", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COOKED_COD, Items.COOKED_SALMON, CDModItems.COOKED_TROPICAL_FISH.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.COCONUT_MILK.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(CDModItems.COCONUT.get())
                .unlockedBy("has_cooked_fish", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COOKED_COD, Items.COOKED_SALMON, CDModItems.COOKED_TROPICAL_FISH.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.KELP_SHAKE.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.SUGAR)
                .requires(Items.KELP)
                .requires(Items.KELP)
                .requires(Items.KELP)
                .requires(Items.KELP)
                .unlockedBy("has_kelp", InventoryChangeTrigger.TriggerInstance.hasItems(Items.KELP))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.SURF_AND_TURF.get())
                .requires(COOKED_SEAFOOD)
                .requires(Items.BAKED_POTATO)
                .requires(Items.COOKED_BEEF)
                .unlockedBy("has_baked_potato", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BAKED_POTATO))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.SQUID_KEBAB.get())
                .requires(Items.STICK)
                .requires(COOKED_SQUID)
                .requires(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .requires(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .requires(vectorwing.farmersdelight.common.registry.ModItems.TOMATO.get())
                .unlockedBy("has_cooked_squid", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.COOKED_SQUID_TENTACLES.get(), CDModItems.COOKED_GLOW_SQUID_TENTACLES.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.FROG_LEG_KEBAB.get())
                .requires(Items.STICK)
                .requires(CDModItems.COOKED_FROG_LEG.get())
                .requires(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .requires(Items.BROWN_MUSHROOM)
                .requires(Items.CARROT)
                .unlockedBy("has_cooked_frog_leg", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.COOKED_FROG_LEG.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, CDModItems.CORAL_CRUNCH.get(), 1)
                .pattern(" m ")
                .pattern("ccc")
                .pattern(" b ")
                .define('m', vectorwing.farmersdelight.common.registry.ModItems.MILK_BOTTLE.get())
                .define('b', Items.BOWL)
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CDModItems.SEA_PICKLE_JUICE.get())
                .requires(Items.SEA_PICKLE)
                .requires(Items.SUGAR)
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy("has_sea_pickle", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SEA_PICKLE))
                .save(output);

        // Chum

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CDModItems.BUCKET_OF_CRAB_CHUM.get(), 1)
                .pattern("mfm")
                .pattern("fbf")
                .pattern("mfm")
                .define('f', Items.COD)
                .define('b', Items.BUCKET)
                .define('m', Items.BONE_MEAL)
                .unlockedBy("has_bonemeal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BONE_MEAL))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CDModItems.BUCKET_OF_CLAWSTER_CHUM.get(), 1)
                .pattern("mfm")
                .pattern("fbf")
                .pattern("mfm")
                .define('f', Items.SALMON)
                .define('b', Items.BUCKET)
                .define('m', Items.BONE_MEAL)
                .unlockedBy("has_bonemeal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BONE_MEAL))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CDModItems.BUCKET_OF_CLAM_CHUM.get(), 1)
                .pattern("mfm")
                .pattern("fbf")
                .pattern("mfm")
                .define('f', Items.PUFFERFISH)
                .define('b', Items.BUCKET)
                .define('m', Items.BONE_MEAL)
                .unlockedBy("has_bonemeal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BONE_MEAL))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CDModItems.BUCKET_OF_SHRIMP_CHUM.get(), 1)
                .pattern("mfm")
                .pattern("fbf")
                .pattern("mfm")
                .define('f', Items.TROPICAL_FISH)
                .define('b', Items.BUCKET)
                .define('m', Items.BONE_MEAL)
                .unlockedBy("has_bonemeal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BONE_MEAL))
                .save(output);

        // Coral Related Recipes

        // Coral Blocks
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.FIRE_CORAL_BLOCK, 1)
                .pattern(" c ")
                .pattern("cdc")
                .pattern(" c ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.RED_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.TUBE_CORAL_BLOCK, 1)
                .pattern(" c ")
                .pattern("cdc")
                .pattern(" c ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.BLUE_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.HORN_CORAL_BLOCK, 1)
                .pattern(" c ")
                .pattern("cdc")
                .pattern(" c ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.YELLOW_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.BRAIN_CORAL_BLOCK, 1)
                .pattern(" c ")
                .pattern("cdc")
                .pattern(" c ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.PINK_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.BUBBLE_CORAL_BLOCK, 1)
                .pattern(" c ")
                .pattern("cdc")
                .pattern(" c ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.MAGENTA_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        // Coral
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.FIRE_CORAL, 1)
                .pattern("   ")
                .pattern("cdc")
                .pattern("   ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.RED_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.TUBE_CORAL, 1)
                .pattern("   ")
                .pattern("cdc")
                .pattern("   ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.BLUE_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.HORN_CORAL, 1)
                .pattern("   ")
                .pattern("cdc")
                .pattern("   ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.YELLOW_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BRAIN_CORAL, 1)
                .pattern("   ")
                .pattern("cdc")
                .pattern("   ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.PINK_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BUBBLE_CORAL, 1)
                .pattern("   ")
                .pattern("cdc")
                .pattern("   ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.MAGENTA_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        // Coral Fans
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.FIRE_CORAL_FAN, 1)
                .pattern(" c ")
                .pattern(" d ")
                .pattern(" c ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.RED_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.TUBE_CORAL_FAN, 1)
                .pattern(" c ")
                .pattern(" d ")
                .pattern(" c ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.BLUE_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.HORN_CORAL_FAN, 1)
                .pattern(" c ")
                .pattern(" d ")
                .pattern(" c ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.YELLOW_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BRAIN_CORAL_FAN, 1)
                .pattern(" c ")
                .pattern(" d ")
                .pattern(" c ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.PINK_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BUBBLE_CORAL_FAN, 1)
                .pattern(" c ")
                .pattern(" d ")
                .pattern(" c ")
                .define('c', CDModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.MAGENTA_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CORAL_FRAGMENTS.get()))
                .save(output);
    }
}