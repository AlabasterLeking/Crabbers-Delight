package alabaster.crabbersdelight.common.event;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class WormDropEvents {
    private static final double WORM_CHANCE = 0.05;

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }
        if (!event.getState().is(BlockTags.DIRT)) {
            return;
        }
        if (serverLevel.getRandom().nextDouble() >= WORM_CHANCE) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack worm = new ItemStack(CDModItems.WORM.get());
        if (!player.getInventory().add(worm)) {
            BlockPos pos = event.getPos();
            serverLevel.addFreshEntity(new ItemEntity(serverLevel, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, worm));
        }
    }
}