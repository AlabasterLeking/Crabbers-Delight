package alabaster.crabbersdelight.common.fishingspot;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.Config;
import alabaster.crabbersdelight.common.registry.CDModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.List;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class FishingSpotManager {
    private static final int SPAWN_SEARCH_RADIUS = 32;
    private static final int CANDIDATE_COUNT = 5;
    private static final float POINTS_FOR_CERTAIN_SPAWN = 10f;
    private static final float RAIN_SPAWN_MULTIPLIER = 1.75f;
    private static final int SCORE_SCAN_RADIUS = 4;
    private static final int SHORE_CLEARANCE_RADIUS = 5;
    private static final double SPOT_QUERY_PADDING = 8;
    private static final double DISTURBANCE_SCAN_RADIUS = 48;
    private static final double AREA_DENSITY_RADIUS = 64;
    private static final int MAX_SPOTS_PER_AREA = 3;

    public static FishingSpotEntity findSpotAt(Level level, BlockPos pos) {
        AABB box = new AABB(pos).inflate(SPOT_QUERY_PADDING);
        List<FishingSpotEntity> nearby = level.getEntitiesOfClass(FishingSpotEntity.class, box);
        for (FishingSpotEntity spot : nearby) {
            if (spot.contains(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5)) {
                return spot;
            }
        }
        return null;
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        if (level.getGameTime() % 20 != 0) {
            return;
        }

        checkDisturbance(level);

        if (level.getGameTime() % Config.FISHING_SPOT_SPAWN_INTERVAL.get() == 0) {
            for (Player player : level.players()) {
                trySpawnNearPlayer(level, player);
            }
        }
    }

    private static void trySpawnNearPlayer(ServerLevel level, Player player) {
        RandomSource random = level.getRandom();
        BlockPos playerPos = player.blockPosition();

        BlockPos bestSurfacePos = null;
        int bestPoints = -1;

        for (int i = 0; i < CANDIDATE_COUNT; i++) {
            int x = playerPos.getX() + random.nextInt(SPAWN_SEARCH_RADIUS * 2) - SPAWN_SEARCH_RADIUS;
            int z = playerPos.getZ() + random.nextInt(SPAWN_SEARCH_RADIUS * 2) - SPAWN_SEARCH_RADIUS;
            BlockPos surfacePos = findWaterSurface(level, x, z);
            if (surfacePos == null || findSpotAt(level, surfacePos) != null) {
                continue;
            }
            if (!hasShoreClearance(level, surfacePos)) {
                continue;
            }

            int points = computePoints(level, surfacePos);
            if (points > bestPoints) {
                bestPoints = points;
                bestSurfacePos = surfacePos;
            }
        }

        if (bestSurfacePos == null || bestPoints <= 0) {
            return;
        }

        float spawnChance = Math.min(1f, bestPoints / POINTS_FOR_CERTAIN_SPAWN);
        if (level.isRainingAt(bestSurfacePos)) {
            spawnChance = Math.min(1f, spawnChance * RAIN_SPAWN_MULTIPLIER);
        }
        if (random.nextFloat() >= spawnChance) {
            return;
        }

        if (countNearbySpots(level, bestSurfacePos) >= MAX_SPOTS_PER_AREA) {
            return;
        }

        int radius = Math.min(2 + bestPoints / 4, 5);
        int lifespanTicks = 2400 + bestPoints * 400;

        FishingSpotEntity spot = CDModEntities.FISHING_SPOT.get().create(level);
        if (spot == null) {
            return;
        }
        spot.moveTo(bestSurfacePos.getX() + 0.5, bestSurfacePos.getY() + 1, bestSurfacePos.getZ() + 0.5, 0f, 0f);
        spot.setRadius(radius);
        spot.setLifetimeTicks(lifespanTicks);
        level.addFreshEntity(spot);
    }

    private static int countNearbySpots(ServerLevel level, BlockPos pos) {
        AABB box = new AABB(pos).inflate(AREA_DENSITY_RADIUS);
        return level.getEntitiesOfClass(FishingSpotEntity.class, box).size();
    }

    public static BlockPos findWaterSurface(ServerLevel level, int x, int z) {
        int topY = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
        for (int y = topY; y > level.getMinBuildHeight(); y--) {
            BlockPos pos = new BlockPos(x, y, z);
            FluidState fluid = level.getFluidState(pos);
            FluidState fluidAbove = level.getFluidState(pos.above());
            if (fluid.is(Fluids.WATER) && !fluidAbove.is(Fluids.WATER)) {
                return pos;
            }
        }
        return null;
    }

    private static boolean hasShoreClearance(ServerLevel level, BlockPos surfacePos) {
        int radiusSquared = SHORE_CLEARANCE_RADIUS * SHORE_CLEARANCE_RADIUS;
        for (int dx = -SHORE_CLEARANCE_RADIUS; dx <= SHORE_CLEARANCE_RADIUS; dx++) {
            for (int dz = -SHORE_CLEARANCE_RADIUS; dz <= SHORE_CLEARANCE_RADIUS; dz++) {
                if (dx * dx + dz * dz > radiusSquared) {
                    continue;
                }
                BlockPos checkPos = surfacePos.offset(dx, 0, dz);
                if (!level.getFluidState(checkPos).is(Fluids.WATER)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int computePoints(ServerLevel level, BlockPos surfacePos) {
        int points = 0;

        for (int dx = -SCORE_SCAN_RADIUS; dx <= SCORE_SCAN_RADIUS; dx++) {
            for (int dz = -SCORE_SCAN_RADIUS; dz <= SCORE_SCAN_RADIUS; dz++) {
                BlockPos checkPos = surfacePos.offset(dx, 0, dz);
                Block block = level.getBlockState(checkPos).getBlock();
                if (block == Blocks.SEAGRASS || block == Blocks.TALL_SEAGRASS
                        || block == Blocks.KELP || block == Blocks.KELP_PLANT) {
                    points += 1;
                }
            }
        }

        int depth = 0;
        BlockPos.MutableBlockPos cursor = surfacePos.mutable();
        while (level.getFluidState(cursor).is(Fluids.WATER) && depth < 10) {
            depth++;
            cursor.move(0, -1, 0);
        }
        if (depth >= 4) {
            points += 3;
        } else if (depth >= 2) {
            points += 1;
        }

        return points;
    }

    private static void checkDisturbance(ServerLevel level) {
        for (Player player : level.players()) {
            AABB scanBox = new AABB(player.blockPosition()).inflate(DISTURBANCE_SCAN_RADIUS);
            List<FishingSpotEntity> spots = level.getEntitiesOfClass(FishingSpotEntity.class, scanBox);
            for (FishingSpotEntity spot : spots) {
                AABB box = new AABB(
                        spot.getX() - spot.getRadius(), spot.getY() - 2, spot.getZ() - spot.getRadius(),
                        spot.getX() + spot.getRadius() + 1, spot.getY() + 2, spot.getZ() + spot.getRadius() + 1
                );
                List<Entity> nearby = level.getEntitiesOfClass(Entity.class, box, entity -> true);
                for (Entity entity : nearby) {
                    if (entity instanceof Boat) {
                        spot.disturb();
                        break;
                    }
                    if (entity instanceof Player p && p.isInWater()) {
                        spot.disturb();
                        break;
                    }
                    if (entity instanceof LivingEntity living && !(entity instanceof Player)
                            && living.isInWater() && !(entity instanceof AbstractFish)) {
                        spot.disturb();
                        break;

                    }
                }
            }
        }
    }
}