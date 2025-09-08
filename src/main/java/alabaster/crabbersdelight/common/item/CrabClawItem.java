package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.common.registry.CDModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class CrabClawItem extends ShearsItem {

    public static final int MAX_DAMAGE = 128;

    private static final AttributeModifier BLOCK_REACH_MOD =
            new AttributeModifier(ResourceLocation.parse("crabbersdelight:claw_block_reach"),
                    3.0, AttributeModifier.Operation.ADD_VALUE);

    private static final AttributeModifier ENTITY_REACH_MOD =
            new AttributeModifier(ResourceLocation.parse("crabbersdelight:claw_entity_reach"),
                    3.0, AttributeModifier.Operation.ADD_VALUE);

    public CrabClawItem(Properties properties) {
        super(properties.durability(MAX_DAMAGE));
    }

    @SubscribeEvent
    public static void extendRange(PlayerTickEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player)) return;

        boolean main = player.getMainHandItem().is(CDModItems.CRAB_CLAW.get());
        boolean off  = player.getOffhandItem().is(CDModItems.CRAB_CLAW.get());
        boolean holdingExactlyOne = main ^ off;

        applyModifier(player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE), BLOCK_REACH_MOD, holdingExactlyOne);
        applyModifier(player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE), ENTITY_REACH_MOD, holdingExactlyOne);
    }

    private static void applyModifier(AttributeInstance attr, AttributeModifier mod, boolean active) {
        if (attr == null) return;
        if (active) {
            if (!attr.hasModifier(mod.id())) attr.addTransientModifier(mod);
        } else {
            if (attr.hasModifier(mod.id())) attr.removeModifier(mod.id());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void consumeOnBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof Player player) {
            double dist = player.getEyePosition().distanceTo(event.getPos().getCenter());
            if (dist > getVanillaBlockReach(player)) {
                damageClaws(player);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void consumeOnPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player) {
            double dist = player.getEyePosition().distanceTo(event.getPos().getCenter());
            if (dist > getVanillaBlockReach(player)) {
                damageClaws(player);
            }
        }
    }

    private static double getVanillaBlockReach(Player player) {
        var inst = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
        if (inst == null) return player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE).getBaseValue();
        double val = inst.getValue();
        if (inst.hasModifier(BLOCK_REACH_MOD.id())) val -= BLOCK_REACH_MOD.amount();
        return val;
    }

    private static double distanceEyeToAABB(Player player, LivingEntity target) {
        Vec3 eye = player.getEyePosition();
        var box = target.getBoundingBox();

        double x = Math.max(box.minX, Math.min(eye.x, box.maxX));
        double y = Math.max(box.minY, Math.min(eye.y, box.maxY));
        double z = Math.max(box.minZ, Math.min(eye.z, box.maxZ));

        return eye.distanceTo(new Vec3(x, y, z));
    }

    private static boolean beyondBaseEntityReach(Player player, LivingEntity target) {
        double dist = distanceEyeToAABB(player, target);
        var attr = player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
        if (attr == null) return false;

        double reach = attr.getValue();
        if (attr.hasModifier(ENTITY_REACH_MOD.id())) reach -= ENTITY_REACH_MOD.amount();
        return dist > reach;
    }

    @Override
    public boolean hurtEnemy(ItemStack claw, LivingEntity target, LivingEntity attacker) {
        // Only handles mainhand shears attacks
        if (attacker instanceof Player player && !player.isCreative()) {
            if (beyondBaseEntityReach(player, target)) {
                claw.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
            return true;
        }
        return super.hurtEnemy(claw, target, attacker);
    }

    @SubscribeEvent
    public static void handleAttackEntity(AttackEntityEvent event) {
        Entity attackerEntity = event.getEntity();
        if (!(attackerEntity instanceof Player player)) return;
        if (player.level().isClientSide) return;

        Entity targetEntity = event.getTarget();
        if (!(targetEntity instanceof LivingEntity target)) return;

        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        boolean clawMain = main.is(CDModItems.CRAB_CLAW.get());
        boolean clawOff = off.is(CDModItems.CRAB_CLAW.get());

        if (!clawMain && !clawOff) return;

        if (beyondBaseEntityReach(player, target)) {
            if (clawMain) main.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            if (clawOff) off.hurtAndBreak(1, player, EquipmentSlot.OFFHAND);
        }
    }

    private static void damageClaws(Player player) {
        if (player == null || player.level().isClientSide) return;
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        if (main.is(CDModItems.CRAB_CLAW.get())) main.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
        if (off.is(CDModItems.CRAB_CLAW.get())) off.hurtAndBreak(1, player, EquipmentSlot.OFFHAND);
    }
}
