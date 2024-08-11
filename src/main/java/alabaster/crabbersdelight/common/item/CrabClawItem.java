package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.common.registry.ModItems;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.UUID;
import java.util.function.Supplier;

@EventBusSubscriber
public class CrabClawItem extends Item {
    public static final int MAX_DAMAGE = 128;

    public static final AttributeModifier rangeAttributeModifier =
            new AttributeModifier(UUID.fromString("7f7dbdb2-0d0d-458a-aa40-ac7633691f66"), "Range Modifier", 3,
                    AttributeModifier.Operation.ADDITION);

    private static final Supplier<Multimap<Attribute, AttributeModifier>> rangeModifier = Suppliers.memoize(() ->
            ImmutableMultimap.of(PlayerTickEvent..get(), rangeAttributeModifier));

    public CrabClawItem(Properties properties) {
        super(properties.durability(MAX_DAMAGE));
    }

    public static final String CLAW_MARKER = "clawMarker";

    @SubscribeEvent
    public static void extendRange(PlayerTickEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        CompoundTag persistentData = player.getPersistentData();

        boolean clawMainHand = (player.getMainHandItem().is(ModItems.CRAB_CLAW.get()));
        boolean clawOffHand = (player.getOffhandItem().is(ModItems.CRAB_CLAW.get()));
        boolean clawHeld = clawMainHand ^ clawOffHand;
        boolean hadClaw = persistentData.contains(CLAW_MARKER);

        if (clawHeld != hadClaw ) {
            if (!clawHeld) {
                player.getAttributes()
                        .removeAttributeModifiers(rangeModifier.get());
                persistentData.remove(CLAW_MARKER);
            } else {
                player.getAttributes()
                        .addTransientAttributeModifiers(rangeModifier.get());
                persistentData.putBoolean(CLAW_MARKER, true);
            }
        }
    }

    @SubscribeEvent
    public static void adjustReachOnJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        CompoundTag persistentData = player.getPersistentData();

        if (persistentData.contains(CLAW_MARKER))
            player.getAttributes()
                    .addTransientAttributeModifiers(rangeModifier.get());
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
        claw.hurtAndBreak(1, attacker, (user) -> user(EquipmentSlot.MAINHAND));
        return true;
    }

    private static void damageClaw(Player player) {
        if (player == null)
            return;
        if (player.level().isClientSide)
            return;

        InteractionHand hand;
        ItemStack claw;

        if (player.getOffhandItem().is(ModItems.CRAB_CLAW.get())) {
            hand = InteractionHand.OFF_HAND;
            claw = player.getOffhandItem();

            final InteractionHand h = hand;
            claw.hurtAndBreak(1, player, user -> user.broadcastBreakEvent(h));
        }

        if (player.getMainHandItem().is(ModItems.CRAB_CLAW.get())) {
            hand = InteractionHand.MAIN_HAND;
            claw = player.getMainHandItem();

            final InteractionHand h = hand;
            claw.hurtAndBreak(1, player, user -> user.broadcastBreakEvent(h));
        }
    }
}