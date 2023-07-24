package alabaster.sniffersdelight.common;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class Configuration {
    public static final String CATEGORY_OVERRIDES_STACK_SIZE = "stack_size";
    public static ForgeConfigSpec.BooleanValue ENABLE_STACKABLE_SOUP_ITEMS;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> SOUP_ITEM_LIST;


    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.comment("Stack size overrides").push(CATEGORY_OVERRIDES_STACK_SIZE);
        ENABLE_STACKABLE_SOUP_ITEMS = COMMON_BUILDER.comment("Should BowlFoodItems in the following list become stackable to 16, much like Farmer's Delight's meals?")
                .define("enableStackableSoupItems", true);
        SOUP_ITEM_LIST = COMMON_BUILDER.comment("List of BowlFoodItems. They must extend this class to be affected. Default: vanilla soups and stews.")
                .defineList("soupItemList", ImmutableList.of("minecraft:mushroom_stew", "minecraft:beetroot_soup", "minecraft:rabbit_stew"), obj -> true);
    }
}