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

    public ItemTags(PackOutput generator, CompletableFuture<HolderLookup.Provider> pProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, pProvider, blockProvider, CrabbersDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        // Crab Trap Bait - this tag is required for any item that needs to be able to be placed in the bait slot
        tag(CDModTags.CRAB_TRAP_BAIT)
                .add(Items.TROPICAL_FISH)
                .add(Items.COD)
                .add(Items.SALMON)
                .add(Items.PUFFERFISH)
                .add(ModItems.BUCKET_OF_CLAM_CHUM.get())
                .add(ModItems.BUCKET_OF_CLAWSTER_CHUM.get())
                .add(ModItems.BUCKET_OF_CRAB_CHUM.get())
                .add(ModItems.BUCKET_OF_SHRIMP_CHUM.get())
                .add(Items.BUCKET);

        // Crab Tempt Items
        tag(CDModTags.CRAB_TEMPT_ITEM)
                .add(Items.KELP);

        // Raw Seafood
        tag(CDModTags.RAW_SEAFOOD)
                .add(ModItems.RAW_CLAM_MEAT.get())
                .add(ModItems.RAW_CRAB.get())
                .add(ModItems.RAW_CLAWSTER.get())
                .add(ModItems.RAW_SHRIMP.get());

        // Cooked Seafood
        tag(CDModTags.COOKED_SEAFOOD)
                .add(ModItems.COOKED_CLAM_MEAT.get())
                .add(ModItems.COOKED_CRAB.get())
                .add(ModItems.COOKED_CLAWSTER.get())
                .add(ModItems.COOKED_SHRIMP.get());

        // Raw Squid
        tag(CDModTags.RAW_SQUID)
                .add(ModItems.RAW_SQUID_TENTACLES.get())
                .add(ModItems.RAW_GLOW_SQUID_TENTACLES.get());

        // Cooked Squid
        tag(CDModTags.COOKED_SQUID)
                .add(ModItems.COOKED_SQUID_TENTACLES.get())
                .add(ModItems.COOKED_GLOW_SQUID_TENTACLES.get());

        // Raw Fishes
        tag(ForgeTags.RAW_FISHES)
                .add(ModItems.TROPICAL_FISH_SLICE.get())
                .add(ModItems.PUFFERFISH_SLICE.get());

        // Cooked Fishes
        tag(ForgeTags.COOKED_FISHES)
                .add(ModItems.COOKED_TROPICAL_FISH_SLICE.get())
                .add(ModItems.COOKED_TROPICAL_FISH.get())
                .add(ModItems.COOKED_PUFFERFISH_SLICE.get());

        // Creature Chums - this tag is necessary for any item considered a chum
        tag(CDModTags.CREATURE_CHUMS)
                .add(ModItems.BUCKET_OF_CLAM_CHUM.get())
                .add(ModItems.BUCKET_OF_CLAWSTER_CHUM.get())
                .add(ModItems.BUCKET_OF_CRAB_CHUM.get())
                .add(ModItems.BUCKET_OF_SHRIMP_CHUM.get());

        // Individual Chum Types
        tag(CDModTags.IS_CRAB_CHUM).add(Items.COD);
        tag(CDModTags.IS_CLAWSTER_CHUM).add(Items.SALMON);
        tag(CDModTags.IS_CLAM_CHUM).add(Items.TROPICAL_FISH);
        tag(CDModTags.IS_SHRIMP_CHUM).add(Items.PUFFERFISH);
    }
}