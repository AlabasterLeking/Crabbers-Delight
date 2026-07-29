package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.common.registry.CDModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class CDLeavesBlock extends LeavesBlock {
    public static final BooleanProperty GENERATED = BooleanProperty.create("generated");

    public CDLeavesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(GENERATED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(GENERATED);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        if (state.getValue(GENERATED) && level instanceof ServerLevel serverLevel) {
            serverLevel.scheduleTick(pos, this, 1);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(GENERATED) && !hasPalmLogNearby(level, pos, 4)) {
            level.destroyBlock(pos, true);
            return;
        }
        super.tick(state, level, pos, random);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(GENERATED) && !state.getValue(PERSISTENT)) {
            super.randomTick(state, level, pos, random);
            return;
        }

        if (state.getValue(GENERATED) && !hasPalmLogNearby(level, pos, 4)) {
            level.destroyBlock(pos, true);
        }
    }

    private boolean hasPalmLogNearby(Level level, BlockPos pos, int range) {
        for (int dx = -range; dx <= range; dx++) {
            for (int dy = -range; dy <= range; dy++) {
                for (int dz = -range; dz <= range; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) continue;
                    BlockPos checkPos = pos.offset(dx, dy, dz);
                    BlockState checkState = level.getBlockState(checkPos);
                    if (checkState.is(CDModBlocks.PALM_LOG.get())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(PERSISTENT) || state.getValue(GENERATED);
    }
}