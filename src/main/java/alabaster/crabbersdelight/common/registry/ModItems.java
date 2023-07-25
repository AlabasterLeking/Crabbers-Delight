package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import com.google.common.collect.Sets;
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

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrabbersDelight.MODID);
    public static LinkedHashSet<RegistryObject<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    public static RegistryObject<Item> registerWithTab(final String name, final Supplier<Item> supplier) {
        RegistryObject<Item> block = ITEMS.register(name, supplier);
        CREATIVE_TAB_ITEMS.add(block);
        return block;
    }


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

    // Basic Items
    public static final RegistryObject<Item> PEARL = registerWithTab("pearl",
            () -> new Item(basicItem()));

    // Foods
    public static final RegistryObject<Item> RAW_CRAB = registerWithTab("raw_crab",
            () -> new Item(foodItem(CDFoodValues.RAW_CRAB)));

    public static final RegistryObject<Item> COOKED_CRAB = registerWithTab("cooked_crab",
            () -> new Item(foodItem(CDFoodValues.COOKED_CRAB)));

    public static final RegistryObject<Item> RAW_CLAWSTER = registerWithTab("raw_clawster",
            () -> new Item(foodItem(CDFoodValues.RAW_CLAWSTER)));

    public static final RegistryObject<Item> COOKED_CLAWSTER = registerWithTab("cooked_clawster",
            () -> new Item(foodItem(CDFoodValues.COOKED_CLAWSTER)));

    public static final RegistryObject<Item> RAW_SHRIMP = registerWithTab("raw_shrimp",
            () -> new Item(foodItem(CDFoodValues.RAW_SHRIMP)));

    public static final RegistryObject<Item> COOKED_SHRIMP = registerWithTab("cooked_shrimp",
            () -> new Item(foodItem(CDFoodValues.COOKED_SHRIMP)));

    public static final RegistryObject<Item> CLAM = registerWithTab("clam",
            () -> new Item(basicItem()));

    public static final RegistryObject<Item> RAW_CLAM_MEAT = registerWithTab("raw_clam_meat",
            () -> new Item(foodItem(CDFoodValues.RAW_CLAM_MEAT)));

    public static final RegistryObject<Item> COOKED_CLAM_MEAT = registerWithTab("cooked_clam_meat",
            () -> new Item(foodItem(CDFoodValues.COOKED_CLAM_MEAT)));

    public static final RegistryObject<Item> KELP_SHAKE = registerWithTab("kelp_shake",
            () -> new DrinkableItem(drinkItem(CDFoodValues.KELP_SHAKE)));

    public static final RegistryObject<Item> CRAB_CAKES = registerWithTab("crab_cakes",
            () -> new Item(foodItem(CDFoodValues.CRAB_CAKES)));

    public static final RegistryObject<Item> CRAB_LEGS = registerWithTab("crab_legs",
            () -> new Item(foodItem(CDFoodValues.CRAB_LEGS)));

    public static final RegistryObject<Item> FISH_STICK = registerWithTab("fish_stick",
            () -> new Item(foodItem(CDFoodValues.FISH_STICK)));

    public static final RegistryObject<Item> SURF_AND_TURF = registerWithTab("surf_and_turf",
            () -> new Item(foodItem(CDFoodValues.SURF_AND_TURF)));

    public static final RegistryObject<Item> SHRIMP_SKEWER = registerWithTab("shrimp_skewer",
            () -> new Item(foodItem(CDFoodValues.SHRIMP_SKEWER)));

    public static final RegistryObject<Item> CLAM_BAKE = registerWithTab("clam_bake",
            () -> new Item(foodItem(CDFoodValues.CLAM_BAKE)));

    public static final RegistryObject<Item> CLAM_CHOWDER = registerWithTab("clam_chowder",
            () -> new Item(foodItem(CDFoodValues.CLAM_CHOWDER)));

    public static final RegistryObject<Item> COOKED_TROPICAL_FISH = registerWithTab("cooked_tropical_fish",
            () -> new Item(foodItem(CDFoodValues.COOKED_TROPICAL_FISH)));

    public static final RegistryObject<Item> TROPICAL_FISH_SLICE = registerWithTab("tropical_fish_slice",
            () -> new Item(foodItem(CDFoodValues.TROPICAL_FISH_SLICE)));

    public static final RegistryObject<Item> COOKED_TROPICAL_FISH_SLICE = registerWithTab("cooked_tropical_fish_slice",
            () -> new Item(foodItem(CDFoodValues.COOKED_TROPICAL_FISH_SLICE)));

    public static final RegistryObject<Item> BISQUE = registerWithTab("bisque",
            () -> new Item(foodItem(CDFoodValues.BISQUE)));

    public static final RegistryObject<Item> SEAFOOD_GUMBO = registerWithTab("seafood_gumbo",
            () -> new Item(foodItem(CDFoodValues.SEAFOOD_GUMBO)));

    // Barrels
    public static final RegistryObject<Item> CRAB_BARREL = registerWithTab("crab_barrel",
            () -> new BlockItem(ModBlocks.CRAB_BARREL.get(), basicItem()));

    public static final RegistryObject<Item> CLAM_BARREL = registerWithTab("clam_barrel",
            () -> new BlockItem(ModBlocks.CLAM_BARREL.get(), basicItem()));

    public static final RegistryObject<Item> CLAWSTER_BARREL = registerWithTab("clawster_barrel",
            () -> new BlockItem(ModBlocks.CLAWSTER_BARREL.get(), basicItem()));

    public static final RegistryObject<Item> SHRIMP_BARREL = registerWithTab("shrimp_barrel",
            () -> new BlockItem(ModBlocks.SHRIMP_BARREL.get(), basicItem()));
}
