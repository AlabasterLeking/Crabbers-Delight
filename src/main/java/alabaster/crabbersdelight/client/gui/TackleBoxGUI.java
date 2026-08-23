package alabaster.crabbersdelight.client.gui;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.container.TackleBoxMenu;
import alabaster.crabbersdelight.common.block.entity.inventory.TackleBoxItemHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TackleBoxGUI extends AbstractContainerScreen<TackleBoxMenu> {
    private static final ResourceLocation TACKLE_BOX_GUI = CrabbersDelight.modPrefix("textures/gui/tackle_box.png");

    public TackleBoxGUI(TackleBoxMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 176;
        this.imageHeight = 177;
        this.inventoryLabelY = 74;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TACKLE_BOX_GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);

        if (this.hoveredSlot != null && this.hoveredSlot.getItem().isEmpty()) {
            Component label = slotLabel(this.hoveredSlot.getSlotIndex());
            if (label != null) {
                guiGraphics.renderTooltip(this.font, label, mouseX, mouseY);
            }
        }
    }

    private static Component slotLabel(int slotIndex) {
        if (slotIndex == TackleBoxItemHandler.LURE_SLOT) {
            return Component.translatable("gui.crabbersdelight.tackle_box.lure_slot");
        }
        if (slotIndex == TackleBoxItemHandler.LINE_SLOT) {
            return Component.translatable("gui.crabbersdelight.tackle_box.line_slot");
        }
        if (slotIndex == TackleBoxItemHandler.BAIT_SLOT_1 || slotIndex == TackleBoxItemHandler.BAIT_SLOT_2) {
            return Component.translatable("gui.crabbersdelight.tackle_box.bait_slot");
        }
        return null;
    }
}