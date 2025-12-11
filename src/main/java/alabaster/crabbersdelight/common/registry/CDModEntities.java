package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.entity.boat.CDBoatEntity;
import alabaster.crabbersdelight.common.entity.boat.CDChestBoatEntity;
import alabaster.crabbersdelight.common.entity.crab.CrabEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CDModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            CrabbersDelight.MODID);

    public static final RegistryObject<EntityType<CrabEntity>> CRAB = ENTITIES.register("crab",
            () -> EntityType.Builder.of(CrabEntity::new, MobCategory.CREATURE)
                    .sized(.7f, .7f)
                    .build(new ResourceLocation(CrabbersDelight.MODID, "crab").toString()));

    public static final Supplier<EntityType<CDBoatEntity>> MOD_BOAT =
            ENTITIES.register("mod_boat", () -> EntityType.Builder.<CDBoatEntity>of(CDBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("palm_boat"));
    public static final Supplier<EntityType<CDChestBoatEntity>> MOD_CHEST_BOAT =
            ENTITIES.register("mod_chest_boat", () -> EntityType.Builder.<CDChestBoatEntity>of(CDChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("palm_chest_boat"));

    public static <T extends Mob> RegistryObject<EntityType<T>> registerMob(
            String name, EntityType.EntityFactory<T> entity, float width, float height, int primaryEggColor, int secondaryEggColor) {
        RegistryObject<EntityType<T>> entityType = ENTITIES.register(name,
                () -> EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).build(name));

        return entityType;
    }
}
