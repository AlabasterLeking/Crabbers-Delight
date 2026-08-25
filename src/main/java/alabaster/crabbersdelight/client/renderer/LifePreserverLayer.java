package alabaster.crabbersdelight.client.renderer;

import alabaster.crabbersdelight.common.block.LifePreserverBlock;
import alabaster.crabbersdelight.common.registry.CDModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class LifePreserverLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final float FLIP_ROTATION_DEGREES = 90f;
    private static final float RENDER_SCALE = 2.0f;
    private static final double RENDER_Y_OFFSET = 2.0 / 16.0;

    public LifePreserverLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack stack = entity.getItemBySlot(EquipmentSlot.LEGS);
        if (!stack.is(CDModItems.LIFE_PRESERVER.get())) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(0, LifePreserverBlock.WAIST_HEIGHT - RENDER_Y_OFFSET, 0);
        poseStack.mulPose(Axis.XP.rotationDegrees(FLIP_ROTATION_DEGREES));
        poseStack.scale(RENDER_SCALE, RENDER_SCALE, RENDER_SCALE);

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), 0);

        poseStack.popPose();
    }
}