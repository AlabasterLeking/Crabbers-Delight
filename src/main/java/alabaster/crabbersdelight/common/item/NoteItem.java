package alabaster.crabbersdelight.common.item;

import alabaster.crabbersdelight.client.gui.NoteClientHandler;
import alabaster.crabbersdelight.common.block.entity.NoteBlockEntity;
import alabaster.crabbersdelight.common.item.component.NoteContent;
import alabaster.crabbersdelight.common.registry.CDModBlocks;
import alabaster.crabbersdelight.common.registry.CDModDataComponents;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NoteItem extends BlockItem {
    public NoteItem(Item.Properties properties) {
        super(CDModBlocks.NOTE_BLOCK.get(), properties);
    }

    @Override
    public String getDescriptionId() {
        return Util.makeDescriptionId("item", BuiltInRegistries.ITEM.getKey(this));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Direction face = context.getClickedFace();

        if (!canAttachToWall(context)) {
            if (level.isClientSide) {
                NoteClientHandler.openEditScreen(context.getHand(), currentText(context.getItemInHand()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        BlockPlaceContext placeContext = new BlockPlaceContext(context);
        InteractionResult result = place(placeContext);
        if (result.consumesAction() && !level.isClientSide) {
            BlockPos placedPos = placeContext.getClickedPos();
            if (level.getBlockEntity(placedPos) instanceof NoteBlockEntity note) {
                note.setUnsignedText(currentText(context.getItemInHand()));
                level.sendBlockUpdated(placedPos, note.getBlockState(), note.getBlockState(), Block.UPDATE_ALL);
            }
        }
        return result;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            NoteClientHandler.openEditScreen(hand, currentText(stack));
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    private boolean canAttachToWall(UseOnContext context) {
        Direction face = context.getClickedFace();
        if (!face.getAxis().isHorizontal()) {
            return false;
        }
        Level level = context.getLevel();
        BlockPos wallPos = context.getClickedPos().relative(face.getOpposite());
        return level.getBlockState(wallPos).isFaceSturdy(level, wallPos, face);
    }

    private String currentText(ItemStack stack) {
        return stack.getOrDefault(CDModDataComponents.NOTE_CONTENT.get(), NoteContent.EMPTY).text();
    }
}