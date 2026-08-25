package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.common.registry.CDModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.List;

public class LifePreserverItem extends BlockItem {
    public LifePreserverItem(Properties properties) {
        super(CDModBlocks.LIFE_PRESERVER.get(), properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (!player.canUseSlot(EquipmentSlot.LEGS)) {
            return InteractionResultHolder.pass(heldStack);
        }

        ItemStack currentLegs = player.getItemBySlot(EquipmentSlot.LEGS);
        if (currentLegs.is(this)) {
            return InteractionResultHolder.pass(heldStack);
        }
        if (EnchantmentHelper.has(currentLegs, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE) && !player.isCreative()) {
            return InteractionResultHolder.fail(heldStack);
        }

        if (!level.isClientSide()) {
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        player.setItemSlot(EquipmentSlot.LEGS, new ItemStack(this));
        if (!player.isCreative()) {
            heldStack.shrink(1);
        }

        if (!currentLegs.isEmpty() && !player.getInventory().add(currentLegs)) {
            player.drop(currentLegs, false);
        }

        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.crabbersdelight.life_preserver.desc").withStyle(ChatFormatting.GRAY));
    }
}