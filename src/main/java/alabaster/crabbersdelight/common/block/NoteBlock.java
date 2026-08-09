package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.client.gui.NoteClientHandler;
import alabaster.crabbersdelight.common.block.entity.NoteBlockEntity;
import alabaster.crabbersdelight.common.item.component.NoteContent;
import alabaster.crabbersdelight.common.item.component.SignedNoteContent;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import alabaster.crabbersdelight.common.registry.CDModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class NoteBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty SIGNED = BooleanProperty.create("signed");
    public static final BooleanProperty FLAT = BooleanProperty.create("flat");

    private static final VoxelShape SHAPE_NORTH = Block.box(3, 2, 15, 13, 14, 16);
    private static final VoxelShape SHAPE_SOUTH = Block.box(3, 2, 0, 13, 14, 1);
    private static final VoxelShape SHAPE_WEST = Block.box(15, 2, 3, 16, 14, 13);
    private static final VoxelShape SHAPE_EAST = Block.box(0, 2, 3, 1, 14, 13);
    private static final VoxelShape SHAPE_FLAT_NORTH_SOUTH = Block.box(3, 0, 2, 13, 1, 14);
    private static final VoxelShape SHAPE_FLAT_EAST_WEST = Block.box(2, 0, 3, 14, 1, 13);

    public NoteBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(SIGNED, false)
                .setValue(FLAT, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(FLAT)) {
            return switch (state.getValue(FACING)) {
                case WEST, EAST -> SHAPE_FLAT_EAST_WEST;
                default -> SHAPE_FLAT_NORTH_SOUTH;
            };
        }
        return switch (state.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
            default -> Shapes.block();
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SIGNED, FLAT);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NoteBlockEntity(pos, state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        boolean flat = ctx.getClickedFace() == Direction.UP;
        return this.defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection().getOpposite())
                .setValue(FLAT, flat);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(FLAT)) {
            BlockPos below = pos.below();
            return level.getBlockState(below).isCollisionShapeFullBlock(level, below);
        }
        Direction facing = state.getValue(FACING);
        BlockPos supportPos = pos.relative(facing.getOpposite());
        return level.getBlockState(supportPos).isCollisionShapeFullBlock(level, supportPos);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(FLAT)) {
            if (direction == Direction.DOWN && !state.canSurvive(level, pos)) {
                return Blocks.AIR.defaultBlockState();
            }
        } else if (direction == state.getValue(FACING).getOpposite() && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof NoteBlockEntity note)) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide) {
            NoteClientHandler.openBlockScreen(pos, note.isSigned(), note.getTitle(), note.getText(), note.getAuthor());
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock()) && !level.isClientSide
                && level.getBlockEntity(pos) instanceof NoteBlockEntity note) {
            ItemStack drop = note.isSigned() ? signedNoteStack(note) : unsignedNoteStack(note);
            if (drop != null) {
                double x = pos.getX() + 0.5;
                double y = pos.getY() + 0.5;
                double z = pos.getZ() + 0.5;
                level.addFreshEntity(new ItemEntity(level, x, y, z, drop));
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    private ItemStack unsignedNoteStack(NoteBlockEntity note) {
        if (note.getText().isEmpty()) {
            return null;
        }
        ItemStack stack = new ItemStack(CDModItems.NOTE.get());
        stack.set(CDModDataComponents.NOTE_CONTENT.get(), new NoteContent(note.getText()));
        return stack;
    }

    private ItemStack signedNoteStack(NoteBlockEntity note) {
        ItemStack stack = new ItemStack(CDModItems.SIGNED_NOTE.get());
        stack.set(CDModDataComponents.SIGNED_NOTE_CONTENT.get(), new SignedNoteContent(note.getTitle(), note.getText(), note.getAuthor(), note.getGeneration()));
        return stack;
    }
}