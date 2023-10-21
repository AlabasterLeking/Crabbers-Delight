package alabaster.crabbersdelight.client.renderer;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.client.model.CrabModel;
import alabaster.crabbersdelight.common.entity.CrabEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CrabRenderer extends GeoEntityRenderer<CrabEntity> {
    public CrabRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrabModel());
        this.shadowRadius = 0.3F;
    }

    @Override
    public void render(CrabEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
