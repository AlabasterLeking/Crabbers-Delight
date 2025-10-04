package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.common.block.entity.CDHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class CDWallHangingSignBlock extends WallHangingSignBlock {
    public CDWallHangingSignBlock(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CDHangingSignBlockEntity(pos, state);
    }
}
