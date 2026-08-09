package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.common.block.entity.CrabTrapBlockEntity;
import alabaster.crabbersdelight.common.block.entity.inventory.CrabTrapItemHandler;
import alabaster.crabbersdelight.common.item.component.CrabTrapContents;
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

import java.util.List;

public class CrabTrapItem extends BlockItem {
    public CrabTrapItem(Item.Properties properties) {
        super(CDModBlocks.CRAB_TRAP.get(), properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        CrabTrapContents contents = context.getItemInHand().get(CDModDataComponents.CRAB_TRAP_CONTENTS.get());
        BlockPlaceContext placeContext = new BlockPlaceContext(context);
        InteractionResult result = place(placeContext);
        Level level = context.getLevel();
        if (result.consumesAction() && !level.isClientSide) {
            BlockPos placedPos = placeContext.getClickedPos();
            if (contents != null && level.getBlockEntity(placedPos) instanceof CrabTrapBlockEntity crabTrap) {
                CrabTrapItemHandler handler = crabTrap.getInventory();
                List<ItemStack> items = contents.items();
                for (int i = 0; i < handler.getSlots() && i < items.size(); i++) {
                    handler.setStackInSlot(i, items.get(i));
                }
                crabTrap.setChanged();
            }
        }
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        CrabTrapContents contents = stack.get(CDModDataComponents.CRAB_TRAP_CONTENTS.get());
        if (contents == null) {
            return;
        }
        List<ItemStack> items = contents.items();

        if (!items.isEmpty() && !items.get(0).isEmpty()) {
            tooltip.add(Component.translatable("item.crabbersdelight.crab_trap.bait", items.get(0).getHoverName())
                    .withStyle(ChatFormatting.GRAY));
        }

        for (int i = 1; i < items.size(); i++) {
            ItemStack item = items.get(i);
            if (item.isEmpty()) {
                continue;
            }
            tooltip.add(Component.literal(item.getCount() + "x ").append(item.getHoverName())
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}