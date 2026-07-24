package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.recipe.BottledNoteRecipe;
import alabaster.crabbersdelight.common.recipe.NoteCloningRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CDModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CrabbersDelight.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<NoteCloningRecipe>> NOTE_CLONING =
            RECIPE_SERIALIZERS.register("note_cloning", () -> new SimpleCraftingRecipeSerializer<>(NoteCloningRecipe::new));

    public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<BottledNoteRecipe>> BOTTLED_NOTE =
            RECIPE_SERIALIZERS.register("bottled_note", () -> new SimpleCraftingRecipeSerializer<>(BottledNoteRecipe::new));
}