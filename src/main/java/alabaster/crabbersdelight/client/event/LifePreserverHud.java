package alabaster.crabbersdelight.client.event;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class LifePreserverHud {

    @SubscribeEvent
    public static void onRenderGuiLayer(RenderGuiLayerEvent.Pre event) {
        if (!event.getName().equals(VanillaGuiLayers.VEHICLE_HEALTH)) {
            return;
        }

        Entity vehicle = Minecraft.getInstance().player == null ? null : Minecraft.getInstance().player.getVehicle();
        if (vehicle instanceof ArmorStand armorStand && armorStand.isMarker() && armorStand.isInvisible()) {
            event.setCanceled(true);
        }
    }
}