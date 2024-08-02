package alabaster.crabbersdelight.data.recipe;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;
import alabaster.crabbersdelight.CrabbersDelight;
import vectorwing.farmersdelight.common.crafting.ingredient.ToolActionIngredient;
import alabaster.crabbersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.function.Consumer;

public class CuttingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        // Knife
        cuttingAnimalItems(consumer);

        // Pickaxe
        breakingCoral(consumer);
    }

    private static void cuttingAnimalItems(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.TROPICAL_FISH), Ingredient.of(ForgeTags.TOOLS_KNIVES), ModItems.TROPICAL_FISH_SLICE.get(), 2)
                .addResult(Items.BONE_MEAL)
                .build(consumer);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.COOKED_TROPICAL_FISH.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ModItems.COOKED_TROPICAL_FISH_SLICE.get(), 2)
                .addResult(Items.BONE_MEAL)
                .build(consumer);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.PUFFERFISH), Ingredient.of(ForgeTags.TOOLS_KNIVES), ModItems.PUFFERFISH_SLICE.get(), 2)
                .addResult(Items.BONE_MEAL)
                .build(consumer);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.COOKED_CRAB.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ModItems.CRAB_LEGS.get(), 4)
                .build(consumer);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.CLAM.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ModItems.RAW_CLAM_MEAT.get(), 1)
                .addResultWithChance(ModItems.PEARL.get(), 0.5F)
                .build(consumer);
    }

    private static void breakingCoral(Consumer<FinishedRecipe> consumer) {

        // Coral Blocks
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.BRAIN_CORAL_BLOCK), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.BUBBLE_CORAL_BLOCK), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.HORN_CORAL_BLOCK), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.FIRE_CORAL_BLOCK), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.TUBE_CORAL_BLOCK), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(consumer);

        // Dead Coral Blocks
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_BRAIN_CORAL_BLOCK), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_BUBBLE_CORAL_BLOCK), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_HORN_CORAL_BLOCK), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_FIRE_CORAL_BLOCK), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_TUBE_CORAL_BLOCK), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 4)
                .build(consumer);

        // Coral
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BRAIN_CORAL), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BUBBLE_CORAL), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.HORN_CORAL), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.FIRE_CORAL), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.TUBE_CORAL), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);

        // Dead Coral
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_BRAIN_CORAL), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_BUBBLE_CORAL), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_HORN_CORAL), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_FIRE_CORAL), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_TUBE_CORAL), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);

        // Coral Fan
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BRAIN_CORAL_FAN), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BUBBLE_CORAL_FAN), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.HORN_CORAL_FAN), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.FIRE_CORAL_FAN), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.TUBE_CORAL_FAN), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);

        // Dead Coral Fan
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_BRAIN_CORAL_FAN), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_BUBBLE_CORAL_FAN), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_HORN_CORAL_FAN), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_FIRE_CORAL_FAN), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_TUBE_CORAL_FAN), Ingredient.of(ForgeTags.TOOLS_PICKAXES), ModItems.CORAL_FRAGMENTS.get(), 2)
                .build(consumer);
    }
}