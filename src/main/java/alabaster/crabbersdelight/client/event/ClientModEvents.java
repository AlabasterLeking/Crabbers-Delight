package alabaster.crabbersdelight.client.event;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.client.model.CrabModel;
import alabaster.crabbersdelight.client.renderer.CrabRenderer;
import alabaster.crabbersdelight.common.registry.ModEntities;
import net.minecraft.world.level.EntityGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CrabbersDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.CRAB.get(), CrabRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CrabModel.LAYER_LOCATION, CrabModel::createBodyLayer);
    }
}
