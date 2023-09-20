package alabaster.crabbersdelight.common;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config {

    public static ForgeConfigSpec COMMON_CONFIG;

    public static final String CATEGORY_SETTINGS = "settings";
    public static ForgeConfigSpec.BooleanValue FISHERMAN_BUY_SEAFOOD;
    public static ForgeConfigSpec.BooleanValue WANDERING_TRADER_PEARLS;
    public static final ForgeConfigSpec.ConfigValue<Integer> MIN_TICKS;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_TICKS;
    public static ForgeConfigSpec.BooleanValue COOK_IN_POTS;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.comment("Game settings").push(CATEGORY_SETTINGS);
        FISHERMAN_BUY_SEAFOOD = COMMON_BUILDER.comment("Should fisherman buy/sell items related to this mods items (Seafood and pearls)")
                .define("fishermanBuyCDItems", true);
        WANDERING_TRADER_PEARLS = COMMON_BUILDER.comment("Should the Wandering Trader sell items in exchange for pearls?")
                .define("wanderingTraderDealsPearls", true);
        COOK_IN_POTS = COMMON_BUILDER.comment("Should seafood only be able to be cooked through the Farmer's Delight pot?")
                .define("cookInPot", true);
        MIN_TICKS = COMMON_BUILDER.comment("Minimum ticks before seafood can be gathered by the crab trap. Default = 4000")
                .define("minTicks", 40);
        MAX_TICKS = COMMON_BUILDER.comment("Maximum ticks before seafood can be gathered by the crab trap. Default = 8000")
                .define("maxTicks", 80);

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
