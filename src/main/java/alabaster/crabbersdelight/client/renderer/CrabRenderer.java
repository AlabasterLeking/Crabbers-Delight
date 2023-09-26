package alabaster.crabbersdelight.client.renderer;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.client.model.CrabModel;
import alabaster.crabbersdelight.common.entity.Crab;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class CrabRenderer extends MobRenderer<Crab, CrabModel<Crab>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(CrabbersDelight.MODID, "textures/entity/crab.png");

    public CrabRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new CrabModel(ctx.bakeLayer(CrabModel.LAYER_LOCATION)), 1);
    }

    @Override
    public ResourceLocation getTextureLocation(Crab entity) {
        return TEXTURE;
    }
}
