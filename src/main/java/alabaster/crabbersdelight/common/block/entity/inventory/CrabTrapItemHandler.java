package alabaster.crabbersdelight.common.block.entity.inventory;

import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CrabTrapItemHandler extends ItemStackHandler {

    public CrabTrapItemHandler() {
        super(100);
    }

    public void addItemsAndShrinkBait(List<ItemStack> list, ItemStack baitItem) {
        for (ItemStack itemStack : list) {
            if (!itemStack.isEmpty()) {
                for (int i = 0; i < getSlots(); i++) {
                    if (getStackInSlot(i).isEmpty()) {
                        itemStack = insertItem(i, itemStack, false);
                        baitItem.shrink(1);
                        if (itemStack.isEmpty()) {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        if (slot != 0) {
            return 1;
        }
        return getStackInSlot(slot).getMaxStackSize();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (slot == 0) {
            return stack.is(CDModTags.CRAB_TRAP_BAIT);
        }
        return true;
    }

    public NonNullList<ItemStack> getItems() {
        return this.stacks;
    }
}