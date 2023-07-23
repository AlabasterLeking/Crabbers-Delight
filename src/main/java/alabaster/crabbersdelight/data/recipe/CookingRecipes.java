package alabaster.crabbersdelight.data.recipe;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import alabaster.crabbersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class CookingRecipes {
    public static final int FAST_COOKING = 100;      // 5 seconds
    public static final int NORMAL_COOKING = 200;    // 10 seconds
    public static final int SLOW_COOKING = 400;      // 20 seconds

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public static void register(Consumer<FinishedRecipe> consumer) {
        cookMeals(consumer);
        cookMinecraftSoups(consumer);
        //cookMiscellaneous(consumer);
    }

    private static void cookMeals(Consumer<FinishedRecipe> consumer) {
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.CLAM_BAKE.get(), 1, NORMAL_COOKING, LARGE_EXP)
                .addIngredient(ModItems.COOKED_CLAM_MEAT.get())
                .addIngredient(ModItems.COOKED_CLAWSTER.get())
                .addIngredient(ModItems.COOKED_SHRIMP.get())
                .addIngredient(Items.SEAGRASS)
                .addIngredient(Items.CARROT)
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .unlockedByAnyIngredient(ModItems.COOKED_CLAM_MEAT.get(), ModItems.COOKED_CLAWSTER.get(), ModItems.COOKED_SHRIMP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
    }

    private static void cookMinecraftSoups(Consumer<FinishedRecipe> consumer) {
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.CLAM_CHOWDER.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BOWL)
                .addIngredient(ModItems.COOKED_CLAM_MEAT.get())
                .addIngredient(vectorwing.farmersdelight.common.registry.ModItems.ONION.get())
                .addIngredient(Items.POTATO)
                .addIngredient(ForgeTags.MILK)
                .unlockedByAnyIngredient(ModItems.COOKED_CLAM_MEAT.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
    }
}

