package alabaster.crabbersdelight.common.event;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.entity.CrabEntity;
import alabaster.crabbersdelight.common.registry.ModEntities;
import alabaster.crabbersdelight.common.registry.ModPotions;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CRAB.get(), CrabEntity.setAttributes());
    }

    //@SubscribeEvent
    //public static void registerSpawnPlacements(EntityAttributeCreationEvent event) {
    //    entityAttributeEvent (
    //            ModEntities.CRAB.get(),
    //            SpawnPlacements.getPlacementType()
    //            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
    //            CrabEntity::checkSpawnRules,
    //            MobSpawnEvent.SpawnPlacementCheck.Result.
     //   );
    //}
}
