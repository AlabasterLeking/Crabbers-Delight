package alabaster.crabbersdelight.client.renderer;

import alabaster.crabbersdelight.common.block.CrabTrapBlock;
import alabaster.crabbersdelight.common.block.entity.CrabTrapBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class CrabTrapRenderer implements BlockEntityRenderer<CrabTrapBlockEntity> {
    public CrabTrapRenderer(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    public void render(CrabTrapBlockEntity crabTrapBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        Direction direction = crabTrapBlockEntity.getBlockState().getValue(CrabTrapBlock.FACING).getOpposite();
        ItemStack trapStack = crabTrapBlockEntity.getStoredItem();
        int posLong = (int) crabTrapBlockEntity.getBlockPos().asLong();

        if (!trapStack.isEmpty()) {
            poseStack.pushPose();

            ItemRenderer itemRenderer = Minecraft.getInstance()
                    .getItemRenderer();

            poseStack.pushPose();
            boolean isBlockItem = itemRenderer.getModel(trapStack, crabTrapBlockEntity.getLevel(), null, 0).applyTransform(ItemDisplayContext.FIXED, poseStack, false).isGui3d();
            poseStack.popPose();

            renderItemLayingDown(poseStack, direction);

            Minecraft.getInstance().getItemRenderer().renderStatic(trapStack, ItemDisplayContext.FIXED, combinedLight, combinedOverlay, poseStack, buffer, crabTrapBlockEntity.getLevel(), posLong);
            poseStack.popPose();
        }
    }

    public void renderItemLayingDown(PoseStack matrixStackIn, Direction direction) {

        matrixStackIn.translate(0.5D, 0.08D, 0.5D);

        float f = -direction.toYRot();
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(f));

        matrixStackIn.mulPose(Axis.XP.rotationDegrees(90.0F));

        matrixStackIn.scale(0.6F, 0.6F, 0.6F);
    }
}