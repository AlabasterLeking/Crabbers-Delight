package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import com.google.common.collect.Sets;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import alabaster.crabbersdelight.common.registry.CDFoodValues;
import alabaster.crabbersdelight.CrabbersDelight;

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
        return new Item.Properties().tab(CrabbersDelight.CREATIVE_TAB);
    }

    // Food Items
    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food).tab(CrabbersDelight.CREATIVE_TAB);
    }

    // Food
    public static final RegistryObject<Item> RAW_CRAB = ITEMS.register("raw_crab",
            () -> new Item(foodItem(CDFoodValues.RAW_CRAB).tab(CrabbersDelight.CREATIVE_TAB)));

    public static final RegistryObject<Item> COOKED_CRAB = ITEMS.register("cooked_crab",
            () -> new Item(foodItem(CDFoodValues.COOKED_CRAB).tab(CrabbersDelight.CREATIVE_TAB)));

    public static final RegistryObject<Item> RAW_SHRIMP = ITEMS.register("raw_shrimp",
            () -> new Item(foodItem(CDFoodValues.RAW_SHRIMP).tab(CrabbersDelight.CREATIVE_TAB)));

    public static final RegistryObject<Item> COOKED_SHRIMP= ITEMS.register("cooked_shrimp",
            () -> new Item(foodItem(CDFoodValues.COOKED_SHRIMP).tab(CrabbersDelight.CREATIVE_TAB)));

    // Blocks
    public static final RegistryObject<Item> CRAB_BARREL = registerWithTab("crab_barrel",
            () -> new BlockItem(ModBlocks.CRAB_BARREL.get(), basicItem()));
    }