package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.entity.CrabEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE,
            CrabbersDelight.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<CrabEntity>> CRAB = ENTITIES.register("crab",
            () -> EntityType.Builder.of(CrabEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(.7f, .7f)
                    .build(new ResourceLocation(CrabbersDelight.MODID, "crab").toString()));

    public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> register(
            String name, EntityType.EntityFactory<T> entity, float width, float height, int primaryEggColor, int secondaryEggColor) {
                return ENTITIES.register(name, () -> EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).build(name));
    }
}
