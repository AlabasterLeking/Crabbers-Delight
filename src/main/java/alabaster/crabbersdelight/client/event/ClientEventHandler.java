package alabaster.crabbersdelight.client.event;

import alabaster.crabbersdelight.client.renderer.FishPlaqueRenderer;
import alabaster.crabbersdelight.common.registry.CDModBlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = "crabbersdelight", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                CDModBlockEntity.FISH_PLAQUE.get(),
                FishPlaqueRenderer::new
        );
    }
}
