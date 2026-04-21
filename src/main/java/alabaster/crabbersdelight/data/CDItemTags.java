package alabaster.crabbersdelight.data;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.registry.CDModItems;
import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class CDItemTags extends ItemTagsProvider {

    public CDItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, blockTagProvider, CrabbersDelight.MODID, existingFileHelper);
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
        tag(Tags.Items.FOODS_RAW_FISH)
                .add(CDModItems.TROPICAL_FISH_SLICE.get())
                .add(CDModItems.PUFFERFISH_SLICE.get());

        // Cooked Fishes
        tag(Tags.Items.FOODS_COOKED_FISH)
                .add(CDModItems.COOKED_TROPICAL_FISH_SLICE.get())
                .add(CDModItems.COOKED_TROPICAL_FISH.get())
                .add(CDModItems.COOKED_PUFFERFISH_SLICE.get());

        // Creature Chums - this tag is necessary for any item considered a chum
        tag(CDModTags.CHUMS)
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
                .add(CDModItems.CAN.get())
                .add(CDModItems.FISH_BONES.get());

        tag(CDModTags.SALMON)
                .add(CDModItems.RAW_CLAWSTER.get())
                .add(Items.STICK)
                .add(Items.NAUTILUS_SHELL)
                .add(vectorwing.farmersdelight.common.registry.ModItems.ROPE.get())
                .add(Items.COPPER_INGOT)
                .add(CDModItems.CAN.get())
                .add(CDModItems.FISH_BONES.get());

        tag(CDModTags.PUFFERFISH)
                .add(CDModItems.CLAM.get())
                .add(Items.STICK)
                .add(Items.NAUTILUS_SHELL)
                .add(vectorwing.farmersdelight.common.registry.ModItems.ROPE.get())
                .add(Items.COPPER_INGOT)
                .add(CDModItems.CAN.get())
                .add(CDModItems.CORAL_FRAGMENTS.get())
                .add(CDModItems.FISH_BONES.get());

        tag(CDModTags.TROPICAL_FISH)
                .add(CDModItems.RAW_SHRIMP.get())
                .add(Items.STICK)
                .add(Items.NAUTILUS_SHELL)
                .add(vectorwing.farmersdelight.common.registry.ModItems.ROPE.get())
                .add(Items.COPPER_INGOT)
                .add(CDModItems.CAN.get())
                .add(CDModItems.CORAL_FRAGMENTS.get())
                .add(CDModItems.FISH_BONES.get());

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
                .add(CDModItems.RAW_CRAB.get())
                .add(CDModItems.FISH_BONES.get());

        // Trim Materials
        tag(ItemTags.TRIM_MATERIALS)
                .add(CDModItems.PEARL.get());

        tag(ModTags.Items.CABINETS_WOODEN)
                .add(CDModItems.PALM_CABINET.get());


        // Palm
        tag(CDModTags.PALM_LOGS)
                .add(CDModItems.PALM_LOG.get())
                .add(CDModItems.STRIPPED_PALM_LOG.get())
                .add(CDModItems.PALM_WOOD.get())
                .add(CDModItems.STRIPPED_PALM_WOOD.get());
        tag(ItemTags.LOGS_THAT_BURN)
                .add(CDModItems.PALM_LOG.get())
                .add(CDModItems.STRIPPED_PALM_LOG.get())
                .add(CDModItems.PALM_WOOD.get())
                .add(CDModItems.STRIPPED_PALM_WOOD.get());
        tag(Tags.Items.STRIPPED_LOGS).add(CDModItems.STRIPPED_PALM_LOG.get());
        tag(Tags.Items.STRIPPED_WOODS).add(CDModItems.STRIPPED_PALM_WOOD.get());
        tag(ItemTags.PLANKS).add(CDModItems.PALM_PLANKS.get());
        tag(ItemTags.SAPLINGS).add(CDModItems.PALM_SAPLING.get());
        tag(ItemTags.LEAVES).add(CDModItems.PALM_LEAVES.get());
        tag(ItemTags.WOODEN_TRAPDOORS).add(CDModItems.PALM_TRAPDOOR.get());
        tag(ItemTags.WOODEN_DOORS).add(CDModItems.PALM_DOOR.get());
        tag(ItemTags.WOODEN_SLABS).add(CDModItems.PALM_SLAB.get());
        tag(ItemTags.WOODEN_STAIRS).add(CDModItems.PALM_STAIRS.get());
        tag(ItemTags.WOODEN_FENCES).add(CDModItems.PALM_FENCE.get());
        tag(Tags.Items.FENCES_WOODEN).add(CDModItems.PALM_FENCE.get());
        tag(ItemTags.FENCE_GATES).add(CDModItems.PALM_FENCE_GATE.get());
        tag(Tags.Items.FENCE_GATES_WOODEN).add(CDModItems.PALM_FENCE_GATE.get());
        tag(ItemTags.WOODEN_BUTTONS).add(CDModItems.PALM_BUTTON.get());
        tag(ItemTags.WOODEN_PRESSURE_PLATES).add(CDModItems.PALM_PRESSURE_PLATE.get());
        tag(ItemTags.SIGNS).add(CDModItems.PALM_SIGN.get());
        tag(ItemTags.HANGING_SIGNS).add(CDModItems.PALM_HANGING_SIGN.get());
        tag(ItemTags.BOATS).add(CDModItems.PALM_BOAT.get());
        tag(ItemTags.CHEST_BOATS).add(CDModItems.PALM_CHEST_BOAT.get());

        tag(Tags.Items.DRINKS_MILK)
                .add(CDModItems.COCONUT_MILK.get());
    }
}