package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.common.block.entity.WormBinBlockEntity;
import alabaster.crabbersdelight.common.registry.CDModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class WormBinBlock extends BaseEntityBlock {
    private static final int PRODUCE_CHANCE = 8;

    public WormBinBlock(Properties props) {
        super(props);
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

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getBlockEntity(pos) instanceof WormBinBlockEntity wormBin && !wormBin.isFull()) {
            if (random.nextInt(PRODUCE_CHANCE) == 0) {
                wormBin.addWorm();
            }
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof WormBinBlockEntity wormBin) {
            int collected = wormBin.collectWorms();
            if (collected > 0) {
                ItemStack drop = new ItemStack(CDModItems.WORM.get(), collected);
                if (!player.getInventory().add(drop)) {
                    player.drop(drop, false);
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}