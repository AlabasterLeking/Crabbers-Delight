package alabaster.sniffersdelight.data.recipe;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import alabaster.sniffersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.function.Consumer;

public class CuttingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        // Knife
        cuttingAnimalItems(consumer);
    }

    private static void cuttingAnimalItems(Consumer<FinishedRecipe> consumer) {

    }
}