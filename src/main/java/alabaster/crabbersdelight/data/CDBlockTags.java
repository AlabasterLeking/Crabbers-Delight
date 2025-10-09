package alabaster.crabbersdelight.data;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class CDBlockTags extends BlockTagsProvider
{
    public CDBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CrabbersDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.registerBlockTags();
        this.registerStorageBlocks();
        tag(CDModTags.CRAB_SPAWN_ON).add(
                Blocks.GRASS_BLOCK,
                Blocks.DIRT,
                Blocks.SAND,
                Blocks.STONE,
                Blocks.GRAVEL,
                Blocks.SNOW,
                Blocks.MUD);
    }

    protected void registerBlockTags() {
        tag(BlockTags.MINEABLE_WITH_AXE).add(
                CDModBlocks.PALM_LOG.get(),
                CDModBlocks.STRIPPED_PALM_LOG.get(),
                CDModBlocks.PALM_WOOD.get(),
                CDModBlocks.STRIPPED_PALM_WOOD.get(),
                CDModBlocks.PALM_PLANKS.get(),
                CDModBlocks.PALM_STAIRS.get(),
                CDModBlocks.PALM_SLAB.get(),
                CDModBlocks.PALM_DOOR.get(),
                CDModBlocks.PALM_TRAPDOOR.get(),
                CDModBlocks.PALM_FENCE.get(),
                CDModBlocks.PALM_FENCE_GATE.get(),
                CDModBlocks.PALM_PRESSURE_PLATE.get(),
                CDModBlocks.PALM_BUTTON.get(),
                CDModBlocks.PALM_SIGN.get(),
                CDModBlocks.PALM_HANGING_SIGN.get(),
                CDModBlocks.PALM_WALL_SIGN.get(),
                CDModBlocks.PALM_WALL_HANGING_SIGN.get(),
                CDModBlocks.PALM_CABINET.get(),

                CDModBlocks.CRAB_BARREL.get(),
                CDModBlocks.CLAM_BARREL.get(),
                CDModBlocks.CLAWSTER_BARREL.get(),
                CDModBlocks.SHRIMP_BARREL.get(),
                CDModBlocks.COD_BARREL.get(),
                CDModBlocks.SALMON_BARREL.get(),
                CDModBlocks.PUFFERFISH_BARREL.get(),
                CDModBlocks.TROPICAL_FISH_BARREL.get(),
                CDModBlocks.SQUID_BARREL.get(),
                CDModBlocks.GLOW_SQUID_BARREL.get(),
                CDModBlocks.FROG_LEG_BARREL.get(),
                CDModBlocks.COCONUT_CRATE.get(),
                CDModBlocks.SEA_PICKLE_CRATE.get(),
                CDModBlocks.CRAB_TRAP.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                CDModBlocks.NAUTILUS_SHELL_BLOCK.get(),
                CDModBlocks.PEARL_BLOCK.get(),
                CDModBlocks.SCUTE_BLOCK.get());

        tag(BlockTags.LOGS_THAT_BURN).add(
                CDModBlocks.PALM_LOG.get(),
                CDModBlocks.STRIPPED_PALM_LOG.get(),
                CDModBlocks.PALM_WOOD.get(),
                CDModBlocks.STRIPPED_PALM_WOOD.get());
        tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL).add(
                CDModBlocks.PALM_LOG.get());
        tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(
                CDModBlocks.PALM_LOG.get());
        tag(CDModTags.PALM_LOG_BLOCKS).add(
                CDModBlocks.PALM_LOG.get(),
                CDModBlocks.STRIPPED_PALM_LOG.get(),
                CDModBlocks.PALM_WOOD.get(),
                CDModBlocks.STRIPPED_PALM_WOOD.get());
        tag(BlockTags.PLANKS).add(CDModBlocks.PALM_PLANKS.get());
        tag(BlockTags.WOODEN_STAIRS).add(CDModBlocks.PALM_STAIRS.get());
        tag(BlockTags.WOODEN_SLABS).add(CDModBlocks.PALM_SLAB.get());
        tag(BlockTags.LEAVES).add(CDModBlocks.PALM_LEAVES.get());
        tag(BlockTags.SAPLINGS).add(CDModBlocks.PALM_SAPLING.get());
        tag(BlockTags.WOODEN_FENCES).add(CDModBlocks.PALM_FENCE.get());
        tag(Tags.Blocks.FENCES_WOODEN).add(CDModBlocks.PALM_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(CDModBlocks.PALM_FENCE_GATE.get());
        tag(Tags.Blocks.FENCE_GATES_WOODEN).add(CDModBlocks.PALM_FENCE_GATE.get());
        tag(BlockTags.WOODEN_BUTTONS).add(CDModBlocks.PALM_BUTTON.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(CDModBlocks.PALM_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_DOORS).add(CDModBlocks.PALM_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(CDModBlocks.PALM_TRAPDOOR.get());
        tag(BlockTags.SIGNS).add(CDModBlocks.PALM_SIGN.get());
        tag(BlockTags.STANDING_SIGNS).add(CDModBlocks.PALM_SIGN.get());
        tag(BlockTags.CEILING_HANGING_SIGNS).add(CDModBlocks.PALM_HANGING_SIGN.get());
        tag(BlockTags.ALL_HANGING_SIGNS)
                .add(CDModBlocks.PALM_HANGING_SIGN.get())
                .add(CDModBlocks.PALM_WALL_HANGING_SIGN.get());
        tag(BlockTags.WALL_SIGNS).add(CDModBlocks.PALM_WALL_SIGN.get());
        tag(BlockTags.WALL_HANGING_SIGNS).add(CDModBlocks.PALM_WALL_HANGING_SIGN.get());
    }

    protected void registerStorageBlocks() {
        tag(Tags.Blocks.STORAGE_BLOCKS).add(
                CDModBlocks.CRAB_BARREL.get(),
                CDModBlocks.CLAM_BARREL.get(),
                CDModBlocks.CLAWSTER_BARREL.get(),
                CDModBlocks.SHRIMP_BARREL.get(),
                CDModBlocks.COD_BARREL.get(),
                CDModBlocks.SALMON_BARREL.get(),
                CDModBlocks.PUFFERFISH_BARREL.get(),
                CDModBlocks.TROPICAL_FISH_BARREL.get(),
                CDModBlocks.SQUID_BARREL.get(),
                CDModBlocks.GLOW_SQUID_BARREL.get(),
                CDModBlocks.FROG_LEG_BARREL.get(),
                CDModBlocks.COCONUT_CRATE.get(),
                CDModBlocks.NAUTILUS_SHELL_BLOCK.get(),
                CDModBlocks.PEARL_BLOCK.get(),
                CDModBlocks.SCUTE_BLOCK.get(),
                CDModBlocks.SEA_PICKLE_CRATE.get());
    }
}