package alabaster.crabbersdelight.common.fishingspot;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.Config;
import alabaster.crabbersdelight.common.utils.FishingHookReflection;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class FishingSpotHookEvents {
    private static final ResourceLocation FISHING_SPOT_LUCK = CrabbersDelight.modPrefix("fishing_spot_luck");
    private static final double FISHING_SPOT_LUCK_BONUS = 6.0;
    private static final ResourceLocation OUTSIDE_SPOT_LUCK_PENALTY = CrabbersDelight.modPrefix("outside_fishing_spot_luck_penalty");
    private static final int MAX_LURE_TICKS = 60;
    private static final int TWINKLE_COUNT = 8;
    private static final double TWINKLE_HEIGHT_OFFSET = 0.5;
    private static final double TWINKLE_SPREAD = 0.3;

    private static final Set<Integer> hooksInSpot = new HashSet<>();

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }

        FishingHook hook = player.fishing;
        FishingSpotEntity spot = hook == null ? null : FishingSpotManager.findSpotAt(player.level(), hook.blockPosition());
        boolean inSpot = spot != null;

        updateLuck(player, hook != null, inSpot);

        if (inSpot) {
            FishingHookReflection.capLureTime(hook, MAX_LURE_TICKS);

            if (hook.isInWater() && hooksInSpot.add(hook.getId())) {
                spawnTwinkle(hook);
            }
        } else if (hook != null) {
            hooksInSpot.remove(hook.getId());
        }
    }

    private static void spawnTwinkle(FishingHook hook) {
        if (!(hook.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        serverLevel.sendParticles(ParticleTypes.WAX_OFF,
                hook.getX(), hook.getY() + TWINKLE_HEIGHT_OFFSET, hook.getZ(),
                TWINKLE_COUNT, TWINKLE_SPREAD, TWINKLE_SPREAD, TWINKLE_SPREAD, 0.0);
    }

    private static void updateLuck(Player player, boolean isFishing, boolean inSpot) {
        AttributeInstance luck = player.getAttribute(Attributes.LUCK);
        if (luck == null) {
            return;
        }

        boolean hasBonus = luck.getModifier(FISHING_SPOT_LUCK) != null;
        if (inSpot && !hasBonus) {
            luck.addTransientModifier(new AttributeModifier(FISHING_SPOT_LUCK, FISHING_SPOT_LUCK_BONUS, AttributeModifier.Operation.ADD_VALUE));
        } else if (!inSpot && hasBonus) {
            luck.removeModifier(FISHING_SPOT_LUCK);
        }

        boolean penaltyEnabled = Config.OUTSIDE_FISHING_SPOT_LUCK_PENALTY_ENABLED.get();
        boolean hasPenalty = luck.getModifier(OUTSIDE_SPOT_LUCK_PENALTY) != null;
        boolean wantsPenalty = isFishing && !inSpot && penaltyEnabled;
        if (wantsPenalty && !hasPenalty) {
            double amount = -Config.OUTSIDE_FISHING_SPOT_LUCK_PENALTY_AMOUNT.get();
            luck.addTransientModifier(new AttributeModifier(OUTSIDE_SPOT_LUCK_PENALTY, amount, AttributeModifier.Operation.ADD_VALUE));
        } else if (!wantsPenalty && hasPenalty) {
            luck.removeModifier(OUTSIDE_SPOT_LUCK_PENALTY);
        }
    }
}