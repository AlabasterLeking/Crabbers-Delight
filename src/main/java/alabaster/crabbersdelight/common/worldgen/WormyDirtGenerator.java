package alabaster.crabbersdelight.common.worldgen;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.Config;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class WormyDirtGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(WormyDirtGenerator.class);

    private static final int MIN_GROUP_SIZE = 1;
    private static final int MAX_GROUP_SIZE = 3;

    private static final ConcurrentLinkedQueue<ChunkPos> PENDING_CHUNKS = new ConcurrentLinkedQueue<>();

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (!event.getLevel().isClientSide() && event.isNewChunk()) {
            ChunkPos pos = event.getChunk().getPos();
            LOGGER.info("[WormyDirt] Queued new chunk {}", pos);
            PENDING_CHUNKS.add(pos);
        }
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        ChunkPos pos;
        while ((pos = PENDING_CHUNKS.poll()) != null) {
            tryGenerateInChunk(level, pos);
        }
    }

    private static void tryGenerateInChunk(ServerLevel level, ChunkPos chunkPos) {
        if (!level.hasChunk(chunkPos.x, chunkPos.z)) {
            LOGGER.info("[WormyDirt] Chunk {} not yet ready on level tick, skipping this attempt", chunkPos);
            return;
        }
        if (!Config.GENERATE_WORMY_DIRT.get()) {
            LOGGER.info("[WormyDirt] Disabled via config, skipping {}", chunkPos);
            return;
        }
        RandomSource random = level.getRandom();
        if (random.nextInt(Config.CHANCE_WORMY_DIRT.get()) != 0) {
            LOGGER.info("[WormyDirt] Chance roll failed for {}", chunkPos);
            return;
        }

        int x = chunkPos.getMinBlockX() + random.nextInt(16);
        int z = chunkPos.getMinBlockZ() + random.nextInt(16);
        int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
        int minY = level.getMinBuildHeight();
        LOGGER.info("[WormyDirt] Rolled success for {}, scanning full column x={}, z={}, from y={} to y={}", chunkPos, x, z, surfaceY, minY);

        BlockPos chosen = null;
        int dirtSeen = 0;
        BlockPos.MutableBlockPos cursor = new BlockPos(x, surfaceY, z).mutable();
        for (int y = surfaceY; y >= minY; y--) {
            cursor.setY(y);
            if (level.getBlockState(cursor).is(Blocks.DIRT)) {
                dirtSeen++;
                if (random.nextInt(dirtSeen) == 0) {
                    chosen = cursor.immutable();
                }
            }
        }

        if (chosen != null) {
            LOGGER.info("[WormyDirt] Chose {} out of {} dirt blocks in column, placing group", chosen, dirtSeen);
            placeGroup(level, chosen, random);
        } else {
            LOGGER.info("[WormyDirt] No dirt found anywhere in column at x={}, z={}", x, z);
        }
    }

    private static void placeGroup(ServerLevel level, BlockPos start, RandomSource random) {
        int groupSize = MIN_GROUP_SIZE + random.nextInt(MAX_GROUP_SIZE - MIN_GROUP_SIZE + 1);
        BlockPos.MutableBlockPos cursor = start.mutable();
        for (int i = 0; i < groupSize; i++) {
            if (level.getBlockState(cursor).is(Blocks.DIRT)) {
                level.setBlock(cursor, CDModBlocks.WORMY_DIRT.get().defaultBlockState(), 3);
            }
            Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
            cursor.move(dir);
        }
    }
}