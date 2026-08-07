package alabaster.crabbersdelight.common.block.container;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.entity.inventory.TackleBoxItemHandler;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.registry.CDModMenus;
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

public class TackleBoxMenu extends AbstractContainerMenu {
    public final TackleBoxItemHandler inventory;
    private final ContainerLevelAccess access;
    public static final ResourceLocation LURE_SLOT_ICON = CrabbersDelight.modPrefix("gui/lure_slot");
    public static final ResourceLocation LINE_SLOT_ICON = CrabbersDelight.modPrefix("gui/line_slot");
    public static final ResourceLocation BAIT_SLOT_ICON = CrabbersDelight.modPrefix("gui/bait_slot");

    public TackleBoxMenu(int id, Inventory playerInventory, FriendlyByteBuf data) {
        this(id, playerInventory, new TackleBoxItemHandler(), ContainerLevelAccess.NULL);
    }

    public TackleBoxMenu(int id, Inventory playerInv, TackleBoxItemHandler inventory, ContainerLevelAccess access) {
        super(CDModMenus.TACKLE_BOX_MENU.get(), id);
        this.inventory = inventory;
        this.access = access;

        int startX = 8;
        int borderSlotSize = 18;
        int topY = 18;

        this.addSlot(new SlotItemHandler(inventory, TackleBoxItemHandler.LURE_SLOT, 19, 22) {
            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(TextureAtlas.LOCATION_BLOCKS, LURE_SLOT_ICON);
            }
        });

        this.addSlot(new SlotItemHandler(inventory, TackleBoxItemHandler.LINE_SLOT, 47, 22) {
            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(TextureAtlas.LOCATION_BLOCKS, LINE_SLOT_ICON);
            }
        });

        this.addSlot(new SlotItemHandler(inventory, TackleBoxItemHandler.BAIT_SLOT_1, 19, 50) {
            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(TextureAtlas.LOCATION_BLOCKS, BAIT_SLOT_ICON);
            }
        });

        this.addSlot(new SlotItemHandler(inventory, TackleBoxItemHandler.BAIT_SLOT_2, 47, 50) {
            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(TextureAtlas.LOCATION_BLOCKS, BAIT_SLOT_ICON);
            }
        });

        int generalStartX = startX + borderSlotSize * 2 + 4 + 30 + 2;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 5; column++) {
                this.addSlot(new SlotItemHandler(inventory, row * 5 + column, generalStartX + column * borderSlotSize, topY + row * borderSlotSize));
            }
        }

        int startPlayerInvY = 84;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInv, 9 + (row * 9) + column, startX + (column * borderSlotSize),
                        startPlayerInvY + (row * borderSlotSize)));
            }
        }

        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(playerInv, column, startX + (column * borderSlotSize), 142));
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