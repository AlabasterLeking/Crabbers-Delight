package alabaster.crabbersdelight.client.renderer;

import alabaster.crabbersdelight.common.block.FishPlaqueBlock;
import alabaster.crabbersdelight.common.block.entity.FishPlaqueBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.LinkedHashMap;
import java.util.Map;

public class FishPlaqueRenderer implements BlockEntityRenderer<FishPlaqueBlockEntity> {

    private final Map<Long, CachedFishEntity> cache = new LinkedHashMap<>(256, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, CachedFishEntity> eldest) {
            return size() > 256;
        }
    };

    public FishPlaqueRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(FishPlaqueBlockEntity be, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        if (!be.hasFish() || be.getLevel() == null) return;

        LivingEntity entity = getOrCreateEntity(be, be.getLevel());
        if (entity == null) return;

        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (!(dispatcher.getRenderer(entity) instanceof LivingEntityRenderer<?, ?> livingRenderer)) return;

        Direction facing = be.getBlockState().getValue(FishPlaqueBlock.FACING);
        renderModel(livingRenderer, entity, facing, poseStack, bufferSource, packedLight, packedOverlay);
    }

    @SuppressWarnings("unchecked")
    private <T extends LivingEntity, M extends EntityModel<T>> void renderModel(
            LivingEntityRenderer<T, M> renderer,
            LivingEntity entity,
            Direction facing,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            int packedOverlay) {

        M model = renderer.getModel();
        ResourceLocation texture = renderer.getTextureLocation((T) entity);

        // Fully zeroed = completely frozen pose, no swim cycle, no bobbing
        model.setupAnim((T) entity, 0f, 0f, 0f, 0f, 0f);

        poseStack.pushPose();

        poseStack.translate(0.5, 0.75, 0.5);
        poseStack.translate(0, 1.0, 0);

        float yRot = switch (facing) {
            case SOUTH -> 90f;
            case WEST  -> 0f;
            case NORTH -> 270f;
            case EAST  -> 180f;
            default    -> 0f;
        };
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        poseStack.translate(0.35, 0.0, -0.5);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180f));

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));
        model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay);

        poseStack.popPose();
    }

    private LivingEntity getOrCreateEntity(FishPlaqueBlockEntity be, Level level) {
        long posKey = be.getBlockPos().asLong();
        CachedFishEntity cached = cache.get(posKey);

        if (cached == null || cached.version != be.getDataVersion()) {
            EntityType<?> type = be.getEntityType();
            if (type == null) {
                cache.remove(posKey);
                return null;
            }

            Entity entity = type.create(level);
            if (!(entity instanceof LivingEntity living)) return null;

            // Load variant NBT (tropical fish color/pattern, axolotl variant, etc.)
            if (!be.getEntityData().isEmpty()) {
                entity.load(be.getEntityData());
            }

            cache.put(posKey, new CachedFishEntity(be.getDataVersion(), living));
            return living;
        }

        return cached.entity;
    }

    private record CachedFishEntity(int version, LivingEntity entity) {}
}