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

    private static final int FISH_COUNT = 12;
    private static final int FISH_CYCLE_TICKS = 240;
    private static final int FISH_VISIBLE_TICKS = 140;
    private static final int FISH_FADE_TICKS = 20;
    private static final boolean[][] FISH_SHAPE = {
            {false, false, true, false, false},
            {false, true, true, true, false},
            {true, true, true, true, true},
            {true, true, true, true, true},
            {false, false, true, false, false},
            {true, true, false, true, true},
            {true, false, false, false, true},
    };
    private static final int FISH_COLS = FISH_SHAPE.length;
    private static final int FISH_ROWS = FISH_SHAPE[0].length;

    private static final float FISH_ORBIT_SPEED = 0.09f;
    private static final float FISH_SPEED_WOBBLE_AMOUNT = 0.9f;
    private static final float FISH_RADIUS_WOBBLE_AMOUNT = 0.35f;
    private static final float FISH_VERTICAL_RANGE = 0.1f;
    private static final float FISH_MIN_DEPTH = 0.3f;
    private static final float FISH_MAX_DEPTH = 3.0f;
    private static final float FISH_ALPHA = 0.85f;
    private static final float FISH_DART_DISTANCE_MULTIPLIER = 4f;
    private static final int FISH_DART_TICKS = 12;

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
        float ticksSinceDisturbance = entity.isDisturbed()
                ? Math.max(0f, FishingSpotEntity.EXPIRE_FADE_TICKS - ticksRemaining)
                : 0f;
        float shrinkFactor = ticksRemaining < FishingSpotEntity.EXPIRE_FADE_TICKS
                ? smoothstep(Math.max(0f, ticksRemaining) / FishingSpotEntity.EXPIRE_FADE_TICKS)
                : 1f;
        float growFactor = age < FishingSpotEntity.FADE_IN_TICKS
                ? smoothstep(age / FishingSpotEntity.FADE_IN_TICKS)
                : 1f;
        spotRadius *= shrinkFactor * growFactor;

        for (int fish = 0; fish < FISH_COUNT; fish++) {
            float fishOffset = (FISH_CYCLE_TICKS / (float) FISH_COUNT) * fish;
            float fishShiftedAge = age + fishOffset;
            float fishCycleAge = fishShiftedAge % FISH_CYCLE_TICKS;
            long fishCycleIteration = (long) (fishShiftedAge / FISH_CYCLE_TICKS);
            long fishSeed = entitySeed * 1000003L + fish * 613L + fishCycleIteration * 2862933555777941757L;

            if (fishCycleAge >= FISH_VISIBLE_TICKS) {
                continue;
            }

            float fishAlpha = FISH_ALPHA;
            if (fishCycleAge < FISH_FADE_TICKS) {
                fishAlpha *= smoothstep(fishCycleAge / FISH_FADE_TICKS);
            } else if (fishCycleAge > FISH_VISIBLE_TICKS - FISH_FADE_TICKS) {
                fishAlpha *= smoothstep((FISH_VISIBLE_TICKS - fishCycleAge) / FISH_FADE_TICKS);
            }
            if (entity.isDisturbed()) {
                fishAlpha *= 1f - smoothstep(ticksSinceDisturbance / FishingSpotEntity.EXPIRE_FADE_TICKS);
            }

            float orbitAngleOffset = randomFloat(fishSeed, 3) * 2f * (float) Math.PI;
            float orbitRadius = spotRadius * (0.25f + randomFloat(fishSeed, 4) * 0.5f);
            float orbitDirection = randomFloat(fishSeed, 5) > 0.5f ? 1f : -1f;
            float speedWobbleFreq = 0.03f + randomFloat(fishSeed, 6) * 0.05f;
            float speedWobblePhase = randomFloat(fishSeed, 7) * 2f * (float) Math.PI;
            float radiusWobbleFreq = 0.02f + randomFloat(fishSeed, 8) * 0.04f;
            float radiusWobblePhase = randomFloat(fishSeed, 9) * 2f * (float) Math.PI;
            float verticalFreq = 0.015f + randomFloat(fishSeed, 10) * 0.025f;
            float verticalPhase = randomFloat(fishSeed, 11) * 2f * (float) Math.PI;
            float baseDepth = -(FISH_MIN_DEPTH + randomFloat(fishSeed, 12) * (FISH_MAX_DEPTH - FISH_MIN_DEPTH));

            float[] herePos = fishOrbitPosition(fishCycleAge, orbitAngleOffset, orbitRadius, orbitDirection,
                    speedWobbleFreq, speedWobblePhase, radiusWobbleFreq, radiusWobblePhase, verticalFreq, verticalPhase);
            float[] aheadPos = fishOrbitPosition(fishCycleAge + 1f, orbitAngleOffset, orbitRadius, orbitDirection,
                    speedWobbleFreq, speedWobblePhase, radiusWobbleFreq, radiusWobblePhase, verticalFreq, verticalPhase);

            if (entity.isDisturbed()) {
                float dartBoostHere = 1f + smoothstep(ticksSinceDisturbance / FISH_DART_TICKS) * FISH_DART_DISTANCE_MULTIPLIER;
                float dartBoostAhead = 1f + smoothstep((ticksSinceDisturbance + 1f) / FISH_DART_TICKS) * FISH_DART_DISTANCE_MULTIPLIER;
                herePos[0] *= dartBoostHere;
                herePos[2] *= dartBoostHere;
                aheadPos[0] *= dartBoostAhead;
                aheadPos[2] *= dartBoostAhead;
            }

            float facingAngle = (float) Math.atan2(aheadPos[2] - herePos[2], aheadPos[0] - herePos[0]);

            drawFish(consumer, matrix, herePos[0], baseDepth + herePos[1], herePos[2], facingAngle, fishAlpha);
        }

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

    private static float[] fishOrbitPosition(float time, float angleOffset, float baseRadius, float direction, float speedWobbleFreq, float speedWobblePhase, float radiusWobbleFreq, float radiusWobblePhase, float verticalFreq, float verticalPhase) {
        float speedWobble = FISH_SPEED_WOBBLE_AMOUNT * (float) Math.sin(time * speedWobbleFreq + speedWobblePhase);
        float angle = angleOffset + (time * FISH_ORBIT_SPEED + speedWobble) * direction;

        float radiusWobble = 1f + FISH_RADIUS_WOBBLE_AMOUNT * (float) Math.sin(time * radiusWobbleFreq + radiusWobblePhase);
        float radius = baseRadius * radiusWobble;

        float verticalOffset = FISH_VERTICAL_RANGE * (float) Math.sin(time * verticalFreq + verticalPhase);

        return new float[]{(float) Math.cos(angle) * radius, verticalOffset, (float) Math.sin(angle) * radius};
    }

    private static void drawFish(VertexConsumer consumer, Matrix4f matrix, float centerX, float centerY, float centerZ, float facingAngle, float alpha) {
        float cos = (float) Math.cos(facingAngle);
        float sin = (float) Math.sin(facingAngle);

        float colCenterOffset = (FISH_COLS - 1) / 2f;
        float rowCenterOffset = (FISH_ROWS - 1) / 2f;

        for (int col = 0; col < FISH_COLS; col++) {
            for (int row = 0; row < FISH_ROWS; row++) {
                if (!FISH_SHAPE[col][row]) continue;

                float localX = (colCenterOffset - col) * CELL_SIZE;
                float localY = (rowCenterOffset - row) * CELL_SIZE;
                drawFishCell(consumer, matrix, centerX, centerY, centerZ, cos, sin, localX, localY, alpha);
            }
        }
    }

    private static void drawFishCell(VertexConsumer consumer, Matrix4f matrix, float centerX, float centerY, float centerZ, float cos, float sin, float localX, float localY, float alpha) {
        float half = CELL_SIZE / 2f;
        float x0 = localX - half;
        float x1 = localX + half;
        float y0 = localY - half;
        float y1 = localY + half;

        addFishVertex(consumer, matrix, centerX, centerY, centerZ, cos, sin, x0, y0, alpha);
        addFishVertex(consumer, matrix, centerX, centerY, centerZ, cos, sin, x1, y0, alpha);
        addFishVertex(consumer, matrix, centerX, centerY, centerZ, cos, sin, x1, y1, alpha);

        addFishVertex(consumer, matrix, centerX, centerY, centerZ, cos, sin, x0, y0, alpha);
        addFishVertex(consumer, matrix, centerX, centerY, centerZ, cos, sin, x1, y1, alpha);
        addFishVertex(consumer, matrix, centerX, centerY, centerZ, cos, sin, x0, y1, alpha);
    }

    private static void addFishVertex(VertexConsumer consumer, Matrix4f matrix, float centerX, float centerY, float centerZ, float cos, float sin, float localX, float localY, float alpha) {
        float rotatedX = localX * cos;
        float rotatedZ = localX * sin;
        consumer.addVertex(matrix, centerX + rotatedX, centerY + localY, centerZ + rotatedZ)
                .setColor(0f, 0f, 0f, alpha);
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