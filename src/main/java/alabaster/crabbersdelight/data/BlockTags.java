package alabaster.crabbersdelight.data;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class BlockTags extends BlockTagsProvider
{
    public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CrabbersDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.registerBlockMineables();
        this.registerStorageBlocks();
        tag(CDModTags.CRAB_SPAWN_ON).add(
                Blocks.GRASS_BLOCK,
                Blocks.DIRT,
                Blocks.SAND,
                Blocks.STONE,
                Blocks.GRAVEL,
                Blocks.SNOW,
                Blocks.MUD);

        tag(net.minecraft.tags.BlockTags.FENCES).add(CDModBlocks.PALM_FENCE.get());
        tag(net.minecraft.tags.BlockTags.FENCE_GATES).add(CDModBlocks.PALM_FENCE_GATE.get());

    }

    protected void registerBlockMineables() {
        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE).add(
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
                CDModBlocks.SEA_PICKLE_CRATE.get(),
                CDModBlocks.CRAB_TRAP.get());

        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(
                CDModBlocks.NAUTILUS_SHELL_BLOCK.get(),
                CDModBlocks.PEARL_BLOCK.get(),
                CDModBlocks.SCUTE_BLOCK.get());

        tag(net.minecraft.tags.BlockTags.LOGS_THAT_BURN).add(
                CDModBlocks.PALM_LOG.get(),
                CDModBlocks.STRIPPED_PALM_LOG.get(),
                CDModBlocks.PALM_WOOD.get(),
                CDModBlocks.STRIPPED_PALM_WOOD.get());

        tag(net.minecraft.tags.BlockTags.PLANKS).add(
                CDModBlocks.PALM_PLANKS.get());

        tag(net.minecraft.tags.BlockTags.PLANKS).add(
                CDModBlocks.PALM_PLANKS.get());

        tag(net.minecraft.tags.BlockTags.LEAVES).add(
                CDModBlocks.PALM_LEAVES.get());

        tag(net.minecraft.tags.BlockTags.SAPLINGS).add(
                CDModBlocks.PALM_SAPLING.get());
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
                CDModBlocks.NAUTILUS_SHELL_BLOCK.get(),
                CDModBlocks.PEARL_BLOCK.get(),
                CDModBlocks.SCUTE_BLOCK.get(),
                CDModBlocks.SEA_PICKLE_CRATE.get());
    }
}