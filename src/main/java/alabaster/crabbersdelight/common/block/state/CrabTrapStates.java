package alabaster.crabbersdelight.common.block.state;

import net.minecraft.util.StringRepresentable;

public enum CrabTrapStates implements StringRepresentable
{
    SURROUNDED("surrounded");

    private final String supportName;

    CrabTrapStates(String name) {
        this.supportName = name;
    }

    @Override
    public String toString() {
        return this.getSerializedName();
    }

    @Override
    public String getSerializedName() {
        return this.supportName;
    }
}
