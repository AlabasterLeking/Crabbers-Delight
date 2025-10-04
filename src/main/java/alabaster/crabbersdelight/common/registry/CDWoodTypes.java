package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class CDWoodTypes {
    public static final WoodType PALM = WoodType.register(new WoodType(CrabbersDelight.MODID + ":palm", BlockSetType.OAK));
}
