package alabaster.crabbersdelight.data.recipe;


import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import alabaster.crabbersdelight.common.registry.ModBlocks;
import alabaster.crabbersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.registry.ModRecipeSerializers;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class CraftingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        recipesBlocks(consumer);
        //recipesMaterials(consumer);
        //recipesFoodstuffs(consumer);
        //recipesCraftedMeals(consumer);
        SpecialRecipeBuilder.special(ModRecipeSerializers.FOOD_SERVING.get()).save(consumer, "food_serving");
    }

    private static void recipesBlocks(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItems.CRAB_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.RAW_CRAB.get())
                .unlockedBy("has_cabbage", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_CRAB.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.CLAM_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.CLAM.get())
                .unlockedBy("has_cabbage", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CLAM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.CLAWSTER_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.RAW_CLAWSTER.get())
                .unlockedBy("has_cabbage", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_CLAWSTER.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.SHRIMP_BARREL.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.RAW_SHRIMP.get())
                .unlockedBy("has_cabbage", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_SHRIMP.get()))
                .save(consumer);
    }
}
