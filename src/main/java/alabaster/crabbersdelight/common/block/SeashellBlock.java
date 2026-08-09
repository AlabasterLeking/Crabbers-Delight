package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.common.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SeashellBlock extends Block implements SimpleWaterloggedBlock {
    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 6);
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 1, 13);

    public SeashellBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(VARIANT, 0)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VARIANT, FACING, WATERLOGGED);
    }

    private int getVariantCount() {
        return Config.SEASHELL_VARIANT_COUNT.get();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        int count = getVariantCount();
        int variant = context.getLevel().getRandom().nextInt(count);
        Direction facing = Direction.Plane.HORIZONTAL.getRandomDirection(context.getLevel().getRandom());

        boolean waterlogged = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;

        return this.defaultBlockState()
                .setValue(VARIANT, variant)
                .setValue(FACING, facing)
                .setValue(WATERLOGGED, waterlogged);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        var offset = state.getOffset(world, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        BlockState ground = level.getBlockState(below);
        return ground.isFaceSturdy(level, below, Direction.UP);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        boolean inWater = level.getFluidState(pos).getType() == Fluids.WATER;

        if (inWater && !state.getValue(WATERLOGGED)) {
            state = state.setValue(WATERLOGGED, true);
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        } else if (!inWater && state.getValue(WATERLOGGED)) {
            state = state.setValue(WATERLOGGED, false);
        }

        return super.updateShape(state, dir, neighborState, level, pos, neighborPos);
    }
}