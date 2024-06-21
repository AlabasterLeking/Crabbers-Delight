package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.common.registry.ModItems;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;
import java.util.function.Supplier;

@Mod.EventBusSubscriber
public class CrabClawItem extends Item {
    public static final int MAX_DAMAGE = 128;

    public static final AttributeModifier rangeAttributeModifier =
            new AttributeModifier(UUID.fromString("7f7dbdb2-0d0d-458a-aa40-ac7633691f66"), "Range Modifier", 3,
                    AttributeModifier.Operation.ADDITION);

    private static final Supplier<Multimap<Attribute, AttributeModifier>> rangeModifier = Suppliers.memoize(() ->
            ImmutableMultimap.of(ForgeMod.BLOCK_REACH.get(), rangeAttributeModifier));

    public CrabClawItem(Properties properties) {
        super(properties.defaultDurability(MAX_DAMAGE));
    }

    public static final String CLAW_HELD = "clawHeld";

    @SubscribeEvent
    public static void extendRange(LivingTickEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        CompoundTag persistentData = player.getPersistentData();

        boolean clawMainHand = (player.getMainHandItem().is(ModItems.CRAB_CLAW.get()));
        boolean clawOffHand = (player.getOffhandItem().is(ModItems.CRAB_CLAW.get()));
        boolean clawHeld = (clawMainHand && clawOffHand);
        boolean hadClaw = persistentData.contains(CLAW_HELD);

        if (clawHeld != hadClaw ) {
            if (!clawMainHand || !clawOffHand) {
                player.getAttributes()
                        .removeAttributeModifiers(rangeModifier.get());
                persistentData.remove(CLAW_HELD);
            } else {
                player.getAttributes()
                        .addTransientAttributeModifiers(rangeModifier.get());
                persistentData.putBoolean(CLAW_HELD, true);
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

    private static void damageClaw(Player player) {
        if (player == null)
            return;
        if (player.level().isClientSide)
            return;

        InteractionHand hand = InteractionHand.MAIN_HAND;
        ItemStack claw = player.getMainHandItem();

        if (!(player.getMainHandItem().is(ModItems.CRAB_CLAW.get()))) {
            hand = InteractionHand.OFF_HAND;
            claw = player.getOffhandItem();
        }

        final InteractionHand h = hand;
        claw.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(h));
    }
}