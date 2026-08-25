package alabaster.crabbersdelight.common.event;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.LifePreserverBlock;
import alabaster.crabbersdelight.common.registry.CDModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class LifePreserverEvents {
    private static final int MAX_SURFACE_SEARCH = 8;
    private static final double DEADZONE = 0.05;
    private static final double LIFT_FACTOR = 0.15;
    private static final double MAX_LIFT = 0.08;

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            applyBuoyancy(entity);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        applyBuoyancy(event.getEntity());
    }

    private static void applyBuoyancy(LivingEntity entity) {
        if (!entity.getItemBySlot(EquipmentSlot.LEGS).is(CDModItems.LIFE_PRESERVER.get())) {
            return;
        }
        if (!entity.isInWater()) {
            return;
        }

        double waistY = entity.getY() + LifePreserverBlock.WAIST_HEIGHT;
        double surfaceY = findWaterSurfaceY(entity);
        double diff = surfaceY - waistY;

        if (diff > DEADZONE) {
            double lift = Math.min(diff * LIFT_FACTOR, MAX_LIFT);
            Vec3 motion = entity.getDeltaMovement();
            entity.setDeltaMovement(motion.x, motion.y + lift, motion.z);
        }
    }

    private static double findWaterSurfaceY(LivingEntity entity) {
        Level level = entity.level();
        BlockPos.MutableBlockPos cursor = entity.blockPosition().mutable();
        double lastWaterTop = cursor.getY();
        for (int i = 0; i < MAX_SURFACE_SEARCH; i++) {
            FluidState fluid = level.getFluidState(cursor);
            if (!fluid.is(FluidTags.WATER)) {
                break;
            }
            lastWaterTop = cursor.getY() + fluid.getHeight(level, cursor);
            cursor.move(Direction.UP);
        }
        return lastWaterTop;
    }
}