package alabaster.crabbersdelight.common.block;

import alabaster.crabbersdelight.common.block.entity.CDSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class CDStandingSignBlock extends StandingSignBlock {
    public CDStandingSignBlock(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CDSignBlockEntity(pos, state);
    }
}
