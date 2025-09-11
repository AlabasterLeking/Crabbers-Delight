package alabaster.crabbersdelight.data.recipe;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class SmeltingRecipes {

    public static void register(Consumer<FinishedRecipe> consumer) {

        //foodSmeltingRecipes("cooked_crab", ModItems.RAW_CRAB.get(), ModItems.COOKED_CRAB.get(), 0.35F, consumer);
        //foodSmeltingRecipes("cooked_shrimp", ModItems.RAW_SHRIMP.get(), ModItems.COOKED_SHRIMP.get(), 0.35F, consumer);
        //foodSmeltingRecipes("cooked_clawster", ModItems.RAW_CLAWSTER.get(), ModItems.COOKED_CLAWSTER.get(), 0.35F, consumer);

        foodSmeltingRecipes("cooked_clam_meat", CDModItems.RAW_CLAM_MEAT.get(), CDModItems.COOKED_CLAM_MEAT.get(), 0.35F, consumer);
        foodSmeltingRecipes("cooked_tropical_fish", Items.TROPICAL_FISH, CDModItems.COOKED_TROPICAL_FISH.get(), 0.35f, consumer);
        foodSmeltingRecipes("cooked_tropical_fish_slice", CDModItems.TROPICAL_FISH_SLICE.get(), CDModItems.COOKED_TROPICAL_FISH_SLICE.get(), 0.35f, consumer);
        foodSmeltingRecipes("cooked_pufferfish_slice", CDModItems.PUFFERFISH_SLICE.get(), CDModItems.COOKED_PUFFERFISH_SLICE.get(), 0.35f, consumer);
        foodSmeltingRecipes("cooked_squid_tentacles", CDModItems.RAW_SQUID_TENTACLES.get(), CDModItems.COOKED_SQUID_TENTACLES.get(), 0.35f, consumer);
        foodSmeltingRecipes("cooked_glow_squid_tentacles", CDModItems.RAW_GLOW_SQUID_TENTACLES.get(), CDModItems.COOKED_GLOW_SQUID_TENTACLES.get(), 0.35f, consumer);
        foodSmeltingRecipes("cooked_frog_leg", CDModItems.RAW_FROG_LEG.get(), CDModItems.COOKED_FROG_LEG.get(), 0.35f, consumer);
        //foodSmeltingRecipes("arid_flesh", Items.ROTTEN_FLESH, ModItems.ARID_FLESH.get(), 0.35f, consumer);
        //foodSmeltingRecipes("rotten_flesh", ModItems.SOGGY_FLESH.get(), Items.ROTTEN_FLESH, 0.35f, consumer);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(CDModItems.CAN.get()), RecipeCategory.MISC,
                        Items.IRON_NUGGET, 0.1F, 200)
                .unlockedBy("has_can", InventoryChangeTrigger.TriggerInstance.hasItems(CDModItems.CAN.get()))
                .save(consumer, new ResourceLocation(CrabbersDelight.MODID, "iron_nugget_from_smelting_can"));
    }

    private static void foodSmeltingRecipes(String name, ItemLike ingredient, ItemLike result, float experience, Consumer<FinishedRecipe> consumer)
    {
        String namePrefix = new ResourceLocation(CrabbersDelight.MODID, name).toString();

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 200)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer);

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 600)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, namePrefix + "_from_campfire_cooking");

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 100)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, namePrefix + "_from_smoking");
    }
}

