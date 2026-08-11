package alabaster.crabbersdelight.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.OrganicCompostBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class WormItem extends Item {
    private static final int BONEMEAL_USE_EVENT = 1505;

    public WormItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (!(state.getBlock() instanceof OrganicCompostBlock compostBlock)) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            int stage = state.getValue(OrganicCompostBlock.COMPOSTING);
            int maxStage = compostBlock.getMaxCompostingStage();

            BlockState advanced = stage >= maxStage
                    ? ModBlocks.RICH_SOIL.get().defaultBlockState()
                    : state.setValue(OrganicCompostBlock.COMPOSTING, stage + 1);

            level.setBlockAndUpdate(pos, advanced);
            level.levelEvent(BONEMEAL_USE_EVENT, pos, 0);

            ItemStack heldWorm = context.getItemInHand();
            heldWorm.shrink(1);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}