package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.common.block.entities.CrabTrapBlockEntity;
import alabaster.crabbersdelight.common.registry.ModBlockEntity;
import alabaster.crabbersdelight.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class CrabTrapBlock extends Block implements EntityBlock
{
    private static final VoxelShape SHAPE = makeShape();

    public CrabTrapBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.125, 0.625, 0.5, 0.875, 1, 0.5), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.875, 1, 0.625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.0625, 1, 0.625, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.0625, 0.0625, 0.625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.9375, 0, 0.0625, 1, 0.625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.0625, 1, 0.0625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.5625, 0.0625, 1, 0.625, 0.9375), BooleanOp.OR);

        return shape;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof CrabTrapBlockEntity crab_trap) {
                SimpleMenuProvider provider = new SimpleMenuProvider(, CrabTrapBlockEntity.TITLE);
                NetworkHooks.openScreen((ServerPlayer) player, provider, pos);
            }
        }

        return InteractionResult.sidedSuccess(!level.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntity.CRAB_TRAP.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : ($0, $1, $2, blockEntity) -> {
            if(blockEntity instanceof CrabTrapBlockEntity crab_trap) {
                crab_trap.tick();
            }
        };
    }
}
