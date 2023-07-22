package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import alabaster.crabbersdelight.common.CDFoodValues;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrabbersDelight.MODID);

    public static Item.Properties basicItem() {
        return new Item.Properties().tab(CrabbersDelight.TAB);
    }
    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food).tab(CrabbersDelight.TAB);
    }

    public static final RegistryObject<Item> RAW_CRAB = ITEMS.register("raw_crab", () -> new Item(foodItem(CDFoodValues.RAW_CRAB)));
    public static final RegistryObject<Item> COOKED_CRAB = ITEMS.register("cooked_crab", () -> new Item(foodItem(CDFoodValues.COOKED_CRAB)));
    public static final RegistryObject<Item> RAW_CLAWSTER = ITEMS.register("raw_clawster", () -> new Item(foodItem(CDFoodValues.RAW_CLAWSTER)));
    public static final RegistryObject<Item> COOKED_CLAWSTER = ITEMS.register("cooked_clawster", () -> new Item(foodItem(CDFoodValues.COOKED_CLAWSTER)));
    public static final RegistryObject<Item> RAW_SHRIMP = ITEMS.register("raw_shrimp", () -> new Item(foodItem(CDFoodValues.RAW_SHRIMP)));
    public static final RegistryObject<Item> COOKED_SHRIMP = ITEMS.register("cooked_shrimp", () -> new Item(foodItem(CDFoodValues.COOKED_SHRIMP)));
    public static final RegistryObject<Item> CLAM = ITEMS.register("clam", () -> new Item(basicItem()));
    public static final RegistryObject<Item> RAW_CLAM_MEAT = ITEMS.register("raw_clam_meat", () -> new Item(foodItem(CDFoodValues.RAW_CLAM_MEAT)));
    public static final RegistryObject<Item> COOKED_CLAM_MEAT = ITEMS.register("cooked_clam_meat", () -> new Item(foodItem(CDFoodValues.COOKED_CLAM_MEAT)));
    public static final RegistryObject<Item> PEARL = ITEMS.register("pearl", () -> new Item(basicItem()));
    public static final RegistryObject<Item> KELP_SHAKE = ITEMS.register("kelp_shake", () -> new Item(foodItem(CDFoodValues.KELP_SHAKE)));
    public static final RegistryObject<Item> CRAB_CAKES = ITEMS.register("crab_cakes", () -> new Item(foodItem(CDFoodValues.CRAB_CAKES)));
    public static final RegistryObject<Item> CRAB_LEGS = ITEMS.register("crab_legs", () -> new Item(foodItem(CDFoodValues.CRAB_LEGS)));
    public static final RegistryObject<Item> COOKED_TROPICAL_FISH = ITEMS.register("cooked_tropical_fish", () -> new Item(foodItem(CDFoodValues.COOKED_TROPICAL_FISH)));

}
