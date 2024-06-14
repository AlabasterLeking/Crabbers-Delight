package alabaster.crabbersdelight.common.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import alabaster.crabbersdelight.CrabbersDelight;

public class CDModTags {
    public static final TagKey<Item> RAW_SEAFOOD = modItemTag("raw_seafood");
    public static final TagKey<Item> COOKED_SEAFOOD = modItemTag("cooked_seafood");
    public static final TagKey<Item> CRAB_TRAP_BAIT = modItemTag("crab_trap_bait");
    public static final TagKey<Item> CRAB_TEMPT_ITEM = modItemTag("crab_tempt_item");
    public static final TagKey<Item> CREATURE_CHUMS = modItemTag("creature_chums");
    public static final TagKey<Item> IS_CRAB_CHUM = modItemTag("is_crab_chum");
    public static final TagKey<Item> IS_CLAWSTER_CHUM = modItemTag("is_clawster_chum");
    public static final TagKey<Item> IS_CLAM_CHUM = modItemTag("is_clam_chum");
    public static final TagKey<Item> IS_SHRIMP_CHUM = modItemTag("is_shrimp_chum");

    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(new ResourceLocation(CrabbersDelight.MODID, path));
    }
}
