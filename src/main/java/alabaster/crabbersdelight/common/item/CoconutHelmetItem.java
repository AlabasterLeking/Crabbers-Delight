package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.common.registry.CDModItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class CoconutHelmetItem extends Item {
    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("1e6e6f80-bdf7-4d57-b010-22a9cb3b4f2c");
    private static final ResourceLocation ARMOR_MODIFIER_ID =
            new ResourceLocation("crabbersdelight", "coconut_helmet_armor");
    private static final double ARMOR_VALUE = 3.0;
    private static final int DAMAGE_INTERVAL_TICKS = 600; // 30 s
    private static final int EFFECT_DURATION = 10;         // ticks

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public CoconutHelmetItem(Properties properties) {
        super(properties.durability(64));

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ARMOR, new AttributeModifier(
                ARMOR_MODIFIER_UUID, "Coconut armor", ARMOR_VALUE, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);

        if (head.isEmpty()) {
            player.setItemSlot(EquipmentSlot.HEAD, stack.copy());
            if (!player.isCreative()) stack.shrink(1);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ARMOR_EQUIP_TURTLE, player.getSoundSource(), 1.0F, 1.0F);
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairCandidate) {
        return repairCandidate.is(CDModItems.COCONUT_HALVE.get());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.HEAD ? this.defaultModifiers : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        if (level.isClientSide) return;
        if (!(entity instanceof Player player)) return;
        if (player.getItemBySlot(EquipmentSlot.HEAD) != stack) return;

        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, EFFECT_DURATION, 0, false, false, true));

        if (level.getGameTime() % DAMAGE_INTERVAL_TICKS == 0) {
            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.HEAD));
        }
    }
}
