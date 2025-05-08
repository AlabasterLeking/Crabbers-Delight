package alabaster.crabbersdelight.data;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.tags.CDModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
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
        tag(CDModTags.CRAB_SPAWN_ON).add(
                Blocks.GRASS_BLOCK,
                Blocks.DIRT,
                Blocks.SAND,
                Blocks.STONE,
                Blocks.GRAVEL,
                Blocks.SNOW,
                Blocks.MUD);
    }

    protected void registerBlockMineables() {
        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE).add(
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
                //ModBlocks.LANTERNFISH_BARREL.get(),
                CDModBlocks.CRAB_TRAP.get());

        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(
                CDModBlocks.NAUTILUS_SHELL_BLOCK.get(),
                CDModBlocks.PEARL_BLOCK.get());
    }


}