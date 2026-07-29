package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.common.block.entity.TackleBoxBlockEntity;
import alabaster.crabbersdelight.common.item.component.TackleBoxContents;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TackleBoxBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_NORTH_SOUTH = Block.box(0, 0, 4, 16, 8, 12);
    private static final VoxelShape SHAPE_EAST_WEST = Block.box(4, 0, 0, 12, 8, 16);

    public TackleBoxBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(FACING).getAxis() == Direction.Axis.X ? SHAPE_EAST_WEST : SHAPE_NORTH_SOUTH;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TackleBoxBlockEntity(pos, state);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        if (builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof TackleBoxBlockEntity tackleBox) {
            return Collections.singletonList(saveTileToItem(tackleBox));
        }
        return super.getDrops(state, builder);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        if (level.getBlockEntity(pos) instanceof TackleBoxBlockEntity tackleBox) {
            return saveTileToItem(tackleBox);
        }
        return super.getCloneItemStack(level, pos, state);
    }

    public static ItemStack saveTileToItem(BlockEntity tile) {
        Block block = tile.getBlockState().getBlock();
        ItemStack stack = new ItemStack(block.asItem());

        if (tile instanceof TackleBoxBlockEntity tackleBox) {
            List<ItemStack> items = new ArrayList<>();
            boolean hasItems = false;
            for (int i = 0; i < tackleBox.handler.getSlots(); i++) {
                ItemStack slotStack = tackleBox.handler.getStackInSlot(i);
                items.add(slotStack);
                if (!slotStack.isEmpty()) {
                    hasItems = true;
                }
            }
            if (hasItems) {
                stack.set(CDModDataComponents.TACKLE_BOX_CONTENTS.get(), new TackleBoxContents(items));
            }
        }

        return stack;
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return ItemInteractionResult.SUCCESS;
        }

        if (!(level.getBlockEntity(pos) instanceof TackleBoxBlockEntity tackleBox)) {
            return ItemInteractionResult.SUCCESS;
        }

        if (player.isShiftKeyDown() && stack.isEmpty() && hand == InteractionHand.MAIN_HAND) {
            ItemStack tackleBoxItem = saveTileToItem(tackleBox);
            level.removeBlock(pos, false);
            level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2f,
                    ((level.random.nextFloat() - level.random.nextFloat()) * 0.7f + 1f) * 2f);
            if (!player.addItem(tackleBoxItem)) {
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, tackleBoxItem));
            }
            return ItemInteractionResult.SUCCESS;
        }

        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(tackleBox, pos);
        }
        return ItemInteractionResult.SUCCESS;
    }
}