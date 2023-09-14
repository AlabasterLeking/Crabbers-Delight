package alabaster.crabbersdelight.data;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

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
    }

    protected void registerBlockMineables() {
        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE).add(
                ModBlocks.CRAB_BARREL.get(),
                ModBlocks.CLAM_BARREL.get(),
                ModBlocks.CLAWSTER_BARREL.get(),
                ModBlocks.SHRIMP_BARREL.get(),
                ModBlocks.CRAB_TRAP.get());

        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.NAUTILUS_SHELL_BLOCK.get(),
                ModBlocks.PEARL_BLOCK.get());

    }


}