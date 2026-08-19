package alabaster.crabbersdelight.common.fishingspot;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class FishingSpotEntity extends Entity {
    private static final EntityDataAccessor<Integer> RADIUS =
            SynchedEntityData.defineId(FishingSpotEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LIFETIME_TICKS =
            SynchedEntityData.defineId(FishingSpotEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DISTURBED =
            SynchedEntityData.defineId(FishingSpotEntity.class, EntityDataSerializers.BOOLEAN);

    public static final int EXPIRE_FADE_TICKS = 20;
    public static final int FADE_IN_TICKS = 20;

    private int age = 0;

    public FishingSpotEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    @Override
    public void tick() {
        super.tick();
        age++;

        if (random.nextFloat() < 0.16f) {
            spawnBubbles(3 + random.nextInt(3));
        }

        if (random.nextFloat() < 0.05f) {
            spawnFishingParticles(1 + random.nextInt(2));
        }

        if (age >= getLifetimeTicks()) {
            discard();
        }
    }

    private void spawnBubbles(int count) {
        for (int i = 0; i < count; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double dist = Math.sqrt(random.nextDouble()) * getRadius();
            double x = getX() + Math.cos(angle) * dist;
            double z = getZ() + Math.sin(angle) * dist;
            level().addParticle(ParticleTypes.BUBBLE_POP, x, getY() + 0.1, z, 0, 0.05, 0);
        }
    }

    private void spawnFishingParticles(int count) {
        for (int i = 0; i < count; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double dist = Math.sqrt(random.nextDouble()) * getRadius();
            double x = getX() + Math.cos(angle) * dist;
            double z = getZ() + Math.sin(angle) * dist;
            level().addParticle(ParticleTypes.FISHING, x, getY() + 0.1, z, 0, 0.05, 0);
        }
    }

    public boolean contains(double x, double y, double z) {
        double dx = x - getX();
        double dz = z - getZ();
        return dx * dx + dz * dz <= (double) getRadius() * getRadius() && Math.abs(y - getY()) <= 2;
    }

    public void expireNow() {
        setLifetimeTicks(Math.min(getLifetimeTicks(), age + EXPIRE_FADE_TICKS));
    }

    public void disturb() {
        this.entityData.set(DISTURBED, true);
        expireNow();
    }

    public boolean isDisturbed() {
        return this.entityData.get(DISTURBED);
    }

    public int getRadius() {
        return this.entityData.get(RADIUS);
    }

    public void setRadius(int radius) {
        this.entityData.set(RADIUS, radius);
    }

    public int getLifetimeTicks() {
        return this.entityData.get(LIFETIME_TICKS);
    }

    public void setLifetimeTicks(int ticks) {
        this.entityData.set(LIFETIME_TICKS, ticks);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(RADIUS, 4);
        builder.define(LIFETIME_TICKS, 2400);
        builder.define(DISTURBED, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        this.age = compound.getInt("Age");
        setRadius(compound.getInt("Radius"));
        setLifetimeTicks(compound.getInt("LifetimeTicks"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("Age", age);
        compound.putInt("Radius", getRadius());
        compound.putInt("LifetimeTicks", getLifetimeTicks());
    }

    @Override
    public boolean isPickable() {
        return false;
    }
}