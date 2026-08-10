package alabaster.crabbersdelight.client.renderer;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.fishingspot.FishingSpotEntity;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class FishingSpotRenderer extends EntityRenderer<FishingSpotEntity> {
    private static final float CELL_SIZE = 0.05f;
    private static final int CENTER_LIFETIME_TICKS = 90;
    private static final int CENTER_COUNT = 3;
    private static final float CENTER_SPREAD_FRACTION = 0.55f;
    private static final float CENTER_MAX_RADIUS_FRACTION = 0.85f;
    private static final float MAX_THICKNESS = 0.15f;
    private static final float BAND_ALPHA = 0.7f;

    private static final RenderType WAVE_BAND = RenderType.create(
            "crabbersdelight_wave_band",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.TRIANGLES,
            256,
            RenderType.CompositeState.builder()
                    .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
                    .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                    .setCullState(RenderStateShard.NO_CULL)
                    .createCompositeState(false)
    );

    public FishingSpotRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(FishingSpotEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0, 0.05, 0);

        Matrix4f matrix = poseStack.last().pose();
        VertexConsumer consumer = buffer.getBuffer(WAVE_BAND);

        float spotRadius = entity.getRadius();
        float age = entity.tickCount + partialTick;
        float brightness = lightBrightness(packedLight);
        long entitySeed = entity.getId();

        float ticksRemaining = entity.getLifetimeTicks() - age;
        float shrinkFactor = ticksRemaining < FishingSpotEntity.EXPIRE_FADE_TICKS
                ? smoothstep(Math.max(0f, ticksRemaining) / FishingSpotEntity.EXPIRE_FADE_TICKS)
                : 1f;
        float growFactor = age < FishingSpotEntity.FADE_IN_TICKS
                ? smoothstep(age / FishingSpotEntity.FADE_IN_TICKS)
                : 1f;
        spotRadius *= shrinkFactor * growFactor;

        for (int center = 0; center < CENTER_COUNT; center++) {
            float centerOffset = (CENTER_LIFETIME_TICKS / (float) CENTER_COUNT) * center;
            float shiftedAge = age + centerOffset;
            float centerAge = shiftedAge % CENTER_LIFETIME_TICKS;
            float progress = centerAge / CENTER_LIFETIME_TICKS;
            long cycleIteration = (long) (shiftedAge / CENTER_LIFETIME_TICKS);
            long positionSeed = entitySeed * 1000003L + center * 97L + cycleIteration * 6364136223846793005L;

            float centerAngle = randomFloat(positionSeed, 1) * 2f * (float) Math.PI;
            float centerDist = randomFloat(positionSeed, 2) * spotRadius * CENTER_SPREAD_FRACTION;
            float centerX = (float) Math.cos(centerAngle) * centerDist;
            float centerZ = (float) Math.sin(centerAngle) * centerDist;

            long waveSeed = positionSeed * 2862933555777941757L;
            float waveMaxRadius = spotRadius * CENTER_MAX_RADIUS_FRACTION;

            renderWaveBand(consumer, matrix, centerX, centerZ, waveMaxRadius, progress, brightness, waveSeed);
        }

        poseStack.popPose();
    }

    private static void renderWaveBand(VertexConsumer consumer, Matrix4f matrix, float centerX, float centerZ, float maxRadius, float progress, float brightness, long seed) {
        float eased = smoothstep(progress);
        float currentRadius = eased * maxRadius;
        float thickness = eased * MAX_THICKNESS;
        float innerRadius = Math.max(0f, currentRadius - thickness / 2f);
        float outerRadius = currentRadius + thickness / 2f;

        int gridExtent = (int) Math.ceil(outerRadius / CELL_SIZE) + 1;

        for (int gx = -gridExtent; gx <= gridExtent; gx++) {
            for (int gz = -gridExtent; gz <= gridExtent; gz++) {
                float cellCenterX = gx * CELL_SIZE;
                float cellCenterZ = gz * CELL_SIZE;
                float dist = (float) Math.sqrt(cellCenterX * cellCenterX + cellCenterZ * cellCenterZ);

                if (dist < innerRadius || dist > outerRadius) {
                    continue;
                }
                if (progress > cellDropoutThreshold(gx, gz, seed)) {
                    continue;
                }

                drawSquare(consumer, matrix, centerX + cellCenterX, centerZ + cellCenterZ, brightness);
            }
        }
    }

    private static float cellDropoutThreshold(int gx, int gz, long seed) {
        long combined = gx * 2654435761L + gz * 40503L + seed * 6364136223846793005L;
        int hash = (int) ((combined ^ (combined >>> 33)) & 0x7FFFFFFFL);
        return (hash % 10000) / 10000f;
    }

    private static float randomFloat(long seed, int salt) {
        long combined = seed * 2862933555777941757L + salt * 3935559000370003845L;
        int hash = (int) ((combined ^ (combined >>> 33)) & 0x7FFFFFFFL);
        return (hash % 10000) / 10000f;
    }

    private static void drawSquare(VertexConsumer consumer, Matrix4f matrix, float centerX, float centerZ, float brightness) {
        float half = CELL_SIZE / 2f;
        float x0 = centerX - half;
        float x1 = centerX + half;
        float z0 = centerZ - half;
        float z1 = centerZ + half;

        addVertex(consumer, matrix, x0, 0, z0, brightness);
        addVertex(consumer, matrix, x1, 0, z0, brightness);
        addVertex(consumer, matrix, x1, 0, z1, brightness);

        addVertex(consumer, matrix, x0, 0, z0, brightness);
        addVertex(consumer, matrix, x1, 0, z1, brightness);
        addVertex(consumer, matrix, x0, 0, z1, brightness);
    }

    private static float smoothstep(float x) {
        float clamped = Math.max(0f, Math.min(1f, x));
        return clamped * clamped * (3f - 2f * clamped);
    }

    private static float lightBrightness(int packedLight) {
        int skyLight = (packedLight >> 20) & 0xF;
        int blockLight = (packedLight >> 4) & 0xF;
        return Math.max(skyLight, blockLight) / 15f;
    }

    private static void addVertex(VertexConsumer consumer, Matrix4f matrix, float x, float y, float z, float brightness) {
        consumer.addVertex(matrix, x, y, z)
                .setColor(brightness, brightness, brightness, BAND_ALPHA);
    }

    @Override
    public ResourceLocation getTextureLocation(FishingSpotEntity entity) {
        return null;
    }

    @Override
    public boolean shouldRender(FishingSpotEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }
}