package alabaster.crabbersdelight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class LifePreserverBlock extends Block implements Equipable {

    public static final double WAIST_HEIGHT = 0.7;
    public static final String SEAT_KEY = "crabbersdelight_life_preserver_seat";
    private static final String SEAT_POS_KEY = "crabbersdelight_life_preserver_seat_pos";
    private static final double SEAT_Y_OFFSET = 3.0 / 16.0;
    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final MapCodec<LifePreserverBlock> CODEC = simpleCodec(LifePreserverBlock::new);

    private static final VoxelShape FLOOR_SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 4, 4),
            Block.box(0, 0, 4, 4, 4, 12),
            Block.box(12, 0, 4, 16, 4, 12),
            Block.box(0, 0, 12, 16, 4, 16)
    );

    private static final VoxelShape WALL_SHAPE_SOUTH = Shapes.or(
            Block.box(0, 0, 0, 16, 4, 4),
            Block.box(0, 4, 0, 4, 12, 4),
            Block.box(12, 4, 0, 16, 12, 4),
            Block.box(0, 12, 0, 16, 16, 4)
    );

    private static final VoxelShape MIDDLE_SHAPE_SOUTH = Shapes.or(
            Block.box(0, 0, 6, 16, 4, 10),
            Block.box(0, 4, 6, 4, 12, 10),
            Block.box(12, 4, 6, 16, 12, 10),
            Block.box(0, 12, 6, 16, 16, 10)
    );

    private static final VoxelShape[] WALL_SHAPES_BY_HORIZONTAL_INDEX = buildRotatedShapes(WALL_SHAPE_SOUTH);
    private static final VoxelShape[] MIDDLE_SHAPES_BY_HORIZONTAL_INDEX = buildRotatedShapes(MIDDLE_SHAPE_SOUTH);

    private static VoxelShape[] buildRotatedShapes(VoxelShape southDefault) {
        VoxelShape[] shapes = new VoxelShape[4];
        VoxelShape current = southDefault;
        for (int i = 0; i < 4; i++) {
            shapes[i] = current;
            current = rotateClockwise90(current);
        }
        return shapes;
    }

    private static VoxelShape rotateClockwise90(VoxelShape shape) {
        VoxelShape result = Shapes.empty();
        for (AABB box : shape.toAabbs()) {
            double newMinX = 1 - box.maxZ;
            double newMaxX = 1 - box.minZ;
            double newMinZ = box.minX;
            double newMaxZ = box.maxX;
            result = Shapes.or(result, Shapes.box(newMinX, box.minY, newMinZ, newMaxX, box.maxY, newMaxZ));
        }
        return result;
    }

    private static VoxelShape shapeFor(VoxelShape[] shapesByIndex, Direction facing) {
        return switch (facing) {
            case SOUTH -> shapesByIndex[0];
            case WEST -> shapesByIndex[1];
            case NORTH -> shapesByIndex[2];
            case EAST -> shapesByIndex[3];
            default -> shapesByIndex[0];
        };
    }

    public LifePreserverBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACE, AttachFace.FLOOR)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<LifePreserverBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACE, FACING, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clicked = context.getClickedFace();
        boolean waterlogged = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        BlockState state = this.defaultBlockState().setValue(WATERLOGGED, waterlogged);
        if (clicked.getAxis().isHorizontal()) {
            return state.setValue(FACE, AttachFace.WALL).setValue(FACING, clicked);
        } else if (clicked == Direction.UP) {
            return state.setValue(FACE, AttachFace.FLOOR);
        } else {
            return state.setValue(FACE, AttachFace.CEILING).setValue(FACING, context.getHorizontalDirection());
        }
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACE)) {
            case WALL -> shapeFor(WALL_SHAPES_BY_HORIZONTAL_INDEX, state.getValue(FACING));
            case CEILING -> shapeFor(MIDDLE_SHAPES_BY_HORIZONTAL_INDEX, state.getValue(FACING));
            default -> FLOOR_SHAPE;
        };
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.isShiftKeyDown()) {
            return tryEquip(level, pos, player);
        }
        if (state.getValue(FACE) != AttachFace.FLOOR) {
            return InteractionResult.PASS;
        }
        return trySit(level, pos, player);
    }

    private InteractionResult tryEquip(Level level, BlockPos pos, Player player) {
        if (!player.canUseSlot(EquipmentSlot.LEGS) || !player.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            level.removeBlock(pos, false);
            player.setItemSlot(EquipmentSlot.LEGS, new ItemStack(this));
            level.playSound(null, pos, getEquipSound().value(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return InteractionResult.SUCCESS;
    }

    private InteractionResult trySit(Level level, BlockPos pos, Player player) {
        if (player.isPassenger()) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        ArmorStand seat = getSeat(level, pos);
        if (seat != null && !seat.getPassengers().isEmpty()) {
            return InteractionResult.PASS;
        }
        if (seat == null) {
            seat = new ArmorStand(level, pos.getX() + 0.5, pos.getY() + SEAT_Y_OFFSET, pos.getZ() + 0.5);
            seat.setInvisible(true);
            seat.setNoGravity(true);
            seat.setInvulnerable(true);
            seat.setSilent(true);
            seat.setNoBasePlate(true);
            applyMarkerFlag(seat);
            seat.getPersistentData().putBoolean(SEAT_KEY, true);
            seat.getPersistentData().putLong(SEAT_POS_KEY, pos.asLong());
            level.addFreshEntity(seat);
        }

        player.startRiding(seat);
        return InteractionResult.SUCCESS;
    }

    private void applyMarkerFlag(ArmorStand seat) {
        CompoundTag data = new CompoundTag();
        seat.saveWithoutId(data);
        data.putBoolean("Marker", true);
        seat.load(data);
    }

    @Nullable
    private ArmorStand getSeat(Level level, BlockPos pos) {
        AABB box = new AABB(pos).inflate(0.3, 0.5, 0.3);
        List<ArmorStand> seats = level.getEntitiesOfClass(ArmorStand.class, box, entity ->
                entity.getPersistentData().getBoolean(SEAT_KEY)
                        && entity.getPersistentData().getLong(SEAT_POS_KEY) == pos.asLong());
        return seats.isEmpty() ? null : seats.get(0);
    }

    private void clearSeat(Level level, BlockPos pos) {
        ArmorStand seat = getSeat(level, pos);
        if (seat != null) {
            for (Entity passenger : seat.getPassengers()) {
                passenger.stopRiding();
            }
            seat.discard();
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            clearSeat(level, pos);
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.LEGS;
    }
}