package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.CDFoodValues;
import alabaster.crabbersdelight.common.item.ChumItem;
import alabaster.crabbersdelight.common.item.CrabClawItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.function.Supplier;

import static alabaster.crabbersdelight.common.registry.ModCreativeTabs.addToTab;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrabbersDelight.MODID);

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

    // Bucket Items Helper

    public static Item createMobBucketItem(Supplier<EntityType<? extends Animal>> entityType) {
        return new MobBucketItem(entityType, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1));
    }

    // Spawn Eggs

    public static final RegistryObject<ForgeSpawnEggItem> CRAB_SPAWN_EGG = addToTab(ITEMS.register("crab_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CRAB, 0x2f437c, 0xf48b45, new Item.Properties())));

    // Materials

    public static final RegistryObject<Item> CLAM = addToTab(ITEMS.register("clam",
            () -> new Item(basicItem())));

    public static final RegistryObject<Item> PEARL = addToTab(ITEMS.register("pearl",
            () -> new Item(basicItem())));

    public static final RegistryObject<Item> CAN = addToTab(ITEMS.register("can",
            () -> new Item(basicItem())));

    public static final RegistryObject<Item> CORAL_FRAGMENTS = addToTab(ITEMS.register("coral_fragments",
            () -> new Item(basicItem())));

    // Tools

    public static final RegistryObject<Item> BUCKET_OF_CRAB_CHUM = addToTab(ITEMS.register("bucket_of_crab_chum",
            () -> new ChumItem(basicItem())));

    public static final RegistryObject<Item> BUCKET_OF_CLAWSTER_CHUM = addToTab(ITEMS.register("bucket_of_clawster_chum",
            () -> new ChumItem(basicItem())));

    public static final RegistryObject<Item> BUCKET_OF_CLAM_CHUM = addToTab(ITEMS.register("bucket_of_clam_chum",
            () -> new ChumItem(basicItem())));

    public static final RegistryObject<Item> BUCKET_OF_SHRIMP_CHUM = addToTab(ITEMS.register("bucket_of_shrimp_chum",
            () -> new ChumItem(basicItem())));

    public static final RegistryObject<Item> CRAB_CLAW = addToTab(ITEMS.register("crab_claw",
            () -> new CrabClawItem(basicItem())));

    // Mob Buckets

    public static final RegistryObject<Item> CRAB_BUCKET = addToTab(ITEMS.register("crab_bucket",
            () -> ModItems.createMobBucketItem(ModEntities.CRAB::get)));

    // Raw / Cooked Meats

    public static final RegistryObject<Item> RAW_CRAB = addToTab(ITEMS.register("crab",
            () -> new ConsumableItem(foodItem(CDFoodValues.RAW_CRAB))));

    public static final RegistryObject<Item> COOKED_CRAB = addToTab(ITEMS.register("cooked_crab",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_CRAB))));

    public static final RegistryObject<Item> RAW_CLAWSTER = addToTab(ITEMS.register("clawster",
            () -> new ConsumableItem(foodItem(CDFoodValues.RAW_CLAWSTER))));

    public static final RegistryObject<Item> COOKED_CLAWSTER = addToTab(ITEMS.register("cooked_clawster",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_CLAWSTER))));

    public static final RegistryObject<Item> RAW_SHRIMP = addToTab(ITEMS.register("shrimp",
            () -> new ConsumableItem(foodItem(CDFoodValues.RAW_SHRIMP), true)));

    public static final RegistryObject<Item> COOKED_SHRIMP = addToTab(ITEMS.register("cooked_shrimp",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_SHRIMP))));

    public static final RegistryObject<Item> RAW_CLAM_MEAT = addToTab(ITEMS.register("raw_clam_meat",
            () -> new ConsumableItem(foodItem(CDFoodValues.RAW_CLAM_MEAT))));

    public static final RegistryObject<Item> COOKED_CLAM_MEAT = addToTab(ITEMS.register("cooked_clam_meat",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_CLAM_MEAT))));

    public static final RegistryObject<Item> RAW_SQUID_TENTACLES = addToTab(ITEMS.register("raw_squid_tentacles",
            () -> new ConsumableItem(foodItem(CDFoodValues.RAW_SQUID_TENTACLES))));

    public static final RegistryObject<Item> RAW_GLOW_SQUID_TENTACLES = addToTab(ITEMS.register("raw_glow_squid_tentacles",
            () -> new ConsumableItem(foodItem(CDFoodValues.RAW_SQUID_TENTACLES))));

    public static final RegistryObject<Item> COOKED_SQUID_TENTACLES = addToTab(ITEMS.register("cooked_squid_tentacles",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_SQUID_TENTACLES))));

    public static final RegistryObject<Item> COOKED_GLOW_SQUID_TENTACLES = addToTab(ITEMS.register("cooked_glow_squid_tentacles",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_SQUID_TENTACLES))));

    public static final RegistryObject<Item> RAW_FROG_LEG = addToTab(ITEMS.register("raw_frog_leg",
            () -> new ConsumableItem(foodItem(CDFoodValues.RAW_FROG_LEG))));

    public static final RegistryObject<Item> COOKED_FROG_LEG = addToTab(ITEMS.register("cooked_frog_leg",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_FROG_LEG))));

    // Meals

    public static final RegistryObject<Item> CRAB_CAKES = addToTab(ITEMS.register("crab_cakes",
            () -> new ConsumableItem(foodItem(CDFoodValues.CRAB_CAKES), true)));

    public static final RegistryObject<Item> CRAB_LEGS = addToTab(ITEMS.register("crab_legs",
            () -> new ConsumableItem(foodItem(CDFoodValues.CRAB_LEGS))));

    public static final RegistryObject<Item> FISH_STICK = addToTab(ITEMS.register("fish_stick",
            () -> new ConsumableItem(foodItem(CDFoodValues.FISH_STICK))));

    public static final RegistryObject<Item> SURF_AND_TURF = addToTab(ITEMS.register("surf_and_turf",
            () -> new ConsumableItem(foodItem(CDFoodValues.SURF_AND_TURF), true)));

    public static final RegistryObject<Item> SHRIMP_SKEWER = addToTab(ITEMS.register("shrimp_skewer",
            () -> new ConsumableItem(foodItem(CDFoodValues.SHRIMP_SKEWER))));

    public static final RegistryObject<Item> CLAM_BAKE = addToTab(ITEMS.register("clam_bake",
            () -> new ConsumableItem(foodItem(CDFoodValues.CLAM_BAKE), true)));

    public static final RegistryObject<Item> COOKED_TROPICAL_FISH = addToTab(ITEMS.register("cooked_tropical_fish",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_TROPICAL_FISH))));

    public static final RegistryObject<Item> PUFFERFISH_SLICE = addToTab(ITEMS.register("pufferfish_slice",
            () -> new ConsumableItem(foodItem(CDFoodValues.PUFFERFISH_SLICE), true)));

    public static final RegistryObject<Item> COOKED_PUFFERFISH_SLICE = addToTab(ITEMS.register("cooked_pufferfish_slice",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_PUFFERFISH_SLICE))));

    public static final RegistryObject<Item> TROPICAL_FISH_SLICE = addToTab(ITEMS.register("tropical_fish_slice",
            () -> new ConsumableItem(foodItem(CDFoodValues.TROPICAL_FISH_SLICE))));

    public static final RegistryObject<Item> COOKED_TROPICAL_FISH_SLICE = addToTab(ITEMS.register("cooked_tropical_fish_slice",
            () -> new ConsumableItem(foodItem(CDFoodValues.COOKED_TROPICAL_FISH_SLICE))));

    public static final RegistryObject<Item> STUFFED_NAUTILUS_SHELL = addToTab(ITEMS.register("stuffed_nautilus_shell",
            () -> new ConsumableItem(shellFoodItem(CDFoodValues.STUFFED_NAUTILUS_SHELL), true)));

    //public static final RegistryObject<Item> SOGGY_FLESH = addToTab(ITEMS.register("soggy_flesh",
    //        () -> new ConsumableItem(foodItem(CDFoodValues.SOGGY_FLESH), true)));

    //public static final RegistryObject<Item> ARID_FLESH = addToTab(ITEMS.register("arid_flesh",
    //        () -> new ConsumableItem(foodItem(CDFoodValues.ARID_FLESH), true)));

    public static final RegistryObject<Item> SQUID_KEBOB = addToTab(ITEMS.register("squid_kebob",
            () -> new ConsumableItem(foodItem(CDFoodValues.SQUID_KEBOB))));

    public static final RegistryObject<Item> FROG_LEG_KEBOB = addToTab(ITEMS.register("frog_leg_kebob",
            () -> new ConsumableItem(foodItem(CDFoodValues.FROG_LEG_KEBOB))));

    public static final RegistryObject<Item> JAR_OF_PICKLES = addToTab(ITEMS.register("jar_of_pickles",
            () -> new ConsumableItem(foodItem(CDFoodValues.JAR_OF_PICKLES).craftRemainder(Items.GLASS_BOTTLE))));

    // Drink Food Items

    public static final RegistryObject<Item> KELP_SHAKE = addToTab(ITEMS.register("kelp_shake",
            () -> new DrinkableItem(drinkItem(CDFoodValues.KELP_SHAKE), true)));

    public static final RegistryObject<Item> SEA_PICKLE_JUICE = addToTab(ITEMS.register("sea_pickle_juice",
            () -> new DrinkableItem(drinkItem(CDFoodValues.SEA_PICKLE_JUICE), true)));

    // Bowl Food Items

    public static final RegistryObject<Item> BISQUE = addToTab(ITEMS.register("bisque",
            () -> new ConsumableItem(bowlFoodItem(CDFoodValues.BISQUE), true)));

    public static final RegistryObject<Item> SEAFOOD_GUMBO = addToTab(ITEMS.register("seafood_gumbo",
            () -> new ConsumableItem(bowlFoodItem(CDFoodValues.SEAFOOD_GUMBO), true)));

    public static final RegistryObject<Item> CLAM_CHOWDER = addToTab(ITEMS.register("clam_chowder",
            () -> new ConsumableItem(bowlFoodItem(CDFoodValues.CLAM_CHOWDER), true)));

    public static final RegistryObject<Item> SHRIMP_FRIED_RICE = addToTab(ITEMS.register("shrimp_fried_rice",
            () -> new ConsumableItem(bowlFoodItem(CDFoodValues.SHRIMP_FRIED_RICE), true)));

    public static final RegistryObject<Item> CORAL_CRUNCH = addToTab(ITEMS.register("coral_crunch",
            () -> new ConsumableItem(bowlFoodItem(CDFoodValues.CORAL_CRUNCH), true)));

    // Storage Blocks

    public static final RegistryObject<Item> CRAB_BARREL = addToTab(ITEMS.register("crab_barrel",
            () -> new BlockItem(ModBlocks.CRAB_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> CLAM_BARREL = addToTab(ITEMS.register("clam_barrel",
            () -> new BlockItem(ModBlocks.CLAM_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> CLAWSTER_BARREL = addToTab(ITEMS.register("clawster_barrel",
            () -> new BlockItem(ModBlocks.CLAWSTER_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> SHRIMP_BARREL = addToTab(ITEMS.register("shrimp_barrel",
            () -> new BlockItem(ModBlocks.SHRIMP_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> COD_BARREL = addToTab(ITEMS.register("cod_barrel",
            () -> new BlockItem(ModBlocks.COD_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> SALMON_BARREL = addToTab(ITEMS.register("salmon_barrel",
            () -> new BlockItem(ModBlocks.SALMON_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> PUFFERFISH_BARREL = addToTab(ITEMS.register("pufferfish_barrel",
            () -> new BlockItem(ModBlocks.PUFFERFISH_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> TROPICAL_FISH_BARREL = addToTab(ITEMS.register("tropical_fish_barrel",
            () -> new BlockItem(ModBlocks.TROPICAL_FISH_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> SQUID_BARREL = addToTab(ITEMS.register("squid_barrel",
            () -> new BlockItem(ModBlocks.SQUID_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> GLOW_SQUID_BARREL = addToTab(ITEMS.register("glow_squid_barrel",
            () -> new BlockItem(ModBlocks.GLOW_SQUID_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> FROG_LEG_BARREL = addToTab(ITEMS.register("frog_leg_barrel",
            () -> new BlockItem(ModBlocks.FROG_LEG_BARREL.get(), basicItem())));

    //public static final RegistryObject<Item> LANTERNFISH_BARREL = addToTab(ITEMS.register("lanternfish_barrel",
            //() -> new BlockItem(ModBlocks.LANTERNFISH_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> NAUTILUS_SHELL_BLOCK = addToTab(ITEMS.register("nautilus_shell_block",
            () -> new BlockItem(ModBlocks.NAUTILUS_SHELL_BLOCK.get(), basicItem())));

    public static final RegistryObject<Item> PEARL_BLOCK = addToTab(ITEMS.register("pearl_block",
            () -> new BlockItem(ModBlocks.PEARL_BLOCK.get(), basicItem())));

    // Crab Trap

    public static final RegistryObject<Item> CRAB_TRAP = addToTab(ITEMS.register("crab_trap",
            () -> new BlockItem(ModBlocks.CRAB_TRAP.get(), basicItem())));

}
