package alabaster.crabbersdelight.common.block.entity.inventory;

import alabaster.crabbersdelight.common.block.entity.WormBinBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class WormBinItemHandler extends ItemStackHandler {
    public static final int FUEL_SLOT = 0;
    public static final int WORM_SLOT = 1;

    public WormBinItemHandler() {
        super(2);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (slot == FUEL_SLOT) {
            return WormBinBlockEntity.isValidFuel(stack);
        }
        return false;
    }

    @Override
    protected int getStackLimit(int slot, ItemStack stack) {
        return slot == FUEL_SLOT ? 1 : super.getStackLimit(slot, stack);
    }
}