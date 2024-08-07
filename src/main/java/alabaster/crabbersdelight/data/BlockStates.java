package alabaster.crabbersdelight.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.ModBlocks;
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
        this.crateBlock(ModBlocks.CRAB_BARREL.get(), "crab");
        this.crateBlock(ModBlocks.CLAM_BARREL.get(), "clam");
        this.crateBlock(ModBlocks.CLAWSTER_BARREL.get(), "clawster");
        this.crateBlock(ModBlocks.SHRIMP_BARREL.get(), "shrimp");
        this.crateBlock(ModBlocks.COD_BARREL.get(), "cod");
        this.crateBlock(ModBlocks.SALMON_BARREL.get(), "salmon");
        this.crateBlock(ModBlocks.PUFFERFISH_BARREL.get(), "pufferfish");
        this.crateBlock(ModBlocks.TROPICAL_FISH_BARREL.get(), "tropical_fish");
        this.crateBlock(ModBlocks.SQUID_BARREL.get(), "squid");
        this.crateBlock(ModBlocks.GLOW_SQUID_BARREL.get(), "glow_squid");
        this.crateBlock(ModBlocks.FROG_LEG_BARREL.get(), "frog_leg");
        //this.crateBlock(ModBlocks.LANTERNFISH_BARREL.get(), "lanternfish");
    }

    public void crateBlock(Block block, String cropName) {
        this.simpleBlock(block,
                models().cubeBottomTop(blockName(block), resourceBlock("barrel_side"), resourceBlock("barrel_bottom"), resourceBlock(cropName + "_barrel_top")));
    }
}
