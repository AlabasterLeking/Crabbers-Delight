package alabaster.crabbersdelight.common.utils;

import alabaster.crabbersdelight.common.block.entity.TackleBoxBlockEntity;
import alabaster.crabbersdelight.common.item.TackleBoxItem;
import alabaster.crabbersdelight.common.item.component.TackleBoxContents;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

// Tackle box gets used in the following order of proximity: main hand, off hand, hotbar, closest placed tackle box (in radius)

public class TackleBoxProximity {
    private static final int SEARCH_RADIUS = 3;

    public interface TackleBoxAccess {
        ItemStack getSlot(int index);

        void setSlot(int index, ItemStack stack);
    }
    public static TackleBoxAccess find(Player player) {
        if (player.getMainHandItem().getItem() instanceof TackleBoxItem) {
            return heldAccess(player.getMainHandItem());
        }
        if (player.getOffhandItem().getItem() instanceof TackleBoxItem) {
            return heldAccess(player.getOffhandItem());
        }

        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.getItem() instanceof TackleBoxItem) {
                return heldAccess(stack);
            }
        }
        return findClosestPlaced(player);
    }

    private static TackleBoxAccess findClosestPlaced(Player player) {
        BlockPos center = player.blockPosition();
        TackleBoxBlockEntity closest = null;
        double closestDistSqr = Double.MAX_VALUE;

        for (int dx = -SEARCH_RADIUS; dx <= SEARCH_RADIUS; dx++) {
            for (int dy = -SEARCH_RADIUS; dy <= SEARCH_RADIUS; dy++) {
                for (int dz = -SEARCH_RADIUS; dz <= SEARCH_RADIUS; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    if (player.level().getBlockEntity(pos) instanceof TackleBoxBlockEntity tackleBox) {
                        double distSqr = pos.distSqr(center);
                        if (distSqr < closestDistSqr) {
                            closestDistSqr = distSqr;
                            closest = tackleBox;
                        }
                    }
                }
            }
        }

        return closest != null ? placedAccess(closest) : null;
    }

    private static TackleBoxAccess heldAccess(ItemStack holder) {
        return new TackleBoxAccess() {
            @Override
            public ItemStack getSlot(int index) {
                TackleBoxContents contents = holder.get(CDModDataComponents.TACKLE_BOX_CONTENTS.get());
                if (contents == null || index >= contents.items().size()) {
                    return ItemStack.EMPTY;
                }
                return contents.items().get(index);
            }

            @Override
            public void setSlot(int index, ItemStack stack) {
                TackleBoxContents contents = holder.get(CDModDataComponents.TACKLE_BOX_CONTENTS.get());
                if (contents == null || index >= contents.items().size()) {
                    return;
                }
                List<ItemStack> updated = new ArrayList<>(contents.items());
                updated.set(index, stack);
                holder.set(CDModDataComponents.TACKLE_BOX_CONTENTS.get(), new TackleBoxContents(updated));
            }
        };
    }

    private static TackleBoxAccess placedAccess(TackleBoxBlockEntity tackleBox) {
        return new TackleBoxAccess() {
            @Override
            public ItemStack getSlot(int index) {
                return tackleBox.handler.getStackInSlot(index);
            }

            @Override
            public void setSlot(int index, ItemStack stack) {
                tackleBox.handler.setStackInSlot(index, stack);
            }
        };
    }
}