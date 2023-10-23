package alabaster.crabbersdelight.common.tags;

import net.minecraft.core.Registry;
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

    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(new ResourceLocation(CrabbersDelight.MODID, path));
    }
}
