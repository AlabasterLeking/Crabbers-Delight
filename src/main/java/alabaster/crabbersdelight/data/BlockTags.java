package alabaster.crabbersdelight.data;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.ModBlocks;
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
                ModBlocks.CRAB_BARREL.get(),
                ModBlocks.CLAM_BARREL.get(),
                ModBlocks.CLAWSTER_BARREL.get(),
                ModBlocks.SHRIMP_BARREL.get(),
                ModBlocks.COD_BARREL.get(),
                ModBlocks.SALMON_BARREL.get(),
                ModBlocks.PUFFERFISH_BARREL.get(),
                ModBlocks.TROPICAL_FISH_BARREL.get(),
                ModBlocks.SQUID_BARREL.get(),
                ModBlocks.GLOW_SQUID_BARREL.get(),
                ModBlocks.FROG_LEG_BARREL.get(),
                //ModBlocks.LANTERNFISH_BARREL.get(),
                ModBlocks.CRAB_TRAP.get());

        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.NAUTILUS_SHELL_BLOCK.get(),
                ModBlocks.PEARL_BLOCK.get());
    }


}