package alabaster.crabbersdelight.init;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CrabbersDelight.MODID);

    // Basic Items
    public static Item.Properties basicItem() {
        return new Item.Properties().tab(CrabbersDelight.CREATIVE_TAB);
    }
    // Food Items
    public static Item.Properties foodItem() {
        return new Item.Properties().food(new FoodProperties.Builder().build()).tab(CrabbersDelight.CREATIVE_TAB);
    }

    // Begin Item Registry
    public static final RegistryObject<Item> RAWCRAB = ITEMS.register("raw_crab",
            () -> new Item(basicItem().tab(CrabbersDelight.CREATIVE_TAB)));

    public static final RegistryObject<Item> COOKEDCRAB = ITEMS.register("cooked_crab",
            () -> new Item(basicItem().tab(CrabbersDelight.CREATIVE_TAB)));

    public static final RegistryObject<Item> CRABLEGS = ITEMS.register("crab_legs",
            () -> new Item(basicItem().tab(CrabbersDelight.CREATIVE_TAB)));

    public static final RegistryObject<Item> CRABCAKES = ITEMS.register("crab_cakes",
            () -> new Item(basicItem().tab(CrabbersDelight.CREATIVE_TAB)));

    public static final RegistryObject<Item> RAWLOBSTER = ITEMS.register("lobster",
            () -> new Item(basicItem().tab(CrabbersDelight.CREATIVE_TAB)));
    }