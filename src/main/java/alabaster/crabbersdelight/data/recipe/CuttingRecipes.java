package alabaster.crabbersdelight.data.recipe;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
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
}