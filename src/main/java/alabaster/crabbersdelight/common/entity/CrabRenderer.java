package alabaster.crabbersdelight.common.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CrabRenderer extends MobRenderer<CrabEntity, CrabModel<CrabEntity>> {
    public CrabRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrabModel<>(renderManager.bakeLayer(CrabModel.LAYER_LOCATION)), 0.3F);
        this.shadowRadius = 0.3F;
    }

    @Override
    public void render(CrabEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(CrabEntity crabEntity) {
        return null;
    }
}