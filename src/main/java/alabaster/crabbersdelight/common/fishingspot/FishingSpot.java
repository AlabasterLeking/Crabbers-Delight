package alabaster.crabbersdelight.common.fishingspot;

import net.minecraft.core.BlockPos;

public class FishingSpot {
    public final BlockPos center;
    public final int radius;
    public long expiresAtTick;

    public FishingSpot(BlockPos center, int radius, long expiresAtTick) {
        this.center = center;
        this.radius = radius;
        this.expiresAtTick = expiresAtTick;
    }

    public boolean contains(BlockPos pos) {
        return contains(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
    }

    public boolean contains(double x, double y, double z) {
        double dx = x - (center.getX() + 0.5);
        double dz = z - (center.getZ() + 0.5);
        return dx * dx + dz * dz <= (double) radius * radius && Math.abs(y - center.getY()) <= 2;
    }
}