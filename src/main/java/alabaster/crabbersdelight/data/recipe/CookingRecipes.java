package alabaster.crabbersdelight.data.recipe;

import alabaster.crabbersdelight.common.registry.CDModItems;
import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.registry.ModItems;
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
        CookingPotRecipeBuilder.cookingPotRecipe(CDModItems.COCONUT_PUDDING.get(), 1, NORMAL_COOKING, SMALL_EXP)
                .addIngredient(CDModItems.COCONUT_MILK.get())
                .addIngredient(CDModItems.COCONUT_HALVE.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.EGG)
                .unlockedByAnyIngredient(CDModItems.COCONUT_MILK.get(),CDModItems.COCONUT_HALVE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);

        CookingPotRecipeBuilder.cookingPotRecipe(CDModItems.CLAM_BAKE.get(), 1, NORMAL_COOKING, LARGE_EXP)
                .addIngredient(CDModItems.COOKED_CLAM_MEAT.get())
                .addIngredient(CDModItems.COOKED_CLAWSTER.get())
                .addIngredient(CDModItems.COOKED_SHRIMP.get())
                .addIngredient(Items.SEAGRASS)
                .addIngredient(Items.CARROT)
                .addIngredient(ModItems.ONION.get())
                .unlockedByAnyIngredient(CDModItems.COOKED_CLAM_MEAT.get(), CDModItems.COOKED_CLAWSTER.get(), CDModItems.COOKED_SHRIMP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);

        CookingPotRecipeBuilder.cookingPotRecipe(CDModItems.CRAB_CAKES.get(), 1, NORMAL_COOKING, LARGE_EXP)
                .addIngredient(CDModItems.RAW_CRAB.get())
                .addIngredient(ModItems.WHEAT_DOUGH.get())
                .addIngredient(Tags.Items.DRINKS_MILK)
                .addIngredient(Items.EGG)
                .addIngredient(ModItems.ONION.get())
                .unlockedByAnyIngredient(CDModItems.RAW_CRAB.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);

        CookingPotRecipeBuilder.cookingPotRecipe(CDModItems.COOKED_CRAB.get(), 1, NORMAL_COOKING, SMALL_EXP)
                .addIngredient(CDModItems.RAW_CRAB.get())
                .unlockedByAnyIngredient(CDModItems.RAW_CRAB.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .save(output);

        CookingPotRecipeBuilder.cookingPotRecipe(CDModItems.COOKED_CLAWSTER.get(), 1, NORMAL_COOKING, SMALL_EXP)
                .addIngredient(CDModItems.RAW_CLAWSTER.get())
                .unlockedByAnyIngredient(CDModItems.RAW_CLAWSTER.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .save(output);

        CookingPotRecipeBuilder.cookingPotRecipe(CDModItems.COOKED_SHRIMP.get(), 1, NORMAL_COOKING, SMALL_EXP)
                .addIngredient(CDModItems.RAW_SHRIMP.get())
                .unlockedByAnyIngredient(CDModItems.RAW_SHRIMP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .save(output);
    }

    private static void cookMinecraftSoups(RecipeOutput output) {
        CookingPotRecipeBuilder.cookingPotRecipe(CDModItems.CLAM_CHOWDER.get(), 1, SLOW_COOKING, LARGE_EXP, Items.BOWL)
                .addIngredient(CDModItems.COOKED_CLAM_MEAT.get())
                .addIngredient(ModItems.ONION.get())
                .addIngredient(Items.POTATO)
                .addIngredient(Tags.Items.DRINKS_MILK)
                .unlockedByAnyIngredient(CDModItems.COOKED_CLAM_MEAT.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);

        CookingPotRecipeBuilder.cookingPotRecipe(CDModItems.BISQUE.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(CDModTags.COOKED_SEAFOOD),
                        new Ingredient.TagValue(CDModTags.RAW_SEAFOOD)
                )))
                .addIngredient(ModItems.ONION.get())
                .addIngredient(ModItems.RICE.get())
                .addIngredient(Items.CARROT)
                .addIngredient(Tags.Items.DRINKS_MILK)
                .unlockedByAnyIngredient(CDModItems.RAW_CLAM_MEAT.get(), CDModItems.RAW_CRAB.get(), CDModItems.RAW_CLAWSTER.get(), CDModItems.RAW_SHRIMP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);

        CookingPotRecipeBuilder.cookingPotRecipe(CDModItems.SEAFOOD_GUMBO.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(CDModItems.RAW_CLAWSTER.get())
                .addIngredient(CDModItems.RAW_SHRIMP.get())
                .addIngredient(ModItems.ONION.get())
                .addIngredient(ModItems.RICE.get())
                .addIngredient(Items.PORKCHOP)
                .unlockedByAnyIngredient(CDModItems.COOKED_CLAM_MEAT.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);

        CookingPotRecipeBuilder.cookingPotRecipe(CDModItems.STUFFED_NAUTILUS_SHELL.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.NAUTILUS_SHELL)
                .addIngredient(Tags.Items.FOODS_COOKED_FISH)
                .addIngredient(Items.KELP)
                .addIngredient(Items.KELP)
                .addIngredient(Items.KELP)
                .unlockedByAnyIngredient(Items.NAUTILUS_SHELL)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);

        CookingPotRecipeBuilder.cookingPotRecipe(CDModItems.SHRIMP_FRIED_RICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(ModItems.COOKED_RICE.get())
                .addIngredient(ModItems.ONION.get())
                .addIngredient(CDModItems.RAW_SHRIMP.get())
                .addIngredient(Items.CARROT)
                .unlockedByAnyIngredient(CDModItems.RAW_SHRIMP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);

        CookingPotRecipeBuilder.cookingPotRecipe(CDModItems.JAR_OF_PICKLES.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.GLASS_BOTTLE)
                .addIngredient(Items.SEA_PICKLE)
                .addIngredient(Items.SEA_PICKLE)
                .addIngredient(Items.SEA_PICKLE)
                .unlockedByAnyIngredient(Items.SEA_PICKLE)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);

        CookingPotRecipeBuilder.cookingPotRecipe(CDModItems.TURTLE_STEW.get(), 1, SLOW_COOKING, LARGE_EXP, Items.TURTLE_SCUTE)
                .addIngredient(Items.SEAGRASS)
                .addIngredient(Items.TROPICAL_FISH)
                .unlockedByAnyIngredient(Items.TROPICAL_FISH)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
    }
}

