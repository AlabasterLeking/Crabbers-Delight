package alabaster.crabbersdelight.client.gui;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.container.CrabTrapMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrabTrapGUI extends AbstractContainerScreen<CrabTrapMenu> {
    private static final ResourceLocation CRAB_TRAP_GUI = CrabbersDelight.modPrefix("textures/gui/crab_trap.png");

    public CrabTrapGUI(CrabTrapMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 176;
        this.imageHeight = 182;
        this.inventoryLabelY = 90;
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
        guiGraphics.blit(CRAB_TRAP_GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}