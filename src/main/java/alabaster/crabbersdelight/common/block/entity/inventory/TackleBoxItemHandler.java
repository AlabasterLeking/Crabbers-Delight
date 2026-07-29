package alabaster.crabbersdelight.common.block.entity.inventory;

import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class TackleBoxItemHandler extends ItemStackHandler {
    public static final int SLOTS = 9;

    public TackleBoxItemHandler() {
        super(SLOTS);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return stack.is(CDModTags.FISHING_BAIT)
                || stack.is(CDModTags.FISHING_LURE)
                || stack.is(CDModTags.RAW_SEAFOOD)
                || stack.is(CDModTags.COOKED_SEAFOOD);
    }
}