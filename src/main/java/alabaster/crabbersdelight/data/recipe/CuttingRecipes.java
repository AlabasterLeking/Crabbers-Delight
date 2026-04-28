package alabaster.crabbersdelight.data.recipe;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import alabaster.crabbersdelight.common.registry.CDModItems;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.crafting.ingredient.ToolActionIngredient;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.function.Consumer;

public class CuttingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        // Knife
        cuttingAnimalItems(consumer);

        // Pickaxe
        breakingCoral(consumer);

        // Axe
        strippingWood(consumer);
        salvagingWoodenFurniture(consumer);
    }

    private static void cuttingAnimalItems(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.COD), Ingredient.of(CommonTags.Items.TOOLS_KNIVES), ModItems.COD_SLICE.get(), 2)
                .addResult(CDModItems.FISH_BONES.get())
                .save(consumer);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.COOKED_COD), Ingredient.of(CommonTags.Items.TOOLS_KNIVES), ModItems.COOKED_COD_SLICE.get(), 2)
                .addResult(CDModItems.FISH_BONES.get())
                .save(consumer);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.SALMON), Ingredient.of(CommonTags.Items.TOOLS_KNIVES), ModItems.SALMON_SLICE.get(), 2)
                .addResult(CDModItems.FISH_BONES.get())
                .save(consumer);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.COOKED_SALMON), Ingredient.of(CommonTags.Items.TOOLS_KNIVES), ModItems.COOKED_SALMON_SLICE.get(), 2)
                .addResult(CDModItems.FISH_BONES.get())
                .save(consumer);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.TROPICAL_FISH), Ingredient.of(CommonTags.Items.TOOLS_KNIVES), CDModItems.TROPICAL_FISH_SLICE.get(), 2)
                .addResult(CDModItems.FISH_BONES.get())
                .save(consumer);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(CDModItems.COOKED_TROPICAL_FISH.get()), Ingredient.of(CommonTags.Items.TOOLS_KNIVES), CDModItems.COOKED_TROPICAL_FISH_SLICE.get(), 2)
                .addResult(CDModItems.FISH_BONES.get())
                .save(consumer);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.PUFFERFISH), Ingredient.of(CommonTags.Items.TOOLS_KNIVES), CDModItems.PUFFERFISH_SLICE.get(), 2)
                .addResult(CDModItems.FISH_BONES.get())
                .save(consumer);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(CDModItems.COOKED_CRAB.get()), Ingredient.of(CommonTags.Items.TOOLS_KNIVES), CDModItems.CRAB_LEGS.get(), 4)
                .save(consumer);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(CDModItems.CLAM.get()), Ingredient.of(CommonTags.Items.TOOLS_KNIVES), CDModItems.RAW_CLAM_MEAT.get(), 1)
                .addResultWithChance(CDModItems.PEARL.get(), 0.5F)
                .save(consumer);

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(CDModItems.COCONUT.get()), Ingredient.of(CommonTags.Items.TOOLS_KNIVES), CDModItems.COCONUT_HALVE.get(), 2)
                .save(consumer);
    }

    private static void strippingWood(Consumer<FinishedRecipe> consumer) {
        stripLogForBark(consumer, CDModItems.PALM_LOG.get(), CDModItems.STRIPPED_PALM_LOG.get());
    }

    private static void salvagingWoodenFurniture(Consumer<FinishedRecipe> consumer) {
        salvagePlankFromFurniture(consumer, CDModItems.PALM_PLANKS.get(), CDModItems.PALM_DOOR.get(), CDModItems.PALM_TRAPDOOR.get(), CDModItems.PALM_SIGN.get(), CDModItems.PALM_HANGING_SIGN.get());
    }

    private static void breakingCoral(Consumer<FinishedRecipe> consumer) {

        // Coral Blocks
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.BRAIN_CORAL_BLOCK), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 4)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.BUBBLE_CORAL_BLOCK), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 4)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.HORN_CORAL_BLOCK), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 4)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.FIRE_CORAL_BLOCK), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 4)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.TUBE_CORAL_BLOCK), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 4)
                .save(consumer);

        // Dead Coral Blocks
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_BRAIN_CORAL_BLOCK), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 4)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_BUBBLE_CORAL_BLOCK), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 4)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_HORN_CORAL_BLOCK), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 4)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_FIRE_CORAL_BLOCK), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 4)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Blocks.DEAD_TUBE_CORAL_BLOCK), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 4)
                .save(consumer);

        // Coral
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BRAIN_CORAL), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BUBBLE_CORAL), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.HORN_CORAL), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.FIRE_CORAL), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.TUBE_CORAL), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);

        // Dead Coral
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_BRAIN_CORAL), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_BUBBLE_CORAL), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_HORN_CORAL), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_FIRE_CORAL), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_TUBE_CORAL), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);

        // Coral Fan
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BRAIN_CORAL_FAN), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.BUBBLE_CORAL_FAN), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.HORN_CORAL_FAN), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.FIRE_CORAL_FAN), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.TUBE_CORAL_FAN), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);

        // Dead Coral Fan
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_BRAIN_CORAL_FAN), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_BUBBLE_CORAL_FAN), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_HORN_CORAL_FAN), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_FIRE_CORAL_FAN), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.DEAD_TUBE_CORAL_FAN), Ingredient.of(CommonTags.Items.TOOLS_PICKAXES), CDModItems.CORAL_FRAGMENTS.get(), 2)
                .save(consumer);
    }

    private static void stripLogForBark(Consumer<FinishedRecipe> consumer, ItemLike log, ItemLike strippedLog) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(log), new ToolActionIngredient(ToolActions.AXE_STRIP), strippedLog)
                .addResult(ModItems.TREE_BARK.get())
                .addSound(ForgeRegistries.SOUND_EVENTS.getKey(SoundEvents.AXE_STRIP).toString()).save(consumer);
    }

    private static void salvagePlankFromFurniture(Consumer<FinishedRecipe> consumer, ItemLike plank, ItemLike door, ItemLike trapdoor, ItemLike sign, ItemLike hangingSign) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(door), new ToolActionIngredient(ToolActions.AXE_DIG), plank).save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(trapdoor), new ToolActionIngredient(ToolActions.AXE_DIG), plank).save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(sign), new ToolActionIngredient(ToolActions.AXE_DIG), plank).save(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(hangingSign), new ToolActionIngredient(ToolActions.AXE_DIG), plank).save(consumer);
    }
}