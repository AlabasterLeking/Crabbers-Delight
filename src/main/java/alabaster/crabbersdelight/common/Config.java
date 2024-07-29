package alabaster.crabbersdelight.common;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config {

    public static ForgeConfigSpec COMMON_CONFIG;
    public static final String CATEGORY_SETTINGS = "Settings";
    public static ForgeConfigSpec.BooleanValue FISHERMAN_BUY_SEAFOOD;
    public static ForgeConfigSpec.BooleanValue WANDERING_TRADER_PEARLS;
    public static final ForgeConfigSpec.ConfigValue<Integer> MIN_TICKS;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_TICKS;
    public static ForgeConfigSpec.BooleanValue COOK_IN_POTS;
    public static ForgeConfigSpec.BooleanValue REQUIRE_SURROUNDING_WATER;

    static {

        // Common settings
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.comment("Game Settings").push(CATEGORY_SETTINGS);
        FISHERMAN_BUY_SEAFOOD = COMMON_BUILDER.comment("Should fisherman buy/sell items related to this mods items (Seafood and pearls)")
                .define("fishermanBuyCDItems", true);
        WANDERING_TRADER_PEARLS = COMMON_BUILDER.comment("Should the Wandering Trader sell items in exchange for pearls?")
                .define("wanderingTraderDealsPearls", true);
        COOK_IN_POTS = COMMON_BUILDER.comment("Should seafood only be able to be cooked through the Farmer's Delight pot?")
                .define("cookInPot", true);
        MIN_TICKS = COMMON_BUILDER.comment("Minimum ticks before seafood can be gathered by the crab trap. Default = 4000")
                .define("minTicks", 4000);
        MAX_TICKS = COMMON_BUILDER.comment("Maximum ticks before seafood can be gathered by the crab trap. Default = 8000")
                .define("maxTicks", 8000);
        REQUIRE_SURROUNDING_WATER = COMMON_BUILDER.comment("Requires the crab trap to have a 3x3 of open water or waterlogged blocks around it to be able to function. Default = true")
                .define("require_surrounding_water", true);

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();

    }
}
