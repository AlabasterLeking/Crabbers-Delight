package alabaster.crabbersdelight.common.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class CDModTags {
    public static final TagKey<Item> RAW_SEAFOOD = modItemTag("raw_seafood");
    public static final TagKey<Item> COOKED_SEAFOOD = modItemTag("cooked_seafood");
    public static final TagKey<Item> RAW_SQUID = modItemTag("raw_squid");
    public static final TagKey<Item> COOKED_SQUID = modItemTag("cooked_squid");

    public static final TagKey<Item> CRAB_TRAP_BAIT = modItemTag("crab_trap_bait");
    public static final TagKey<Item> CRAB_TEMPT_ITEM = modItemTag("crab_tempt_item");
    public static final TagKey<Item> CREATURE_CHUMS = modItemTag("creature_chums");
    public static final TagKey<Item> IS_CRAB_CHUM = modItemTag("is_crab_chum");
    public static final TagKey<Item> IS_CLAWSTER_CHUM = modItemTag("is_clawster_chum");
    public static final TagKey<Item> IS_CLAM_CHUM = modItemTag("is_clam_chum");
    public static final TagKey<Item> IS_SHRIMP_CHUM = modItemTag("is_shrimp_chum");

    public static final TagKey<Item> JUNK = modItemTag("jei_display_results/minecraft/junk");
    public static final TagKey<Item> TREASURE = modItemTag("jei_display_results/minecraft/treasure");
    public static final TagKey<Item> BUCKET_OF_CLAWSTER_CHUM = modItemTag("jei_display_results/minecraft/bucket_of_clawster_chum");
    public static final TagKey<Item> BUCKET_OF_CRAB_CHUM = modItemTag("jei_display_results/minecraft/bucket_of_crab_chum");
    public static final TagKey<Item> BUCKET_OF_CLAM_CHUM = modItemTag("jei_display_results/minecraft/bucket_of_clam_chum");
    public static final TagKey<Item> BUCKET_OF_SHRIMP_CHUM = modItemTag("jei_display_results/minecraft/bucket_of_shrimp_chum");

    public static final TagKey<Block> CRAB_SPAWN_ON = modBlockTag("crab_spawn_on");

    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(new ResourceLocation(CrabbersDelight.MODID, path));
    }

    private static TagKey<Block> modBlockTag(String path) {
        return BlockTags.create(new ResourceLocation(CrabbersDelight.MODID, path));
    }
}
