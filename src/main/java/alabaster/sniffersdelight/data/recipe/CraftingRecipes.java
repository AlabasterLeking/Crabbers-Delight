package alabaster.sniffersdelight.data.recipe;

import alabaster.sniffersdelight.SniffersDelight;
import alabaster.sniffersdelight.common.tags.CDModTags;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import alabaster.sniffersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.registry.ModRecipeSerializers;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import java.util.function.Consumer;

public class CraftingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        recipesBlocks(consumer);
        recipesMaterials(consumer);
        recipesCraftedMeals(consumer);
        SpecialRecipeBuilder.special(ModRecipeSerializers.FOOD_SERVING.get()).save(consumer, "food_serving");
    }

    private static void recipesBlocks(Consumer<FinishedRecipe> consumer) {

    }

    private static void recipesMaterials(Consumer<FinishedRecipe> consumer) {

    }

    private static void recipesCraftedMeals(Consumer<FinishedRecipe> consumer) {

    }
}