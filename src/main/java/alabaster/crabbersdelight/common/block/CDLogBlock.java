package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.common.registry.CDModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.common.ItemAbility;

import javax.annotation.Nullable;

public class CDLogBlock extends RotatedPillarBlock {
    public CDLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if(context.getItemInHand().getItem() instanceof AxeItem) {
            if(state.is(CDModBlocks.PALM_LOG.get())) {
                return CDModBlocks.STRIPPED_PALM_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            if(state.is(CDModBlocks.PALM_WOOD.get() )) {
                return CDModBlocks.STRIPPED_PALM_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }

        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }


    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, level, pos, newState, isMoving);

        if (!level.isClientSide) {
            int rangeX = 5;
            int rangeY = 10;
            int rangeZ = 5;

            BlockPos.betweenClosed(pos.offset(-rangeX, 0, -rangeZ), pos.offset(rangeX, rangeY, rangeZ))
                    .forEach(checkPos -> {
                        BlockState leafState = level.getBlockState(checkPos);
                        if (leafState.is(CDModBlocks.PALM_LEAVES.get())) {
                            if (leafState.hasProperty(LeavesBlock.PERSISTENT)) {
                                BooleanProperty persistentProp = LeavesBlock.PERSISTENT;
                                if (leafState.getValue(persistentProp)) {
                                    level.destroyBlock(checkPos, true);
                                }
                            }
                        }
                    });
        }
    }
}
