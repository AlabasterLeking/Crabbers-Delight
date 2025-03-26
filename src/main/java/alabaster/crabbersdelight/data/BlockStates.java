package alabaster.crabbersdelight.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.ModBlocks;
import alabaster.crabbersdelight.common.registry.ModItems;

public class BlockStates extends BlockStateProvider {
    private static final int DEFAULT_ANGLE_OFFSET = 180;

    public BlockStates(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CrabbersDelight.MODID, existingFileHelper);
    }

    private String blockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(CrabbersDelight.MODID, "block/" + path);
    }

    public ModelFile existingModel(Block block) {
        return new ModelFile.ExistingModelFile(resourceBlock(blockName(block)), models().existingFileHelper);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.barrelBlock(ModBlocks.CRAB_BARREL.get(), "crab");
        this.barrelBlock(ModBlocks.CLAM_BARREL.get(), "clam");
        this.barrelBlock(ModBlocks.CLAWSTER_BARREL.get(), "clawster");
        this.barrelBlock(ModBlocks.SHRIMP_BARREL.get(), "shrimp");
        this.barrelBlock(ModBlocks.COD_BARREL.get(), "cod");
        this.barrelBlock(ModBlocks.SALMON_BARREL.get(), "salmon");
        this.barrelBlock(ModBlocks.PUFFERFISH_BARREL.get(), "pufferfish");
        this.barrelBlock(ModBlocks.TROPICAL_FISH_BARREL.get(), "tropical_fish");
        this.barrelBlock(ModBlocks.SQUID_BARREL.get(), "squid");
        this.barrelBlock(ModBlocks.GLOW_SQUID_BARREL.get(), "glow_squid");
        this.barrelBlock(ModBlocks.FROG_LEG_BARREL.get(), "frog_leg");
        this.crateBlock(ModBlocks.SEA_PICKLE_CRATE.get(), "sea_pickle");
    }

    public void barrelBlock(Block block, String cropName) {
        this.simpleBlock(block,
                models().cubeBottomTop(blockName(block), resourceBlock("barrel_side"), resourceBlock("barrel_bottom"), resourceBlock(cropName + "_barrel_top")));
    }

    public void crateBlock(Block block, String cropName) {
        this.simpleBlock(block,
                models().cubeBottomTop(blockName(block), resourceBlock(cropName + "_crate_side"), resourceBlock("crate_bottom"), resourceBlock(cropName + "_crate_top")));
    }
}
