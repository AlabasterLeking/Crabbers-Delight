package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.FoodValues;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrabbersDelight.MODID);

    // Basic Items
    public static Item.Properties basicItem() {
        return new Item.Properties().tab(CrabbersDelight.CREATIVE_TAB);
    }

    // Food Items
    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food).tab(CrabbersDelight.CREATIVE_TAB);
    }

    // Begin Item Registry
    public static final RegistryObject<Item> RAW_CRAB = ITEMS.register("raw_crab",
            () -> new Item(foodItem(CDFoodValues.RAW_CRAB).tab(CrabbersDelight.CREATIVE_TAB)));

    public static final RegistryObject<Item> COOKED_CRAB = ITEMS.register("cooked_crab",
            () -> new Item(foodItem(CDFoodValues.COOKED_CRAB).tab(CrabbersDelight.CREATIVE_TAB)));

    public static final RegistryObject<Item> RAW_SHRIMP = ITEMS.register("raw_shrimp",
            () -> new Item(foodItem(CDFoodValues.RAW_SHRIMP).tab(CrabbersDelight.CREATIVE_TAB)));

    public static final RegistryObject<Item> COOKED_SHRIMP= ITEMS.register("cooked_shrimp",
            () -> new Item(foodItem(CDFoodValues.COOKED_SHRIMP).tab(CrabbersDelight.CREATIVE_TAB)));
    }