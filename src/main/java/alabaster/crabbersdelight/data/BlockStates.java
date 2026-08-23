package alabaster.crabbersdelight.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import vectorwing.farmersdelight.common.block.CabinetBlock;

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
        this.barrelBlock(CDModBlocks.CRAB_BARREL.get(), "crab");
        this.barrelBlock(CDModBlocks.CLAM_BARREL.get(), "clam");
        this.barrelBlock(CDModBlocks.CLAWSTER_BARREL.get(), "clawster");
        this.barrelBlock(CDModBlocks.SHRIMP_BARREL.get(), "shrimp");
        this.barrelBlock(CDModBlocks.COD_BARREL.get(), "cod");
        this.barrelBlock(CDModBlocks.SALMON_BARREL.get(), "salmon");
        this.barrelBlock(CDModBlocks.PUFFERFISH_BARREL.get(), "pufferfish");
        this.barrelBlock(CDModBlocks.TROPICAL_FISH_BARREL.get(), "tropical_fish");
        this.barrelBlock(CDModBlocks.SQUID_BARREL.get(), "squid");
        this.barrelBlock(CDModBlocks.GLOW_SQUID_BARREL.get(), "glow_squid");
        this.barrelBlock(CDModBlocks.FROG_LEG_BARREL.get(), "frog_leg");
        this.crateBlock(CDModBlocks.COCONUT_CRATE.get(), "coconut");

        this.logBlock(((RotatedPillarBlock) CDModBlocks.PALM_LOG.get()));
        this.axisBlock(((RotatedPillarBlock) CDModBlocks.PALM_WOOD.get()), blockTexture(CDModBlocks.PALM_LOG.get()), blockTexture(CDModBlocks.PALM_LOG.get()));
        this.logBlock(((RotatedPillarBlock) CDModBlocks.STRIPPED_PALM_LOG.get()));
        this.axisBlock(((RotatedPillarBlock) CDModBlocks.STRIPPED_PALM_WOOD.get()), blockTexture(CDModBlocks.STRIPPED_PALM_LOG.get()), blockTexture(CDModBlocks.STRIPPED_PALM_LOG.get()));
        this.simpleBlockWithItem(CDModBlocks.PALM_PLANKS.get(), models().cubeAll("palm_planks", blockTexture(CDModBlocks.PALM_PLANKS.get())));
        this.stairsBlock(CDModBlocks.PALM_STAIRS.get(), blockTexture(CDModBlocks.PALM_PLANKS.get()));
        this.slabBlock(CDModBlocks.PALM_SLAB.get(), blockTexture(CDModBlocks.PALM_PLANKS.get()), blockTexture(CDModBlocks.PALM_PLANKS.get()));
        this.fenceBlock(CDModBlocks.PALM_FENCE.get(), blockTexture(CDModBlocks.PALM_PLANKS.get()));
        this.fenceGateBlock(CDModBlocks.PALM_FENCE_GATE.get(), blockTexture(CDModBlocks.PALM_PLANKS.get()));
        this.doorBlockWithRenderType(CDModBlocks.PALM_DOOR.get(), modLoc("block/palm_door_bottom"), modLoc("block/palm_door_top"), "cutout");
        this.trapdoorBlockWithRenderType(CDModBlocks.PALM_TRAPDOOR.get(), modLoc("block/palm_trapdoor"), true, "cutout");
        this.signBlock(CDModBlocks.PALM_SIGN.get(), CDModBlocks.PALM_WALL_SIGN.get(), blockTexture(CDModBlocks.PALM_PLANKS.get()));
        this.hangingSignBlock(CDModBlocks.PALM_HANGING_SIGN.get(), CDModBlocks.PALM_WALL_HANGING_SIGN.get(), blockTexture(CDModBlocks.PALM_PLANKS.get()));
        this.pressurePlateBlock(CDModBlocks.PALM_PRESSURE_PLATE.get(), blockTexture(CDModBlocks.PALM_PLANKS.get()));
        this.buttonBlock(CDModBlocks.PALM_BUTTON.get(), blockTexture(CDModBlocks.PALM_PLANKS.get()));
        this.leavesBlock(CDModBlocks.PALM_LEAVES.get());
        this.saplingBlock(CDModBlocks.PALM_SAPLING.get());
        this.cabinetBlock(CDModBlocks.PALM_CABINET.get(), "palm");
        this.horizontalBlock(CDModBlocks.TACKLE_BOX.get(), existingModel("tackle_box"));
        this.simpleBlock(CDModBlocks.WORMY_DIRT.get());
    }

    public void crateBlock(Block block, String cropName) {
        this.simpleBlock(block,
                models().cubeBottomTop(blockName(block), resourceBlock(cropName + "_crate_side"), resourceBlock("crate_bottom"), resourceBlock(cropName + "_crate_top")));
    }

    public void barrelBlock(Block block, String cropName) {
        this.simpleBlock(block,
                models().cubeBottomTop(blockName(block), resourceBlock("barrel_side"), resourceBlock("barrel_bottom"), resourceBlock(cropName + "_barrel_top")));
    }

    private void leavesBlock(Block block) {
        simpleBlockWithItem(block,
                models().singleTexture(BuiltInRegistries.BLOCK.getKey(block).getPath(), mcLoc("block/leaves"), "all", blockTexture(block)).renderType("cutout")
        );
    }

    private void saplingBlock(Block block) {
        simpleBlock(block,
                models().cross(BuiltInRegistries.BLOCK.getKey(block).getPath(), blockTexture(block)).renderType("cutout"));
    }

    public void cabinetBlock(Block block, String woodType) {
        this.horizontalBlock(block, state -> {
            String suffix = state.getValue(CabinetBlock.OPEN) ? "_open" : "";
            return models().orientable(blockName(block) + suffix,
                    resourceBlock(woodType + "_cabinet_side"),
                    resourceBlock(woodType + "_cabinet_front" + suffix),
                    resourceBlock(woodType + "_cabinet_top"));
        });
    }
}