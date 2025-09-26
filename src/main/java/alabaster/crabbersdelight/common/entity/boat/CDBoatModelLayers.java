package alabaster.crabbersdelight.common.entity.boat;

import alabaster.crabbersdelight.CrabbersDelight;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class CDBoatModelLayers {
    public static final ModelLayerLocation PALM_BOAT_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "boat/palm"), "main");
    public static final ModelLayerLocation PALM_CHEST_BOAT_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "chest_boat/palm"), "main");
}