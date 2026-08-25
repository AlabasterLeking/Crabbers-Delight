package alabaster.crabbersdelight.client.event;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.client.renderer.LifePreserverLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LifePreserverRenderers {
    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skin : event.getSkins()) {
            LivingEntityRenderer<AbstractClientPlayer, EntityModel<AbstractClientPlayer>> renderer = event.getSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new LifePreserverLayer<>(renderer));
            }
        }

        for (EntityType<?> entityType : event.getEntityTypes()) {
            addMobLayerIfLiving(event, entityType);
        }
    }

    private static <T extends Entity> void addMobLayerIfLiving(EntityRenderersEvent.AddLayers event, EntityType<T> entityType) {
        EntityRenderer<T> renderer = event.getRenderer(entityType);
        if (renderer instanceof LivingEntityRenderer<?, ?> livingRenderer) {
            addLayerToLivingRenderer(livingRenderer);
        }
    }

    private static <T extends LivingEntity, M extends EntityModel<T>> void addLayerToLivingRenderer(LivingEntityRenderer<T, M> renderer) {
        renderer.addLayer(new LifePreserverLayer<>(renderer));
    }
}