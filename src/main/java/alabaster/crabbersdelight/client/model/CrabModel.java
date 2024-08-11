package alabaster.crabbersdelight.client.model;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.entity.CrabEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class CrabModel extends DefaultedEntityGeoModel<CrabEntity> {

	public CrabModel() {
		super(ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "crab"));
	}
}
