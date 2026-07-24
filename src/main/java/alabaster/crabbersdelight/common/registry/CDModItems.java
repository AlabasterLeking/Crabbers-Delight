package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.CDFoodValues;
import alabaster.crabbersdelight.common.entity.boat.CDBoatEntity;
import alabaster.crabbersdelight.common.item.*;
import com.google.common.collect.Sets;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.item.MilkBottleItem;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class CDModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(CrabbersDelight.MODID);
    public static LinkedHashSet<Supplier<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    public static Supplier<Item> registerWithTab(final String name, final Supplier<Item> supplier) {
        Supplier<Item> block = ITEMS.register(name,supplier);
        CREATIVE_TAB_ITEMS.add(block);
        return block;
    }

    // Basic Items Helper
    public static Item.Properties basicItem() {
        return new Item.Properties();
    }

    // Food Items Helper
    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food);
    }

    // Bowl Items Helper
    public static Item.Properties bowlFoodItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.BOWL).stacksTo(16);
    }

    // Drink Items Helper
    public static Item.Properties drinkItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
    }

    // Shell Items Helper
    public static Item.Properties shellFoodItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.NAUTILUS_SHELL);
    }

    // Spawn Eggs
    public static final Supplier<Item> CRAB_SPAWN_EGG = registerWithTab("crab_spawn_egg",
            () -> new DeferredSpawnEggItem(CDModEntities.CRAB, 0x2f437c, 0xf48b45,
                    new Item.Properties()));

    // Mob Buckets
    public static final Supplier<Item> CRAB_BUCKET = registerWithTab("crab_bucket",
            () -> new MobBucketItem(
                    CDModEntities.CRAB.get(),
                    Fluids.WATER,
                    SoundEvents.BUCKET_EMPTY_FISH,
                    new Item.Properties().stacksTo(1)));

    // Materials
    public static final Supplier<Item> CLAM = registerWithTab("clam",
            () -> new Item(basicItem()));
    public static final Supplier<Item> PEARL = registerWithTab("pearl",
            () -> new Item(basicItem()));
    public static final Supplier<Item> CAN = registerWithTab("can",
            () -> new Item(basicItem()));
    public static final Supplier<Item> CORAL_FRAGMENTS = registerWithTab("coral_fragments",
            () -> new Item(basicItem()));
    public static final Supplier<Item> FISH_BONES = registerWithTab("fish_bones",
            () -> new Item(basicItem()));

    // Tools
    public static final Supplier<Item> BUCKET_OF_CRAB_CHUM = registerWithTab("bucket_of_crab_chum",
            () -> new ChumItem(basicItem()));
    public static final Supplier<Item> BUCKET_OF_CLAWSTER_CHUM = registerWithTab("bucket_of_clawster_chum",
            () -> new ChumItem(basicItem()));
    public static final Supplier<Item> BUCKET_OF_CLAM_CHUM = registerWithTab("bucket_of_clam_chum",
            () -> new ChumItem(basicItem()));
    public static final Supplier<Item> BUCKET_OF_SHRIMP_CHUM = registerWithTab("bucket_of_shrimp_chum",
            () -> new ChumItem(basicItem()));
    public static final Supplier<Item> CRAB_CLAW = registerWithTab("crab_claw",
            () -> new CrabClawItem(basicItem()));
    public static final Supplier<Item> PEARL_NECKLACE = registerWithTab("pearl_necklace",
            () -> new PearlNecklaceItem(basicItem().stacksTo(1)));
    public static final Supplier<Item> COCONUT_HELMET = registerWithTab("coconut_helmet",
            () -> new CoconutHelmetItem(basicItem().stacksTo(1)));

    // Palm
    public static final Supplier<Item> PALM_LOG = registerWithTab("palm_log",
            () -> new BlockItem(CDModBlocks.PALM_LOG.get(), basicItem()));
    public static final Supplier<Item> PALM_WOOD = registerWithTab("palm_wood",
            () -> new BlockItem(CDModBlocks.PALM_WOOD.get(), basicItem()));
    public static final Supplier<Item> STRIPPED_PALM_LOG = registerWithTab("stripped_palm_log",
            () -> new BlockItem(CDModBlocks.STRIPPED_PALM_LOG.get(), basicItem()));
    public static final Supplier<Item> STRIPPED_PALM_WOOD = registerWithTab("stripped_palm_wood",
            () -> new BlockItem(CDModBlocks.STRIPPED_PALM_WOOD.get(), basicItem()));
    public static final Supplier<Item> PALM_PLANKS = registerWithTab("palm_planks",
            () -> new BlockItem(CDModBlocks.PALM_PLANKS.get(), basicItem()));
    public static final Supplier<Item> PALM_STAIRS = registerWithTab("palm_stairs",
            () -> new BlockItem(CDModBlocks.PALM_STAIRS.get(), basicItem()));
    public static final Supplier<Item> PALM_SLAB = registerWithTab("palm_slab",
            () -> new BlockItem(CDModBlocks.PALM_SLAB.get(), basicItem()));
    public static final Supplier<Item> PALM_FENCE = registerWithTab("palm_fence",
            () -> new BlockItem(CDModBlocks.PALM_FENCE.get(), basicItem()));
    public static final Supplier<Item> PALM_FENCE_GATE = registerWithTab("palm_fence_gate",
            () -> new BlockItem(CDModBlocks.PALM_FENCE_GATE.get(), basicItem()));
    public static final Supplier<Item> PALM_DOOR = registerWithTab("palm_door",
            () -> new BlockItem(CDModBlocks.PALM_DOOR.get(), basicItem()));
    public static final Supplier<Item> PALM_TRAPDOOR = registerWithTab("palm_trapdoor_bottom",
            () -> new BlockItem(CDModBlocks.PALM_TRAPDOOR.get(), basicItem()));
    public static final Supplier<Item> PALM_PRESSURE_PLATE = registerWithTab("palm_pressure_plate",
            () -> new BlockItem(CDModBlocks.PALM_PRESSURE_PLATE.get(), basicItem()));
    public static final Supplier<Item> PALM_BUTTON = registerWithTab("palm_button",
            () -> new BlockItem(CDModBlocks.PALM_BUTTON.get(), basicItem()));
    public static final Supplier<Item> PALM_LEAVES = registerWithTab("palm_leaves",
            () -> new BlockItem(CDModBlocks.PALM_LEAVES.get(), basicItem()));
    public static final Supplier<Item> PALM_SAPLING = registerWithTab("palm_sapling",
            () -> new BlockItem(CDModBlocks.PALM_SAPLING.get(), basicItem()));
    public static final Supplier<Item> PALM_SIGN = registerWithTab("palm_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), CDModBlocks.PALM_SIGN.get(), CDModBlocks.PALM_WALL_SIGN.get()));
    public static final Supplier<Item> PALM_HANGING_SIGN = registerWithTab("palm_hanging_sign",
            () -> new HangingSignItem(CDModBlocks.PALM_HANGING_SIGN.get(), CDModBlocks.PALM_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final Supplier<Item> PALM_BOAT = registerWithTab("palm_boat",
            () -> new CDBoatItem(false, CDBoatEntity.Type.PALM, new Item.Properties()));
    public static final Supplier<Item> PALM_CHEST_BOAT = registerWithTab("palm_chest_boat",
            () -> new CDBoatItem(true, CDBoatEntity.Type.PALM, new Item.Properties()));
    public static final Supplier<Item> PALM_CABINET = registerWithTab("palm_cabinet",
            () -> new BlockItem(CDModBlocks.PALM_CABINET.get(), basicItem()));

    // Coconut
    public static final Supplier<Item> COCONUT = registerWithTab("coconut",
            () -> new BlockItem(CDModBlocks.COCONUT.get(), basicItem()));
    public static final Supplier<Item> COCONUT_HALVE = registerWithTab("coconut_halve",
            () -> new ConsumableItem(foodItem(CDFoodValues.COCONUT_HALVE)));
    public static final Supplier<Item> COCONUT_MILK = registerWithTab("coconut_milk",
            () -> new MilkBottleItem(drinkItem(CDFoodValues.COCONUT_MILK)));
    public static final Supplier<Item> COCONUT_PUDDING = registerWithTab("coconut_pudding",
            () -> new ConsumableItem(foodItem(CDFoodValues.COCONUT_PUDDING).craftRemainder(Items.GLASS_BOTTLE)));

    public static final Supplier<Item> SEASHELLS = registerWithTab("seashells",
            () -> new BlockItem(CDModBlocks.SEASHELLS.get(), basicItem()));

    // Raw / Cooked Meats
    public static final Supplier<Item> RAW_CRAB = registerWithTab("crab",
            () -> new ConsumableItem(foodItem(CDFoodValues.RAW_CRAB)));
    public static final Supplier<Item> COOKED_CRAB = registerWithTab("cooked_crab",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_CRAB)));

    public static final Supplier<Item> RAW_CLAWSTER = registerWithTab("clawster",
            () -> new ConsumableItem(foodItem(CDFoodValues.RAW_CLAWSTER)));
    public static final Supplier<Item> COOKED_CLAWSTER = registerWithTab("cooked_clawster",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_CLAWSTER)));

    public static final Supplier<Item> RAW_SHRIMP = registerWithTab("shrimp",
            () -> new ConsumableItem(foodItem(CDFoodValues.RAW_SHRIMP), true));
    public static final Supplier<Item> COOKED_SHRIMP = registerWithTab("cooked_shrimp",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_SHRIMP)));

    public static final Supplier<Item> RAW_CLAM_MEAT = registerWithTab("raw_clam_meat",
            () -> new ConsumableItem(foodItem(CDFoodValues.RAW_CLAM_MEAT)));
    public static final Supplier<Item> COOKED_CLAM_MEAT = registerWithTab("cooked_clam_meat",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_CLAM_MEAT)));

    public static final Supplier<Item> RAW_SQUID_TENTACLES = registerWithTab("raw_squid_tentacles",
            () -> new ConsumableItem(foodItem(CDFoodValues.RAW_SQUID_TENTACLES)));
    public static final Supplier<Item> RAW_GLOW_SQUID_TENTACLES = registerWithTab("raw_glow_squid_tentacles",
            () -> new ConsumableItem(foodItem(CDFoodValues.RAW_SQUID_TENTACLES)));
    public static final Supplier<Item> COOKED_SQUID_TENTACLES = registerWithTab("cooked_squid_tentacles",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_SQUID_TENTACLES)));
    public static final Supplier<Item> COOKED_GLOW_SQUID_TENTACLES = registerWithTab("cooked_glow_squid_tentacles",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_SQUID_TENTACLES)));

    public static final Supplier<Item> RAW_FROG_LEG = registerWithTab("raw_frog_leg",
            () -> new ConsumableItem(foodItem(CDFoodValues.RAW_FROG_LEG)));
    public static final Supplier<Item> COOKED_FROG_LEG = registerWithTab("cooked_frog_leg",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_FROG_LEG)));

    // Meals
    public static final Supplier<Item> CRAB_CAKES = registerWithTab("crab_cakes",
            () -> new ConsumableItem(foodItem(CDFoodValues.CRAB_CAKES), true));
    public static final Supplier<Item> CRAB_LEGS = registerWithTab("crab_legs",
            () -> new ConsumableItem(foodItem(CDFoodValues.CRAB_LEGS)));
    public static final Supplier<Item> FISH_STICK = registerWithTab("fish_stick",
            () -> new ConsumableItem(foodItem(CDFoodValues.FISH_STICK)));
    public static final Supplier<Item> SURF_AND_TURF = registerWithTab("surf_and_turf",
            () -> new ConsumableItem(foodItem(CDFoodValues.SURF_AND_TURF), true));
    public static final Supplier<Item> SHRIMP_SKEWER = registerWithTab("shrimp_skewer",
            () -> new ConsumableItem(foodItem(CDFoodValues.SHRIMP_SKEWER)));
    public static final Supplier<Item> CLAM_BAKE = registerWithTab("clam_bake",
            () -> new ConsumableItem(foodItem(CDFoodValues.CLAM_BAKE), true));
    public static final Supplier<Item> COOKED_TROPICAL_FISH = registerWithTab("cooked_tropical_fish",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_TROPICAL_FISH)));

    public static final Supplier<Item> PUFFERFISH_SLICE = registerWithTab("pufferfish_slice",
            () -> new ConsumableItem(foodItem(CDFoodValues.PUFFERFISH_SLICE), true));
    public static final Supplier<Item> COOKED_PUFFERFISH_SLICE = registerWithTab("cooked_pufferfish_slice",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_PUFFERFISH_SLICE)));

    public static final Supplier<Item> TROPICAL_FISH_SLICE = registerWithTab("tropical_fish_slice",
            () -> new ConsumableItem(foodItem(CDFoodValues.TROPICAL_FISH_SLICE)));
    public static final Supplier<Item> COOKED_TROPICAL_FISH_SLICE = registerWithTab("cooked_tropical_fish_slice",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_TROPICAL_FISH_SLICE)));

    public static final Supplier<Item> STUFFED_NAUTILUS_SHELL = registerWithTab("stuffed_nautilus_shell",
            () -> new ConsumableItem(shellFoodItem(CDFoodValues.STUFFED_NAUTILUS_SHELL), true));
    public static final Supplier<Item> SQUID_KEBAB = registerWithTab("squid_kebab",
            () -> new ConsumableItem(foodItem(CDFoodValues.SQUID_KEBAB)));
    public static final Supplier<Item> FROG_LEG_KEBAB = registerWithTab("frog_leg_kebab",
            () -> new ConsumableItem(foodItem(CDFoodValues.FROG_LEG_KEBAB)));
    public static final Supplier<Item> JAR_OF_PICKLES = registerWithTab("jar_of_pickles",
            () -> new ConsumableItem(foodItem(CDFoodValues.JAR_OF_PICKLES).craftRemainder(Items.GLASS_BOTTLE)));

    // Drink Food Items
    public static final Supplier<Item> KELP_SHAKE = registerWithTab("kelp_shake",
            () -> new DrinkableItem(drinkItem(CDFoodValues.KELP_SHAKE), true));
    public static final Supplier<Item> SEA_PICKLE_JUICE = registerWithTab("sea_pickle_juice",
            () -> new DrinkableItem(drinkItem(CDFoodValues.SEA_PICKLE_JUICE), true));

    // Bowl Food Items
    public static final Supplier<Item> BISQUE = registerWithTab("bisque",
            () -> new ConsumableItem(bowlFoodItem(CDFoodValues.BISQUE), true));
    public static final Supplier<Item> SEAFOOD_GUMBO = registerWithTab("seafood_gumbo",
            () -> new ConsumableItem(bowlFoodItem(CDFoodValues.SEAFOOD_GUMBO), true));
    public static final Supplier<Item> CLAM_CHOWDER = registerWithTab("clam_chowder",
            () -> new ConsumableItem(bowlFoodItem(CDFoodValues.CLAM_CHOWDER), true));
    public static final Supplier<Item> SHRIMP_FRIED_RICE = registerWithTab("shrimp_fried_rice",
            () -> new ConsumableItem(bowlFoodItem(CDFoodValues.SHRIMP_FRIED_RICE), true));
    public static final Supplier<Item> CORAL_CRUNCH = registerWithTab("coral_crunch",
            () -> new ConsumableItem(bowlFoodItem(CDFoodValues.CORAL_CRUNCH), true));

    // Storage Blocks
    public static final Supplier<Item> CRAB_BARREL = registerWithTab("crab_barrel",
            () -> new BlockItem(CDModBlocks.CRAB_BARREL.get(), basicItem()));
    public static final Supplier<Item> CLAM_BARREL = registerWithTab("clam_barrel",
            () -> new BlockItem(CDModBlocks.CLAM_BARREL.get(), basicItem()));
    public static final Supplier<Item> CLAWSTER_BARREL = registerWithTab("clawster_barrel",
            () -> new BlockItem(CDModBlocks.CLAWSTER_BARREL.get(), basicItem()));
    public static final Supplier<Item> SHRIMP_BARREL = registerWithTab("shrimp_barrel",
            () -> new BlockItem(CDModBlocks.SHRIMP_BARREL.get(), basicItem()));
    public static final Supplier<Item> COD_BARREL = registerWithTab("cod_barrel",
            () -> new BlockItem(CDModBlocks.COD_BARREL.get(), basicItem()));
    public static final Supplier<Item> SALMON_BARREL = registerWithTab("salmon_barrel",
            () -> new BlockItem(CDModBlocks.SALMON_BARREL.get(), basicItem()));
    public static final Supplier<Item> PUFFERFISH_BARREL = registerWithTab("pufferfish_barrel",
            () -> new BlockItem(CDModBlocks.PUFFERFISH_BARREL.get(), basicItem()));
    public static final Supplier<Item> TROPICAL_FISH_BARREL = registerWithTab("tropical_fish_barrel",
            () -> new BlockItem(CDModBlocks.TROPICAL_FISH_BARREL.get(), basicItem()));
    public static final Supplier<Item> SQUID_BARREL = registerWithTab("squid_barrel",
            () -> new BlockItem(CDModBlocks.SQUID_BARREL.get(), basicItem()));
    public static final Supplier<Item> GLOW_SQUID_BARREL = registerWithTab("glow_squid_barrel",
            () -> new BlockItem(CDModBlocks.GLOW_SQUID_BARREL.get(), basicItem()));
    public static final Supplier<Item> FROG_LEG_BARREL = registerWithTab("frog_leg_barrel",
            () -> new BlockItem(CDModBlocks.FROG_LEG_BARREL.get(), basicItem()));
    public static final Supplier<Item> COCONUT_CRATE = registerWithTab("coconut_crate",
            () -> new BlockItem(CDModBlocks.COCONUT_CRATE.get(), basicItem()));
    //public static final Supplier<Item> LANTERNFISH_BARREL = registerWithTab("lanternfish_barrel",
    //() -> new BlockItem(ModBlocks.LANTERNFISH_BARREL.get(), basicItem()));
    public static final Supplier<Item> NAUTILUS_SHELL_BLOCK = registerWithTab("nautilus_shell_block",
            () -> new BlockItem(CDModBlocks.NAUTILUS_SHELL_BLOCK.get(), basicItem()));
    public static final Supplier<Item> PEARL_BLOCK = registerWithTab("pearl_block",
            () -> new BlockItem(CDModBlocks.PEARL_BLOCK.get(), basicItem()));
    public static final Supplier<Item> SCUTE_BLOCK = registerWithTab("scute_block",
            () -> new BlockItem(CDModBlocks.SCUTE_BLOCK.get(), basicItem()));
    public static final Supplier<Item> SEA_PICKLE_CRATE = registerWithTab("sea_pickle_crate",
            () -> new BlockItem(CDModBlocks.SEA_PICKLE_CRATE.get(), basicItem()));

    // Crab Trap
    public static final Supplier<Item> CRAB_TRAP = registerWithTab("crab_trap",
            () -> new BlockItem(CDModBlocks.CRAB_TRAP.get(), basicItem()));

    public static final Supplier<Item> FISH_PLAQUE = registerWithTab("fish_plaque",
            () -> new BlockItem(CDModBlocks.FISH_PLAQUE.get(), basicItem()));

    // Notes + Messages in a Bottle
    public static final Supplier<Item> NOTE = registerWithTab("note",
            () -> new NoteItem(basicItem().stacksTo(16)));
    public static final Supplier<Item> SIGNED_NOTE = registerWithTab("signed_note",
            () -> new SignedNoteItem(basicItem().stacksTo(16)));
    public static final Supplier<Item> MESSAGE_BOTTLE = registerWithTab("message_bottle",
            () -> new MessageBottleItem(basicItem()));
    public static final Supplier<Item> BOTTLED_NOTE = registerWithTab("bottled_note",
            () -> new BottledNoteItem(basicItem().stacksTo(16)));

}