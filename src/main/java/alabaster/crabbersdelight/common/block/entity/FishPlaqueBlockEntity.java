package alabaster.crabbersdelight.common.block.entity;

import alabaster.crabbersdelight.common.registry.CDModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FishPlaqueBlockEntity extends BlockEntity {

    @Nullable private EntityType<?> entityType = null;
    private CompoundTag entityData = new CompoundTag();

    // Incremented each time data changes so the renderer knows to recreate the cached entity
    private int dataVersion = 0;

    public FishPlaqueBlockEntity(BlockPos pos, BlockState state) {
        super(CDModBlockEntity.FISH_PLAQUE.get(), pos, state);
    }

    // ── Data accessors ─────────────────────────────────────────────────────────

    public boolean hasFish() {
        return entityType != null;
    }

    @Nullable
    public EntityType<?> getEntityType() {
        return entityType;
    }

    public CompoundTag getEntityData() {
        return entityData;
    }

    public int getDataVersion() {
        return dataVersion;
    }

    public void setFishData(EntityType<?> type, CompoundTag data) {
        this.entityType = type;
        this.entityData = data != null ? data : new CompoundTag();
        this.dataVersion++;
        setChanged();
    }

    public void clearFish() {
        this.entityType = null;
        this.entityData = new CompoundTag();
        this.dataVersion++;
        setChanged();
    }

    // ── NBT save/load ──────────────────────────────────────────────────────────

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (entityType != null) {
            tag.putString("EntityType", BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
            if (!entityData.isEmpty()) {
                tag.put("EntityData", entityData.copy());
            }
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("EntityType")) {
            ResourceLocation rl = ResourceLocation.parse(tag.getString("EntityType"));
            this.entityType = BuiltInRegistries.ENTITY_TYPE.getOptional(rl).orElse(null);
            this.entityData = tag.contains("EntityData") ? tag.getCompound("EntityData").copy() : new CompoundTag();
            this.dataVersion++;
        } else {
            this.entityType = null;
            this.entityData = new CompoundTag();
        }
    }

    // ── Client sync ────────────────────────────────────────────────────────────

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}