package alabaster.crabbersdelight.common.block.entity;

import alabaster.crabbersdelight.common.registry.CDModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WormBinBlockEntity extends BlockEntity {
    public static final int MAX_WORMS = 3;

    private int worms = 0;

    public WormBinBlockEntity(BlockPos pos, BlockState state) {
        super(CDModBlockEntity.WORM_BIN.get(), pos, state);
    }

    public int getWorms() {
        return worms;
    }

    public boolean isFull() {
        return worms >= MAX_WORMS;
    }

    public void addWorm() {
        if (worms < MAX_WORMS) {
            worms++;
            setChanged();
        }
    }

    public int collectWorms() {
        int collected = worms;
        worms = 0;
        setChanged();
        return collected;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Worms", worms);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        worms = tag.getInt("Worms");
    }
}