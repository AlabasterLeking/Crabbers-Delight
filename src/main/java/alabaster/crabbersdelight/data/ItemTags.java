package alabaster.crabbersdelight.data;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.ModItems;
import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ItemTags extends ItemTagsProvider {
    public ItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, blockTagProvider, CrabbersDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.registerModTags();
    }

    private void registerModTags() {
        tag(CDModTags.RAW_SEAFOOD).add(ModItems.RAW_CLAM_MEAT.get(), ModItems.RAW_CRAB.get(), ModItems.RAW_CLAWSTER.get(), ModItems.RAW_SHRIMP.get());
        tag(CDModTags.COOKED_SEAFOOD).add(ModItems.COOKED_CLAM_MEAT.get(), ModItems.COOKED_CRAB.get(), ModItems.COOKED_CLAWSTER.get(), ModItems.COOKED_SHRIMP.get());
        tag(ForgeTags.COOKED_FISHES).add(ModItems.COOKED_TROPICAL_FISH_SLICE.get(), ModItems.COOKED_TROPICAL_FISH.get());
        tag(ForgeTags.RAW_FISHES).add(ModItems.TROPICAL_FISH_SLICE.get());
        tag(CDModTags.CRAB_TRAP_BAIT).add(Items.TROPICAL_FISH, Items.COD, Items.SALMON, Items.PUFFERFISH);
    }
}