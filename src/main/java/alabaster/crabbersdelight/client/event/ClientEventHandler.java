package alabaster.crabbersdelight.client.event;

import alabaster.crabbersdelight.client.renderer.FishPlaqueRenderer;
import alabaster.crabbersdelight.client.renderer.LureAwareFishingHookRenderer;
import alabaster.crabbersdelight.common.registry.CDModBlockEntity;
import alabaster.crabbersdelight.common.registry.CDModEntities;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
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

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                CDModEntities.THROWN_BOTTLED_NOTE.get(),
                ThrownItemRenderer::new
        );
        event.registerEntityRenderer(
                EntityType.FISHING_BOBBER,
                LureAwareFishingHookRenderer::new
        );
    }
}