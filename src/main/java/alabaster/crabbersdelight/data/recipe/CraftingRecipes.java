package alabaster.crabbersdelight.data.recipe;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.common.crafting.FoodServingRecipe;

import static alabaster.crabbersdelight.common.tags.CDModTags.*;

public class CraftingRecipes {
    public static void register(RecipeOutput output) {
        recipesBlocks(output);
        recipesMaterials(output);
        recipesCraftedMeals(output);
        SpecialRecipeBuilder.special(FoodServingRecipe::new).save(output, "food_serving");
    }

    private static void recipesBlocks(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CRAB_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.RAW_CRAB.get())
                .unlockedBy("has_crab", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_CRAB.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CLAM_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.CLAM.get())
                .unlockedBy("has_clam", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CLAM.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CLAWSTER_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.RAW_CLAWSTER.get())
                .unlockedBy("has_clawster", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_CLAWSTER.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SHRIMP_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.RAW_SHRIMP.get())
                .unlockedBy("has_shrimp", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_SHRIMP.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.COD_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.COD)
                .unlockedBy("has_cod", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COD))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SALMON_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.SALMON)
                .unlockedBy("has_salmon", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SALMON))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.PUFFERFISH_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.PUFFERFISH)
                .unlockedBy("has_pufferfish", InventoryChangeTrigger.TriggerInstance.hasItems(Items.PUFFERFISH))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.TROPICAL_FISH_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.TROPICAL_FISH)
                .unlockedBy("has_tropical_fish", InventoryChangeTrigger.TriggerInstance.hasItems(Items.TROPICAL_FISH))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SQUID_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.RAW_SQUID_TENTACLES.get())
                .unlockedBy("has_raw_squid_tentacles", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_SQUID_TENTACLES.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.GLOW_SQUID_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.RAW_GLOW_SQUID_TENTACLES.get())
                .unlockedBy("has_raw_glow_squid_tentacles", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_GLOW_SQUID_TENTACLES.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.FROG_LEG_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.RAW_FROG_LEG.get())
                .unlockedBy("has_raw_frog_leg", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_FROG_LEG.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.NAUTILUS_SHELL_BLOCK.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Items.NAUTILUS_SHELL)
                .unlockedBy("has_nautilus_shell", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NAUTILUS_SHELL))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.PEARL_BLOCK.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.PEARL.get())
                .unlockedBy("has_pearl", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PEARL.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.CRAB_TRAP.get(), 1)
                .pattern("nsn")
                .pattern("s s")
                .pattern("www")
                .define('n', vectorwing.farmersdelight.common.registry.ModItems.SAFETY_NET.get())
                .define('s', Items.STICK)
                .define('w', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_stick", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STICK))
                .save(output);
    }

    private static void recipesMaterials(RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.RAW_CRAB.get(), 9)
                .requires(ModItems.CRAB_BARREL.get())
                .unlockedBy("has_crab_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CRAB_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "crab_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CLAM.get(), 9)
                .requires(ModItems.CLAM_BARREL.get())
                .unlockedBy("has_clam_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CLAM_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "clam_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.RAW_CLAWSTER.get(), 9)
                .requires(ModItems.CLAWSTER_BARREL.get())
                .unlockedBy("has_clawster_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CLAWSTER_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "clawster_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.RAW_SHRIMP.get(), 9)
                .requires(ModItems.SHRIMP_BARREL.get())
                .unlockedBy("has_shrimp_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SHRIMP_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "shrimp_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.COD, 9)
                .requires(ModItems.COD_BARREL.get())
                .unlockedBy("has_cod_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COD_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "cod_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.SALMON, 9)
                .requires(ModItems.SALMON_BARREL.get())
                .unlockedBy("has_salmon_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SALMON_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "salmon_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.PUFFERFISH, 9)
                .requires(ModItems.PUFFERFISH_BARREL.get())
                .unlockedBy("has_pufferfish_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PUFFERFISH_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "pufferfish_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.TROPICAL_FISH, 9)
                .requires(ModItems.TROPICAL_FISH_BARREL.get())
                .unlockedBy("has_tropical_fish_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TROPICAL_FISH_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "tropical_fish_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.RAW_SQUID_TENTACLES.get(), 9)
                .requires(ModItems.SQUID_BARREL.get())
                .unlockedBy("has_squid_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SQUID_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "squid_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.RAW_GLOW_SQUID_TENTACLES.get(), 9)
                .requires(ModItems.GLOW_SQUID_BARREL.get())
                .unlockedBy("has_glow_squid_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GLOW_SQUID_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "glow_squid_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.RAW_FROG_LEG.get(), 9)
                .requires(ModItems.FROG_LEG_BARREL.get())
                .unlockedBy("has_frog_leg_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.FROG_LEG_BARREL.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "frog_leg_from_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.NAUTILUS_SHELL, 9)
                .requires(ModItems.NAUTILUS_SHELL_BLOCK.get())
                .unlockedBy("has_nautilus_shell_block", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.NAUTILUS_SHELL_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "nautilus_shell_from_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PEARL.get(), 9)
                .requires(ModItems.PEARL_BLOCK.get())
                .unlockedBy("has_pearl_block", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PEARL_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "pearl_from_block"));

    }

    private static void recipesCraftedMeals(RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SHRIMP_SKEWER.get())
                .requires(Items.STICK)
                .requires(ModItems.COOKED_SHRIMP.get())
                .requires(ModItems.COOKED_SHRIMP.get())
                .requires(vectorwing.farmersdelight.common.registry.ModItems.TOMATO.get())
                .unlockedBy("has_cooked_shrimp", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COOKED_SHRIMP.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.FISH_STICK.get())
                .requires(Items.STICK)
                .requires(Tags.Items.FOODS_COOKED_FISH)
                .unlockedBy("has_cooked_fish", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COOKED_COD, Items.COOKED_SALMON, ModItems.COOKED_TROPICAL_FISH.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.KELP_SHAKE.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.SUGAR)
                .requires(Items.KELP)
                .requires(Items.KELP)
                .requires(Items.KELP)
                .requires(Items.KELP)
                .unlockedBy("has_kelp", InventoryChangeTrigger.TriggerInstance.hasItems(Items.KELP))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SURF_AND_TURF.get())
                .requires(COOKED_SEAFOOD)
                .requires(Items.BAKED_POTATO)
                .requires(Items.COOKED_BEEF)
                .unlockedBy("has_baked_potato", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BAKED_POTATO))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SQUID_KEBAB.get())
                .requires(Items.STICK)
                .requires(COOKED_SQUID)
                .requires(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .requires(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .requires(vectorwing.farmersdelight.common.registry.ModItems.TOMATO.get())
                .unlockedBy("has_cooked_squid", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COOKED_SQUID_TENTACLES.get(), ModItems.COOKED_GLOW_SQUID_TENTACLES.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.FROG_LEG_KEBAB.get())
                .requires(Items.STICK)
                .requires(ModItems.COOKED_FROG_LEG.get())
                .requires(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .requires(Items.BROWN_MUSHROOM)
                .requires(Items.CARROT)
                .unlockedBy("has_cooked_frog_leg", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COOKED_FROG_LEG.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.CORAL_CRUNCH.get(), 1)
                .pattern(" m ")
                .pattern("ccc")
                .pattern(" b ")
                .define('m', vectorwing.farmersdelight.common.registry.ModItems.MILK_BOTTLE.get())
                .define('b', Items.BOWL)
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SEA_PICKLE_JUICE.get())
                .requires(Items.SEA_PICKLE)
                .requires(Items.SUGAR)
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy("has_sea_pickle", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SEA_PICKLE))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BUCKET_OF_CRAB_CHUM.get(), 1)
                .pattern("mfm")
                .pattern("fbf")
                .pattern("mfm")
                .define('f', Items.COD)
                .define('b', Items.BUCKET)
                .define('m', Items.BONE_MEAL)
                .unlockedBy("has_bonemeal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BONE_MEAL))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BUCKET_OF_CLAWSTER_CHUM.get(), 1)
                .pattern("mfm")
                .pattern("fbf")
                .pattern("mfm")
                .define('f', Items.SALMON)
                .define('b', Items.BUCKET)
                .define('m', Items.BONE_MEAL)
                .unlockedBy("has_bonemeal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BONE_MEAL))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BUCKET_OF_CLAM_CHUM.get(), 1)
                .pattern("mfm")
                .pattern("fbf")
                .pattern("mfm")
                .define('f', Items.PUFFERFISH)
                .define('b', Items.BUCKET)
                .define('m', Items.BONE_MEAL)
                .unlockedBy("has_bonemeal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BONE_MEAL))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BUCKET_OF_SHRIMP_CHUM.get(), 1)
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
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.RED_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.TUBE_CORAL_BLOCK, 1)
                .pattern(" c ")
                .pattern("cdc")
                .pattern(" c ")
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.BLUE_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.HORN_CORAL_BLOCK, 1)
                .pattern(" c ")
                .pattern("cdc")
                .pattern(" c ")
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.YELLOW_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.BRAIN_CORAL_BLOCK, 1)
                .pattern(" c ")
                .pattern("cdc")
                .pattern(" c ")
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.PINK_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.BUBBLE_CORAL_BLOCK, 1)
                .pattern(" c ")
                .pattern("cdc")
                .pattern(" c ")
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.MAGENTA_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        // Coral
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.FIRE_CORAL, 1)
                .pattern("   ")
                .pattern("cdc")
                .pattern("   ")
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.RED_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.TUBE_CORAL, 1)
                .pattern("   ")
                .pattern("cdc")
                .pattern("   ")
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.BLUE_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.HORN_CORAL, 1)
                .pattern("   ")
                .pattern("cdc")
                .pattern("   ")
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.YELLOW_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BRAIN_CORAL, 1)
                .pattern("   ")
                .pattern("cdc")
                .pattern("   ")
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.PINK_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BUBBLE_CORAL, 1)
                .pattern("   ")
                .pattern("cdc")
                .pattern("   ")
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.MAGENTA_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        // Coral Fans
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.FIRE_CORAL_FAN, 1)
                .pattern(" c ")
                .pattern(" d ")
                .pattern(" c ")
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.RED_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.TUBE_CORAL_FAN, 1)
                .pattern(" c ")
                .pattern(" d ")
                .pattern(" c ")
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.BLUE_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.HORN_CORAL_FAN, 1)
                .pattern(" c ")
                .pattern(" d ")
                .pattern(" c ")
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.YELLOW_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BRAIN_CORAL_FAN, 1)
                .pattern(" c ")
                .pattern(" d ")
                .pattern(" c ")
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.PINK_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BUBBLE_CORAL_FAN, 1)
                .pattern(" c ")
                .pattern(" d ")
                .pattern(" c ")
                .define('c', ModItems.CORAL_FRAGMENTS.get())
                .define('d', Items.MAGENTA_DYE)
                .unlockedBy("has_coral_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORAL_FRAGMENTS.get()))
                .save(output);
    }
}