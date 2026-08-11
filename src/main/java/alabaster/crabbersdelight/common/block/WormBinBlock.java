package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.common.block.entity.WormBinBlockEntity;
import alabaster.crabbersdelight.common.registry.CDModBlockEntity;
import alabaster.crabbersdelight.common.registry.CDModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.Nullable;

public class WormBinBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = Shapes.or(
            Shapes.box(0.0, 0.0, 0.0, 1.0, 1.0, 0.125),
            Shapes.box(0.0, 0.0, 0.875, 1.0, 1.0, 1.0),
            Shapes.box(0.0, 0.0, 0.125, 0.125, 1.0, 0.875),
            Shapes.box(0.875, 0.0, 0.125, 1.0, 1.0, 0.875),
            Shapes.box(0.125, 0.0, 0.125, 0.875, 0.875, 0.875)
    );

    public WormBinBlock(Properties props) {
        super(props);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WormBinBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, CDModBlockEntity.WORM_BIN.get(), WormBinBlockEntity::serverTick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof WormBinBlockEntity wormBin) {
            if (player.isShiftKeyDown()) {
                for (ItemStack ejected : wormBin.ejectFuel()) {
                    if (!player.getInventory().add(ejected)) {
                        player.drop(ejected, false);
                    }
                }
            } else {
                int collected = wormBin.collectWorms();
                if (collected > 0) {
                    ItemStack drop = new ItemStack(CDModItems.WORM.get(), collected);
                    if (!player.getInventory().add(drop)) {
                        player.drop(drop, false);
                    }
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }
        if (level.getBlockEntity(pos) instanceof WormBinBlockEntity wormBin && wormBin.insertFuel(stack)) {
            return ItemInteractionResult.CONSUME;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}