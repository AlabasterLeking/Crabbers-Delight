package alabaster.crabbersdelight.common.block.container;

import alabaster.crabbersdelight.common.block.entity.inventory.TackleBoxItemHandler;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.registry.CDModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

public class TackleBoxMenu extends AbstractContainerMenu {
    public final TackleBoxItemHandler inventory;
    private final ContainerLevelAccess access;

    public TackleBoxMenu(int id, Inventory playerInventory, FriendlyByteBuf data) {
        this(id, playerInventory, new TackleBoxItemHandler(), ContainerLevelAccess.NULL);
    }

    public TackleBoxMenu(int id, Inventory playerInv, TackleBoxItemHandler inventory, ContainerLevelAccess access) {
        super(CDModMenus.TACKLE_BOX_MENU.get(), id);
        this.inventory = inventory;
        this.access = access;

        int startX = 8;
        int borderSlotSize = 18;

        for (int column = 0; column < TackleBoxItemHandler.SLOTS; column++) {
            this.addSlot(new SlotItemHandler(inventory, column, startX + column * borderSlotSize, 18));
        }

        int startPlayerInvY = 48;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInv, 9 + (row * 9) + column, startX + (column * borderSlotSize),
                        startPlayerInvY + (row * borderSlotSize)));
            }
        }

        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(playerInv, column, startX + (column * borderSlotSize), 106));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.inventory.getSlots()) {
                if (!this.moveItemStackTo(itemstack1, this.inventory.getSlots(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.inventory.getSlots(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, CDModBlocks.TACKLE_BOX.get());
    }
}