package alabaster.crabbersdelight.common.block.container;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.entity.inventory.CrabTrapItemHandler;
import alabaster.crabbersdelight.common.registry.ModBlocks;
import alabaster.crabbersdelight.common.registry.ModMenus;
import alabaster.crabbersdelight.common.tags.CDModTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

public class CrabTrapMenu extends AbstractContainerMenu {
    public final CrabTrapItemHandler inventory;
    private final ContainerLevelAccess access;
    public static final ResourceLocation BAIT_SLOT = CrabbersDelight.modPrefix("gui/bait_slot");

    public CrabTrapMenu(int id, Inventory playerInventory, FriendlyByteBuf data) {
        this(id, playerInventory, new CrabTrapItemHandler(), ContainerLevelAccess.NULL);
    }

    public CrabTrapMenu(int id, Inventory playerInv, CrabTrapItemHandler inventory, final ContainerLevelAccess containerLevelAccess) {
        super(ModMenus.CRAB_TRAP_MENU.get(), id);
        this.inventory = inventory;
        this.access = containerLevelAccess;

        int startX = 8;
        int borderSlotSize = 18;

        // Bait Slot
        this.addSlot(new SlotItemHandler(inventory, 0, 80, 14) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(CDModTags.CRAB_TRAP_BAIT);
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(TextureAtlas.LOCATION_BLOCKS, BAIT_SLOT);
            }
        });

        // Row 1
        for(int column = 0; column < 9; ++column) {
            this.addSlot(new SlotItemHandler(inventory, column + 1, 8 + column * 18, 34));
        }

        // Row 2
        for(int column = 0; column < 9; ++column) {
            this.addSlot(new SlotItemHandler(inventory, column + 10, 8 + column * 18, 52));
        }

        // Row 3
        for(int column = 0; column < 9; ++column) {
            this.addSlot(new SlotItemHandler(inventory, column + 19, 8 + column * 18, 70));
        }

        // Main Player Inventory
        int startPlayerInvY = 101;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInv, 9 + (row * 9) + column, startX + (column * borderSlotSize),
                        startPlayerInvY + (row * borderSlotSize)));
            }
        }

        // Hotbar
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInv, column, startX + (column * borderSlotSize), 159));
        }

    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.inventory.getItems().size()) {
                if (!this.moveItemStackTo(itemstack1, this.inventory.getItems().size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.inventory.getItems().size(), false)) {
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
        return stillValid(this.access, player, ModBlocks.CRAB_TRAP.get());
    }
}