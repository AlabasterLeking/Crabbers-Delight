package alabaster.crabbersdelight.data.recipe;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import alabaster.crabbersdelight.common.registry.ModItems;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.common.crafting.ingredient.ItemAbilityIngredient;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

public class CuttingRecipes {
    public static void register(RecipeOutput output) {
        // Knife
        cuttingAnimalItems(output);

        // Pickaxe
        breakingCoral(output);
    }

    private static void cuttingAnimalItems(RecipeOutput output) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.TROPICAL_FISH), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.TROPICAL_FISH_SLICE.get(), 2)
                .addResult(Items.BONE_MEAL)
                .build(output);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.COOKED_TROPICAL_FISH.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.COOKED_TROPICAL_FISH_SLICE.get(), 2)
                .addResult(Items.BONE_MEAL)
                .build(output);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.PUFFERFISH), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.PUFFERFISH_SLICE.get(), 2)
                .addResult(Items.BONE_MEAL)
                .build(output);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.COOKED_CRAB.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.CRAB_LEGS.get(), 4)
                .build(output);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.CLAM.get()), Ingredient.of(CommonTags.TOOLS_KNIFE), ModItems.RAW_CLAM_MEAT.get(), 1)
                .addResultWithChance(ModItems.PEARL.get(), 0.5F)
                .build(output);
    }

    private static void breakingCoral(RecipeOutput output) {

        // Coral Blocks
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.BRAIN_CORAL_BLOCK), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.BUBBLE_CORAL_BLOCK), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.HORN_CORAL_BLOCK), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.FIRE_CORAL_BLOCK), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.TUBE_CORAL_BLOCK), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(output);

        // Dead Coral Blocks
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_BRAIN_CORAL_BLOCK), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_BUBBLE_CORAL_BLOCK), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_HORN_CORAL_BLOCK), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_FIRE_CORAL_BLOCK), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_TUBE_CORAL_BLOCK), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(output);

        // Coral
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BRAIN_CORAL), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BUBBLE_CORAL), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.HORN_CORAL), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.FIRE_CORAL), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.TUBE_CORAL), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);

        // Dead Coral
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_BRAIN_CORAL), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_BUBBLE_CORAL), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_HORN_CORAL), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_FIRE_CORAL), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_TUBE_CORAL), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);

        // Coral Fan
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BRAIN_CORAL_FAN), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BUBBLE_CORAL_FAN), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.HORN_CORAL_FAN), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.FIRE_CORAL_FAN), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.TUBE_CORAL_FAN), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);

        // Dead Coral Fan
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_BRAIN_CORAL_FAN), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_BUBBLE_CORAL_FAN), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_HORN_CORAL_FAN), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_FIRE_CORAL_FAN), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_TUBE_CORAL_FAN), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(output);
    }
}