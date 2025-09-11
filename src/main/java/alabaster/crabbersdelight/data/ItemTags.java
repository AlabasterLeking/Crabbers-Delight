package alabaster.crabbersdelight.data;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModItems;
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
                .add(Items.AIR)
                .add(Items.TROPICAL_FISH)
                .add(Items.COD)
                .add(Items.SALMON)
                .add(Items.PUFFERFISH)
                .add(CDModItems.BUCKET_OF_CLAM_CHUM.get())
                .add(CDModItems.BUCKET_OF_CLAWSTER_CHUM.get())
                .add(CDModItems.BUCKET_OF_CRAB_CHUM.get())
                .add(CDModItems.BUCKET_OF_SHRIMP_CHUM.get())
                .add(Items.BUCKET);

        // Crab Tempt Items
        tag(CDModTags.CRAB_TEMPT_ITEM)
                .add(Items.KELP);

        // Raw Seafood
        tag(CDModTags.RAW_SEAFOOD)
                .add(CDModItems.RAW_CLAM_MEAT.get())
                .add(CDModItems.RAW_CRAB.get())
                .add(CDModItems.RAW_CLAWSTER.get())
                .add(CDModItems.RAW_SHRIMP.get());

        // Cooked Seafood
        tag(CDModTags.COOKED_SEAFOOD)
                .add(CDModItems.COOKED_CLAM_MEAT.get())
                .add(CDModItems.COOKED_CRAB.get())
                .add(CDModItems.COOKED_CLAWSTER.get())
                .add(CDModItems.COOKED_SHRIMP.get());

        // Raw Squid
        tag(CDModTags.RAW_SQUID)
                .add(CDModItems.RAW_SQUID_TENTACLES.get())
                .add(CDModItems.RAW_GLOW_SQUID_TENTACLES.get());

        // Cooked Squid
        tag(CDModTags.COOKED_SQUID)
                .add(CDModItems.COOKED_SQUID_TENTACLES.get())
                .add(CDModItems.COOKED_GLOW_SQUID_TENTACLES.get());

        // Raw Fishes
        tag(ForgeTags.RAW_FISHES)
                .add(CDModItems.TROPICAL_FISH_SLICE.get())
                .add(CDModItems.PUFFERFISH_SLICE.get());

        // Cooked Fishes
        tag(ForgeTags.COOKED_FISHES)
                .add(CDModItems.COOKED_TROPICAL_FISH_SLICE.get())
                .add(CDModItems.COOKED_TROPICAL_FISH.get())
                .add(CDModItems.COOKED_PUFFERFISH_SLICE.get());

        // Creature Chums - this tag is necessary for any item considered a chum
        tag(CDModTags.CREATURE_CHUMS)
                .add(CDModItems.BUCKET_OF_CLAM_CHUM.get())
                .add(CDModItems.BUCKET_OF_CLAWSTER_CHUM.get())
                .add(CDModItems.BUCKET_OF_CRAB_CHUM.get())
                .add(CDModItems.BUCKET_OF_SHRIMP_CHUM.get());

        // JEI Compat Tags
        tag(CDModTags.COD)
                .add(CDModItems.RAW_CRAB.get())
                .add(Items.STICK)
                .add(Items.NAUTILUS_SHELL)
                .add(vectorwing.farmersdelight.common.registry.ModItems.ROPE.get())
                .add(Items.COPPER_INGOT)
                .add(CDModItems.CAN.get());

        tag(CDModTags.SALMON)
                .add(CDModItems.RAW_CLAWSTER.get())
                .add(Items.STICK)
                .add(Items.NAUTILUS_SHELL)
                .add(vectorwing.farmersdelight.common.registry.ModItems.ROPE.get())
                .add(Items.COPPER_INGOT)
                .add(CDModItems.CAN.get());

        tag(CDModTags.PUFFERFISH)
                .add(CDModItems.RAW_SHRIMP.get())
                .add(Items.STICK)
                .add(Items.NAUTILUS_SHELL)
                .add(vectorwing.farmersdelight.common.registry.ModItems.ROPE.get())
                .add(Items.COPPER_INGOT)
                .add(CDModItems.CAN.get())
                .add(CDModItems.CORAL_FRAGMENTS.get());

        tag(CDModTags.TROPICAL_FISH)
                .add(CDModItems.CLAM.get())
                .add(Items.STICK)
                .add(Items.NAUTILUS_SHELL)
                .add(vectorwing.farmersdelight.common.registry.ModItems.ROPE.get())
                .add(Items.COPPER_INGOT)
                .add(CDModItems.CAN.get())
                .add(CDModItems.CORAL_FRAGMENTS.get());

        tag(CDModTags.BUCKET_OF_CRAB_CHUM)
                .add(CDModItems.RAW_CRAB.get());

        tag(CDModTags.BUCKET_OF_CLAWSTER_CHUM)
                .add(CDModItems.RAW_CLAWSTER.get());

        tag(CDModTags.BUCKET_OF_CLAM_CHUM)
                .add(CDModItems.CLAM.get());

        tag(CDModTags.BUCKET_OF_SHRIMP_CHUM)
                .add(CDModItems.RAW_SHRIMP.get());

        tag(CDModTags.AIR)
                .add(Items.STICK)
                .add(Items.KELP)
                .add(CDModItems.CAN.get())
                .add(CDModItems.RAW_SHRIMP.get())
                .add(CDModItems.CLAM.get())
                .add(CDModItems.RAW_CLAWSTER.get())
                .add(CDModItems.RAW_CRAB.get());

        tag(net.minecraft.tags.ItemTags.TRIM_MATERIALS)
                .add(CDModItems.PEARL.get());
    }
}