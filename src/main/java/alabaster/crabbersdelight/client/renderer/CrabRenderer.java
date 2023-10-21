package alabaster.crabbersdelight.client.renderer;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.client.model.CrabModel;
import alabaster.crabbersdelight.common.entity.CrabEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CrabRenderer extends MobRenderer<CrabEntity, CrabModel<CrabEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(CrabbersDelight.MODID, "textures/entity/crab.png");

    public CrabRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new CrabModel(ctx.bakeLayer(CrabModel.LAYER_LOCATION)), 1);
    }

    @Override
    public ResourceLocation getTextureLocation(CrabEntity entity) {
        return TEXTURE;
    }
}
