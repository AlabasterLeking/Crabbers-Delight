package alabaster.crabbersdelight.data.recipe;

import alabaster.crabbersdelight.common.registry.ModItems;
import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

import java.util.stream.Stream;

public class CookingRecipes {
    public static final int FAST_COOKING = 100;      // 5 seconds
    public static final int NORMAL_COOKING = 200;    // 10 seconds
    public static final int SLOW_COOKING = 400;      // 20 seconds

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public static void register(RecipeOutput output) {
        cookMeals(output);
        cookMinecraftSoups(output);
        //cookMiscellaneous(consumer);
    }

    private static void cookMeals(RecipeOutput output) {
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.CLAM_BAKE.get(), 1, NORMAL_COOKING, LARGE_EXP)
                .addIngredient(ModItems.COOKED_CLAM_MEAT.get())
                .addIngredient(ModItems.COOKED_CLAWSTER.get())
                .addIngredient(ModItems.COOKED_SHRIMP.get())
                .addIngredient(Items.SEAGRASS)
                .addIngredient(Items.CARROT)
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .unlockedByAnyIngredient(ModItems.COOKED_CLAM_MEAT.get(), ModItems.COOKED_CLAWSTER.get(), ModItems.COOKED_SHRIMP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);

        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.CRAB_CAKES.get(), 1, NORMAL_COOKING, LARGE_EXP)
                .addIngredient(ModItems.RAW_CRAB.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.WHEAT_DOUGH.get())
                .addIngredient(CommonTags.FOODS_MILK)
                .addIngredient(Items.EGG)
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .unlockedByAnyIngredient(ModItems.RAW_CRAB.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);

        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.COOKED_CRAB.get(), 1, NORMAL_COOKING, SMALL_EXP)
                .addIngredient(ModItems.RAW_CRAB.get())
                .unlockedByAnyIngredient(ModItems.RAW_CRAB.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);

        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.COOKED_CLAWSTER.get(), 1, NORMAL_COOKING, SMALL_EXP)
                .addIngredient(ModItems.RAW_CLAWSTER.get())
                .unlockedByAnyIngredient(ModItems.RAW_CLAWSTER.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);

        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.COOKED_SHRIMP.get(), 1, NORMAL_COOKING, SMALL_EXP)
                .addIngredient(ModItems.RAW_SHRIMP.get())
                .unlockedByAnyIngredient(ModItems.RAW_SHRIMP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output);
    }

    private static void cookMinecraftSoups(RecipeOutput output) {
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.CLAM_CHOWDER.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(ModItems.COOKED_CLAM_MEAT.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .addIngredient(Items.POTATO)
                .addIngredient(CommonTags.FOODS_MILK)
                .unlockedByAnyIngredient(ModItems.COOKED_CLAM_MEAT.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);

        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.BISQUE.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(CDModTags.COOKED_SEAFOOD),
                        new Ingredient.TagValue(CDModTags.RAW_SEAFOOD)
                )))
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.RICE.get())
                .addIngredient(Items.CARROT)
                .addIngredient(CommonTags.FOODS_MILK)
                .unlockedByAnyIngredient(ModItems.RAW_CLAM_MEAT.get(), ModItems.RAW_CRAB.get(), ModItems.RAW_CLAWSTER.get(), ModItems.RAW_SHRIMP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);

        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.SEAFOOD_GUMBO.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(ModItems.RAW_CLAWSTER.get())
                .addIngredient(ModItems.RAW_SHRIMP.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.RICE.get())
                .addIngredient(Items.PORKCHOP)
                .unlockedByAnyIngredient(ModItems.COOKED_CLAM_MEAT.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);

        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.STUFFED_NAUTILUS_SHELL.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.NAUTILUS_SHELL)
                .addIngredient(Tags.Items.FOODS_COOKED_FISH)
                .addIngredient(Items.KELP)
                .addIngredient(Items.KELP)
                .addIngredient(Items.KELP)
                .unlockedByAnyIngredient(Items.NAUTILUS_SHELL)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);

        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.SHRIMP_FRIED_RICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.COOKED_RICE.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .addIngredient(ModItems.RAW_SHRIMP.get())
                .addIngredient(Items.CARROT)
                .unlockedByAnyIngredient(ModItems.RAW_SHRIMP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);

        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.JAR_OF_PICKLES.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.GLASS_BOTTLE)
                .addIngredient(Items.SEA_PICKLE)
                .addIngredient(Items.SEA_PICKLE)
                .addIngredient(Items.SEA_PICKLE)
                .unlockedByAnyIngredient(Items.SEA_PICKLE)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output);
    }
}

