package alabaster.crabbersdelight.common.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import alabaster.crabbersdelight.CrabbersDelight;

public class CDModTags {
    public static final TagKey<Item> RAW_SEAFOOD = modItemTag("raw_seafood");
    public static final TagKey<Item> COOKED_SEAFOOD = modItemTag("cooked_seafood");
    public static final TagKey<Item> CRAB_TRAP_BAIT = modItemTag("crab_trap_bait")

    private static TagKey<Item> modItemTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CrabbersDelight.MODID, path));
    }
}
