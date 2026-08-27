package alabaster.crabbersdelight.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ConchItem extends Item {
    private static final int COOLDOWN_TICKS = 100;

    public ConchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.pass(stack);
        }

        if (!level.isClientSide) {
            LuckTier tier = LuckTier.fromLuck(player.getLuck());
            player.displayClientMessage(tier.randomMessage(player.getRandom()), true);
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.crabbersdelight.conch.desc").withStyle(ChatFormatting.GRAY));
    }

    private enum LuckTier {
        VERY_BAD("very_bad", 3),
        BAD("bad", 3),
        NEUTRAL("neutral", 3),
        GOOD("good", 3),
        VERY_GOOD("very_good", 3);

        private final String keySuffix;
        private final int messageCount;

        LuckTier(String keySuffix, int messageCount) {
            this.keySuffix = keySuffix;
            this.messageCount = messageCount;
        }

        Component randomMessage(RandomSource random) {
            int index = random.nextInt(messageCount) + 1;
            return Component.translatable("message.crabbersdelight.conch." + keySuffix + "." + index)
                    .withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC);
        }

        static LuckTier fromLuck(float luck) {
            if (luck <= -0.5F) {
                return VERY_BAD;
            } else if (luck < -0.05F) {
                return BAD;
            } else if (luck <= 0.05F) {
                return NEUTRAL;
            } else if (luck < 0.5F) {
                return GOOD;
            } else {
                return VERY_GOOD;
            }
        }
    }
}