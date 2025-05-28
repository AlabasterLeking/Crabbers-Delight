package alabaster.crabbersdelight.common.entity.crab;

import alabaster.crabbersdelight.CrabbersDelight;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class CrabRenderer extends MobRenderer<CrabEntity, CrabModel<CrabEntity>> {
    private static final Map<CrabVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(CrabVariant.class), map -> {
                map.put(CrabVariant.BLACK,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/black_crab.png"));
                map.put(CrabVariant.BLUE,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/blue_crab.png"));
                map.put(CrabVariant.BROWN,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/brown_crab.png"));
                map.put(CrabVariant.CYAN,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/cyan_crab.png"));
                map.put(CrabVariant.GRAY,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/gray_crab.png"));
                map.put(CrabVariant.GREEN,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/green_crab.png"));
                map.put(CrabVariant.LIGHT_BLUE,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/light_blue_crab.png"));
                map.put(CrabVariant.LIGHT_GRAY,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/light_gray_crab.png"));
                map.put(CrabVariant.LIME,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/lime_crab.png"));
                map.put(CrabVariant.MAGENTA,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/magenta_crab.png"));
                map.put(CrabVariant.ORANGE,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/orange_crab.png"));
                map.put(CrabVariant.PINK,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/pink_crab.png"));
                map.put(CrabVariant.PURPLE,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/purple_crab.png"));
                map.put(CrabVariant.RED,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/red_crab.png"));
                map.put(CrabVariant.WHITE,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/white_crab.png"));
                map.put(CrabVariant.YELLOW,
                        ResourceLocation.fromNamespaceAndPath(CrabbersDelight.MODID, "textures/entity/yellow_crab.png"));
            });

    public CrabRenderer(EntityRendererProvider.Context context) {
        super(context, new CrabModel(context.bakeLayer(CrabModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(CrabEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(CrabEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            poseStack.scale(1f, 1f, 1f);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}