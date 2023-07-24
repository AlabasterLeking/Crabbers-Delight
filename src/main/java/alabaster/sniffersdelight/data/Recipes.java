package alabaster.sniffersdelight.data;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import alabaster.sniffersdelight.data.recipe.SmeltingRecipes;
import alabaster.sniffersdelight.data.recipe.CraftingRecipes;
import alabaster.sniffersdelight.data.recipe.CookingRecipes;
import alabaster.sniffersdelight.data.recipe.CuttingRecipes;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Recipes extends RecipeProvider
{
    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        CraftingRecipes.register(consumer);
        SmeltingRecipes.register(consumer);
        CookingRecipes.register(consumer);
        CuttingRecipes.register(consumer);
    }
}