package alabaster.crabbersdelight.common.block.entity.inventory;

import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CrabTrapItemHandler extends ItemStackHandler {

    public CrabTrapItemHandler() {
        super(28);
    }

    public void addItemsAndShrinkBait(Level level, BlockPos pos, List<ItemStack> lootList, ItemStack baitItem, RandomSource random) {
        for (ItemStack lootStack : lootList) {
            if (lootStack.isEmpty()) continue;

            for (int slot = 0; slot < getSlots(); slot++) {
                if (!getStackInSlot(slot).isEmpty()) continue;

                // Insert Loot
                lootStack = insertItem(slot, lootStack, false);
                level.playSound(null,
                        pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                        SoundEvents.FISH_SWIM, SoundSource.BLOCKS,
                        0.5F, 1.0F);

                // Normal bait: just consume one
                if (baitItem.is(CDModTags.CRAB_TRAP_BAIT) && !baitItem.is(CDModTags.CHUMS)) {
                    baitItem.shrink(1);
                }

                // Chums: use NeoForge durability API
                if (baitItem.is(CDModTags.CHUMS)) {
                    IItemExtension ext = (IItemExtension) baitItem.getItem();
                    int curr  = ext.getDamage(baitItem);
                    int max   = ext.getMaxDamage(baitItem);
                    // apply one point of damage
                    ext.setDamage(baitItem, curr + 1);
                    // if it just broke, consume the stack and return bucket
                    if (curr + 1 >= max) {
                        baitItem.shrink(1);
                        this.insertItem(0, new ItemStack(Items.BUCKET), false);
                    }
                }

                if (lootStack.isEmpty()) break;
            }
        }
    }

    @Override
    protected int getStackLimit(int slot, ItemStack stack) {
        return slot != 0 ? 1 : stack.getMaxStackSize();
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