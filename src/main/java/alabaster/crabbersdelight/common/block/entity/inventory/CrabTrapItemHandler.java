package alabaster.crabbersdelight.common.block.entity.inventory;

import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CrabTrapItemHandler extends ItemStackHandler {

    public CrabTrapItemHandler() {
        super(28);
    }

    public void addItemsAndShrinkBait(Level level, BlockPos pos, BlockState state, List<ItemStack> list, ItemStack baitItem) {
        for (ItemStack itemStack : list) {
            if (!itemStack.isEmpty()) {
                for (int i = 0; i < getSlots(); i++) {
                    if (getStackInSlot(i).isEmpty()) {
                        itemStack = insertItem(i, itemStack, false);
                        baitItem.hurtAndBreak(1, level, null);
                        level.playSound(null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.FISH_SWIM, SoundSource.BLOCKS, 0.5F, 1.0F);
                        if (baitItem.is(CDModTags.CREATURE_CHUMS)) {
                            if (baitItem.getDamageValue() == 48) {
                                baitItem.shrink(1);
                                ItemStack bucketStack = new ItemStack(Items.BUCKET);
                                this.insertItem(0, bucketStack, false);
                            }
                        }
                        if (baitItem.is(CDModTags.CRAB_TRAP_BAIT) && !(baitItem.is(CDModTags.CREATURE_CHUMS))) {
                            baitItem.shrink(1);
                        }
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