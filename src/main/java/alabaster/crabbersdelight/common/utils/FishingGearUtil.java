package alabaster.crabbersdelight.common.utils;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.ItemAbilities;

public class FishingGearUtil {
    public static InteractionHand findRodHand(Player player) {
        if (player.getMainHandItem().canPerformAction(ItemAbilities.FISHING_ROD_CAST)) {
            return InteractionHand.MAIN_HAND;
        }
        if (player.getOffhandItem().canPerformAction(ItemAbilities.FISHING_ROD_CAST)) {
            return InteractionHand.OFF_HAND;
        }
        return null;
    }
}