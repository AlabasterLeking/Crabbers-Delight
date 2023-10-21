package alabaster.crabbersdelight.client.model;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.entity.CrabEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class CrabModel<T extends CrabEntity> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CrabbersDelight.MODID, "crab"), "main");
	private final ModelPart crab;

	public CrabModel(ModelPart root) {
		this.crab = root.getChild("crab");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition crab = partdefinition.addOrReplaceChild("crab", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 2).addBox(1.0F, -5.0F, -3.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -5.0F, -3.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		crab.addOrReplaceChild("leg_r1", CubeListBuilder.create().texOffs(0, 14).addBox(-2.9937F, -0.0042F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -2.0F, -2.0F, 0.0F, 0.0F, -0.3927F));
		crab.addOrReplaceChild("leg_r2", CubeListBuilder.create().texOffs(15, 15).addBox(-2.8405F, -0.0609F, -0.923F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -2.0F, 3.0F, -0.1226F, 0.47F, -0.4622F));
		crab.addOrReplaceChild("leg_r3", CubeListBuilder.create().texOffs(8, 16).addBox(-2.9148F, -0.0007F, -0.8101F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -2.0F, 1.0F, -0.0038F, 0.0872F, -0.3929F));
		crab.addOrReplaceChild("leg_r4", CubeListBuilder.create().texOffs(8, 14).addBox(-0.1595F, -0.0609F, -0.923F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -2.0F, 3.0F, -0.1226F, -0.47F, 0.4622F));
		crab.addOrReplaceChild("leg_r5", CubeListBuilder.create().texOffs(0, 16).addBox(-0.0852F, -0.0007F, -0.8101F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -2.0F, 1.0F, -0.0038F, -0.0872F, 0.3929F));
		crab.addOrReplaceChild("leg_r6", CubeListBuilder.create().texOffs(17, 8).addBox(-0.0063F, -0.0042F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -2.0F, -2.0F, 0.0F, 0.0F, 0.3927F));
		crab.addOrReplaceChild("claw_r1", CubeListBuilder.create().texOffs(0, 8).addBox(-1.2396F, -1.0F, -3.4024F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -3.0F, -2.0F, 0.0F, -0.9163F, 0.0F));
		crab.addOrReplaceChild("claw_r2", CubeListBuilder.create().texOffs(10, 8).addBox(-0.7604F, -1.0F, -3.4024F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -3.0F, -2.0F, 0.0F, 0.9163F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		crab.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}