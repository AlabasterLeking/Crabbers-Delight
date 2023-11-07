package alabaster.crabbersdelight.client.model;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.entity.CrabEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import software.bernie.geckolib.model.GeoModel;

public class CrabModel extends GeoModel<CrabEntity> {

	@Override
	public ResourceLocation getModelResource(CrabEntity crabEntity) {
		return new ResourceLocation(CrabbersDelight.MODID, "geo/crab.geo.json");
	}

	@Override
	public ResourceLocation getAnimationResource(CrabEntity crabEntity) {
		return new ResourceLocation(CrabbersDelight.MODID, "animations/crab.animation.json");
	}

	@Override
	public ResourceLocation getTextureResource(CrabEntity crabEntity) {
		if (crabEntity.getCrabColor() != null) {
			int color = crabEntity.getCrabColor().getId();
			return new ResourceLocation(CrabbersDelight.MODID, "textures/entity/" + DyeColor.byId(color).getName() + "_crab.png");
		}
		return new ResourceLocation(CrabbersDelight.MODID, "textures/entity/blue_crab.png");
	}
}
