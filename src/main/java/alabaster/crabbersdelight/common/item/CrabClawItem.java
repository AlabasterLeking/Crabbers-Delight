package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.common.registry.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.TickEvent;

@Mod.EventBusSubscriber
public class CrabClawItem extends ShearsItem {

    public static final int MAX_DAMAGE = 128;

    private static final AttributeModifier BLOCK_REACH_MOD =
            new AttributeModifier("crabbersdelight:claw_block_reach",
                    3.0, AttributeModifier.Operation.ADDITION);

    private static final AttributeModifier ENTITY_REACH_MOD =
            new AttributeModifier("crabbersdelight:claw_entity_reach",
                    3.0, AttributeModifier.Operation.ADDITION);

    public CrabClawItem(Properties properties) {
        super(properties.durability(MAX_DAMAGE));
    }

    @SubscribeEvent
    public static void extendRange(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        Player player = event.player;

        boolean main = player.getMainHandItem().is(ModItems.CRAB_CLAW.get());
        boolean off  = player.getOffhandItem().is(ModItems.CRAB_CLAW.get());
        boolean holdingExactlyOne = main ^ off;

        applyModifier(player.getAttribute(ForgeMod.BLOCK_REACH.get()), BLOCK_REACH_MOD, holdingExactlyOne);
        applyModifier(player.getAttribute(ForgeMod.ENTITY_REACH.get()), ENTITY_REACH_MOD, holdingExactlyOne);
    }

    private static void applyModifier(AttributeInstance attr, AttributeModifier mod, boolean active) {
        if (attr == null) return;

        if (active) {
            if (attr.getModifier(mod.getId()) == null) { // not present
                attr.addTransientModifier(mod);
            }
        } else {
            if (attr.getModifier(mod.getId()) != null) { // present
                attr.removeModifier(mod.getId());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void consumeOnBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        double dist = player.getEyePosition().distanceTo(event.getPos().getCenter());
        if (dist > getVanillaBlockReach(player)) {
            damageClaws(player);
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
        AttributeInstance inst = player.getAttribute(ForgeMod.BLOCK_REACH.get());
        if (inst == null) return 4.5D; // Vanilla default reach distance
        double val = inst.getValue();
        if (inst.hasModifier(BLOCK_REACH_MOD)) {
            val -= BLOCK_REACH_MOD.getAmount();
        }
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
        AttributeInstance attr = player.getAttribute(ForgeMod.ENTITY_REACH.get());
        if (attr == null) return false;

        double reach = attr.getValue();
        if (attr.hasModifier(ENTITY_REACH_MOD)) reach -= ENTITY_REACH_MOD.getAmount();
        return dist > reach;
    }

    @Override
    public boolean hurtEnemy(ItemStack claw, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player) {
            Player player = (Player) attacker;
            if (!player.isCreative()) {
                if (beyondBaseEntityReach(player, target)) {
                    claw.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }
                return true;
            }
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
        boolean clawMain = main.is(ModItems.CRAB_CLAW.get());
        boolean clawOff = off.is(ModItems.CRAB_CLAW.get());

        if (!clawMain && !clawOff) return;

        if (beyondBaseEntityReach(player, target)) {
            if (clawMain) main.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            if (clawOff) off.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.OFFHAND));
        }
    }

    private static void damageClaws(Player player) {
        if (player == null || player.level().isClientSide) return;
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        if (main.is(ModItems.CRAB_CLAW.get())) main.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if (off.is(ModItems.CRAB_CLAW.get())) off.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.OFFHAND));
    }
}
