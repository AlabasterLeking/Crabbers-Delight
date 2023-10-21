package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.entity.CrabEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            CrabbersDelight.MODID);

    public static final RegistryObject<EntityType<CrabEntity>> CRAB = ENTITIES.register("crab",
            () -> EntityType.Builder.of(CrabEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(.7f, .7f)
                    .build(new ResourceLocation(CrabbersDelight.MODID, "crab").toString()));

    public static <T extends Mob> RegistryObject<EntityType<T>> registerMob(
            String name, EntityType.EntityFactory<T> entity, float width, float height, int primaryEggColor, int secondaryEggColor) {
        RegistryObject<EntityType<T>> entityType = ENTITIES.register(name,
                () -> EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).build(name));

        return entityType;
    }
}
