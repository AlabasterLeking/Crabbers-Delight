package alabaster.crabbersdelight.client.renderer;

import alabaster.crabbersdelight.common.entity.LifePreserverSeat;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LifePreserverSeatRenderer extends EntityRenderer<LifePreserverSeat> {
    private static final ResourceLocation UNUSED_TEXTURE = ResourceLocation.fromNamespaceAndPath("crabbersdelight", "textures/entity/life_preserver_seat_unused.png");

    public LifePreserverSeatRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(LifePreserverSeat entity) {
        return UNUSED_TEXTURE;
    }
}