package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.common.registry.CDDamageSources;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CoconutBlock extends FallingBlock {
    private static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D);

    public static final MapCodec<CoconutBlock> CODEC = simpleCodec(CoconutBlock::new);

    public CoconutBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState();
    }

    @Override
    public void tick(BlockState state, net.minecraft.server.level.ServerLevel level, BlockPos pos, net.minecraft.util.RandomSource random) {
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        boolean hanging = aboveState.is(CDModBlocks.PALM_LEAVES.get());
        if (!hanging) {
            super.tick(state, level, pos, random);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, net.minecraft.util.RandomSource random) {
        // Do nothing
    }

    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState replaceState, FallingBlockEntity fallingEntity) {
        super.onLand(level, pos, state, replaceState, fallingEntity);

        if (!level.isClientSide && fallingEntity != null) {
            boolean hitPlayer = false;

            for (Entity entity : level.getEntities(null, SHAPE.bounds().move(pos))) {
                if (entity instanceof LivingEntity living) {
                    living.hurt(CDDamageSources.getSimpleDamageSource(level, CDDamageSources.FALLING_COCONUT), 2.0F);

                    if (entity instanceof Player) {
                        hitPlayer = true;
                    }
                }
            }

            if (hitPlayer) {
                // Break block and drop loot
                level.destroyBlock(pos, true);
            }
        }
    }

}

