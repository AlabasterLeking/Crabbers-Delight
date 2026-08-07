package alabaster.crabbersdelight.common.block.entity.inventory;

import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class TackleBoxItemHandler extends ItemStackHandler {
    public static final int GENERAL_SLOTS = 15;
    public static final int LURE_SLOT = 15;
    public static final int LINE_SLOT = 16;
    public static final int BAIT_SLOT_1 = 17;
    public static final int BAIT_SLOT_2 = 18;
    public static final int SLOTS = 19;

    public TackleBoxItemHandler() {
        super(SLOTS);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (slot == LURE_SLOT) {
            return stack.is(CDModTags.FISHING_LURE);
        }
        if (slot == LINE_SLOT) {
            return stack.is(CDModTags.FISHING_LINE);
        }
        if (slot == BAIT_SLOT_1 || slot == BAIT_SLOT_2) {
            return stack.is(CDModTags.FISHING_BAIT);
        }
        return true;
    }
}