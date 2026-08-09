package alabaster.crabbersdelight.client.renderer;

import alabaster.crabbersdelight.common.entity.FishingSpotEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FishingSpotRenderer extends EntityRenderer<FishingSpotEntity> {

    public FishingSpotRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(FishingSpotEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
    }

    @Override
    public ResourceLocation getTextureLocation(FishingSpotEntity entity) {
        return null;
    }

    @Override
    public boolean shouldRender(FishingSpotEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }
}