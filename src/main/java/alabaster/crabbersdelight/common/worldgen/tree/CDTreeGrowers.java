package alabaster.crabbersdelight.common.worldgen.tree;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.worldgen.CDConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class CDTreeGrowers {
    public static final TreeGrower PALM = new TreeGrower(CrabbersDelight.MODID + ":palm",
            Optional.empty(), Optional.of(CDConfiguredFeatures.PALM_KEY), Optional.empty());
}
