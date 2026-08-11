package alabaster.crabbersdelight.client.renderer;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.block.entity.inventory.TackleBoxItemHandler;
import alabaster.crabbersdelight.common.item.LureItem;
import alabaster.crabbersdelight.common.utils.TackleBoxProximity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;

public class LureAwareFishingHookRenderer extends EntityRenderer<FishingHook> {
    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/fishing_hook.png");
    private static final ResourceLocation BARBED_TEXTURE = CrabbersDelight.modPrefix("textures/entity/fishing_hook_barbed.png");
    private static final ResourceLocation DOUBLE_TEXTURE = CrabbersDelight.modPrefix("textures/entity/fishing_hook_double.png");
    private static final ResourceLocation MAGNETIC_TEXTURE = CrabbersDelight.modPrefix("textures/entity/fishing_hook_magnetic.png");
    private static final ResourceLocation AUTOMATIC_TEXTURE = CrabbersDelight.modPrefix("textures/entity/fishing_hook_automatic.png");
    private static final ResourceLocation STORM_TEXTURE = CrabbersDelight.modPrefix("textures/entity/fishing_hook_storm.png");

    private static final RenderType DEFAULT_RENDER_TYPE = RenderType.entityCutout(DEFAULT_TEXTURE);
    private static final RenderType BARBED_RENDER_TYPE = RenderType.entityCutout(BARBED_TEXTURE);
    private static final RenderType DOUBLE_RENDER_TYPE = RenderType.entityCutout(DOUBLE_TEXTURE);
    private static final RenderType MAGNETIC_RENDER_TYPE = RenderType.entityCutout(MAGNETIC_TEXTURE);
    private static final RenderType AUTOMATIC_RENDER_TYPE = RenderType.entityCutout(AUTOMATIC_TEXTURE);
    private static final RenderType STORM_RENDER_TYPE = RenderType.entityCutout(STORM_TEXTURE);

    public LureAwareFishingHookRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(FishingHook entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Player player = entity.getPlayerOwner();
        if (player != null) {
            poseStack.pushPose();
            poseStack.pushPose();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            PoseStack.Pose posestack$pose = poseStack.last();
            RenderType renderType = resolveRenderType(player);
            VertexConsumer vertexconsumer = buffer.getBuffer(renderType);
            vertex(vertexconsumer, posestack$pose, packedLight, 0.0F, 0, 0, 1);
            vertex(vertexconsumer, posestack$pose, packedLight, 1.0F, 0, 1, 1);
            vertex(vertexconsumer, posestack$pose, packedLight, 1.0F, 1, 1, 0);
            vertex(vertexconsumer, posestack$pose, packedLight, 0.0F, 1, 0, 0);
            poseStack.popPose();
            float f = player.getAttackAnim(partialTicks);
            float f1 = Mth.sin(Mth.sqrt(f) * (float) Math.PI);
            Vec3 vec3 = this.getPlayerHandPos(player, f1, partialTicks);
            Vec3 vec31 = entity.getPosition(partialTicks).add(0.0F, 0.25F, 0.0F);
            float f2 = (float) (vec3.x - vec31.x);
            float f3 = (float) (vec3.y - vec31.y);
            float f4 = (float) (vec3.z - vec31.z);
            VertexConsumer vertexconsumer1 = buffer.getBuffer(RenderType.lineStrip());
            PoseStack.Pose posestack$pose1 = poseStack.last();

            for (int j = 0; j <= 16; ++j) {
                stringVertex(f2, f3, f4, vertexconsumer1, posestack$pose1, fraction(j, 16), fraction(j + 1, 16));
            }

            poseStack.popPose();
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        }
    }

    private RenderType resolveRenderType(Player player) {
        TackleBoxProximity.TackleBoxAccess tackleBox = TackleBoxProximity.find(player);
        if (tackleBox == null) {
            return DEFAULT_RENDER_TYPE;
        }

        ItemStack lure = tackleBox.getSlot(TackleBoxItemHandler.LURE_SLOT);
        if (!(lure.getItem() instanceof LureItem lureItem)) {
            return DEFAULT_RENDER_TYPE;
        }

        return switch (lureItem.getEffect()) {
            case BARBED -> BARBED_RENDER_TYPE;
            case DOUBLE -> DOUBLE_RENDER_TYPE;
            case MAGNETIC -> MAGNETIC_RENDER_TYPE;
            case AUTOMATIC -> AUTOMATIC_RENDER_TYPE;
            case STORM -> STORM_RENDER_TYPE;
        };
    }

    private Vec3 getPlayerHandPos(Player player, float p_340872_, float partialTick) {
        int i = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
        ItemStack itemstack = player.getMainHandItem();
        if (!itemstack.canPerformAction(ItemAbilities.FISHING_ROD_CAST)) {
            i = -i;
        }

        if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && player == Minecraft.getInstance().player) {
            double d4 = (double) 960.0F / (double) (Integer) this.entityRenderDispatcher.options.fov().get();
            Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float) i * 0.525F, -0.1F).scale(d4).yRot(p_340872_ * 0.5F).xRot(-p_340872_ * 0.7F);
            return player.getEyePosition(partialTick).add(vec3);
        } else {
            float f = Mth.lerp(partialTick, player.yBodyRotO, player.yBodyRot) * ((float) Math.PI / 180F);
            double d0 = (double) Mth.sin(f);
            double d1 = (double) Mth.cos(f);
            float f1 = player.getScale();
            double d2 = (double) i * 0.35 * (double) f1;
            double d3 = 0.8 * (double) f1;
            float f2 = player.isCrouching() ? -0.1875F : 0.0F;
            return player.getEyePosition(partialTick).add(-d1 * d2 - d0 * d3, (double) f2 - 0.45 * (double) f1, -d0 * d2 + d1 * d3);
        }
    }

    private static float fraction(int numerator, int denominator) {
        return (float) numerator / (float) denominator;
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, int y, int u, int v) {
        consumer.addVertex(pose, x - 0.5F, (float) y - 0.5F, 0.0F).setColor(-1).setUv((float) u, (float) v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    private static void stringVertex(float x, float y, float z, VertexConsumer consumer, PoseStack.Pose pose, float stringFraction, float nextStringFraction) {
        float f = x * stringFraction;
        float f1 = y * (stringFraction * stringFraction + stringFraction) * 0.5F + 0.25F;
        float f2 = z * stringFraction;
        float f3 = x * nextStringFraction - f;
        float f4 = y * (nextStringFraction * nextStringFraction + nextStringFraction) * 0.5F + 0.25F - f1;
        float f5 = z * nextStringFraction - f2;
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        f3 /= f6;
        f4 /= f6;
        f5 /= f6;
        consumer.addVertex(pose, f, f1, f2).setColor(-16777216).setNormal(pose, f3, f4, f5);
    }

    @Override
    public ResourceLocation getTextureLocation(FishingHook entity) {
        return DEFAULT_TEXTURE;
    }
}