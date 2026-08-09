package alabaster.crabbersdelight.common.event;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.entity.FishingSpotEntity;
import alabaster.crabbersdelight.common.fishingspot.FishingSpotManager;
import alabaster.crabbersdelight.common.utils.FishingHookReflection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class FishingSpotHookEvents {
    private static final ResourceLocation FISHING_SPOT_LUCK_ID = CrabbersDelight.modPrefix("fishing_spot_luck");
    private static final double FISHING_SPOT_LUCK_BONUS = 6.0; // raised from 2.0
    private static final int LURE_SPEEDUP = 15; // raised from 2

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }

        FishingHook hook = player.fishing;
        FishingSpotEntity spot = hook == null ? null : FishingSpotManager.findSpotAt(player.level(), hook.blockPosition());
        boolean inSpot = spot != null;

        updateLuck(player, inSpot);

        if (inSpot) {
            FishingHookReflection.speedUpLure(hook, LURE_SPEEDUP);
        }
    }

    private static void updateLuck(Player player, boolean inSpot) {
        AttributeInstance luck = player.getAttribute(Attributes.LUCK);
        if (luck == null) {
            return;
        }
        boolean hasModifier = luck.getModifier(FISHING_SPOT_LUCK_ID) != null;

        if (inSpot && !hasModifier) {
            luck.addTransientModifier(new AttributeModifier(FISHING_SPOT_LUCK_ID, FISHING_SPOT_LUCK_BONUS, AttributeModifier.Operation.ADD_VALUE));
        } else if (!inSpot && hasModifier) {
            luck.removeModifier(FISHING_SPOT_LUCK_ID);
        }
    }
}