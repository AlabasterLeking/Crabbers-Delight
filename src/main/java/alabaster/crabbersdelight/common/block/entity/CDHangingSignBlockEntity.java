package alabaster.crabbersdelight.common.block.entity;

import alabaster.crabbersdelight.common.registry.CDModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CDHangingSignBlockEntity extends HangingSignBlockEntity {
    public CDHangingSignBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return CDModBlockEntity.HANGING_PALM_SIGN.get();
    }
}