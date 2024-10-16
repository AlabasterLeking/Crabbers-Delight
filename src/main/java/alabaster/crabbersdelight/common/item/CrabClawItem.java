package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.common.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class CrabClawItem extends Item {

    public static final int MAX_DAMAGE = 128;

    public static AttributeModifier REACH_MODIFIER;

    public CrabClawItem(Properties properties) {
        super(properties.durability(MAX_DAMAGE));

        REACH_MODIFIER = new AttributeModifier(
            ResourceLocation.parse("crabbersdelight:reach_modifier"),
            3.0, AttributeModifier.Operation.ADD_VALUE
        );
    }

    public static final String CLAW_MARKER = "clawMarker";

    @SubscribeEvent
    public static void extendRange(PlayerTickEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        CompoundTag persistentData = player.getPersistentData();

        boolean clawMainHand = (player.getMainHandItem().is(ModItems.CRAB_CLAW.get()));
        boolean clawOffHand = (player.getOffhandItem().is(ModItems.CRAB_CLAW.get()));
        boolean clawHeld = clawMainHand ^ clawOffHand;
        boolean hadClaw = persistentData.contains(CLAW_MARKER);

        var attribute = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
        if (attribute == null) return;

        if (clawHeld != hadClaw ) {
            if (!attribute.hasModifier(REACH_MODIFIER.id())) {
                attribute.addTransientModifier(REACH_MODIFIER);
            }
        } else {
            if (attribute.hasModifier(REACH_MODIFIER.id())) {
                attribute.removeModifier(REACH_MODIFIER.id());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void consumeOnBreak(BlockEvent.BreakEvent event) {
        damageClaw(event.getPlayer());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void consumeOnPlace(BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player)
            damageClaw((Player) entity);
    }

    @Override
    public boolean hurtEnemy(ItemStack claw, LivingEntity target, LivingEntity attacker) {
        claw.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    private static void damageClaw(Player player) {
        if (player == null)
            return;
        if (player.level().isClientSide)
            return;
        
        ItemStack claw;

        if (player.getOffhandItem().is(ModItems.CRAB_CLAW.get())) {
            claw = player.getOffhandItem();
            
            claw.hurtAndBreak(1, player, EquipmentSlot.OFFHAND);
        }

        if (player.getMainHandItem().is(ModItems.CRAB_CLAW.get())) {
            claw = player.getMainHandItem();
            
            claw.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
        }
    }
}