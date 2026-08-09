package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.common.item.fishing.LureEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class LureItem extends Item {
    private final LureEffect effect;

    public LureItem(Item.Properties properties, LureEffect effect) {
        super(properties);
        this.effect = effect;
    }

    public LureEffect getEffect() {
        return effect;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(descriptionKey()).withStyle(ChatFormatting.GRAY));
    }

    private String descriptionKey() {
        return switch (effect) {
            case BARBED -> "tooltip.crabbersdelight.barbed_lure.desc";
            case DOUBLE -> "tooltip.crabbersdelight.double_lure.desc";
            case SHINY -> "tooltip.crabbersdelight.shiny_lure.desc";
            case AUTOMATIC -> "tooltip.crabbersdelight.automatic_lure.desc";
            case STORM -> "tooltip.crabbersdelight.storm_lure.desc";
        };
    }
}