package alabaster.crabbersdelight.data;

import alabaster.crabbersdelight.common.registry.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class BlockTags extends BlockTagsProvider {
    public BlockTags(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
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