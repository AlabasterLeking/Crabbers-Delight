package alabaster.crabbersdelight.common;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    public static ModConfigSpec COMMON_CONFIG;

    public static final String CATEGORY_SETTINGS = "Settings";
    public static ModConfigSpec.BooleanValue FISHERMAN_BUY_SEAFOOD;
    public static ModConfigSpec.BooleanValue WANDERING_TRADER_PEARLS;
    public static ModConfigSpec.ConfigValue<Integer> MIN_TICKS;
    public static ModConfigSpec.ConfigValue<Integer> MAX_TICKS;
    public static ModConfigSpec.BooleanValue COOK_IN_POTS;
    public static ModConfigSpec.BooleanValue REQUIRE_SURROUNDING_WATER;

    public static final String CATEGORY_WORLD = "world";
    public static ModConfigSpec.BooleanValue GENERATE_PALM_TREES;
    public static ModConfigSpec.IntValue CHANCE_PALM_TREES;
    public static ModConfigSpec.BooleanValue GENERATE_SEASHELLS;
    public static ModConfigSpec.IntValue CHANCE_SEASHELLS;
    public static ModConfigSpec.IntValue CHANCE_SEASHELLS_UNDERWATER;
    public static ModConfigSpec.ConfigValue<Integer> SEASHELL_VARIANT_COUNT;

    public static ModConfigSpec.BooleanValue GENERATE_CRAB_SPAWNS;
    public static ModConfigSpec.IntValue CRAB_SPAWN_WEIGHT;
    public static ModConfigSpec.IntValue CRAB_SPAWN_MIN;
    public static ModConfigSpec.IntValue CRAB_SPAWN_MAX;

    public static final String CATEGORY_NOTES = "notes";
    public static ModConfigSpec.BooleanValue THROWN_NOTES_PERSIST;

    static {
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

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

        COMMON_BUILDER.comment("World generation").push(CATEGORY_WORLD);

        COMMON_BUILDER.comment("Palm Tree generation").push("palm_trees");
        GENERATE_PALM_TREES = COMMON_BUILDER.comment("Should palm trees generate on beaches?")
                .define("generatePalmTrees", true);
        CHANCE_PALM_TREES = COMMON_BUILDER.comment("Chance of generating. Smaller value = more frequent.")
                .defineInRange("chance", 1, 1, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Seashell generation").push("seashells");
        GENERATE_SEASHELLS = COMMON_BUILDER.comment("Should seashells generate on beaches and ocean floors?")
                .define("generateSeashells", true);
        CHANCE_SEASHELLS = COMMON_BUILDER.comment("Chance of generating on beaches. Smaller value = more frequent.")
                .defineInRange("chanceBeach", 2, 1, Integer.MAX_VALUE);
        CHANCE_SEASHELLS_UNDERWATER = COMMON_BUILDER.comment("Chance of generating on ocean floors. Smaller value = more frequent.")
                .defineInRange("chanceUnderwater", 2, 1, Integer.MAX_VALUE);
        SEASHELL_VARIANT_COUNT = COMMON_BUILDER.comment("Number of seashell variants available (must match textures/models/blockstates files)")
                .defineInRange("seashellVariantCount", 7, 1, 7);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Crab spawning").push("crab_spawns");
        GENERATE_CRAB_SPAWNS = COMMON_BUILDER.comment("Should crabs spawn naturally in the world?")
                .define("generateCrabSpawns", true);
        CRAB_SPAWN_WEIGHT = COMMON_BUILDER.comment("Spawn weight. Higher value = more frequent relative to other mobs.")
                .defineInRange("spawnWeight", 10, 1, Integer.MAX_VALUE);
        CRAB_SPAWN_MIN = COMMON_BUILDER.comment("Minimum crabs per spawn attempt.")
                .defineInRange("minCount", 2, 1, Integer.MAX_VALUE);
        CRAB_SPAWN_MAX = COMMON_BUILDER.comment("Maximum crabs per spawn attempt.")
                .defineInRange("maxCount", 3, 1, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Notes and Messages in a Bottle").push(CATEGORY_NOTES);
        THROWN_NOTES_PERSIST = COMMON_BUILDER.comment(
                        "If true, notes thrown into the water in a bottle stay in the world's loot pool",
                        "forever and can be fished up by multiple players. If false (default), each thrown",
                        "note is single-use - once someone finds it, it's gone.")
                .define("thrownNotesPersist", false);
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}