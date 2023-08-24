package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.CDFoodValues;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import static alabaster.crabbersdelight.common.registry.ModCreativeTabs.addToTab;
import static vectorwing.farmersdelight.common.registry.ModItems.bowlFoodItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrabbersDelight.MODID);

    // Basic Items
    public static Item.Properties basicItem() {
        return new Item.Properties();
    }

    // Foods
    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food);
    }

    // Drinks
    public static Item.Properties drinkItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
    }

    // Materials
    public static final RegistryObject<Item> PEARL = addToTab(ITEMS.register("pearl",
            () -> new Item(basicItem())));

    // Foods
    public static final RegistryObject<Item> RAW_CRAB = addToTab(ITEMS.register("crab",
            () -> new Item(foodItem(CDFoodValues.RAW_CRAB))));

    public static final RegistryObject<Item> COOKED_CRAB = addToTab(ITEMS.register("cooked_crab",
            () -> new Item(foodItem(CDFoodValues.COOKED_CRAB))));

    public static final RegistryObject<Item> RAW_CLAWSTER = addToTab(ITEMS.register("clawster",
            () -> new Item(foodItem(CDFoodValues.RAW_CLAWSTER))));

    public static final RegistryObject<Item> COOKED_CLAWSTER = addToTab(ITEMS.register("cooked_clawster",
            () -> new Item(foodItem(CDFoodValues.COOKED_CLAWSTER))));

    public static final RegistryObject<Item> RAW_SHRIMP = addToTab(ITEMS.register("shrimp",
            () -> new Item(foodItem(CDFoodValues.RAW_SHRIMP))));

    public static final RegistryObject<Item> COOKED_SHRIMP = addToTab(ITEMS.register("cooked_shrimp",
            () -> new Item(foodItem(CDFoodValues.COOKED_SHRIMP))));

    public static final RegistryObject<Item> CLAM = addToTab(ITEMS.register("clam",
            () -> new Item(basicItem())));

    public static final RegistryObject<Item> RAW_CLAM_MEAT = addToTab(ITEMS.register("raw_clam_meat",
            () -> new Item(foodItem(CDFoodValues.RAW_CLAM_MEAT))));

    public static final RegistryObject<Item> COOKED_CLAM_MEAT = addToTab(ITEMS.register("cooked_clam_meat",
            () -> new Item(foodItem(CDFoodValues.COOKED_CLAM_MEAT))));

    public static final RegistryObject<Item> KELP_SHAKE = addToTab(ITEMS.register("kelp_shake",
            () -> new DrinkableItem(drinkItem(CDFoodValues.KELP_SHAKE))));

    public static final RegistryObject<Item> CRAB_CAKES = addToTab(ITEMS.register("crab_cakes",
            () -> new Item(foodItem(CDFoodValues.CRAB_CAKES))));

    public static final RegistryObject<Item> CRAB_LEGS = addToTab(ITEMS.register("crab_legs",
            () -> new Item(foodItem(CDFoodValues.CRAB_LEGS))));

    public static final RegistryObject<Item> FISH_STICK = addToTab(ITEMS.register("fish_stick",
            () -> new Item(foodItem(CDFoodValues.FISH_STICK))));

    public static final RegistryObject<Item> SURF_AND_TURF = addToTab(ITEMS.register("surf_and_turf",
            () -> new Item(foodItem(CDFoodValues.SURF_AND_TURF))));

    public static final RegistryObject<Item> SHRIMP_SKEWER = addToTab(ITEMS.register("shrimp_skewer",
            () -> new Item(foodItem(CDFoodValues.SHRIMP_SKEWER))));

    public static final RegistryObject<Item> CLAM_BAKE = addToTab(ITEMS.register("clam_bake",
            () -> new Item(foodItem(CDFoodValues.CLAM_BAKE))));

    public static final RegistryObject<Item> CLAM_CHOWDER = addToTab(ITEMS.register("clam_chowder",
            () -> new Item(foodItem(CDFoodValues.CLAM_CHOWDER))));

    public static final RegistryObject<Item> COOKED_TROPICAL_FISH = addToTab(ITEMS.register("cooked_tropical_fish",
            () -> new Item(foodItem(CDFoodValues.COOKED_TROPICAL_FISH))));

    public static final RegistryObject<Item> TROPICAL_FISH_SLICE = addToTab(ITEMS.register("tropical_fish_slice",
            () -> new Item(foodItem(CDFoodValues.TROPICAL_FISH_SLICE))));

    public static final RegistryObject<Item> COOKED_TROPICAL_FISH_SLICE = addToTab(ITEMS.register("cooked_tropical_fish_slice",
            () -> new Item(foodItem(CDFoodValues.COOKED_TROPICAL_FISH_SLICE))));

    public static final RegistryObject<Item> BISQUE = addToTab(ITEMS.register("bisque",
            () -> new Item(foodItem(CDFoodValues.BISQUE))));

    public static final RegistryObject<Item> SEAFOOD_GUMBO = addToTab(ITEMS.register("seafood_gumbo",
            () -> new Item(foodItem(CDFoodValues.SEAFOOD_GUMBO))));

    public static final RegistryObject<Item> STUFFED_NAUTILUS_SHELL = addToTab(ITEMS.register("stuffed_nautilus_shell",
            () -> new Item(foodItem(CDFoodValues.STUFFED_NAUTILUS_SHELL))));

    // Storage Blocks
    public static final RegistryObject<Item> CRAB_BARREL = addToTab(ITEMS.register("crab_barrel",
            () -> new BlockItem(ModBlocks.CRAB_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> CLAM_BARREL = addToTab(ITEMS.register("clam_barrel",
            () -> new BlockItem(ModBlocks.CLAM_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> CLAWSTER_BARREL = addToTab(ITEMS.register("clawster_barrel",
            () -> new BlockItem(ModBlocks.CLAWSTER_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> SHRIMP_BARREL = addToTab(ITEMS.register("shrimp_barrel",
            () -> new BlockItem(ModBlocks.SHRIMP_BARREL.get(), basicItem())));

    public static final RegistryObject<Item> NAUTILUS_SHELL_BLOCK = addToTab(ITEMS.register("nautilus_shell_block",
            () -> new BlockItem(ModBlocks.NAUTILUS_SHELL_BLOCK.get(), basicItem())));

    public static final RegistryObject<Item> CLAWSTER_FEAST_BLOCK = addToTab(ITEMS.register("clawster_feast_block",
            () -> new BlockItem(ModBlocks.CLAWSTER_FEAST_BLOCK.get(), basicItem().stacksTo(1))));
    public static final RegistryObject<Item> CLAWSTER_FEAST = addToTab(ITEMS.register("clawster_feast",
            () -> new ConsumableItem(bowlFoodItem(CDFoodValues.CLAWSTER_FEAST), true)));

    // Crab Trap
    public static final RegistryObject<Item> CRAB_TRAP = addToTab(ITEMS.register("crab_trap",
            () -> new BlockItem(ModBlocks.CRAB_TRAP.get(), basicItem())));
}
