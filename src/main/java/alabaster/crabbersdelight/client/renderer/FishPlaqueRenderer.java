package alabaster.crabbersdelight.client.renderer;

import alabaster.crabbersdelight.common.block.FishPlaqueBlock;
import alabaster.crabbersdelight.common.block.entity.FishPlaqueBlockEntity;
import alabaster.crabbersdelight.common.entity.crab.CrabEntity;
import alabaster.crabbersdelight.common.registry.CDModEntities;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class FishPlaqueRenderer implements BlockEntityRenderer<FishPlaqueBlockEntity> {

    private final EntityRenderDispatcher entityRenderer;

    private final Map<Long, CachedFishEntity> entityCache = new LinkedHashMap<>(256, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, CachedFishEntity> eldest) {
            return size() > 256;
        }
    };

    private static final Set<EntityType<?>> SPECIAL_RENDER = Set.of(
            EntityType.PUFFERFISH,
            EntityType.AXOLOTL,
            CDModEntities.CRAB.get()
    );

    public FishPlaqueRenderer(BlockEntityRendererProvider.Context context) {
        this.entityRenderer = context.getEntityRenderer();
    }

    @Override
    public void render(FishPlaqueBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (!be.hasFish() || be.getLevel() == null) return;

        Entity entity = getOrCreateEntity(be, be.getLevel());
        if (entity == null) return;

        var facing = be.getBlockState().getValue(FishPlaqueBlock.FACING);

        // Branch here — special types get their own render path
        if (SPECIAL_RENDER.contains(entity.getType())) {
            renderSpecial(entity, be, poseStack, bufferSource, packedLight, packedOverlay, partialTick);
            return;
        }

        float scale = 0.53125f;
        float maxDim = Math.max(entity.getBbWidth(), entity.getBbHeight());
        if (maxDim > 1.0f) {
            scale /= maxDim;
        }

        float stepMultiplier = 0.25f;
        var vec3 = new Vec3(
                facing.getStepX() * stepMultiplier,
                -scale,
                facing.getStepZ() * stepMultiplier
        );

        float yDegree = -facing.toYRot() + 90f + 180f;

        poseStack.pushPose();
        poseStack.translate(0.5, 0.0, 0.5);
        poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
        poseStack.mulPose(Axis.YP.rotationDegrees(yDegree));
        poseStack.translate(-0.125f, 0, 0.125f);
        poseStack.translate(0, -0.06f, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(90f));
        poseStack.translate(0, -0.10f, 0);
        poseStack.scale(scale * 1.5f, scale * 1.5f, scale * 1.5f);

        entity.setYHeadRot(0);
        entity.setYBodyRot(0);

        this.entityRenderer.render(entity, 0.0, 0.0, 0.0, 0.0f, 0f, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }

    private void renderSpecial(Entity entity, FishPlaqueBlockEntity be, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, float partialTick) {
        if (entity instanceof Pufferfish puffer) {
            puffer.setPuffState(2);

            var facing = be.getBlockState().getValue(FishPlaqueBlock.FACING);
            float yRot = facing.toYRot();
            float scale = 0.53125f * 1.5f;

            poseStack.pushPose();
            poseStack.translate(0.5, 0.0, 0.5);
            poseStack.translate(
                    facing.getStepX() * -0.35f,
                    0.175,
                    facing.getStepZ() * -0.35f
            );

            poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
            poseStack.translate(0, -0.02f, 0);
            poseStack.scale(scale, scale, scale);

            entity.setYHeadRot(yRot);
            entity.setYBodyRot(yRot);

            this.entityRenderer.render(entity, 0.0, 0.0, 0.0, yRot, 0f, poseStack, bufferSource, packedLight);
            poseStack.popPose();
        } else if (entity instanceof Axolotl axolotl) {
            var facing = be.getBlockState().getValue(FishPlaqueBlock.FACING);
            float yRot = facing.toYRot() + 180f;
            float scale = 0.53125f * 1.5f;

            if (!(entityRenderer.getRenderer(axolotl) instanceof LivingEntityRenderer<?, ?> livingRenderer)) return;
            if (!(livingRenderer.getModel() instanceof AxolotlModel axolotlModel)) return;

            axolotlModel.setupAnim(axolotl, 0f, 0f, 0f, 0f, 0f);

            @SuppressWarnings("unchecked")
            var texture = ((LivingEntityRenderer<Axolotl, ?>) livingRenderer).getTextureLocation(axolotl);

            ModelPart headPart = null;
            try {
                var field = AxolotlModel.class.getDeclaredField("head");
                field.setAccessible(true);
                headPart = (ModelPart) field.get(axolotlModel);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                this.entityRenderer.render(axolotl, 0.0, 0.0, 0.0, yRot, 0f, poseStack, bufferSource, packedLight);
                return;
            }

            poseStack.pushPose();
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.translate(
                    facing.getStepX() * -0.9f,
                    0,
                    facing.getStepZ() * -0.9f);
            poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
            poseStack.translate(0, -0.02f, 0);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
            poseStack.scale(scale, scale, scale);

            var consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));
            headPart.render(poseStack, consumer, packedLight, packedOverlay);

            poseStack.popPose();
        } else if (entity instanceof CrabEntity crab) {
            var facing = be.getBlockState().getValue(FishPlaqueBlock.FACING);
            float scale = 0.53125f * 1.5f;
            float yDegree = -facing.toYRot() + 90f + 180f;

            poseStack.pushPose();
            poseStack.translate(0.5, 0.0, 0.5);

            var vec3 = new Vec3(
                    facing.getStepX() * 0.25f,
                    -scale + 0.1875f,   // shift down 5px
                    facing.getStepZ() * 0.25f
            );
            poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());

            poseStack.mulPose(Axis.YP.rotationDegrees(yDegree));
            poseStack.translate(0, -0.06f, 0);

            poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
            poseStack.mulPose(Axis.ZP.rotationDegrees(-90f));

            poseStack.translate(0, -0.2f, 0);
            poseStack.scale(scale, scale, scale);

            entity.setYHeadRot(0);
            entity.setYBodyRot(0);

            this.entityRenderer.render(entity, 0.0, 0.0, 0.0, 0.0f, 0f, poseStack, bufferSource, packedLight);
            poseStack.popPose();
        }
    }

    @Nullable
    private Entity getOrCreateEntity(FishPlaqueBlockEntity be, Level level) {
        long posKey = be.getBlockPos().asLong();
        CachedFishEntity cached = entityCache.get(posKey);

        if (cached == null || cached.version != be.getDataVersion()) {
            EntityType<?> type = be.getEntityType();
            if (type == null) {
                entityCache.remove(posKey);
                return null;
            }

            Entity entity = type.create(level);
            if (entity == null) return null;

            if (!be.getEntityData().isEmpty()) {
                if (entity instanceof Bucketable bucketable) {
                    bucketable.loadFromBucketTag(be.getEntityData());
                } else {
                    entity.load(be.getEntityData());
                }
            }

            entityCache.put(posKey, new CachedFishEntity(be.getDataVersion(), entity));
            return entity;
        }

        return cached.entity;
    }

    private record CachedFishEntity(int version, Entity entity) {}
}