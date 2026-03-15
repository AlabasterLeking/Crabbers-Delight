package alabaster.crabbersdelight.common.block.entity;

import alabaster.crabbersdelight.common.registry.CDModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
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

    private int dataVersion = 0;

    public FishPlaqueBlockEntity(BlockPos pos, BlockState state) {
        super(CDModBlockEntity.FISH_PLAQUE.get(), pos, state);
    }

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
        this.dataVersion++;

        if (tag.contains("EntityType")) {
            ResourceLocation rl = ResourceLocation.parse(tag.getString("EntityType"));
            this.entityType = BuiltInRegistries.ENTITY_TYPE.getOptional(rl).orElse(null);
            this.entityData = tag.contains("EntityData") ? tag.getCompound("EntityData").copy() : new CompoundTag();
        } else {
            this.entityType = null;
            this.entityData = new CompoundTag();
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        if (entityType != null) {
            tag.putString("EntityType", BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
            if (!entityData.isEmpty()) {
                tag.put("EntityData", entityData.copy());
            }
        }
        return tag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            loadAdditional(tag, lookupProvider);
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        loadAdditional(tag, registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}