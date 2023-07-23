package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import alabaster.crabbersdelight.common.CDFoodValues;
import vectorwing.farmersdelight.common.item.DrinkableItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrabbersDelight.MODID);

    // Basic Items
    public static Item.Properties basicItem() {
        return new Item.Properties().tab(CrabbersDelight.TAB);
    }

    // Foods
    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food).tab(CrabbersDelight.TAB);
    }

    // Drinks
    public static Item.Properties drinkItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(CrabbersDelight.TAB);
    }

    // Basic Items
    public static final RegistryObject<Item> PEARL = ITEMS.register("pearl",
            () -> new Item(basicItem()));

    // Foods
    public static final RegistryObject<Item> RAW_CRAB = ITEMS.register("raw_crab",
            () -> new Item(foodItem(CDFoodValues.RAW_CRAB)));

    public static final RegistryObject<Item> COOKED_CRAB = ITEMS.register("cooked_crab",
            () -> new Item(foodItem(CDFoodValues.COOKED_CRAB)));

    public static final RegistryObject<Item> RAW_CLAWSTER = ITEMS.register("raw_clawster",
            () -> new Item(foodItem(CDFoodValues.RAW_CLAWSTER)));

    public static final RegistryObject<Item> COOKED_CLAWSTER = ITEMS.register("cooked_clawster",
            () -> new Item(foodItem(CDFoodValues.COOKED_CLAWSTER)));

    public static final RegistryObject<Item> RAW_SHRIMP = ITEMS.register("raw_shrimp",
            () -> new Item(foodItem(CDFoodValues.RAW_SHRIMP)));

    public static final RegistryObject<Item> COOKED_SHRIMP = ITEMS.register("cooked_shrimp",
            () -> new Item(foodItem(CDFoodValues.COOKED_SHRIMP)));

    public static final RegistryObject<Item> CLAM = ITEMS.register("clam",
            () -> new Item(basicItem()));

    public static final RegistryObject<Item> RAW_CLAM_MEAT = ITEMS.register("raw_clam_meat",
            () -> new Item(foodItem(CDFoodValues.RAW_CLAM_MEAT)));

    public static final RegistryObject<Item> COOKED_CLAM_MEAT = ITEMS.register("cooked_clam_meat",
            () -> new Item(foodItem(CDFoodValues.COOKED_CLAM_MEAT)));

    public static final RegistryObject<Item> KELP_SHAKE = ITEMS.register("kelp_shake",
            () -> new DrinkableItem(drinkItem(CDFoodValues.KELP_SHAKE)));

    public static final RegistryObject<Item> CRAB_CAKES = ITEMS.register("crab_cakes",
            () -> new Item(foodItem(CDFoodValues.CRAB_CAKES)));

    public static final RegistryObject<Item> CRAB_LEGS = ITEMS.register("crab_legs",
            () -> new Item(foodItem(CDFoodValues.CRAB_LEGS)));

    public static final RegistryObject<Item> FISH_STICK = ITEMS.register("fish_stick",
            () -> new Item(foodItem(CDFoodValues.FISH_STICK)));

    public static final RegistryObject<Item> SURF_AND_TURF = ITEMS.register("surf_and_turf",
            () -> new Item(foodItem(CDFoodValues.SURF_AND_TURF)));

    public static final RegistryObject<Item> SHRIMP_SKEWER = ITEMS.register("shrimp_skewer",
            () -> new Item(foodItem(CDFoodValues.SHRIMP_SKEWER)));

    public static final RegistryObject<Item> CLAM_BAKE = ITEMS.register("clam_bake",
            () -> new Item(foodItem(CDFoodValues.CLAM_BAKE)));

    public static final RegistryObject<Item> CLAM_CHOWDER = ITEMS.register("clam_chowder",
            () -> new Item(foodItem(CDFoodValues.CLAM_CHOWDER)));

    public static final RegistryObject<Item> COOKED_TROPICAL_FISH = ITEMS.register("cooked_tropical_fish",
            () -> new Item(foodItem(CDFoodValues.COOKED_TROPICAL_FISH)));

    public static final RegistryObject<Item> TROPICAL_FISH_SLICE = ITEMS.register("tropical_fish_slice",
            () -> new Item(foodItem(CDFoodValues.TROPICAL_FISH_SLICE)));

    public static final RegistryObject<Item> COOKED_TROPICAL_FISH_SLICE = ITEMS.register("cooked_tropical_fish_slice",
            () -> new Item(foodItem(CDFoodValues.COOKED_TROPICAL_FISH_SLICE)));


    // Barrels
    public static final RegistryObject<Item> CRAB_BARREL = ITEMS.register("crab_barrel",
            () -> new BlockItem(ModBlocks.CRAB_BARREL.get(), basicItem()));

    public static final RegistryObject<Item> CLAM_BARREL = ITEMS.register("clam_barrel",
            () -> new BlockItem(ModBlocks.CLAM_BARREL.get(), basicItem()));

    public static final RegistryObject<Item> CLAWSTER_BARREL = ITEMS.register("clawster_barrel",
            () -> new BlockItem(ModBlocks.CLAWSTER_BARREL.get(), basicItem()));

    public static final RegistryObject<Item> SHRIMP_BARREL = ITEMS.register("shrimp_barrel",
            () -> new BlockItem(ModBlocks.SHRIMP_BARREL.get(), basicItem()));
}
