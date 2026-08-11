package alabaster.crabbersdelight.common.event;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModItems;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class CoconutHelmetEvents {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (serverPlayer.getItemBySlot(EquipmentSlot.HEAD).is(CDModItems.COCONUT_HELMET.get())) {
            ResourceLocation advancementId = ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "main/castaway_couture");
            AdvancementHolder advancement = serverPlayer.server.getAdvancements().get(advancementId);
            if (advancement != null) {
                serverPlayer.getAdvancements().award(advancement, "equip_coconut_helmet");
            }
        }
    }
}