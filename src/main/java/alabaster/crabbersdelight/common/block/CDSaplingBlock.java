package alabaster.crabbersdelight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class CDSaplingBlock extends SaplingBlock {
    private final Supplier<Block> blockToSurviveOn;

    public CDSaplingBlock(AbstractTreeGrower treeGrower, Properties properties, Supplier<Block> block) {
        super(treeGrower, properties);
        this.blockToSurviveOn = block;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        Block block = state.getBlock();

        return blockToSurviveOn.get() == block ||
                state.is(BlockTags.DIRT) ||   // includes dirt, coarse dirt, podzol, etc.
                state.is(BlockTags.SAND);    // includes sand and red sand
    }
}