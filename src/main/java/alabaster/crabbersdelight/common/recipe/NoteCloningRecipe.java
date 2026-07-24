package alabaster.crabbersdelight.common.recipe;

import alabaster.crabbersdelight.common.item.NoteItem;
import alabaster.crabbersdelight.common.item.SignedNoteItem;
import alabaster.crabbersdelight.common.item.component.SignedNoteContent;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import alabaster.crabbersdelight.common.registry.CDModItems;
import alabaster.crabbersdelight.common.registry.CDModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class NoteCloningRecipe extends CustomRecipe {
    public NoteCloningRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        int blankCount = 0;
        ItemStack signedNote = ItemStack.EMPTY;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (stack.getItem() instanceof SignedNoteItem) {
                if (!signedNote.isEmpty()) {
                    return false;
                }
                signedNote = stack;
            } else if (stack.getItem() instanceof NoteItem) {
                blankCount++;
            } else {
                return false;
            }
        }

        return !signedNote.isEmpty() && blankCount > 0;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        int blankCount = 0;
        ItemStack signedNote = ItemStack.EMPTY;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (stack.getItem() instanceof SignedNoteItem) {
                if (!signedNote.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                signedNote = stack;
            } else if (stack.getItem() instanceof NoteItem) {
                blankCount++;
            } else {
                return ItemStack.EMPTY;
            }
        }

        SignedNoteContent content = signedNote.get(CDModDataComponents.SIGNED_NOTE_CONTENT.get());
        if (signedNote.isEmpty() || blankCount < 1 || content == null) {
            return ItemStack.EMPTY;
        }

        SignedNoteContent copyContent = content.tryCraftCopy();
        if (copyContent == null) {
            return ItemStack.EMPTY;
        }

        ItemStack result = new ItemStack(CDModItems.SIGNED_NOTE.get(), blankCount);
        result.set(CDModDataComponents.SIGNED_NOTE_CONTENT.get(), copyContent);
        return result;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for (int i = 0; i < remaining.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.hasCraftingRemainingItem()) {
                remaining.set(i, stack.getCraftingRemainingItem());
            } else if (stack.getItem() instanceof SignedNoteItem) {
                remaining.set(i, stack.copyWithCount(1));
                break;
            }
        }

        return remaining;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CDModRecipeSerializers.NOTE_CLONING.get();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }
}