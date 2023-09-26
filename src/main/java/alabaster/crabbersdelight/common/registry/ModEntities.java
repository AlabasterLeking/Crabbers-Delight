package alabaster.crabbersdelight.common.registry;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.entity.Crab;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CrabbersDelight.MODID);

    public static final RegistryObject<EntityType<Crab>> CRAB = ENTITIES.register("crab",
            () -> EntityType.Builder.<Crab>of(Crab::new, MobCategory.WATER_AMBIENT)
                    .sized(1.0f, 1.0f)
                    .build(new ResourceLocation(CrabbersDelight.MODID, "crab").toString())
    );
}
