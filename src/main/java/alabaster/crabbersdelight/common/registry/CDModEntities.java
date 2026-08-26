package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.entity.LifePreserverSeat;
import alabaster.crabbersdelight.common.entity.ThrownBottledNote;
import alabaster.crabbersdelight.common.fishingspot.FishingSpotEntity;
import alabaster.crabbersdelight.common.entity.boat.CDBoatEntity;
import alabaster.crabbersdelight.common.entity.boat.CDChestBoatEntity;
import alabaster.crabbersdelight.common.entity.crab.CrabEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CDModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, CrabbersDelight.MODID);

    public static final Supplier<EntityType<CrabEntity>> CRAB =
            ENTITY_TYPES.register("crab",
                    () -> EntityType.Builder.of(CrabEntity::new, MobCategory.CREATURE).sized(0.7f, 0.7f).build("crab"));

    public static final Supplier<EntityType<CDBoatEntity>> MOD_BOAT =
            ENTITY_TYPES.register("mod_boat", () -> EntityType.Builder.<CDBoatEntity>of(CDBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("palm_boat"));
    public static final Supplier<EntityType<CDChestBoatEntity>> MOD_CHEST_BOAT =
            ENTITY_TYPES.register("mod_chest_boat", () -> EntityType.Builder.<CDChestBoatEntity>of(CDChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("palm_chest_boat"));

    public static final Supplier<EntityType<ThrownBottledNote>> THROWN_BOTTLED_NOTE =
            ENTITY_TYPES.register("thrown_bottled_note", () -> EntityType.Builder.<ThrownBottledNote>of(ThrownBottledNote::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build("thrown_bottled_note"));

    public static final Supplier<EntityType<FishingSpotEntity>> FISHING_SPOT =
            ENTITY_TYPES.register("fishing_spot", () -> EntityType.Builder.<FishingSpotEntity>of(FishingSpotEntity::new, MobCategory.MISC)
                    .sized(0.1f, 0.1f).noSave().clientTrackingRange(6).updateInterval(40).build("fishing_spot"));

    public static final Supplier<EntityType<LifePreserverSeat>> LIFE_PRESERVER_SEAT =
            ENTITY_TYPES.register("life_preserver_seat", () -> EntityType.Builder.<LifePreserverSeat>of(LifePreserverSeat::new, MobCategory.MISC)
                    .sized(0.1f, 0.1f).noSave().clientTrackingRange(6).updateInterval(40).build("life_preserver_seat"));
}