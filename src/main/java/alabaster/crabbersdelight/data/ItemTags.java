package alabaster.crabbersdelight.data;

import alabaster.crabbersdelight.common.registry.ModItems;
import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import javax.annotation.Nullable;

public class ItemTags extends ItemTagsProvider {
    public ItemTags(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {

        this.registerModTags();
    }

    private void registerModTags() {
        tag(CDModTags.RAW_SEAFOOD).add(ModItems.RAW_CLAM_MEAT.get(), ModItems.RAW_CRAB.get(), ModItems.RAW_CLAWSTER.get(), ModItems.RAW_SHRIMP.get());
        tag(CDModTags.COOKED_SEAFOOD).add(ModItems.COOKED_CLAM_MEAT.get(), ModItems.COOKED_CRAB.get(), ModItems.COOKED_CLAWSTER.get(), ModItems.COOKED_SHRIMP.get());
        tag(ForgeTags.COOKED_FISHES).add(ModItems.COOKED_TROPICAL_FISH_SLICE.get(), ModItems.COOKED_TROPICAL_FISH.get());
        tag(ForgeTags.RAW_FISHES).add(ModItems.TROPICAL_FISH_SLICE.get());
        tag(CDModTags.CRAB_TRAP_BAIT).add(Items.TROPICAL_FISH, Items.COD, Items.SALMON, Items.PUFFERFISH, Items.AIR);
    }
}