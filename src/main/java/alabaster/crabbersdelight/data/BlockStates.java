package alabaster.crabbersdelight.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {
    private static final int DEFAULT_ANGLE_OFFSET = 180;

    public BlockStates(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CrabbersDelight.MODID, existingFileHelper);
    }

    private String blockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "block/" + path);
    }

    public ModelFile existingModel(Block block) {
        return new ModelFile.ExistingModelFile(resourceBlock(blockName(block)), models().existingFileHelper);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.crateBlock(CDModBlocks.CRAB_BARREL.get(), "crab");
        this.crateBlock(CDModBlocks.CLAM_BARREL.get(), "clam");
        this.crateBlock(CDModBlocks.CLAWSTER_BARREL.get(), "clawster");
        this.crateBlock(CDModBlocks.SHRIMP_BARREL.get(), "shrimp");
        this.crateBlock(CDModBlocks.COD_BARREL.get(), "cod");
        this.crateBlock(CDModBlocks.SALMON_BARREL.get(), "salmon");
        this.crateBlock(CDModBlocks.PUFFERFISH_BARREL.get(), "pufferfish");
        this.crateBlock(CDModBlocks.TROPICAL_FISH_BARREL.get(), "tropical_fish");
        this.crateBlock(CDModBlocks.SQUID_BARREL.get(), "squid");
        this.crateBlock(CDModBlocks.GLOW_SQUID_BARREL.get(), "glow_squid");
        this.crateBlock(CDModBlocks.FROG_LEG_BARREL.get(), "frog_leg");
        //this.crateBlock(ModBlocks.LANTERNFISH_BARREL.get(), "lanternfish");
    }

    public void crateBlock(Block block, String cropName) {
        this.simpleBlock(block,
                models().cubeBottomTop(blockName(block), resourceBlock("barrel_side"), resourceBlock("barrel_bottom"), resourceBlock(cropName + "_barrel_top")));
    }
}
