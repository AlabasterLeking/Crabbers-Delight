package alabaster.crabbersdelight.common.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.world.level.block.Block;

public class CDModTags {
    public static final TagKey<Item> RAW_SEAFOOD = modItemTag("raw_seafood");
    public static final TagKey<Item> COOKED_SEAFOOD = modItemTag("cooked_seafood");
    public static final TagKey<Item> RAW_SQUID = modItemTag("raw_squid");
    public static final TagKey<Item> COOKED_SQUID = modItemTag("cooked_squid");

    public static final TagKey<Item> CRAB_TRAP_BAIT = modItemTag("crab_trap_bait");
    public static final TagKey<Item> CRAB_TEMPT_ITEM = modItemTag("crab_tempt_item");
    public static final TagKey<Item> CREATURE_CHUMS = modItemTag("creature_chums");

    public static final TagKey<Item> AIR = modItemTag("jei_display_results/minecraft/air");
    public static final TagKey<Item> COD = modItemTag("jei_display_results/minecraft/cod");
    public static final TagKey<Item> SALMON = modItemTag("jei_display_results/minecraft/salmon");
    public static final TagKey<Item> PUFFERFISH = modItemTag("jei_display_results/minecraft/pufferfish");
    public static final TagKey<Item> TROPICAL_FISH = modItemTag("jei_display_results/minecraft/tropical_fish");
    public static final TagKey<Item> BUCKET_OF_CLAWSTER_CHUM = modItemTag("jei_display_results/crabbersdelight/bucket_of_clawster_chum");
    public static final TagKey<Item> BUCKET_OF_CRAB_CHUM = modItemTag("jei_display_results/crabbersdelight/bucket_of_crab_chum");
    public static final TagKey<Item> BUCKET_OF_CLAM_CHUM = modItemTag("jei_display_results/crabbersdelight/bucket_of_clam_chum");
    public static final TagKey<Item> BUCKET_OF_SHRIMP_CHUM = modItemTag("jei_display_results/crabbersdelight/bucket_of_shrimp_chum");

    public static final TagKey<Block> CRAB_SPAWN_ON = modBlockTag("crab_spawn_on");

    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, path));
    }

    private static TagKey<Block> modBlockTag(String path) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, path));
    }
}
