package alabaster.crabbersdelight.common;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config {

    public static ForgeConfigSpec COMMON_CONFIG;

    public static final String CATEGORY_SETTINGS = "settings";
    public static ForgeConfigSpec.BooleanValue FISHERMAN_BUY_SEAFOOD;
    public static ForgeConfigSpec.BooleanValue WANDERING_TRADER_PEARLS;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.comment("Game settings").push(CATEGORY_SETTINGS);
        FISHERMAN_BUY_SEAFOOD = COMMON_BUILDER.comment("Should fisherman buy/sell items related to this mods items (Seafood and pearls)")
                .define("fishermanBuyCDItems", true);
        WANDERING_TRADER_PEARLS = COMMON_BUILDER.comment("Should the Wandering Trader sell items in exchange for pearls?")
                .define("wanderingTraderDealsPearls", true);

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
