package alabaster.crabbersdelight.client.event;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.client.renderer.CrabTrapRenderer;
import alabaster.crabbersdelight.client.renderer.FishPlaqueRenderer;
import alabaster.crabbersdelight.client.renderer.FishingSpotRenderer;
import alabaster.crabbersdelight.client.renderer.LureAwareFishingHookRenderer;
import alabaster.crabbersdelight.common.registry.CDModBlockEntity;
import alabaster.crabbersdelight.common.registry.CDModEntities;
import alabaster.crabbersdelight.common.registry.CDModFluids;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;

@EventBusSubscriber(modid = "crabbersdelight", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                CDModBlockEntity.FISH_PLAQUE.get(),
                FishPlaqueRenderer::new
        );
        event.registerBlockEntityRenderer(
                CDModBlockEntity.CRAB_TRAP.get(),
                CrabTrapRenderer::new
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
        event.registerEntityRenderer(
                CDModEntities.FISHING_SPOT.get(),
                FishingSpotRenderer::new
        );
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        registerFluidTextures(event, "sea_pickle_juice", CDModFluids.SEA_PICKLE_JUICE.type().get());
        registerFluidTextures(event, "coconut_milk", CDModFluids.COCONUT_MILK.type().get());
    }

    private static void registerFluidTextures(RegisterClientExtensionsEvent event, String name, FluidType type) {
        ResourceLocation still   = ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "block/" + name + "_still");
        ResourceLocation flowing = ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "block/" + name + "_flow");

        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override public ResourceLocation getStillTexture()   { return still; }
            @Override public ResourceLocation getFlowingTexture() { return flowing; }
        }, type);
    }
}