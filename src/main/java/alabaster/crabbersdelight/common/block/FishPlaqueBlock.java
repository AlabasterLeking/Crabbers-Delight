package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.common.block.entity.FishPlaqueBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FishPlaqueBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_NORTH = Block.box(0, 3, 15, 16, 13, 16);
    private static final VoxelShape SHAPE_SOUTH = Block.box(0, 3,  0, 16, 13,  1);
    private static final VoxelShape SHAPE_WEST  = Block.box(15, 3, 0, 16, 13, 16);
    private static final VoxelShape SHAPE_EAST  = Block.box(0,  3, 0,  1, 13, 16);

    public FishPlaqueBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST  -> SHAPE_WEST;
            case EAST  -> SHAPE_EAST;
            default    -> Shapes.block();
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FishPlaqueBlockEntity(pos, state);
    }

    @Nullable
    private static EntityType<?> getEntityTypeFromBucket(MobBucketItem bucket) {
        try {
            var field = MobBucketItem.class.getDeclaredField("type");
            field.setAccessible(true);
            return (EntityType<?>) field.get(bucket);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    private static void sync(FishPlaqueBlockEntity be, Level level, BlockPos pos, BlockState state) {
        // setChanged() marks the BE dirty so it gets saved
        be.setChanged();
        // ServerLevel.sendBlockUpdated internally calls getChunkSource().blockChanged(pos)
        // which queues the ClientboundBlockEntityDataPacket for all tracking players.
        // This is the only call needed — additional manual blockChanged() calls are redundant.
        level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return ItemInteractionResult.SUCCESS;
        if (!(level.getBlockEntity(pos) instanceof FishPlaqueBlockEntity be)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        if (stack.getItem() instanceof MobBucketItem mobBucket) {
            EntityType<?> entityType = getEntityTypeFromBucket(mobBucket);
            if (entityType == null) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            if (be.hasFish()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

            CompoundTag extraData = new CompoundTag();
            CustomData bucketData = stack.get(DataComponents.BUCKET_ENTITY_DATA);
            if (bucketData != null) extraData = bucketData.copyTag();

            be.setFishData(entityType, extraData);
            sync(be, level, pos, state);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0f, 1.0f);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }

            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        if (!(level.getBlockEntity(pos) instanceof FishPlaqueBlockEntity be)) return InteractionResult.PASS;

        if (be.hasFish()) {
            Item bucketItem = BuiltInRegistries.ITEM.stream()
                    .filter(item -> item instanceof MobBucketItem mob && getEntityTypeFromBucket(mob) == be.getEntityType())
                    .findFirst()
                    .orElse(Items.WATER_BUCKET);

            ItemStack bucket = new ItemStack(bucketItem);
            if (!be.getEntityData().isEmpty()) {
                bucket.set(DataComponents.BUCKET_ENTITY_DATA, CustomData.of(be.getEntityData()));
            }
            if (!player.getInventory().add(bucket)) {
                player.drop(bucket, false);
            }

            be.clearFish();
            sync(be, level, pos, state);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0f, 1.0f);

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }
}