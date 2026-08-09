package alabaster.crabbersdelight.common.recipe;

import alabaster.crabbersdelight.common.item.SignedNoteItem;
import alabaster.crabbersdelight.common.item.component.BottledNoteReward;
import alabaster.crabbersdelight.common.item.component.SignedNoteContent;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import alabaster.crabbersdelight.common.registry.CDModItems;
import alabaster.crabbersdelight.common.registry.CDModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class BottledNoteRecipe extends CustomRecipe {
    public BottledNoteRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        int bottleCount = 0;
        ItemStack signedNote = ItemStack.EMPTY;
        ItemStack rewardStack = ItemStack.EMPTY;

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
            } else if (stack.is(Items.GLASS_BOTTLE)) {
                bottleCount++;
            } else if (rewardStack.isEmpty()) {
                rewardStack = stack;
            } else if (!rewardStack.is(stack.getItem())) {
                return false;
            }
        }

        return !signedNote.isEmpty() && bottleCount == 1;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack signedNote = ItemStack.EMPTY;
        int bottleCount = 0;
        ItemStack rewardStack = ItemStack.EMPTY;

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
            } else if (stack.is(Items.GLASS_BOTTLE)) {
                bottleCount++;
            } else if (rewardStack.isEmpty()) {
                rewardStack = stack.copy();
            } else if (rewardStack.is(stack.getItem())) {
                rewardStack.grow(stack.getCount());
            } else {
                return ItemStack.EMPTY;
            }
        }

        SignedNoteContent content = signedNote.get(CDModDataComponents.SIGNED_NOTE_CONTENT.get());
        if (signedNote.isEmpty() || bottleCount != 1 || content == null) {
            return ItemStack.EMPTY;
        }

        ItemStack result = new ItemStack(CDModItems.BOTTLED_NOTE.get());
        result.set(CDModDataComponents.SIGNED_NOTE_CONTENT.get(), content);
        if (!rewardStack.isEmpty()) {
            result.set(CDModDataComponents.BOTTLED_NOTE_REWARD.get(), new BottledNoteReward(rewardStack));
        }
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CDModRecipeSerializers.BOTTLED_NOTE.get();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }
}