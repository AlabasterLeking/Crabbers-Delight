package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.common.block.entity.CrabTrapBlockEntity;
import alabaster.crabbersdelight.common.block.entity.inventory.CrabTrapItemHandler;
import alabaster.crabbersdelight.common.item.component.CrabTrapContents;
import alabaster.crabbersdelight.common.registry.CDModBlockEntity;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class CrabTrapBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final MapCodec<CrabTrapBlock> CODEC = simpleCodec(CrabTrapBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE_NORTH_SOUTH = Block.box(0.0D, 0.0D, 1.0D, 16.0D, 10.0D, 15.0D);
    private static final VoxelShape SHAPE_EAST_WEST = Block.box(1.0D, 0.0D, 0.0D, 15.0D, 10.0D, 16.0D);

    public CrabTrapBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HANGING, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result) {
        if (!level.isClientSide) {
            BlockEntity tileEntity = level.getBlockEntity(pos);
            if (tileEntity instanceof CrabTrapBlockEntity crabTrapBlockEntity) {
                if (player instanceof ServerPlayer serverplayer) {
                    serverplayer.openMenu(crabTrapBlockEntity, pos);
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof CrabTrapBlockEntity) {
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        boolean active = (state.getValue(WATERLOGGED) || state.getValue(HANGING))
                && CrabTrapBlockEntity.isSurroundedByWater(level, pos);
        if (!active) {
            return;
        }
        if (random.nextInt(14) == 0) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.6;
            double y = pos.getY() + 0.6;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.6;
            level.addParticle(ParticleTypes.BUBBLE, x, y, z, 0.0, 0.05, 0.0);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            return SHAPE_NORTH_SOUTH;
        }
        return SHAPE_EAST_WEST;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState stateAbove = context.getLevel().getBlockState(context.getClickedPos().above());
        Direction direction = context.getHorizontalDirection().getOpposite();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean hangingFlag = !stateAbove.isAir() && !(stateAbove.is(Blocks.WATER) || stateAbove.is(Blocks.LAVA));

        return this.defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, fluidstate.is(Fluids.WATER)).setValue(HANGING, hangingFlag);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.NORMAL;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        BlockState stateAbove = level.getBlockState(currentPos.above());
        boolean isWater = level.getFluidState(currentPos).is(Fluids.WATER);
        if (isWater && !state.getValue(WATERLOGGED)) {
            state = state.setValue(WATERLOGGED, true);
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        } else if (!isWater && state.getValue(WATERLOGGED)) {
            state = state.setValue(WATERLOGGED, false);
        }
        if (!stateAbove.isAir() && !(stateAbove.is(Blocks.WATER) || stateAbove.is(Blocks.LAVA))) {
            return state.setValue(HANGING, true);
        }

        return super.updateShape(state, dir, neighborState, level, currentPos, neighborPos).setValue(HANGING, false);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HANGING, WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrabTrapBlockEntity(pos, state);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        if (builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof CrabTrapBlockEntity crabTrap) {
            return Collections.singletonList(saveTileToItem(crabTrap));
        }
        return super.getDrops(state, builder);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        if (level.getBlockEntity(pos) instanceof CrabTrapBlockEntity crabTrap) {
            return saveTileToItem(crabTrap);
        }
        return super.getCloneItemStack(level, pos, state);
    }

    public static ItemStack saveTileToItem(BlockEntity tile) {
        Block block = tile.getBlockState().getBlock();
        ItemStack stack = new ItemStack(block.asItem());

        if (tile instanceof CrabTrapBlockEntity crabTrap) {
            CrabTrapItemHandler handler = crabTrap.getInventory();
            List<ItemStack> items = new ArrayList<>();
            boolean hasItems = false;
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack slotStack = handler.getStackInSlot(i);
                items.add(slotStack);
                if (!slotStack.isEmpty()) {
                    hasItems = true;
                }
            }
            if (hasItems) {
                stack.set(CDModDataComponents.CRAB_TRAP_CONTENTS.get(), new CrabTrapContents(items));
            }
        }

        return stack;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
        return level.isClientSide ? null : createTickerHelper(blockEntity, CDModBlockEntity.CRAB_TRAP.get(), CrabTrapBlockEntity::serverTick);
    }
}