package alabaster.crabbersdelight.common.event;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.entity.inventory.TackleBoxItemHandler;
import alabaster.crabbersdelight.common.utils.TackleBoxProximity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class FishingLineEvents {
    private static final double DISTANCE_MULTIPLIER = 1.5;

    @SubscribeEvent
    public static void onHookSpawn(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() || !(event.getEntity() instanceof FishingHook hook)) {
            return;
        }
        if (!(hook.getOwner() instanceof Player player)) {
            return;
        }

        TackleBoxProximity.TackleBoxAccess tackleBox = TackleBoxProximity.find(player);
        if (tackleBox == null || tackleBox.getSlot(TackleBoxItemHandler.LINE_SLOT).isEmpty()) {
            return;
        }

        Vec3 velocity = hook.getDeltaMovement();
        hook.setDeltaMovement(velocity.scale(DISTANCE_MULTIPLIER));
    }

    @SubscribeEvent(receiveCanceled = true)
    public static void onItemFished(ItemFishedEvent event) {
        Player player = event.getEntity();
        if (event.getDrops().isEmpty()) {
            return;
        }

        TackleBoxProximity.TackleBoxAccess tackleBox = TackleBoxProximity.find(player);
        if (tackleBox == null) {
            return;
        }

        ItemStack line = tackleBox.getSlot(TackleBoxItemHandler.LINE_SLOT).copy();
        if (line.isEmpty()) {
            return;
        }

        line.hurtAndBreak(1, player, LivingEntity.getSlotForHand(InteractionHand.MAIN_HAND));
        tackleBox.setSlot(TackleBoxItemHandler.LINE_SLOT, line);
    }
}