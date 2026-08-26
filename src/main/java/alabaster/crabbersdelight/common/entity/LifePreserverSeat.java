package alabaster.crabbersdelight.common.entity;

import alabaster.crabbersdelight.common.registry.CDModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LifePreserverSeat extends Entity {
    private static final double PASSENGER_HEIGHT = 0.25;

    public LifePreserverSeat(EntityType<? extends LifePreserverSeat> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public LifePreserverSeat(Level level, double x, double y, double z) {
        this(CDModEntities.LIFE_PRESERVER_SEAT.get(), level);
        this.setPos(x, y, z);
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float partialTick) {
        return new Vec3(0, PASSENGER_HEIGHT, 0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
    }
}