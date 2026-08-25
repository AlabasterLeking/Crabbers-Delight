package alabaster.crabbersdelight.common.fluid;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

import java.util.function.Supplier;

public class CDFluidType extends BaseFlowingFluid {
    private final boolean source;
    private final Supplier<Item> bottleItem;

    public CDFluidType(BaseFlowingFluid.Properties properties, boolean source, Supplier<Item> bottleItem, int unused) {
        super(properties);
        this.source = source;
        this.bottleItem = bottleItem;
    }

    @Override public Fluid getSource() {
        return source ? this : super.getSource();
    }

    @Override public Fluid getFlowing() {
        return source ? super.getFlowing() : this;
    }

    @Override public boolean isSource(FluidState fluidState) {
        return source;
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return source ? 8 : fluidState.getValue(LEVEL);
    }

    public Item getBottle() {
        return bottleItem.get();
    }
}