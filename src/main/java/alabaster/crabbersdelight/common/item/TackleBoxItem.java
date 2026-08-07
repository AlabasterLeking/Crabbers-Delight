package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.common.block.entity.TackleBoxBlockEntity;
import alabaster.crabbersdelight.common.block.entity.inventory.TackleBoxItemHandler;
import alabaster.crabbersdelight.common.item.component.TackleBoxContents;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class TackleBoxItem extends BlockItem {
    public TackleBoxItem(Item.Properties properties) {
        super(CDModBlocks.TACKLE_BOX.get(), properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        TackleBoxContents contents = context.getItemInHand().get(CDModDataComponents.TACKLE_BOX_CONTENTS.get());
        BlockPlaceContext placeContext = new BlockPlaceContext(context);
        InteractionResult result = place(placeContext);
        Level level = context.getLevel();
        if (result.consumesAction() && !level.isClientSide) {
            BlockPos placedPos = placeContext.getClickedPos();
            if (contents != null && level.getBlockEntity(placedPos) instanceof TackleBoxBlockEntity tackleBox) {
                List<ItemStack> items = contents.items();
                for (int i = 0; i < tackleBox.handler.getSlots() && i < items.size(); i++) {
                    tackleBox.handler.setStackInSlot(i, items.get(i));
                }
                tackleBox.setChanged();
            }
        }
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        TackleBoxContents contents = stack.get(CDModDataComponents.TACKLE_BOX_CONTENTS.get());
        if (contents == null) {
            return;
        }
        List<ItemStack> items = contents.items();

        addSlotLine(tooltip, items, TackleBoxItemHandler.LURE_SLOT, "item.crabbersdelight.tackle_box.lure");
        addSlotLine(tooltip, items, TackleBoxItemHandler.LINE_SLOT, "item.crabbersdelight.tackle_box.line");
        addBaitLine(tooltip, items);
    }

    private static void addSlotLine(List<Component> tooltip, List<ItemStack> items, int slot, String translationKey) {
        if (slot >= items.size()) {
            return;
        }
        ItemStack item = items.get(slot);
        if (!item.isEmpty()) {
            tooltip.add(Component.translatable(translationKey, item.getHoverName()).withStyle(ChatFormatting.GRAY));
        }
    }

    private static void addBaitLine(List<Component> tooltip, List<ItemStack> items) {
        List<String> baitNames = new ArrayList<>();
        for (int slot : new int[]{TackleBoxItemHandler.BAIT_SLOT_1, TackleBoxItemHandler.BAIT_SLOT_2}) {
            if (slot < items.size() && !items.get(slot).isEmpty()) {
                baitNames.add(items.get(slot).getHoverName().getString());
            }
        }
        if (!baitNames.isEmpty()) {
            tooltip.add(Component.translatable("item.crabbersdelight.tackle_box.bait", String.join(", ", baitNames))
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}