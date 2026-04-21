package alabaster.crabbersdelight.integration;

import alabaster.crabbersdelight.common.registry.CDModItems;
import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class ThirstWasTakenCompat {

    @SubscribeEvent
    public static void compat(RegisterThirstValueEvent event) {
        event.addDrink(CDModItems.KELP_SHAKE.get(), 12, 18);
        event.addDrink(CDModItems.SEA_PICKLE_JUICE.get(), 8, 12);
        event.addDrink(CDModItems.COCONUT_MILK.get(), 8, 12);
    }
}
