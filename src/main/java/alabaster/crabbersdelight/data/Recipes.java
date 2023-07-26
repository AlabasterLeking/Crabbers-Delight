package alabaster.crabbersdelight.data;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import alabaster.crabbersdelight.data.recipe.SmeltingRecipes;
import alabaster.crabbersdelight.data.recipe.CraftingRecipes;
import alabaster.crabbersdelight.data.recipe.CookingRecipes;
import alabaster.crabbersdelight.data.recipe.CuttingRecipes;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault

public class Recipes extends RecipeProvider
{
    public Recipes(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        CraftingRecipes.register(consumer);
        SmeltingRecipes.register(consumer);
        CookingRecipes.register(consumer);
        CuttingRecipes.register(consumer);
    }
}