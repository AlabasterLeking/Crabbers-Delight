package alabaster.crabbersdelight.client.renderer;

import alabaster.crabbersdelight.common.block.CrabTrapBlock;
import alabaster.crabbersdelight.common.block.entity.CrabTrapBlockEntity;
import alabaster.crabbersdelight.common.block.entity.inventory.CrabTrapItemHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class CrabTrapRenderer implements BlockEntityRenderer<CrabTrapBlockEntity> {
    private static final int GRID_COLUMNS = 3;
    private static final double GRID_SPACING = 0.22;
    private static final double STAGGER_AMOUNT = 0.06;

    private final ItemRenderer itemRenderer;

    public CrabTrapRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(CrabTrapBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (be.getLevel() == null) {
            return;
        }

        CrabTrapItemHandler handler = be.getInventory();
        Direction facing = be.getBlockState().getValue(CrabTrapBlock.FACING);
        float yRot = facing.toYRot();

        for (int slot = 1; slot < handler.getSlots(); slot++) {
            ItemStack stack = handler.getStackInSlot(slot);
            if (stack.isEmpty()) {
                continue;
            }

            int index = slot - 1;
            int column = index % GRID_COLUMNS;
            int row = index / GRID_COLUMNS;

            double offsetX = (column - 1) * GRID_SPACING;
            double offsetZ = (row - 1) * GRID_SPACING + staggerFor(slot);

            poseStack.pushPose();
            poseStack.translate(0.5, 0.3, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
            poseStack.translate(offsetX, 0, offsetZ);
            poseStack.scale(0.4f, 0.4f, 0.4f);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, be.getLevel(), 0);

            poseStack.popPose();
        }
    }

    private static double staggerFor(int slot) {
        Random random = new Random(slot);
        double sign = random.nextBoolean() ? 1.0 : -1.0;
        double magnitude = STAGGER_AMOUNT * (0.4 + random.nextDouble() * 0.6);
        return sign * magnitude;
    }
}