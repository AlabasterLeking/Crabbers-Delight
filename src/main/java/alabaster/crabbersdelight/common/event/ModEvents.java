package alabaster.crabbersdelight.common.event;

import alabaster.crabbersdelight.CrabbersDelight;
import alabaster.crabbersdelight.common.registry.ModPotions;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = CrabbersDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    //@SubscribeEvent
    //public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
     //   event.put(ModEntities.CRAB.get(), CrabEntity.setAttributes());
    //}

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.INK_SAC, ModPotions.INKY_POTION);
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
