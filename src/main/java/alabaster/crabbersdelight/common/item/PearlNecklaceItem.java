package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.common.registry.CDArmorMaterials;
import alabaster.crabbersdelight.common.registry.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

public class PearlNecklaceItem extends ArmorItem {

    private static final int TICK_INTERVAL = 100; // 5 seconds
    private static final int EFFECT_DURATION = 10; // ticks

    public PearlNecklaceItem(Properties properties) {
        super(CDArmorMaterials.PEARL_NECKLACE, Type.CHESTPLATE, properties.durability(128));
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairCandidate) {
        return repairCandidate.is(ModItems.PEARL.get());
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        if (world.isClientSide) return;
        if (!(entity instanceof Player)) return;

        Player player = (Player) entity;

        if (player.getItemBySlot(EquipmentSlot.CHEST) != stack) return;

        if (player.isInWaterRainOrBubble()) {
            player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, EFFECT_DURATION, 0, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, EFFECT_DURATION, 0, false, false, true));

            if (world.getGameTime() % TICK_INTERVAL == 0) {
                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.CHEST));
            }
        }
    }
}
