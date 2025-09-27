package alabaster.crabbersdelight.common.entity.boat;

import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.registry.CDModEntities;
import alabaster.crabbersdelight.common.registry.CDModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.function.IntFunction;

public class CDBoatEntity extends Boat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE =
            SynchedEntityData.defineId(CDBoatEntity.class, EntityDataSerializers.INT);

    public CDBoatEntity(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    public CDBoatEntity(Level level, double x, double y, double z) {
        this(CDModEntities.MOD_BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public Item getDropItem() {
        return switch (getModVariant()) {
            case PALM -> CDModItems.PALM_BOAT.get();
        };
    }

    public void setVariant(CDBoatEntity.Type pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    public Type getModVariant() {
        return Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE, Type.PALM.ordinal());
    }

    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putString("Type", this.getModVariant().getSerializedName());
    }

    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains("Type", 8)) {
            this.setVariant(Type.byName(compoundTag.getString("Type")));
        }
    }

    public static enum Type implements StringRepresentable {
        PALM(CDModBlocks.PALM_PLANKS.get(), "palm");

        private final String name;
        private final Block planks;
        public static final StringRepresentable.EnumCodec<CDBoatEntity.Type> CODEC = StringRepresentable.fromEnum(CDBoatEntity.Type::values);
        private static final IntFunction<CDBoatEntity.Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

        private Type(Block planks, String name) {
            this.name = name;
            this.planks = planks;
        }

        public String getSerializedName() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }

        public Block getPlanks() {
            return this.planks;
        }

        public String toString() {
            return this.name;
        }

        public static CDBoatEntity.Type byId(int id) {
            return BY_ID.apply(id);
        }

        public static CDBoatEntity.Type byName(String name) {
            return CODEC.byName(name, PALM);
        }
    }
}