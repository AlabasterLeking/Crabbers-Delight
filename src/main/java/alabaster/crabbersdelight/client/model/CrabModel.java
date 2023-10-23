package alabaster.crabbersdelight.client.model;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.entity.CrabEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.PathfinderMob;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CrabModel extends GeoModel<CrabEntity> {
	public static final ResourceLocation[] TEXTURE_LOCATIONS = new ResourceLocation[]{
			new ResourceLocation(CrabbersDelight.MODID, "textures/entity/blue_crab.png"),
			new ResourceLocation(CrabbersDelight.MODID, "textures/entity/green_crab.png"),
			new ResourceLocation(CrabbersDelight.MODID, "textures/entity/red_crab.png"),
			new ResourceLocation(CrabbersDelight.MODID, "textures/entity/orange_crab.png"),
			new ResourceLocation(CrabbersDelight.MODID, "textures/entity/yellow_crab.png"),
			new ResourceLocation(CrabbersDelight.MODID, "textures/entity/purple_crab.png"),
			new ResourceLocation(CrabbersDelight.MODID, "textures/entity/white_crab.png"),
			new ResourceLocation(CrabbersDelight.MODID, "textures/entity/gray_crab.png")
	};

	@Override
	public ResourceLocation getModelResource(CrabEntity crabEntity) {
		return new ResourceLocation(CrabbersDelight.MODID, "geo/crab.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CrabEntity crabEntity) {
		if (crabEntity.getVariant().getId() == 0) {
			return new ResourceLocation(CrabbersDelight.MODID, "textures/entity/blue_crab.png");
		} else if (crabEntity.getVariant().getId() == 1) {
				return new ResourceLocation(CrabbersDelight.MODID, "textures/entity/green_crab.png");
		} else if (crabEntity.getVariant().getId() == 2) {
			return new ResourceLocation(CrabbersDelight.MODID, "textures/entity/red_crab.png");
		} else if (crabEntity.getVariant().getId() == 3) {
			return new ResourceLocation(CrabbersDelight.MODID, "textures/entity/orange_crab.png");
		} else if (crabEntity.getVariant().getId() == 4) {
			return new ResourceLocation(CrabbersDelight.MODID, "textures/entity/yellow_crab.png");
		} else if (crabEntity.getVariant().getId() == 5) {
			return new ResourceLocation(CrabbersDelight.MODID, "textures/entity/purple_crab.png");
		} else if (crabEntity.getVariant().getId() == 6) {
			return new ResourceLocation(CrabbersDelight.MODID, "textures/entity/white_crab.png");
		} else if (crabEntity.getVariant().getId() == 7) {
			return new ResourceLocation(CrabbersDelight.MODID, "textures/entity/gray_crab.png");
		} else {
			return new ResourceLocation(CrabbersDelight.MODID, "textures/entity/blue_crab.png");
		}
	}

	@Override
	public ResourceLocation getAnimationResource(CrabEntity crabEntity) {
		return new ResourceLocation(CrabbersDelight.MODID, "animations/crab.animation.json");
	}

}
