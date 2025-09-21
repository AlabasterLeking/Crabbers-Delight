package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.common.block.entity.CDHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class CDHangingSignBlock extends CeilingHangingSignBlock {
    public CDHangingSignBlock(WoodType type, Properties properties) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CDHangingSignBlockEntity(pos, state);
    }
}
