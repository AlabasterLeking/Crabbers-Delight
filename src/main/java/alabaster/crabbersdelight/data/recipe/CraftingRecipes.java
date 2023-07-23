package alabaster.crabbersdelight.data.recipe;

import alabaster.crabbersdelight.CrabbersDelight;
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
        recipesMaterials(consumer);
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


    private static void recipesMaterials(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(ModItems.RAW_CRAB.get(), 9)
                .requires(ModItems.CRAB_BARREL.get())
                .unlockedBy("has_crab_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CRAB_BARREL.get()))
                .save(consumer, new ResourceLocation(CrabbersDelight.MODID, "crab_from_barrel"));

        ShapelessRecipeBuilder.shapeless(ModItems.CLAM.get(), 9)
                .requires(ModItems.CLAM_BARREL.get())
                .unlockedBy("has_clam_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CLAM_BARREL.get()))
                .save(consumer, new ResourceLocation(CrabbersDelight.MODID, "clam_from_barrel"));

        ShapelessRecipeBuilder.shapeless(ModItems.RAW_CLAWSTER.get(), 9)
                .requires(ModItems.CLAWSTER_BARREL.get())
                .unlockedBy("has_clawster_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CLAWSTER_BARREL.get()))
                .save(consumer, new ResourceLocation(CrabbersDelight.MODID, "clawster_from_barrel"));

        ShapelessRecipeBuilder.shapeless(ModItems.RAW_SHRIMP.get(), 9)
                .requires(ModItems.SHRIMP_BARREL.get())
                .unlockedBy("has_shrimp_barrel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SHRIMP_BARREL.get()))
                .save(consumer, new ResourceLocation(CrabbersDelight.MODID, "shrimp_from_barrel"));
    }
}
